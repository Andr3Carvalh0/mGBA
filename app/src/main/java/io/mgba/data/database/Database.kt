package io.mgba.data.database

import android.content.Context

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.mgba.data.database.converters.GameConverter
import io.mgba.data.database.daos.CheatDAO
import io.mgba.data.database.daos.GameDAO
import io.mgba.data.database.model.Cheat
import io.mgba.data.database.model.Game

@androidx.room.Database(entities = [Game::class, Cheat::class], version = 1)
@TypeConverters(GameConverter::class)
abstract class Database : RoomDatabase() {

    abstract fun gameDao(): GameDAO
    abstract fun cheatDAO(): CheatDAO

    companion object {
        private var instance: Database? = null

        fun getInstance(context: Context): Database {
            if (instance == null)
                instance = Room.databaseBuilder<Database>(context, Database::class.java, context.packageName)
                        .build()

            return instance!!
        }

        fun destroyInstance() {
            instance = null
        }
    }
}
