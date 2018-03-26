package io.mgba.Model.Interfaces;

import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import java.io.File;
import java.util.List;

import io.mgba.Data.Database.Cheat;
import io.mgba.Data.Database.Game;
import io.mgba.Data.Platform;

public interface IDatabase {
    List<Game> getGamesForPlatform(final Platform platform);
    List<Game> getFavouritesGames();
    void insert(Game... games);
    void delete();
    void delete(Game game);
    List<Game> queryForGames(String query);
    List<Game> getGames();

    List<Cheat> getGamesCheats(Game key);
    void deleteCheat(Cheat cheat);
    void insertCheat(Cheat cheat);
}
