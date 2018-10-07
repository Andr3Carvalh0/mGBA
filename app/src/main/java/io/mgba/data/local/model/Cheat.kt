package io.mgba.data.local.model

import java.io.File
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

import androidx.room.ForeignKey.CASCADE

@Entity(tableName = "Cheats", foreignKeys = [ForeignKey(entity = Game::class, parentColumns = arrayOf("id"), childColumns = arrayOf("gameKey"), onDelete = CASCADE)])
class Cheat {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(name = "gameKey")
    @NonNull
    @get:NonNull
    var gameKey: File? = null

    @ColumnInfo(name = "cheatName")
    var value: String? = null

    @ColumnInfo(name = "codeType")
    var type: Int = 0

    @ColumnInfo(name = "codeState")
    var enabled: Boolean = false

    @ColumnInfo(name = "codeName")
    var name: String? = null
}
