/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package com.nononsenseapps.filepicker.Views.Fragments;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.FileObserver;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nononsenseapps.filepicker.Controllers.FilePickerController;
import com.nononsenseapps.filepicker.Controllers.Interfaces.OnFilePickedListener;
import com.nononsenseapps.filepicker.Others.Interfaces.AdapterCallback;
import com.nononsenseapps.filepicker.Others.FileItemAdapter;
import com.nononsenseapps.filepicker.Controllers.Interfaces.FileHandler;
import com.nononsenseapps.filepicker.R;
import com.nononsenseapps.filepicker.Views.Fragments.Interfaces.FragmentContract;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;
import androidx.recyclerview.widget.SortedListAdapterCallback;


public class FilePickerFragment extends Fragment implements LoaderManager.LoaderCallbacks<SortedList<File>>, NewItemFragment.OnNewFolderListener, AdapterCallback, FragmentContract{

    // Where to display on open.
    public static final String KEY_START_PATH = "KEY_START_PATH";
    // Used for saving state of current path.
    public static final String KEY_CURRENT_PATH = "KEY_CURRENT_PATH";
    // Used for saving if current dir is sdcard or internal.
    public static final String KEY_CURRENT = "KEY_CURRENT";

    private static List<String> IMAGES_FORMAT;
    private static List<String> AUDIO_FORMAT;
    private static List<String> MOVIE_FORMAT;

    static {
        IMAGES_FORMAT = new LinkedList<>();
        AUDIO_FORMAT = new LinkedList<>();
        MOVIE_FORMAT = new LinkedList<>();

        IMAGES_FORMAT.add("jpg");
        IMAGES_FORMAT.add("png");
        IMAGES_FORMAT.add("tiff");

        AUDIO_FORMAT.add("wav");
        AUDIO_FORMAT.add("mp3");
        AUDIO_FORMAT.add("m4a");

        MOVIE_FORMAT.add("mov");
        MOVIE_FORMAT.add("mp4");
    }

    private static final int DIRECTORY = 1;
    private static final int IMAGE = 2;
    private static final int MOVIE = 3;
    private static final int AUDIO = 4;
    private static final int OTHER = 5;

    private String startPath;
    private boolean onInternalStorage = true;
    private FileHandler mController = null;
    private File mCurrentPath = null;
    private OnFilePickedListener mListener;
    private FileItemAdapter mAdapter = null;
    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private RelativeLayout relativeLayout;
    protected LinearLayoutManager layoutManager;
    protected SortedList mFiles = null;

    protected boolean isLoading = false;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FilePickerFragment() {
        setRetainInstance(true);

    }

