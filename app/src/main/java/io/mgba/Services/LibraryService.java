package io.mgba.Services;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.annimon.stream.Stream;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import io.mgba.Data.ContentProvider.game.GameCursor;
import io.mgba.Data.DTOs.Game;
import io.mgba.Data.DTOs.GameJSON;
import io.mgba.Data.Platform;
import io.mgba.Services.IO.ContentProviderService;
import io.mgba.Services.IO.FilesService;
import io.mgba.Services.Interfaces.IFilesService;
import io.mgba.Services.Interfaces.ILibraryService;
import io.mgba.Services.System.PreferencesService;
import io.mgba.mgba;
import io.reactivex.Observable;

public class LibraryService implements ILibraryService{
    private static final String TAG = "ProcService";
    private final Context mCtx;
    private IFilesService filesService;

    public LibraryService(Context ctx) {
        this.mCtx = ctx;
    }

    @Override
    public Observable<List<Game>> prepareGames(Platform platform) {
        return Observable.create(subscriber -> {

            if(platform == null){
                subscriber.onNext(new LinkedList<>());
                subscriber.onComplete();
                return;
            }

            Cursor cursor = ContentProviderService.getGamesForPlatform(platform, mCtx);

            List<Game> games = copyInformation(cursor);

            // Pass the result to the consumer.
            subscriber.onNext(games);

            // Tell the consumer we're done; it will unsubscribe implicitly.
            subscriber.onComplete();
        });

    }

    @Override
    public Observable<List<Game>> reloadGames(Platform... platform) {
        return Observable.create(subscriber -> {
            //clean up possible removed files from content provider
            Cursor cursor = ContentProviderService.getGamesForPlatform(mCtx);
            List<Game> games = copyInformation(cursor);

            Stream.of(games)
                  .filter(g -> !g.getFile().exists())
                  .forEach(g -> {
                      games.remove(g);
                      ContentProviderService.remove(g, mCtx);}
                  );

            //read the files from the selected dir
            if(filesService == null)
                filesService = new FilesService(mgba.getPreference(PreferencesService.GAMES_DIRECTORY, ""));

            final List<Game> updatedList = Stream.of(filesService.getGameList())
                    .map(f -> new Game(f.getAbsolutePath(), getPlatform(f)))
                    .filter(f -> games.size() == 0 || Stream.of(games)
                            .anyMatch(g -> g.getFile().equals(f.getFile()) && g.needsUpdate()))
                    .map(g -> {
                        Stream.of(games).filter(g1 -> g1.equals(g)).forEach(games::remove);

                        if (calculateMD5(g)) {
                            searchWeb(g);
                            storeInDatabase(g);
                        }

                        return g;
                    }).toList();

            games.addAll(updatedList);

            Collections.sort(games, (o1, o2) -> o1.getName().compareTo(o2.getName()));

            subscriber.onNext(filter(Arrays.asList(platform), games));
            subscriber.onComplete();
        });
    }


    private List<Game> filter(List<Platform> platform, List<Game> games){
        return Stream.of(games)
                     .filter(g -> platform.contains(g.getPlatform()))
                     .toList();
    }

    @Override
    public Observable<List<Game>> query(String query) {
        return Observable.create(subscriber -> {

            if(query == null || query.length() == 0){
                subscriber.onNext(new LinkedList<>());
                subscriber.onComplete();
                return;
            }

            Cursor cursor = ContentProviderService.queryForGames(query, mCtx);

            List<Game> games = copyInformation(cursor);

            // Pass the result to the consumer.
            subscriber.onNext(games);

            // Tell the consumer we're done; it will unsubscribe implicitly.
            subscriber.onComplete();
        });
    }

    private Platform getPlatform(File file) {
        final String fileExtension = FilesService.getFileExtension(file);
        if(fileExtension.equals("gba"))
            return Platform.GBA;

        return Platform.GBC;
    }

    private void storeInDatabase(Game game){
        Log.v(TAG, "Storing recent acquired info on db!");
        ContentProviderService.push(game, mCtx);
    }

    private boolean searchWeb(Game game){
        try {
            //String lang = mgba.getDeviceLanguage();
            final GameJSON json = mgba.getWebService().getGameInformation(game.getMD5(), "eng").execute().body();

            if(json == null)
                return false;

            copyInformation(game, json);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private boolean calculateMD5(Game game) {
        String md5 = FilesService.getFileMD5ToString(game.getFile(), mCtx);
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

    private List<Game> copyInformation(Cursor cursor){
        GameCursor gCursor = new GameCursor(cursor);

        List<Game> games = new LinkedList<>();

        while(gCursor.moveToNext()){
            Game game = new Game(gCursor.getPath(), gCursor.getName(), gCursor.getDescription(),
                                 gCursor.getReleased(), gCursor.getDeveloper(), gCursor.getGenre(),
                                 gCursor.getCover(), gCursor.getMd5(), gCursor.getIsfavourite(),
                                 Platform.forValue(gCursor.getPlatform()));

            games.add(game);
        }

        return games;
    }
}
