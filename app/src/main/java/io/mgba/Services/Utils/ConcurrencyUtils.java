package io.mgba.Services.Utils;

import com.google.common.base.Function;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConcurrencyUtils<T> {

    private final Lock nLock;
    private final LinkedList<Pair> queue;
    //Only support MAX_TASKS simultaneously
    private final int MAX_TASKS = 4;

    private class Pair{
        private Condition mCondition;
        private T game;
        private boolean woke;

        public Pair(Condition mCondition, T game) {
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

        public T getGame() {
            return game;
        }
    }

    public ConcurrencyUtils() {
        this.nLock = new ReentrantLock();
        this.queue = new LinkedList<>();
    }

    public T update(T game, Function<T, T> fetcher) {
        try {
            nLock.lock();

            Pair p = new Pair(nLock.newCondition(), game);
            queue.addLast(p);

            if(queue.size() < MAX_TASKS){
                final T gam = fetcher.apply(p.getGame());
                queue.remove(p);

                return gam;
            }

            do{
                try {
                    p.getmCondition().await();
                }catch (InterruptedException e){
                    if(p.isWoke()){
                        final T gam = fetcher.apply(p.getGame());
                        queue.remove(p);

                        return gam;
                    }

                    queue.remove(p);

                    return null;
                }

                if(p.isWoke()){
                    final T gam = fetcher.apply(p.getGame());
                    queue.remove(p);

                    return gam;
                }

            }while (true);
        }finally {
            nLock.unlock();
        }
    }

    public void release(){
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

}
