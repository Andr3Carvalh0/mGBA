package io.mgba.Services.IO;

import android.content.Context;
import android.database.Cursor;

import io.mgba.Data.ContentProvider.game.GameColumns;
import io.mgba.Data.ContentProvider.game.GameContentValues;
import io.mgba.Data.ContentProvider.game.GameSelection;
import io.mgba.Data.DTOs.Game;
import io.mgba.Data.Platform;

import static io.mgba.mgba.printLog;

public class ContentProviderService {

    private static final String TAG = "ContProvSer";

    public static Cursor getGamesForPlatform(final Platform platform, Context mCtx){
            GameSelection selection = new GameSelection();
            selection.platform(platform.getValue());

            return mCtx.getContentResolver().query(selection.uri(), null,
                    selection.sel(), selection.args(), selection.orderByName(false).order());
    }

    public static Cursor getFavouritesGames(Context mCtx){
            GameSelection selection = new GameSelection();
            selection.isfavourite(true);

            return mCtx.getContentResolver().query(selection.uri(), null,
                    selection.sel(), selection.args(), selection.orderByName(false).order());
    }

    public static void push(Game game, Context mCtx){
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

    public static void update(Game game, Context mCtx){
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
        mCtx.getContentResolver().delete(GameColumns.CONTENT_URI, null, null);
    }
}
