package io.mgba.Services;

import android.content.Context;
import android.content.Intent;

import com.google.common.base.Function;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import io.mgba.Components.Services.ProcessingService;
import io.mgba.Controller.Interfaces.LibraryLists;
import io.mgba.Data.DTOs.Game;
import io.mgba.Services.Interfaces.IFilesService;
import io.mgba.Services.Interfaces.ILibraryService;

public class LibraryService implements ILibraryService {
    private final IFilesService filesService;
    private final Context context;
    //Not a consumer case java 8 only api >= 24
    private Function<LibraryLists, Void> callback;
    private LibraryLists cache;

    public LibraryService(String directory, Context ctx) {
        this.filesService = new FilesService(directory);
        this.context = ctx;
    }

    @Override
    public void getGames(Function<LibraryLists, Void> callback){
        if(cache != null) {
            callback.apply(cache);
            return;

        }

        this.callback = callback;
        final List<File> gameList = filesService.getGameList();

        ArrayList<Game> res = new ArrayList<>();

        for (File file: gameList) {
            res.add(new Game(file));
        }

        startProcessService(res);
    }

    @Override
    public void finalize(ArrayList<Game> gameList){
        cache = filter(gameList);
        callback.apply(cache);
    }

    private LibraryLists filter(ArrayList<Game> list){
        LinkedList<Game> favs = new LinkedList<>();
        LinkedList<Game> gba = new LinkedList<>();
        LinkedList<Game> gbc = new LinkedList<>();

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

    private void startProcessService(ArrayList<? extends Game> list){
        Intent intent = new Intent(context, ProcessingService.class);
        intent.putParcelableArrayListExtra("games", list);
        context.startService(intent);
    }
}
