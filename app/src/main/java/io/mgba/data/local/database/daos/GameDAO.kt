package io.mgba.data.local.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import io.mgba.data.local.database.model.Game

@Dao
interface GameDAO {

    @get:Query("SELECT * FROM Games")
    val games: List<Game>

    @get:Query("SELECT * FROM Games WHERE favourite = 1")
    val favouritesGames: List<Game>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Transaction
    fun insert(vararg games: Game)

    @Update
    @Transaction
    fun update(vararg games: Game)

    @Delete
    @Transaction
    fun delete(vararg games: Game)

    @Query("DELETE FROM Games")
    @Transaction
    fun deleteAll()

    @Query("SELECT * FROM Games WHERE name LIKE :query")
    fun getGames(query: String): List<Game>

    @Query("SELECT * FROM Games WHERE platform = :platform")
    fun getGamesForPlatform(platform: Int): List<Game>

}
