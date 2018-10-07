package io.mgba.data.local.converters;

import java.io.File;

import androidx.room.TypeConverter;

public class GameConverter {
    @TypeConverter
    public static File fromStringToFile(String value) {
        return value == null ? null : new File(value);
    }

    @TypeConverter
    public static String fromFileToString(File value) {
        return value == null ? null : value.getAbsolutePath();
    }

}

