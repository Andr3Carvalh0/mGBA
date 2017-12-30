package io.mgba.Data.Database.DAOs;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import io.mgba.Data.Database.Game;

@Dao
public interface GameDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Game... games);

    @Update
    void update(Game... games);

    @Delete
    void delete(Game... games);

    @Query("DELETE FROM Games")
    void deleteAll();

    @Query("SELECT * FROM Games")
    List<Game> getGames();

    @Query("SELECT * FROM Games WHERE name LIKE :query")
    List<Game> getGames(String query);

    @Query("SELECT * FROM Games WHERE favourite = 1")
    List<Game> getFavouritesGames();

    @Query("SELECT * FROM Games WHERE platform = :platform")
    List<Game> getGamesForPlatform(int platform);

}
