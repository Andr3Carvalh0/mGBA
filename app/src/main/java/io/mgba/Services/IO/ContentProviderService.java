package io.mgba.Services.IO;

import android.content.Context;

import io.mgba.Data.ContentProvider.game.GameContentValues;
import io.mgba.Data.ContentProvider.game.GameCursor;
import io.mgba.Data.ContentProvider.game.GameSelection;
import io.mgba.Data.DTOs.Game;

import static io.mgba.mgba.printLog;

public class ContentProviderService {

    private static final String TAG = "ContProvSer";

    public static boolean doesItemExist(Game game, Context mCtx){
        String md5 = game.getMD5();

        GameSelection query = new GameSelection();
        query.md5(md5);

        GameCursor cursor = new GameCursor(mCtx.getContentResolver().query(query.uri(), null,
                query.sel(), query.args(), null));

        if(cursor.moveToNext()){
            game.setName(cursor.getName());
            game.setCoverURL(cursor.getCover());
            game.setReleased(cursor.getReleased());
            game.setDeveloper(cursor.getDeveloper());
            game.setDescription(cursor.getDescription());
            game.setGenre(cursor.getGenre());

            try {
                game.setFavourite(cursor.getIsfavourite());
            }catch (NullPointerException e){
                game.setFavourite(false);
            }

            printLog(TAG, game.getName() + " exists");
            return true;
        }


        printLog(TAG, game.getName() + " does exists");
        return false;
    }

    public static void push(Game game, Context mCtx){
        GameContentValues values = new GameContentValues();
        printLog(TAG, game.getName() + " adding to db");

        values.putCover(game.getCoverURL());
        values.putDescription(game.getDescription());
        values.putName(game.getName());
        values.putGenre(game.getGenre());
        values.putReleased(game.getReleased());
        values.putDeveloper(game.getDeveloper());
        values.putMd5(game.getMD5());
        values.putIsfavourite(game.isFavourite());

        values.insert(mCtx);
    }

}
