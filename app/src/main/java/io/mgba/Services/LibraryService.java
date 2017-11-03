package io.mgba.Services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import com.google.common.base.Function;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.mgba.Constants;
import io.mgba.Controllers.Services.ProcessingService;
import io.mgba.Data.DTOs.Game;
import io.mgba.Data.Wrappers.LibraryLists;
import io.mgba.Services.IO.FilesService;
import io.mgba.Services.Interfaces.IFilesService;
import io.mgba.Services.Interfaces.ILibraryService;

public class LibraryService implements ILibraryService{

    private final IFilesService filesService;
    private final LibraryReceiver libraryReceiver;
    private final Context context;
    //Not a consumer case java 8 only api >= 24
    private Function<LibraryLists, Void> callback;
    private LibraryLists cache = new LibraryLists(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

    public LibraryService(String directory, Context ctx) {
        this.libraryReceiver = new LibraryReceiver();
        this.context = ctx;
        this.filesService = new FilesService(directory);

        startReceiver();
    }

    private void startProcessService(ArrayList<? extends Game> list){
        Intent intent = new Intent(context, ProcessingService.class);
        intent.putParcelableArrayListExtra(Constants.GAMES_INTENT, list);
        context.startService(intent);
    }

    @Override
    public void prepareGames(Function<LibraryLists, Void> callback) {
        if(!cache.isEmpty()) {
            callback.apply(cache);
            return;
        }

        this.callback = callback;
        final List<File> gameList = filesService.getGameList();

        if(gameList.size() == 0){
            callback.apply(cache);
            return;
        }

        ArrayList<Game> res = new ArrayList<>();

        for (File file: gameList)
            res.add(new Game(file));

        startProcessService(res);
    }

    @Override
    public void stop(){
        if(libraryReceiver != null)
            LocalBroadcastManager.getInstance(context).unregisterReceiver(libraryReceiver);
    }

    @Override
    public void resume() {
        startReceiver();
    }

    @Override
    public void updateFileServicePath(String path) {
        filesService.setCurrentDirectory(path);
    }

    @Override
    public LibraryLists getCachedList() {
        return cache;
    }

    private void deliverResult(ArrayList<Game> gameList){
        cache = filter(gameList);
        callback.apply(cache);
    }

    private void startReceiver(){
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.RECEIVE_GAME_LIST);

        LocalBroadcastManager.getInstance(context.getApplicationContext()).registerReceiver(libraryReceiver, intentFilter);
    }

    private LibraryLists filter(ArrayList<Game> list){
        ArrayList<Game> favs = new ArrayList<>();
        ArrayList<Game> gba = new ArrayList<>();
        ArrayList<Game> gbc = new ArrayList<>();

        for (Game game : list) {
            if(game.isFavourite())
                favs.add(game);

            if(game.isAdvanced()){
                gba.add(game);
            }else{
                gbc.add(game);
            }
        }

        cache = new LibraryLists(favs, gba, gbc);

        return cache;
    }

    public class LibraryReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ArrayList<Game> games = intent.getParcelableArrayListExtra(Constants.GAMES_INTENT);
            deliverResult(games);
        }
    }
}
