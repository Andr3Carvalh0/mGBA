package io.mgba.data.database.model


import java.io.File
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

import androidx.room.ForeignKey.CASCADE

@Entity(tableName = "Cheats", foreignKeys = arrayOf(ForeignKey(entity = Game::class, parentColumns = arrayOf("id"), childColumns = arrayOf("idFK"), onDelete = CASCADE)))
class Cheat {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(name = "idFK")
    @NonNull
    @get:NonNull
    var id_FK: File? = null

    @ColumnInfo(name = "cheatName")
    var value: String? = null

    @ColumnInfo(name = "codeType")
    var type: Int = 0

    @ColumnInfo(name = "codeState")
    var isState: Boolean = false

    @ColumnInfo(name = "codeName")
    var name: String? = null
}
