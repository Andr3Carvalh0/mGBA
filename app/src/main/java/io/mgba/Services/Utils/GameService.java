package io.mgba.Services.Utils;

import android.content.Context;
import android.database.Cursor;

import com.google.common.base.Function;
import org.apache.commons.codec.digest.DigestUtils;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import io.mgba.Data.ContentProvider.game.GameContentValues;
import io.mgba.Data.ContentProvider.game.GameCursor;
import io.mgba.Data.ContentProvider.game.GameSelection;
import io.mgba.Data.DTOs.GameJSON;
import io.mgba.Data.DTOs.Interface.Game;
import io.mgba.Services.Web.Interfaces.IRequest;
import io.mgba.Services.Web.Interfaces.Web;
import io.mgba.Services.Web.RetrofitClientFactory;

public class GameService{

    private IRequest request;
    private Context mCtx;
    private ConcurrencyUtils<Game> concurrency;

    public GameService(Context nCtx) {
        this.mCtx = nCtx;
        this.concurrency = new ConcurrencyUtils<>();
    }

    public boolean process(Game game){
        try {
            return concurrency.update(game, (g) -> {
                if(calculateMD5(game)) {
                    if(fetchDB(game))
                        return game;

                    if(fetchWeb(game)){
                        storeDB(game);
                        return game;
                    }
                }

                return null;
            }) != null;

        }finally {
            concurrency.release();
        }
    }

    private boolean fetchDB(Game game){
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

    private void storeDB(Game game){
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

    private boolean fetchWeb(Game game){
        initRetrofit();
        try {
            final GameJSON gameJSON = request.registerDevice(game.getMD5()).execute().body();
            copyInformation(game, gameJSON);

            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private boolean calculateMD5(Game game) {
        FileInputStream input = null;

        try {
            input = new FileInputStream(game.getFile());
            String md5 = DigestUtils.md5Hex(input);

            game.setMD5(md5);

            return true;
        }catch (IOException e){
            return false;
        }finally {
            if(input != null){
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Game copyInformation(Game game, GameJSON json){
        game.setName(json.getName());
        game.setDescription(json.getDescription());
        game.setDeveloper(json.getDeveloper());
        game.setGenre(json.getGenre());
        game.setReleased(json.getReleased());
        game.setCoverURL(json.getCover());

        return game;
    }

    private synchronized void initRetrofit(){
        if(request == null)
            request = Web.getAPI();
    }
}
