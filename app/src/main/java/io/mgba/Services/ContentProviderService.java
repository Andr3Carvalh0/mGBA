package io.mgba.Services;

import android.content.Context;

import io.mgba.Data.ContentProvider.game.GameContentValues;
import io.mgba.Data.ContentProvider.game.GameCursor;
import io.mgba.Data.ContentProvider.game.GameSelection;
import io.mgba.Data.DTOs.Game;

public class ContentProviderService {

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

            return true;
        }

        return false;
    }

    public static void push(Game game, Context mCtx){
        GameContentValues values = new GameContentValues();

        values.putCover(game.getCoverURL());
        values.putDescription(game.getDescription());
        values.putName(game.getName());
        values.putGenre(game.getGenre());
        values.putReleased(game.getReleased());
        values.putDeveloper(game.getDeveloper());
        values.putMd5(game.getMD5());

        values.insert(mCtx);
    }

}
