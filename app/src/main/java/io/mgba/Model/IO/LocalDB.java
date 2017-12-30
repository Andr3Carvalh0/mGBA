package io.mgba.Model.IO;

import android.arch.persistence.room.Room;
import android.content.Context;

import java.util.List;

import io.mgba.Data.Database.Database;
import io.mgba.Data.Database.Game;
import io.mgba.Data.Platform;

import static io.mgba.mgba.printLog;

public class LocalDB {
    private static final String TAG = "LocalDBSer";
    private Database db;

    public LocalDB(Context context) {
        db = Room.databaseBuilder(context, Database.class, context.getPackageName()).build();
    }

    public List<Game> getGamesForPlatform(final Platform platform){
        return db.gameDao().getGamesForPlatform(platform.ordinal());
    }

    public List<Game> getFavouritesGames(){
        return db.gameDao().getFavouritesGames();
    }

    public void insert(Game... games){
        db.gameDao().insert(games);
    }

    public void delete(){
        printLog(TAG, " deleting everything in db");
        db.gameDao().deleteAll();
    }

    public void delete(Game game) {
        printLog(TAG, game.getName() + " removing from db");
        db.gameDao().delete(game);
    }

    public List<Game> queryForGames(String query) {
        printLog(TAG, "Querying db for " + query);
        return db.gameDao().getGames(query);
    }

    public List<Game> getGames() {
        printLog(TAG, "Getting All Games");
        return db.gameDao().getGames();
    }
}
