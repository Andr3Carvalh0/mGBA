package io.mgba.data.local.model

import java.io.File
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import io.mgba.data.FilesManager
import io.mgba.utilities.Constants.PLATFORM_UNKNOWN
import io.mgba.widgets.RecyclerViewItem

@Entity(tableName = "Games")
class Game : RecyclerViewItem{

    @PrimaryKey
    @ColumnInfo(name = "id")
    lateinit var file: File

    @ColumnInfo
    var name: String? = null
        get() {
            field?.let { return it }
            return FilesManager.getFileWithoutExtension(file)
        }

    @ColumnInfo
    var description: String? = null

    @ColumnInfo
    var released: String? = null

    @ColumnInfo
    var developer: String? = null

    @ColumnInfo
    var genre: String? = null

    @ColumnInfo
    var cover: String? = null

    @ColumnInfo
    var mD5: String? = null

    @ColumnInfo(name = "favourite")
    var isFavourite = false

    @ColumnInfo
    var platform: Int = PLATFORM_UNKNOWN

    constructor()

    @Ignore
    constructor(path: String, name: String, description: String, released: String, developer: String, genre: String, coverURL: String, MD5: String, favourite: Boolean, platform: Int) {
        this.file = File(path)
        this.name = name
        this.description = description
        this.released = released
        this.developer = developer
        this.genre = genre
        this.cover = coverURL
        this.mD5 = MD5
        this.isFavourite = favourite
        this.platform = platform
    }

    @Ignore
    constructor(path: String, platform: Int) {
        this.file = File(path)
        this.platform = platform
    }

    override fun getLetterForItem(): String {
        name?.let { return it.substring(0, 1).toUpperCase() }
        return "?"
    }

}
