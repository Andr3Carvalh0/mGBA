package io.mgba;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import io.mgba.Data.Database.Database;
import io.mgba.Data.Database.Game;
import io.mgba.Data.Platform;
import io.mgba.Model.IO.LocalDB;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class DatabaseTests {

    private LocalDB database;
    private Database db;

    private static Game[] games;

    @BeforeClass
    public static void init() {
        games = new Game[4];
        games[0] = new Game("/a.gba", "Pokemon", "Some desc", "2000", "I", "", "", "AAA", false, Platform.GBA);
        games[1] = new Game("/b.gba", "Metroid", "Some desc", "2000", "I", "", "", "AAA", true, Platform.GBA);
        games[2] = new Game("/c.gba", "Zelda", "Some desc", "2000", "I", "", "", "AAA", true, Platform.GBC);
        games[3] = new Game("/d.gba", "Mario", "Some desc", "2000", "I", "", "", "AAA", false, Platform.GBC);

    }

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        db = Room.inMemoryDatabaseBuilder(context, Database.class).build();
        database = new LocalDB(db);
    }

    @After
    public void closeDb() throws IOException {
        database.delete();
        db.close();
    }

    @Test
    public void addGameAndFetchItTest() throws Exception {
        database.insert(games);
        List<Game> fetched = database.getGames();

        assertEquals(games.length, fetched.size());
    }

    @Test
    public void duplicateTest() throws Exception {
        Game a1 = new Game("/c.gba", "Zelda", "Some desc", "2000", "I", "", "", "AAA", true, Platform.GBC);
        Game a2 = new Game("/c.gba", "Mario", "Some desc", "2000", "I", "", "", "AAA", false, Platform.GBC);


        database.insert(a1);
        database.insert(a2);

        List<Game> fetched = database.getGames();

        assertEquals(1, fetched.size());
        assertEquals(a2.getName(), fetched.get(0).getName());
    }


    @Test
    public void deleteTest() throws Exception {
        database.insert(games);
        database.delete(games[0]);

        List<Game> fetched = database.getGames();

        assertEquals(games.length - 1, fetched.size());
        assertEquals(false, fetched.contains(games[0]));

    }


}
