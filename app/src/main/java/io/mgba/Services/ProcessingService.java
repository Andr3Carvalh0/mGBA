package io.mgba.Services;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import io.mgba.Constants;
import io.mgba.Data.DTOs.Game;
import io.mgba.Data.DTOs.GameJSON;
import io.mgba.Services.IO.ContentProviderService;
import io.mgba.Services.IO.FilesService;
import io.mgba.Services.Interfaces.IProcessingService;
import io.mgba.mgba;

public class ProcessingService implements IProcessingService{
    private static final String TAG = "ProcService";
    private final static int MAX_THREADS = 4;

    private final mgba mApplication;
    private final ExecutorService executor;
    private final Runnable onEnd;
    private final Context mCtx;
    private final AtomicInteger count;
    private volatile ArrayList<Game> games;

    public ProcessingService(mgba app, ArrayList<Game> games, Runnable runnable) {
        this.mCtx = app.getApplicationContext();
        this.mApplication = app;
        this.games = games;
        this.onEnd = runnable;
        this.count = new AtomicInteger(games.size());
        this.executor = Executors.newFixedThreadPool(MAX_THREADS);
    }

    @Override
    public void start() {
        if(games.size() == 0)
            announceResult(games);

        for (int i = 0; i < games.size(); i++) {
            int tmp = i;
            executor.submit(() -> {
                process(games.get(tmp));

                if(count.decrementAndGet() <= 0){
                    sortList(games);
                    announceResult(games);
                }
            });
        }
    }


    private void announceResult(ArrayList<Game> list){
        Log.v(TAG, "Announcing results");

        Intent intent = new Intent(Constants.RECEIVE_GAME_LIST);
        intent.putParcelableArrayListExtra(Constants.GAMES_INTENT, list);

        LocalBroadcastManager.getInstance(mCtx.getApplicationContext()).sendBroadcast(intent);

        //Kill ourselfs
        onEnd.run();
    }

    private void process(Game game) {
        if (calculateMD5(game)) {
            Log.v(TAG, "MD5 calculated!");
            if (searchDatabase(game))
                return;

            if(!mApplication.hasWifiConnection())
                return;

            Log.v(TAG, "Game wasnt present in the DB.Fetching online");
            if (searchWeb(game))
               storeInDatabase(game);
        }
    }

    private boolean searchDatabase(Game game) {
        return ContentProviderService.doesItemExist(game, mCtx);
    }

    private void storeInDatabase(Game game){
        Log.v(TAG, "Storing recent acquired info on db!");
        ContentProviderService.push(game, mCtx);
    }

    private boolean searchWeb(Game game){
        try {
            String lang = mApplication.getDeviceLanguage();
            final GameJSON json = mApplication.getWebService().getGameInformation(game.getMD5(), lang).execute().body();

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

    private void sortList(ArrayList<Game> games) {
        Collections.sort(games, (o1, o2) -> o1.getName().compareToIgnoreCase(o2.getName()));
    }
}