    /**
     * Set before making the fragment visible. This method will re-use the existing
     * arguments bundle in the fragment if it exists so extra arguments will not
     * be overwritten. This allows you to set any extra arguments in the fragment
     * constructor if you wish.
     * <p/>
     * The key/value-pairs listed below will be overwritten however.
     *
     * @param startPath path to directory the picker will show upon start
     */
    public void setArgs(@Nullable final String startPath) {
        // There might have been arguments set elsewhere, if so do not overwrite them.
        Bundle b = getArguments();
        if (b == null) {
            b = new Bundle();
        }

        if (startPath != null) {
            b.putString(KEY_START_PATH, startPath);
            this.startPath = startPath;
        }

        setArguments(b);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflateRootView(inflater, container);

        Toolbar toolbar = view.findViewById(R.id.nnf_picker_toolbar);

        if (toolbar != null) setupToolbar(toolbar);

        mController = new FilePickerController(getActivity().getApplicationContext(), new File(startPath));


        relativeLayout = view.findViewById(R.id.relativeLayout);
        fab = view.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(view1 -> {
            if(mListener != null)
                mListener.onFilePicked(mController.transformPath(mCurrentPath.getAbsolutePath()));
        });

        recyclerView = view.findViewById(android.R.id.list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new FileItemAdapter(getActivity(), this);
        recyclerView.setAdapter(mAdapter);

        return view;
    }

    protected View inflateRootView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.nnf_fragment_filepicker, container, false);
    }

    protected void setupToolbar(@NonNull Toolbar toolbar) {
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnFilePickedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnFilePickedListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Only if we have no state
        if (mCurrentPath == null) {
            if (savedInstanceState != null) {
                onInternalStorage = savedInstanceState.getBoolean(KEY_CURRENT);

                String path = savedInstanceState.getString(KEY_CURRENT_PATH);
                if (path != null) {
                    mCurrentPath = mController.getPath(path.trim());
                }
            } else if (getArguments() != null) {

                if (getArguments().containsKey(KEY_START_PATH)) {
                    String path = getArguments().getString(KEY_START_PATH);
                    if (path != null) {
                        File file = mController.getPath(path.trim());
                        if (mController.isDir(file)) {
                            mCurrentPath = file;
                        } else {
                            mCurrentPath = mController.getParent(file);
                        }
                    }
                }
            }
        }

        // If still null
        if (mCurrentPath == null) {
            mCurrentPath = mController.getRoot();
        }

        refresh(mCurrentPath);
    }

    @Override
    public void onSaveInstanceState(Bundle b) {
        super.onSaveInstanceState(b);
        b.putString(KEY_CURRENT_PATH, mCurrentPath.toString());
        b.putBoolean(KEY_CURRENT, onInternalStorage);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void refresh(@NonNull File nextPath) {
        mCurrentPath = nextPath;
        isLoading = true;
        getLoaderManager().restartLoader(0, null, FilePickerFragment.this);

        if(((AppCompatActivity) getActivity()).getSupportActionBar() != null){
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(mController.getDirFriendlyName(mCurrentPath));

        }
    }

    @Override
    public Loader<SortedList<File>> onCreateLoader(final int id, final Bundle args) {
        return getLoader();
    }

    public void onLoadFinished(final Loader<SortedList<File>> loader, final SortedList<File> data) {
        isLoading = false;
        mFiles = data;

        if(data.size() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
            relativeLayout.setVisibility(View.GONE);

            mAdapter.setList(data);
        }else {
            recyclerView.setVisibility(View.GONE);
            relativeLayout.setVisibility(View.VISIBLE);
        }


        // Stop loading now to avoid a refresh clearing the user's selections
        getLoaderManager().destroyLoader(0);
    }

    public void onLoaderReset(final Loader<SortedList<File>> loader) {
        isLoading = false;
    }

    public void goUp() {
        goToDir(mController.getParent(mCurrentPath));
    }

    @NonNull
    Loader<SortedList<File>> getLoader(){
        return new AsyncTaskLoader<SortedList<File>>(getActivity()) {

            FileObserver fileObserver;

            @Override
            public SortedList<File> loadInBackground() {
                File[] listFiles = mCurrentPath.listFiles();
                final int initCap = listFiles == null ? 0 : listFiles.length;

                SortedList files = new SortedList(File.class, new SortedListAdapterCallback<File>(getDummyAdapter()) {
                    @Override
                    public int compare(File lhs, File rhs) {
                        return mController.compareFiles(lhs, rhs);
                    }

                    @Override
                    public boolean areContentsTheSame(File file, File file2) {
                        return file.getAbsolutePath().equals(file2.getAbsolutePath()) && (file.isFile() == file2.isFile());
                    }

                    @Override
                    public boolean areItemsTheSame(File file, File file2) {
                        return areContentsTheSame(file, file2);
                    }
                }, initCap);


                files.beginBatchedUpdates();
                if (listFiles != null) {
                    for (java.io.File f : listFiles) {
                        if (mController.isItemVisible(f)) {
                            files.add(f);
                        }
                    }
                }
                files.endBatchedUpdates();

                return files;
            }

            /**
             * Handles a request to start the Loader.
             */
            @Override
            protected void onStartLoading() {
                super.onStartLoading();

                // handle if directory does not exist. Fall back to root.
                if (mCurrentPath == null || !mCurrentPath.isDirectory()) {
                    mCurrentPath = mController.getRoot();
                }

                // Start watching for changes
                fileObserver = new FileObserver(mCurrentPath.getPath(),
                        FileObserver.CREATE |
                                FileObserver.DELETE
                                | FileObserver.MOVED_FROM | FileObserver.MOVED_TO
                ) {

                    @Override
                    public void onEvent(int event, String path) {
                        // Reload
                        onContentChanged();
                    }
                };
                fileObserver.startWatching();

                forceLoad();
            }

            /**
             * Handles a request to completely reset the Loader.
             */
            @Override
            protected void onReset() {
                super.onReset();

                // Stop watching
                if (fileObserver != null) {
                    fileObserver.stopWatching();
                    fileObserver = null;
                }
            }
        };
    }

    public void goToDir(@NonNull File file) {
        if (!isLoading) {
            refresh(file);
        }
    }

    @Override
    public void onNewFolder(@NonNull String name) {
        File folder = new File(mCurrentPath, name);

        if (folder.mkdir()) {
            refresh(folder);
        } else {
            Toast.makeText(getActivity(), R.string.nnf_create_folder_error, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.filepickermenu, menu);
    }


    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem item = menu.findItem(R.id.switch_sdcard);

        item.setIcon(onInternalStorage ? R.drawable.ic_sd_storage_white_24dp : R.drawable.ic_phone_android_white_24dp);
        item.setVisible(isDeviceCapableOfSDCard());

    }

    private boolean isDeviceCapableOfSDCard(){
        return mController.deviceHasSDCard();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();

        if (i == R.id.action_create_folder) {
            NewFolderFragment.showDialog((getActivity()).getSupportFragmentManager(), FilePickerFragment.this);

            return true;
        }

        if(i == R.id.switch_sdcard){
            if(onInternalStorage)
                goToDir(mController.getSDCard());
            else
                goToDir(mController.getInternalStorage());

            onInternalStorage = !onInternalStorage;
            Objects.requireNonNull(getActivity()).invalidateOptionsMenu();
            return true;
        }

        return true;
    }

    @Override
    public void onClick(FileItemAdapter.DirViewHolder viewHolder) {
        if (mController.isDir(viewHolder.file)) {
            goToDir(viewHolder.file);
        }
    }

    @Override
    public void onDraw(FileItemAdapter.DirViewHolder vh, File file){
        vh.file = file;

        vh.icon.setImageDrawable(getDrawable(categorizeFile(file)));
        vh.text.setText(mController.getName(file));
    }

    public RecyclerView.Adapter getDummyAdapter() {
        return new FileItemAdapter(getActivity(), this);
    }

    private Drawable getDrawable(int category){
        switch (category) {
            case IMAGE:

                return getContext().getDrawable(R.drawable.ic_photo_black_48dp);
            case MOVIE:
                return getContext().getDrawable(R.drawable.ic_movie_black_24dp);
            case AUDIO:
                return getContext().getDrawable(R.drawable.ic_audiotrack_black_24dp);
            case DIRECTORY:
                return getContext().getDrawable(R.drawable.nnf_ic_folder_black_48dp);
            case OTHER:
            default:
                return getContext().getDrawable(R.drawable.ic_android_black_24dp);
        }
    }

    private int categorizeFile(File file){
        if(file.isDirectory())
            return DIRECTORY;

        final String extension = mController.getFileExtension(file);

        if(IMAGES_FORMAT.contains(extension))
            return IMAGE;

        if(AUDIO_FORMAT.contains(extension))
            return AUDIO;

        if(MOVIE_FORMAT.contains(extension))
            return MOVIE;

        return OTHER;
    }

    @Override
    public boolean onBackPress() {
        if(mController.canGoUp(mCurrentPath)){
            goUp();
            return true;
        }

        return false;
    }
}
