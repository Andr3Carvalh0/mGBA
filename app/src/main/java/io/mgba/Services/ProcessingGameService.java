package io.mgba.Services;

import android.content.Context;
import android.util.Log;

import java.io.IOException;

import io.mgba.Data.DTOs.Game;
import io.mgba.Data.DTOs.GameJSON;
import io.mgba.mgba;

public class ProcessingGameService {
    private final static String TAG = "ProcGameService";

    private Context mCtx;
    private mgba mApplication;

    public ProcessingGameService(mgba app) {
        this.mApplication = app;
        this.mCtx = app.getApplicationContext();
    }

    public void process(Game game) {
        if (calculateMD5(game)) {
            Log.v(TAG, "MD5 calculated!");
            if (searchDatabase(game))
                return;

            Log.v(TAG, "Game wasnt present in the DB.Fetching online");
            if (searchWeb(game))
               storeInDatabase(game);
        }
    }

    private boolean searchDatabase(Game game) {
        return ContentProviderService.doesItemExist(game, mCtx);
    }

    private void storeInDatabase(Game game){
        Log.v(TAG, "Storing recent acquired info on db!");
        ContentProviderService.push(game, mCtx);
    }

    private boolean searchWeb(Game game){
        if(game.getMD5() == null)
            return false;

        try {
            final GameJSON json = mApplication.getWebService().getGameInformation(game.getMD5()).execute().body();
            copyInformation(game, json);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private boolean calculateMD5(Game game) {
       String md5 = FilesService.getFileMD5ToString(game.getFile(), mCtx);
       game.setMD5(md5);

       return md5 != null;
    }

    private void copyInformation(Game game, GameJSON json){
        game.setName(json.getName());
        game.setDescription(json.getDescription());
        game.setDeveloper(json.getDeveloper());
        game.setGenre(json.getGenre());
        game.setReleased(json.getReleased());
        game.setCoverURL(json.getCover());
    }

}
