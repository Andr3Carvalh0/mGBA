package io.mgba.Model;

import android.util.Log;

import com.annimon.stream.Stream;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import io.mgba.Data.Database.Game;
import io.mgba.Data.Platform;
import io.mgba.Data.Remote.DTOs.GameJSON;
import io.mgba.Model.IO.FilesManager;
import io.mgba.Model.IO.LocalDB;
import io.mgba.Model.Interfaces.IFilesManager;
import io.mgba.Model.Interfaces.ILibrary;
import io.mgba.Model.System.PreferencesManager;
import io.mgba.mgba;
import io.reactivex.Single;

public class Library implements ILibrary {
    private static final String TAG = "ProcService";
    private final mgba mApp;
    private final LocalDB mDatabase;
    private IFilesManager filesService;

    public Library(mgba application) {
        this.mApp = application;
        this.mDatabase = new LocalDB(application);
    }

    @Override
    public Single<List<Game>> prepareGames(Platform platform) {
        return Single.create(subscriber -> {

            if(platform == null){
                subscriber.onSuccess(new LinkedList<>());
                return;
            }

            List<Game> games = mDatabase.getGamesForPlatform(platform);

            subscriber.onSuccess(games);
        });
    }

    @Override
    public Single<List<Game>> reloadGames(Platform... platform) {
        return Single.create(subscriber -> {
            //clean up possible removed files from content provider
            List<Game> games = mDatabase.getGames();

            Stream.of(games)
                  .filter(g -> !g.getFile().exists())
                  .forEach(g -> {
                      games.remove(g);
                      mDatabase.delete(g);}
                  );

            //read the files from the selected dir
            if(filesService == null)
                filesService = new FilesManager(mApp.getPreference(PreferencesManager.GAMES_DIRECTORY, ""));

            final List<Game> updatedList = Stream.of(filesService.getGameList())
                    .map(f -> new Game(f.getAbsolutePath(), getPlatform(f)))
                    .filter(f -> games.size() == 0 || Stream.of(games)
                            .anyMatch(g -> g.getFile().equals(f.getFile()) && g.needsUpdate()))
                    .map(g -> {
                        Stream.of(games).filter(g1 -> g1.equals(g)).forEach(games::remove);

                        if (calculateMD5(g)) {
                            if(mApp.isConnectedToWeb())
                                searchWeb(g);
                            storeInDatabase(g);
                        }

                        return g;
                    }).toList();

            games.addAll(updatedList);

            Collections.sort(games, (o1, o2) -> o1.getName().compareTo(o2.getName()));

            subscriber.onSuccess(filter(Arrays.asList(platform), games));
        });
    }


    private List<Game> filter(List<Platform> platform, List<Game> games){
        return Stream.of(games)
                     .filter(g -> platform.contains(g.getPlatform()))
                     .toList();
    }

    @Override
    public Single<List<Game>> query(String query) {
        return Single.create(subscriber -> {

            if(query == null || query.length() == 0){
                subscriber.onSuccess(new LinkedList<>());
                return;
            }

            List<Game> games = mDatabase.queryForGames(query);

            subscriber.onSuccess(games);
        });
    }

    private Platform getPlatform(File file) {
        final String fileExtension = FilesManager.getFileExtension(file);
        if(Platform.GBA.getExtensions().contains(fileExtension))
            return Platform.GBA;

        return Platform.GBC;
    }

    private void storeInDatabase(Game game){
        Log.v(TAG, "Storing recent acquired info on db!");
        mDatabase.insert(game);
    }

    private boolean searchWeb(Game game){
        try {
            Thread.sleep(5000);
            final GameJSON json = mApp.getWebService()
                                      .getGameInformation(game.getMD5(), mApp.getDeviceLanguage())
                                      .execute()
                                      .body();

            if(json == null)
                return false;

            copyInformation(game, json);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean calculateMD5(Game game) {
        String md5 = FilesManager.getFileMD5ToString(game.getFile(), mApp);
        game.setMD5(md5);

        return md5 != null;
    }

    private void copyInformation(Game game, GameJSON json){
        game.setName(json.getName());
        game.setDescription(json.getDescription());
        game.setDeveloper(json.getDeveloper());
        game.setGenre(json.getGenre());
        game.setReleased(json.getReleased());
        game.setCoverURL(json.getCover());
    }
}
