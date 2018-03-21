package io.mgba;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import io.mgba.Data.Database.Game;
import io.mgba.Data.Platform;
import io.mgba.Model.Interfaces.IDatabase;
import io.mgba.Model.Interfaces.IFilesManager;
import io.mgba.Model.Library;
import io.mgba.Utils.IDeviceManager;

@RunWith(MockitoJUnitRunner.class)
public class LibraryTests {

    @Mock
    IDatabase database;

    @Mock
    IFilesManager files;

    @Mock
    IDeviceManager deviceManager;

    @InjectMocks
    Library library;

    private static final String query = "Pokemon";
    private static final String empty_query = "";
    private static List<Game> games = new LinkedList<>();

    @BeforeClass
    public static void init(){
        games.add(new Game("/a.gba", "Pokemon", "Some desc", "2000", "I", "", "", "AAA", false, Platform.GBA));
        games.add(new Game("/b.gba", "Metroid", "Some desc", "2000", "I", "", "", "AAA", true, Platform.GBA));
        games.add(new Game("/c.gba", "Zelda", "Some desc", "2000", "I", "", "", "AAA", true, Platform.GBC));
        games.add(new Game("/d.gba", "Mario", "Some desc", "2000", "I", "", "", "AAA", false, Platform.GBC));
    }


    @Test
    public void testQuerySuccessfullyForGames(){
        library.query(query).subscribe(l -> assertEquals(0, l.size()));
    }

    @Test
    public void testReloadGames(){
    }


}
