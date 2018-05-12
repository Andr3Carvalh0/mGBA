package io.mgba.Data.Database.Converters;

import android.arch.persistence.room.TypeConverter;

import java.io.File;

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
