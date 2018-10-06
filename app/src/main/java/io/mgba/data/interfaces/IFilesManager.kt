package io.mgba.data.interfaces

import java.io.File

interface IFilesManager {
    val gameList: List<File>
    var currentDirectory: String
    fun getGameList(predicate: (File) -> Boolean): List<File>

}
