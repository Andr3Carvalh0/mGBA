package io.mgba.Utils;

import org.junit.BeforeClass;

import java.util.LinkedList;
import java.util.List;

import io.mgba.Data.Database.Game;
import io.mgba.Data.Platform;
import io.mgba.Model.Interfaces.IDatabase;

public class TestsDB implements IDatabase{
    private static List<Game> games;

    @BeforeClass
    public static void init() {
        games = new LinkedList<>();
        games.add(new Game("/a.gba", "Pokemon", "Some desc", "2000", "I", "", "", "AAA", false, Platform.GBA));
        games.add(new Game("/b.gba", "Metroid", "Some desc", "2000", "I", "", "", "AAA", true, Platform.GBA));
        games.add(new Game("/c.gba", "Zelda", "Some desc", "2000", "I", "", "", "AAA", true, Platform.GBC));
        games.add(new Game("/d.gba", "Mario", "Some desc", "2000", "I", "", "", "AAA", false, Platform.GBC));

    }

    @Override
    public List<Game> getGamesForPlatform(Platform platform) {
        return games;
    }

    @Override
    public List<Game> getFavouritesGames() {
        return games;
    }

    @Override
    public void insert(Game... games) {

    }

    @Override
    public void delete() {

    }

    @Override
    public void delete(Game game) {
        System.out.println("");


    }

    @Override
    public List<Game> queryForGames(String query) {
        return null;
    }

    @Override
    public List<Game> getGames() {
        return null;
    }
}
