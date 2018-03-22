package io.mgba;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import io.mgba.Data.Database.Game;
import io.mgba.Data.Platform;
import io.mgba.Data.Remote.Interfaces.IRequest;
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
    IRequest webManager;

    @Mock
    IDeviceManager deviceManager;



    @InjectMocks
    Library library;

    private static final String query = "Pokemon";
    private static final String empty_query = "";
    private static List<Game> games = new LinkedList<>();
    private static List<File> file = new LinkedList<>();

    @BeforeClass
    public static void init(){
        games.add(new Game("/a.gba", "Pokemon", "Some desc", "2000", "I", "", "", "E26EE0D44E809351C8CE2D73C7400CDD", false, Platform.GBA));
        file.add(new File("/a.gba"));
    }


    @Test
    public void testQuerySuccessfullyForGames(){
        when(database.queryForGames(query)).thenReturn(games);
        library.query(query).subscribe(l -> assertEquals(1, l.size()));
    }

    @Test
    public void testEmptyQuerySuccessfullyForGames(){
        library.query(empty_query).subscribe(l -> assertEquals(0, l.size()));
    }

}
