package com.nononsenseapps.filepicker.Controllers.Interfaces;

import android.support.annotation.NonNull;
import java.io.File;

/**
 * An interface for the methods required to handle backend-specific stuff.
 */
public interface FileHandler {

    /**
     * Return true if the path is a directory and not a file.
     *
     * @param path
     */
    boolean isDir(@NonNull final File path);

    /**
     * @param path
     * @return filename of path
     */
    @NonNull
    String getName(@NonNull final File path);

    /**
     * Return the path to the parent directory. Should return the root if
     * from is root.
     *
     * @param from
     */
    @NonNull
    File getParent(@NonNull final File from);

    /**
     * Convert the path to the type used.
     *
     * @param path
     */
    @NonNull
    File getPath(@NonNull final String path);

    /**
     * Get the root path (lowest allowed).
     */
    @NonNull
    File getRoot();

    String getDirFriendlyName(File dir);

    int compareFiles(File lhs, File rhs);

    boolean isItemVisible(File f);

    boolean canGoUp(File mCurrentPath);

    String getFileExtension(File file);

    String transformPath(String absolutePath);

    File getSDCard();

    boolean deviceHasSDCard();
    File getInternalStorage();
}
