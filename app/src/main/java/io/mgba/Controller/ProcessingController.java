package io.mgba.Controller;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

import io.mgba.Components.Views.Activities.MainActivity;
import io.mgba.Data.DTOs.Game;
import io.mgba.Services.ProcessingGameService;
import io.mgba.mgba;

public class ProcessingController {

    private static final String TAG = "ProcController";
    private final static int MAX_THREADS = 4;
    private final ProcessingGameService gameService;
    private final ArrayList<Game> games;
    private final AtomicInteger count;
    private final ThreadPoolExecutor executor;
    private final Runnable onEnd;
    private final Context ctx;

    public ProcessingController(mgba app, ArrayList<Game> games, Runnable runnable) {
        this.gameService = new ProcessingGameService(app);
        this.ctx = app.getApplicationContext();
        this.games = games;
        this.onEnd = runnable;
        this.count = new AtomicInteger(games.size());
        this.executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(MAX_THREADS);
    }

    public void start() {
        for (int i = 0; i < games.size(); i++) {
            int tmp = i;

            executor.submit(() -> {
                gameService.process(games.get(tmp));

                if(count.decrementAndGet() == 0)
                    announceResult(games);

            });
        }
    }

    private void announceResult(ArrayList<Game> list){
        Log.v(TAG, "Announcing results");

        Intent intent = new Intent(ctx, MainActivity.LibraryReceiver.class);
        intent.putParcelableArrayListExtra("games", list);

        ctx.sendBroadcast(intent);

        //Kill ourselfs
        onEnd.run();
    }
}
