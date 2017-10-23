package io.mgba.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import io.mgba.Data.DTOs.Interface.Game;
import io.mgba.Services.Utils.GameService;
import io.mgba.View.Activities.MainActivity;
import io.mgba.mgba;

public class ProcessingService extends Service {
    private final String TAG = "mgba: ProcessingService";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final GameService gameService = ((mgba) getApplication()).getGameService();
        final ArrayList<Game> games = intent.getParcelableArrayListExtra("games");
        final int action = intent.getIntExtra("action", -1);
        final AtomicInteger count = new AtomicInteger(games.size());

        for (Game game : games) {
            new Thread(() -> {

                if(!gameService.process(game)) {
                    Log.v(TAG, "Removing game: " + game.getFile().getName());
                    games.remove(game);
                }

                if(count.decrementAndGet() == 0)
                    announceResult(games, action);

            }).start();
        }

        return START_NOT_STICKY;
    }

    private void announceResult(ArrayList<Game> list, int action){
        Log.v(TAG, "Announcing results");

        Intent intent = new Intent(getApplicationContext(), MainActivity.LibraryReceiver.class);
        intent.putParcelableArrayListExtra("games", list);
        intent.putExtra("action", action);

        sendBroadcast(intent);

        //kill ourself
        stopSelf();
    }
}
