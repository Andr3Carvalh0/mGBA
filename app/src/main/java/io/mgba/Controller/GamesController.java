package io.mgba.Controller;


import android.content.Context;
import android.content.Intent;

import com.google.common.base.Predicate;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import io.mgba.Data.DTOs.GameboyAdvanceGame;
import io.mgba.Data.DTOs.GameboyGame;
import io.mgba.Data.DTOs.Interface.Game;
import io.mgba.Services.GBAService;
import io.mgba.Services.GBService;
import io.mgba.Services.Utils.FilesService;
import io.mgba.Services.Utils.GameService;

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

    public void getGBAGames(){
        final List<File> gameList = filesService.getGameList(new GameboyAdvanceExtensionPredicate());

        ArrayList<GameboyAdvanceGame> gba = new ArrayList<>();

        for (File file: gameList) {
            gba.add(new GameboyAdvanceGame(file));
        }

        startProcessService(gba, GBAService.class);
    }
    public void getGBGames(){
        final List<File> gameList = filesService.getGameList(new GameboyColorExtensionPredicate());

        ArrayList<GameboyGame> gbc = new ArrayList<>();

        for (File file: gameList) {
            gbc.add(new GameboyGame(file));
        }

        startProcessService(gbc, GBService.class);
    }

    private void startProcessService(ArrayList<? extends Game> list, Class service){
        Intent intent = new Intent(ctx, service);
        intent.putParcelableArrayListExtra("games", list);
        ctx.startService(intent);
    }

    private class GameboyAdvancePredicate implements Predicate<Game> {

        @Override
        public boolean apply(@javax.annotation.Nullable Game input) {
            return input instanceof GameboyAdvanceGame;
        }
    }
    private class GameboyColorPredicate implements Predicate<Game> {

        @Override
        public boolean apply(@javax.annotation.Nullable Game input) {
            return input instanceof GameboyGame;
        }
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
