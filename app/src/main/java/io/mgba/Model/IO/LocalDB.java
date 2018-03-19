package io.mgba.Model.IO;

import android.content.Context;

import java.util.List;

import io.mgba.Data.Database.Database;
import io.mgba.Data.Database.Game;
import io.mgba.Data.Platform;
import io.mgba.Model.Interfaces.IDatabase;

import static io.mgba.mgba.printLog;

public class LocalDB implements IDatabase{
    private static final String TAG = "LocalDBSer";
    private Database db;

    public LocalDB(Context context) {
        db = Database.getInstance(context);
    }

    public LocalDB(Database database) {
        db = database;
    }

    @Override
    public List<Game> getGamesForPlatform(final Platform platform){
        return db.gameDao().getGamesForPlatform(platform.ordinal());
    }

    @Override
    public List<Game> getFavouritesGames(){
        return db.gameDao().getFavouritesGames();
    }

    @Override
    public void insert(Game... games){
        db.gameDao().insert(games);
    }

    @Override
    public void delete(){
        printLog(TAG, " deleting everything in db");
        db.gameDao().deleteAll();
    }

    @Override
    public void delete(Game game) {
        printLog(TAG, game.getName() + " removing from db");
        db.gameDao().delete(game);
    }

    @Override
    public List<Game> queryForGames(String query) {
        printLog(TAG, "Querying db for " + query);
        return db.gameDao().getGames(query);
    }

    @Override
    public List<Game> getGames() {
        printLog(TAG, "Getting All Games");
        return db.gameDao().getGames();
    }
}
