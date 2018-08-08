package com.nononsenseapps.filepicker.Controllers;

import android.content.Intent;
import android.text.TextUtils;
import java.io.File;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Some utility methods
 */
public class FilePickerUtils {

    private static final String SEP = File.separator;
    private static final String DOUBLE_DASH = SEP + SEP;

    /**
     * Name is validated to be non-null, non-empty and not containing any
     * slashes.
     *
     * @param name The name of the folder the user wishes to create.
     */
    public static boolean isValidFileName(@Nullable String name) {
        return !TextUtils.isEmpty(name)
                && !name.contains(SEP)
                && !name.equals(".")
                && !name.equals("..");
    }

    /**
     * Append the second pathString to the first. The result will not end with a /.
     * In case two absolute paths are given, e.g. /A/B/, and /C/D/, then the result
     * will be /A/B/C/D
     *
     * Multiple slashes will be shortened to a single slash, so /A///B is equivalent to /A/B
     */
    @NonNull
    public static String appendPath(@NonNull String first, @NonNull String second) {
        String result = first + SEP + second;

        while (result.contains(DOUBLE_DASH)) {
            result = result.replaceAll(DOUBLE_DASH, SEP);
        }

        if (result.length() > 1 && result.endsWith(SEP)) {
            return result.substring(0, result.length() - 1);
        } else {
            return result;
        }
    }

    public static String getSelectedDir(Intent intent) {
        return intent.getStringExtra("PATH");
    }
}
