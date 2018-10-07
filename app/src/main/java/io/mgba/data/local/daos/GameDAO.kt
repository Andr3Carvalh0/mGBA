package io.mgba.data.local.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import io.mgba.data.local.model.Game

@Dao
interface GameDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Transaction
    fun insert(game: Game)

    @Update
    @Transaction
    fun update(game: Game)

    @Delete
    @Transaction
    fun delete(games: Game)

    @Query("SELECT * FROM Games WHERE id = :game")
    fun get(game: String): Game

    @Query("DELETE FROM Games")
    @Transaction
    fun clearLibrary()

    @Query("SELECT * FROM Games WHERE name LIKE :query ORDER BY name")
    fun query(query: String): LiveData<List<Game>>

    @Query("SELECT * FROM Games WHERE favourite = 1 ORDER BY name")
    fun monitorFavouriteGames(): LiveData<List<Game>>

    @Query("SELECT * FROM Games WHERE platform = 1 ORDER BY name")
    fun monitorGameboyColorGames(): LiveData<List<Game>>

    @Query("SELECT * FROM Games WHERE platform = 2 ORDER BY name")
    fun monitorGameboyAdvancedGames(): LiveData<List<Game>>

}
