package io.mgba.Controller;


import android.content.Context;
import android.content.Intent;

import com.google.common.base.Predicate;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.mgba.Components.Services.ProcessingService;
import io.mgba.Data.DTOs.Game;
import io.mgba.Services.FilesService;

/**
 * Handles the interaction with the FilesService. Its used to fetch a gamelist and start its process
 */
public class GamesController {

    private final FilesService filesService;
    private final Context ctx;

    public GamesController(String directory, Context ctx) {
        this.filesService = new FilesService(directory);
        this.ctx = ctx;
    }

    public void getGames(){
        final List<File> gameList = filesService.getGameList();

        ArrayList<Game> res = new ArrayList<>();

        for (File file: gameList) {
            res.add(new Game(file));
        }

        startProcessService(res);
    }

    private void startProcessService(ArrayList<? extends Game> list){
        Intent intent = new Intent(ctx, ProcessingService.class);
        intent.putParcelableArrayListExtra("games", list);
        ctx.startService(intent);
    }

    private class GameboyAdvanceExtensionPredicate implements Predicate<File> {

        @Override
        public boolean apply(@javax.annotation.Nullable File input) {
            return FilesService.GBA_FILES_SUPPORTED.contains(filesService.getFileExtension(input));
        }
    }
    private class GameboyColorExtensionPredicate implements Predicate<File> {

        @Override
        public boolean apply(@javax.annotation.Nullable File input) {
            return FilesService.GBC_FILES_SUPPORTED.contains(filesService.getFileExtension(input));
        }
    }

}