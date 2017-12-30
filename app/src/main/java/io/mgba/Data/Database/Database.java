package io.mgba.Data.Database;


import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import io.mgba.Data.Database.Converters.GameConverter;
import io.mgba.Data.Database.DAOs.GameDAO;

@android.arch.persistence.room.Database(entities = {Game.class}, version = 4)
@TypeConverters(GameConverter.class)
public abstract class Database extends RoomDatabase {
    public abstract GameDAO gameDao();
}
