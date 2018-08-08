package io.mgba.data.database.converters

import java.io.File

import androidx.room.TypeConverter

object GameConverter {
    @TypeConverter
    fun fromStringToFile(value: String?): File? {
        return if (value == null) null else File(value)
    }

    @TypeConverter
    fun fromFileToString(value: File?): String? {
        return value?.absolutePath
    }

}
