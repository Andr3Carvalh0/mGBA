package io.mgba.Controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.google.common.base.Function;

import java.util.ArrayList;

import io.mgba.Constants;
import io.mgba.Controller.Interfaces.ILibraryController;
import io.mgba.Controller.Interfaces.LibraryLists;
import io.mgba.Data.DTOs.Game;
import io.mgba.Services.Interfaces.ILibraryService;
import io.mgba.Services.LibraryService;

public class LibraryController implements ILibraryController{

    private final ILibraryService service;
    private final LibraryReceiver libraryReceiver;
    private final Context context;


    public LibraryController(String directory, Context ctx) {
        this.service = new LibraryService(directory, ctx);
        this.libraryReceiver = new LibraryReceiver();
        this.context = ctx;

        startReceiver();
    }

    @Override
    public void prepareGames(Function<LibraryLists, Void> callback) {
        service.getGames(callback);
    }

    @Override
    public void stop(){
        if(libraryReceiver != null)
            context.unregisterReceiver(libraryReceiver);
    }

    private void startReceiver(){
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.RECEIVE_GAME_LIST);
        context.registerReceiver(libraryReceiver, intentFilter);
    }

    public class LibraryReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ArrayList<Game> games = intent.getParcelableArrayListExtra("games");
            service.finalize(games);
        }
    }
}
