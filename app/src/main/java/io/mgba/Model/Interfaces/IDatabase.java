package io.mgba.Model.Interfaces;


import java.util.List;
import io.mgba.Data.Database.Cheat;
import io.mgba.Data.Database.Game;

public interface IDatabase {
    List<Game> getGamesForPlatform(final int platform);
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
