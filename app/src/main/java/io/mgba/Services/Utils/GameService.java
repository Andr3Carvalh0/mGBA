package io.mgba.Services.Utils;

import android.content.Context;
import com.google.common.base.Function;
import org.apache.commons.codec.digest.DigestUtils;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import io.mgba.Data.DTOs.GameJSON;
import io.mgba.Data.DTOs.Interface.Game;
import io.mgba.Services.Web.Interfaces.IRequest;
import io.mgba.Services.Web.Interfaces.Web;
import io.mgba.Services.Web.RetrofitClientFactory;

public class GameService{

    private final Lock nLock;
    private final Context nCtx;
    private IRequest request;
    private final LinkedList<Pair> queue;
    //Only support MAX_TASKS simultaneously
    private final int MAX_TASKS = 4;

    private class Pair{
        private Condition mCondition;
        private Game game;
        private boolean woke;

        public Pair(Condition mCondition, Game game) {
            this.mCondition = mCondition;
            this.game = game;
            this.woke = false;
        }

        public boolean isWoke() {
            return woke;
        }

        public void setWoke(boolean woke) {
            this.woke = woke;
        }

        public Condition getmCondition() {
            return mCondition;
        }

        public Game getGame() {
            return game;
        }
    }

    public GameService(Context nCtx) {
        this.nLock = new ReentrantLock();
        this.nCtx = nCtx;
        this.queue = new LinkedList<>();
    }


    public boolean process(Game game){
        boolean failed;

        if(failed = (calculateMD5(game))) {

            //check db

        }

        return failed;
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

    private boolean fetchGameInformation(Game game) throws InterruptedException {
        boolean result = false;

        try {
            nLock.lock();

            Pair p = new Pair(nLock.newCondition(), game);
            queue.addLast(p);

            if(queue.size() < MAX_TASKS){
                final GameJSON gameJSON = fetchInformation(p.getGame().getMD5());

                if((result = gameJSON != null))
                    copyInformation(game, gameJSON);

                queue.remove(p);

                return result;
            }

            do{
                try {
                    p.getmCondition().await();
                }catch (InterruptedException e){
                    if(p.isWoke()){
                        final GameJSON gameJSON = fetchInformation(p.getGame().getMD5());

                        if((result = gameJSON != null))
                            copyInformation(game, gameJSON);
                    }

                    queue.remove(p);

                    return result;
                }

                if(p.isWoke()){
                    final GameJSON gameJSON = fetchInformation(p.getGame().getMD5());

                    if((result = gameJSON != null))
                        copyInformation(game, gameJSON);

                    queue.remove(p);

                    return result;
                }

            }while (true);
        }finally {
            nLock.unlock();
        }
    }

    private void release(){
        try {
            nLock.lock();

            for (Pair p : queue) {
                if(!p.isWoke()){
                    p.setWoke(true);
                    p.getmCondition().signal();
                    return;
                }
            }

        }finally {
            nLock.unlock();
        }
    }

    private void copyInformation(Game game, GameJSON json){
        game.setName(json.getName());
        game.setDescription(json.getDescription());
        game.setDeveloper(json.getDeveloper());
        game.setGenre(json.getGenre());
        game.setReleased(json.getReleased());
        game.setCoverURL(json.getCover());
    }

    private GameJSON fetchInformation(String md5){
        initRetrofit();
        try {
            return request.registerDevice(md5).execute().body();
        } catch (IOException e) {
            return null;
        }
    }

    private synchronized void initRetrofit(){
        if(request == null)
            request = Web.getAPI();
    }
}
