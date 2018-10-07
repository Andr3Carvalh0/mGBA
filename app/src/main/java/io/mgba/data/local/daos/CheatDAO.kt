package io.mgba.data.local.daos

import androidx.lifecycle.LiveData
import java.io.File

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import io.mgba.data.local.model.Cheat

@Dao
interface CheatDAO {

    @Query("SELECT * FROM Cheats WHERE gameKey = :key")
    fun monitorCheatsForGame(key: File): LiveData<List<Cheat>>

    @Query("DELETE FROM Cheats WHERE id = :key")
    @Transaction
    fun removeCheat(key: Int)

    @Insert
    @Transaction
    fun addCheat(cheat: Cheat)

}
