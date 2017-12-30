package io.mgba.Data.Database.Converters;

import android.arch.persistence.room.TypeConverter;

import java.io.File;

import io.mgba.Data.Platform;

public class GameConverter {
    @TypeConverter
    public static File fromStringToFile(String value) {
        return value == null ? null : new File(value);
    }

    @TypeConverter
    public static String fromFileToString(File value) {
        return value == null ? null : value.getAbsolutePath();
    }

    @TypeConverter
    public static Platform fromIntToPlatform(Integer value) {
        return value == null ? null : Platform.values()[value];
    }

    @TypeConverter
    public static Integer fromPlatformToInteger(Platform value) {
        return value == null ? null : value.ordinal();
    }

}
