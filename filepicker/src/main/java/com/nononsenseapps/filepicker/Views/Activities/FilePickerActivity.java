package com.nononsenseapps.filepicker.Views.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import com.nononsenseapps.filepicker.Controllers.Interfaces.OnFilePickedListener;
import com.nononsenseapps.filepicker.R;
import com.nononsenseapps.filepicker.Views.Fragments.FilePickerFragment;
import com.nononsenseapps.filepicker.Views.Fragments.Interfaces.FragmentContract;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class FilePickerActivity extends AppCompatActivity implements OnFilePickedListener {
    public static final String EXTRA_START_PATH = "nononsense.intent" + ".START_PATH";

    protected static final String TAG = "filepicker_fragment";

    private FragmentContract fragment;

    @Override
    @SuppressWarnings("unchecked")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.nnf_activity_filepicker);

        Intent intent = getIntent();
        String startPath = Environment.getExternalStorageDirectory().getPath();

        if (intent != null) {
            startPath = intent.getStringExtra(EXTRA_START_PATH);
        }

        FragmentManager fm = getSupportFragmentManager();
        fragment = (FilePickerFragment) fm.findFragmentByTag(TAG);

        if (fragment == null) {
            fragment = getFragment(startPath);
        }

        if (fragment != null) {
            fm.beginTransaction().replace(R.id.fragment, (FilePickerFragment)fragment, TAG)
                    .commit();
        }

        // Default to cancelled
        setResult(Activity.RESULT_CANCELED);
    }

    protected FragmentContract getFragment(@Nullable final String startPath){
        fragment = new FilePickerFragment();
        ((FilePickerFragment)fragment).setArgs(startPath);
        return fragment;
    }

    @Override
    public void onSaveInstanceState(Bundle b) {
        super.onSaveInstanceState(b);
    }

    @Override
    public void onBackPressed() {

        if(fragment != null){
            if(!fragment.onBackPress())
                onCancelled();
        }
    }

    @Override
    public void onFilePicked(@NonNull final String file) {
        Intent i = new Intent();
        i.putExtra("PATH", file);
        setResult(Activity.RESULT_OK, i);
        finish();
    }

    @Override
    public void onCancelled() {
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
}
