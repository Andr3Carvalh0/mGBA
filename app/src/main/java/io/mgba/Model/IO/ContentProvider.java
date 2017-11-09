package io.mgba.Model.IO;

import android.content.Context;
import android.database.Cursor;

import io.mgba.Data.ContentProvider.game.GameColumns;
import io.mgba.Data.ContentProvider.game.GameContentValues;
import io.mgba.Data.ContentProvider.game.GameCursor;
import io.mgba.Data.ContentProvider.game.GameSelection;
import io.mgba.Data.DTOs.Game;
import io.mgba.Data.Platform;

import static io.mgba.mgba.printLog;

public class ContentProvider {

    private static final String TAG = "ContProvSer";

    public static Cursor getGamesForPlatform(final Platform platform, Context mCtx){
        printLog(TAG, "Getting games for " + platform + " from db");
        GameSelection selection = new GameSelection();
        selection.platform(platform.getValue());

        return mCtx.getContentResolver().query(selection.uri(), null,
                selection.sel(), selection.args(), selection.orderByName(false).order());
    }

    public static Cursor getGamesForPlatform(Context mCtx){
        GameSelection selection = new GameSelection();

        return mCtx.getContentResolver().query(selection.uri(), null,
                selection.sel(), selection.args(), selection.orderByName(false).order());
    }

    public static Cursor getFavouritesGames(Context mCtx){
        printLog(TAG, " Getting favs from db");
        GameSelection selection = new GameSelection();
        selection.isfavourite(true);

        return mCtx.getContentResolver().query(selection.uri(), null,
                selection.sel(), selection.args(), selection.orderByName(false).order());
    }

    public static void push(Game game, Context mCtx){
        final GameCursor gameCursor = queryForGame(game, mCtx);

        if(gameCursor.moveToFirst()){
            game.compare(gameCursor);
            update(game, mCtx);
            return;
        }

        GameContentValues values = new GameContentValues();
        printLog(TAG, game.getName() + " adding to db");

        values.putPath(game.getFile().getAbsolutePath());
        values.putName(game.getName());
        values.putDescription(game.getDescription());
        values.putReleased(game.getReleased());
        values.putDeveloper(game.getDeveloper());
        values.putGenre(game.getGenre());
        values.putCover(game.getCoverURL());
        values.putMd5(game.getMD5());
        values.putIsfavourite(game.isFavourite());
        values.putPlatform(game.getPlatform().getValue());

        values.insert(mCtx);
    }

    private static void update(Game game, Context mCtx){
        printLog(TAG, game.getName() + " updating values in db");
        GameContentValues values = new GameContentValues();

        values.putPath(game.getFile().getAbsolutePath());
        values.putName(game.getName());
        values.putDescription(game.getDescription());
        values.putReleased(game.getReleased());
        values.putDeveloper(game.getDeveloper());
        values.putGenre(game.getGenre());
        values.putCover(game.getCoverURL());
        values.putMd5(game.getMD5());
        values.putIsfavourite(game.isFavourite());
        values.putPlatform(game.getPlatform().getValue());

        mCtx.getContentResolver().update(values.uri(), values.values(), null, null);
    }

    public static void delete(Context mCtx){
        printLog(TAG, " deleting everything in db");
        mCtx.getContentResolver().delete(GameColumns.CONTENT_URI, null, null);
    }

    public static void remove(Game game, Context mCtx) {
        printLog(TAG, game.getName() + " removing from db");
        GameSelection selection = new GameSelection();
        selection.path(game.getFile().getAbsolutePath());

        mCtx.getContentResolver().delete(selection.uri(), selection.sel(), selection.args());

    }

    public static Cursor queryForGames(String query, Context mCtx) {
        printLog(TAG, "Querying db for " + query);

        GameSelection selection = new GameSelection();
        selection.nameContains(query);

        return mCtx.getContentResolver().query(selection.uri(), null,
                selection.sel(), selection.args(), selection.orderByName(false).order());
    }

    private static GameCursor queryForGame(Game game, Context mCtx){
        GameSelection selection = new GameSelection();
        selection.path(game.getFile().getAbsolutePath());

        final Cursor query = mCtx.getContentResolver().query(selection.uri(), null,
                selection.sel(), selection.args(), selection.orderByName(false).order());

        return new GameCursor(query);
    }
}
