package io.mgba.model.io

import com.annimon.stream.Stream
import java.io.File
import java.util.Collections
import java.util.Comparator
import java.util.LinkedList
import io.mgba.data.database.Game
import io.mgba.model.interfaces.IFilesManager
import io.mgba.mgba.Companion.printLog

/**
 * Handles the fetching/filtering of the supported files for the selected dir.
 */
class FilesManager(directory: String) : IFilesManager {

    private var gameDir: File? = null

    override val gameList: List<File>
        get() = if (gameDir == null) LinkedList() else fetchGames()

    override var currentDirectory: String
        get() = gameDir!!.absolutePath
        set(directory) {
            if (directory != "")
                this.gameDir = File(directory)
        }

    init {
        printLog(TAG, "CTOR: $directory")

        if (directory != "")
            this.gameDir = File(directory)
    }

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

    private fun fetchGames(): List<File> {
        val files = gameDir!!.listFiles()

        return filter(files)
    }

    private fun fetchGames(predicate: (File) -> Boolean): List<File> {
        val files = gameDir!!.listFiles()

        return filter(files, predicate)
    }

    override fun getGameList(predicate: (File) -> Boolean): List<File> {
        return if (gameDir == null) LinkedList() else fetchGames(predicate)

    }

    companion object {

        private val GBC_FILES_SUPPORTED: LinkedList<String> = LinkedList()
        private val GBA_FILES_SUPPORTED: LinkedList<String> = LinkedList()
        private val TAG = "FileService"

        init {
            GBA_FILES_SUPPORTED.add("gba")
            GBC_FILES_SUPPORTED.add("gb")
            GBC_FILES_SUPPORTED.add("gbc")
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
}
