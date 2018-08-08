package com.nononsenseapps.filepicker.Controllers;

import android.content.Context;
import android.os.Environment;
import com.nononsenseapps.filepicker.Controllers.Interfaces.FileHandler;
import com.nononsenseapps.filepicker.R;
import org.apache.commons.lang3.StringUtils;
import java.io.File;
import java.util.HashMap;

import androidx.annotation.NonNull;

import static android.os.Environment.getExternalStorageState;

public class FilePickerController implements FileHandler {

    private final File initialDirectory;
    private Context mContext;
    private static final String SEP = File.separator;
    private static final int INTERNAL_STORAGE_SLASHES = 3;
    private HashMap<String, String> blackList;

    public FilePickerController(Context mContext, File initialDirectory) {
        this.mContext = mContext;
        this.initialDirectory = initialDirectory;
        blackList = new HashMap<>();
        blackList.put("/storage/emulated/0", mContext.getString(R.string.internal_storage));
    }

    /**
     * Return true if the path is a directory and not a file.
     *
     * @param path either a file or directory
     * @return true if path is a directory, false if file
     */
    public boolean isDir(@NonNull final File path) {
        return path.isDirectory();
    }

    /**
     * @param path either a file or directory
     * @return filename of path
     */
    @NonNull
    public String getName(@NonNull File path) {
        return path.getName();
    }

    /**
     * Return the path to the parent directory. Should return the root if
     * from is root.
     *
     * @param from either a file or directory
     * @return the parent directory
     */
    @NonNull
    public File getParent(@NonNull final File from) {
        if (from.getPath().equals(initialDirectory.getPath())) {
            // Already at root, we can't go higher
            return from;
        } else if (from.getParentFile() != null) {
            return from.getParentFile();
        } else {
            return from;
        }
    }

    /**
     * Convert the path to the type used.
     *
     * @param path either a file or directory
     * @return File representation of the string path
     */
    @NonNull
    public File getPath(@NonNull final String path) {
        return new File(path);
    }

    /**
     * Get the root path.
     *
     * @return the highest allowed path, which is "/" by default
     */
    @NonNull
    @Override
    public File getRoot() {
        return new File(File.separator);
    }

    @Override
    public String getDirFriendlyName(File dir) {
        String tmp = dir.getPath();

        if(StringUtils.countMatches(tmp, SEP) > INTERNAL_STORAGE_SLASHES){
            String last[] = tmp.split(SEP);
            return last[last.length - 1];
        }

        for (String key : blackList.keySet())
            tmp = tmp.replace(key, blackList.get(key));


        return tmp;
    }

    @Override
    public File getInternalStorage() {
        return initialDirectory;
    }

    /**
     * Compare two files to determine their relative sort order. This follows the usual
     * comparison interface. Override to determine your own custom sort order.
     * <p/>
     * Default behaviour is to place directories before files, but sort them alphabetically
     * otherwise.
     *
     * @param lhs File on the "left-hand side"
     * @param rhs File on the "right-hand side"
     * @return -1 if if lhs should be placed before rhs, 0 if they are equal,
     * and 1 if rhs should be placed before lhs
     */
    @Override
    public int compareFiles(File lhs, File rhs) {
        if (lhs.isDirectory() && !rhs.isDirectory()) {
            return -1;
        } else if (rhs.isDirectory() && !lhs.isDirectory()) {
            return 1;
        } else {
            return lhs.getName().compareToIgnoreCase(rhs.getName());
        }
    }

    @Override
    public boolean isItemVisible(File file) {
        return !file.isHidden();
    }

    @Override
    public boolean canGoUp(File mCurrentPath) {
        return !mCurrentPath.getPath().equals(initialDirectory.getPath());
    }

    @Override
    public String getFileExtension(File file) {
        return file.getName().substring(file.getName().lastIndexOf(".") + 1, file.getName().length());
    }

    @Override
    public String transformPath(String absolutePath) {
        return absolutePath;
    }

    @Override
    public File getSDCard() {
        return new File(Environment.getExternalStorageDirectory().getPath() + "/");
    }

    @Override
    public boolean deviceHasSDCard() {
        return getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED) && Environment.isExternalStorageRemovable();
    }
}
