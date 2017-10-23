package com.nononsenseapps.filepicker.Others;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.nononsenseapps.filepicker.Others.Interfaces.AdapterCallback;
import com.nononsenseapps.filepicker.R;

import java.io.File;

public class FileItemAdapter extends RecyclerView.Adapter<FileItemAdapter.DirViewHolder> {

    private AdapterCallback mCallback;
    private Activity mActivity;

    private SortedList<File> mList = null;

    public FileItemAdapter(@NonNull Activity activity, AdapterCallback mCallback) {
        this.mActivity = activity;
        this.mCallback = mCallback;
    }

    public void setList(@Nullable SortedList<File> list) {
        mList = list;
        notifyDataSetChanged();
    }

    @Override
    public DirViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mActivity).inflate(R.layout.nnf_filepicker_listitem_dir, parent, false);
        return new DirViewHolder(v);

    }

    @Override
    @SuppressWarnings("unchecked")
    public void onBindViewHolder(DirViewHolder vh, int position) {
        mCallback.onDraw(vh, mList.get(position));
    }

    @Override
    public int getItemCount() {
        if (mList == null) {
            return 0;
        }

        return mList.size();
    }

    public class DirViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView icon;
        public TextView text;
        public File file;

        public DirViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            icon = v.findViewById(R.id.item_icon);
            text = v.findViewById(android.R.id.text1);
        }

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            mCallback.onClick(this);
        }

    }
}
