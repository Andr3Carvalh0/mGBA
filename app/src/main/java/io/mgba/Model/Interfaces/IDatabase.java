package io.mgba.Model.Interfaces;

import java.util.List;

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
}
