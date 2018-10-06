package io.mgba.data.local.database.daos

import java.io.File

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import io.mgba.data.local.database.model.Cheat

@Dao
interface CheatDAO {

    @Query("SELECT * FROM Cheats WHERE idFK = :key")
    fun getGamesCheats(key: File): List<Cheat>

    @Query("DELETE FROM Cheats WHERE id = :key")
    @Transaction
    fun deleteCheat(key: Int)

    @Insert
    @Transaction
    fun insertCheat(cheat: Cheat)

}
