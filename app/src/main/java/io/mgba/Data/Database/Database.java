package io.mgba.Data.Database;


import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import io.mgba.Data.Database.Converters.GameConverter;
import io.mgba.Data.Database.DAOs.GameDAO;

@android.arch.persistence.room.Database(entities = {Game.class}, version = 4)
@TypeConverters(GameConverter.class)
public abstract class Database extends RoomDatabase {
    private static Database instance;

    public abstract GameDAO gameDao();

    public static Database getInstance(Context context) {
        if(instance == null)
            instance = Room.databaseBuilder(context, Database.class, context.getPackageName()).build();

        return instance;
    }

    public static void destroyInstance(){
        instance = null;
    }
}
