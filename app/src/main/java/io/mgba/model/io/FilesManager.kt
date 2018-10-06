package io.mgba.model.io

import com.annimon.stream.Stream
import java.io.File
import java.util.Collections
import java.util.Comparator
import java.util.LinkedList
import io.mgba.data.database.model.Game
import io.mgba.model.interfaces.IFilesManager
import io.mgba.model.system.PreferencesManager
import io.mgba.model.system.PreferencesManager.GAMES_DIRECTORY

/**
 * Handles the fetching/filtering of the supported files for the selected dir.
 */
object FilesManager : IFilesManager {

    private val TAG = "FileService"

    private val GBC_FILES_SUPPORTED: List<String> = listOf("gba", "gb")
    private val GBA_FILES_SUPPORTED: List<String> = listOf("gbc")
    private var directory: File = File(PreferencesManager[GAMES_DIRECTORY, ""])

    override val gameList: List<File> = if (!directory.exists()) LinkedList() else fetchGames()

    override var currentDirectory: String
        get() = directory.absolutePath
        set(directory) { this.directory = File(directory) }

    /**
     * Based on the files of the directory, discard the ones we dont need.
     * If the file is a directory or the extension isnt in the FILES_SUPPORTED list, that file
     * isnt valid.
     * @param files the files of the directory
     * @return files that contain gba, gb, gbc extension and arent folders
     */
    private fun filter(files: Array<File>): List<File> {
        return filter(files) { f -> !f.isDirectory && (GBA_FILES_SUPPORTED.contains(getFileExtension(f)) || GBC_FILES_SUPPORTED.contains(getFileExtension(f))) }
    }

    /**
     * Return a list thats has been filtered based on the @predicate
     * @param list the list to filter
     * @param predicate the predicate to evaluate every element of the list
     * @return a list that was filtered
     */
    private fun filter(list: Array<File>, predicate: (File) -> Boolean): List<File> {
        return Stream.of(*list)
                .filter { predicate.invoke(it) }
                .toList()
    }

    /**
     * Returns a list that has every element sorted with the help of the @param comparator.
     * This method doesnt sort the original list.Instead it returns a new list with every element
     * of the @param listToSort but sorted.
     * @param listToSort the origin list
     * @param comparator the comparator to sort the elements
     * @return a new list with the elements sorted
     */
    private fun sort(listToSort: LinkedList<out Game>, comparator: Comparator<Game>): List<Game> {
        Collections.sort(listToSort, comparator)

        return listToSort
    }

    private fun fetchGames(): List<File> = filter(directory.listFiles())

    private fun fetchGames(predicate: (File) -> Boolean): List<File> = filter(directory.listFiles(), predicate)

    override fun getGameList(predicate: (File) -> Boolean): List<File> {
        return if (!directory.exists()) LinkedList() else fetchGames(predicate)

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
