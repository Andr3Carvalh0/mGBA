package com.nononsenseapps.filepicker.Others.Interfaces;

import com.nononsenseapps.filepicker.Others.FileItemAdapter;
import java.io.File;

public interface AdapterCallback {
    void onDraw(FileItemAdapter.DirViewHolder vh, File file);
    void onClick(FileItemAdapter.DirViewHolder vh);
}
