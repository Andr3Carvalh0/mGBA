package io.mgba.data

import io.mgba.utilities.Constants.PLATFORM_GBA
import io.mgba.utilities.Constants.PLATFORM_GBC
import java.io.File
import io.mgba.utilities.device.PreferencesManager.GAMES_DIRECTORY
import io.mgba.utilities.device.PreferencesManager.get

/**
 * Handles the fetching/filtering of the supported files for the selected dir.
 */
object FileManager {

    private val TAG = "FileService"

    private val GBC_FILES_SUPPORTED: List<String> = listOf("gba", "gb")
    private val GBA_FILES_SUPPORTED: List<String> = listOf("gbc")
    private var directory: File = File(get(GAMES_DIRECTORY, ""))

    val gameList: List<File> = if (!directory.exists()) emptyList() else fetchGames()
    var path: String
        get() = directory.absolutePath
        set(directory) { FileManager.directory = File(directory) }

    /**
     * Based on the files of the directory, discard the ones we dont need.
     * If the file is a directory or the extension isnt in the FILES_SUPPORTED list, that file
     * isnt valid.
     * @param files the files of the directory
     * @return files that contain gba, gb, gbc extension and arent folders
     */
    private fun filter(files: Array<File>): List<File> {
        return files.toList()
             .filter { f -> !f.isDirectory && (GBA_FILES_SUPPORTED.contains(getFileExtension(f)) || GBC_FILES_SUPPORTED.contains(getFileExtension(f)))  }
             .toList()
    }

    private fun fetchGames(): List<File> = filter(directory.listFiles())

    fun getPlatform(file: File): Int {
        if(GBC_FILES_SUPPORTED.contains(getFileExtension(file))){
            return PLATFORM_GBC
        }

        return PLATFORM_GBA
    }

    /**
     * Gets the file extension.
     * For example. For the file 'a.bc' this method will return 'bc'
     * @param file The file to extract a extension
     * @return the file's extension
     */
    fun getFileExtension(file: File): String {
        val name = file.name

        return if (!name.contains(".")) name.substring(name.length - 3) else name
                .substring(file.name.lastIndexOf("."))
                .substring(1)
                .toLowerCase()

    }

    /**
     * Gets the filename without the extension
     * @param file
     * @return
     */
    fun getFileWithoutExtension(file: File): String {
        val tmp = file.path.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

        return tmp[tmp.size - 1].substring(0, tmp[tmp.size - 1].lastIndexOf("."))
    }
}
