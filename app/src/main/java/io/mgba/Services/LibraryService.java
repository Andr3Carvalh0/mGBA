package io.mgba.Services;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.io.File;
import java.io.IOException;
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
import rx.Observable;

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
                subscriber.onCompleted();
                return;
            }

            Cursor cursor = ContentProviderService.getGamesForPlatform(platform, mCtx);

            List<Game> games = copyInformation(cursor);

            // Pass the result to the consumer.
            subscriber.onNext(games);

            // Tell the consumer we're done; it will unsubscribe implicitly.
            subscriber.onCompleted();
        });

    }

    @Override
    public Observable<List<Game>> reloadGames(Platform platform) {
        return Observable.create(subscriber -> {
            //drop previous tables on db
            ContentProviderService.delete(mCtx);

            //read the files from the selected dir
            if(filesService == null)
                filesService = new FilesService(mgba.getPreference(PreferencesService.GAMES_DIRECTORY, ""));

            final List<File> files = filesService.getGameList();

            final List<Game> games = new LinkedList<>();

            if(files.size() == 0){
                subscriber.onNext(new LinkedList<>());
                subscriber.onCompleted();
                return;
            }

            //Start fetching game information online
            for (File file : files)
                games.add(new Game(file.getAbsolutePath(), getPlatform(file)));

            for (Game game : games) {
                if (calculateMD5(game)){
                    searchWeb(game);
                    storeInDatabase(game);
                }
            }

            subscriber.onNext(games);
            subscriber.onCompleted();
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
