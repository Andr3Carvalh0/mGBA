package io.mgba.model.io

import io.mgba.data.database.model.Cheat
import io.mgba.data.database.Database
import io.mgba.data.database.model.Game
import io.mgba.mgba
import io.mgba.model.interfaces.IDatabase
import io.mgba.mgba.Companion.printLog

object Repository : IDatabase {
    private val TAG = "RepositoryServ"
    private val database: Database = Database.getInstance(mgba.context)

    override val favouritesGames: List<Game> = database.gameDao().favouritesGames

    override val games: List<Game>
        get() {
            printLog(TAG, "Getting all games")
            return database.gameDao().games
        }

    override fun getGamesForPlatform(platform: Int): List<Game> = database.gameDao().getGamesForPlatform(platform)

    override fun insert(vararg games: Game) {
        database.gameDao().insert(*games)
    }

    override fun delete() {
        printLog(TAG, " deleting everything in db")
        database.gameDao().deleteAll()
    }

    override fun delete(game: Game) {
        printLog(TAG, game.getName() + " removing from db")
        database.gameDao().delete(game)
    }

    override fun queryForGames(query: String): List<Game> {
        printLog(TAG, "Querying db for $query")
        return database.gameDao().getGames(query)
    }

    override fun getGamesCheats(key: Game): List<Cheat> {
        printLog(TAG, "Getting all cheats for " + key.getName())
        return database.cheatDAO().getGamesCheats(key.file!!)
    }

    override fun deleteCheat(cheat: Cheat) {
        printLog(TAG, "Deleting cheat: " + cheat.name!!)
        database.cheatDAO().deleteCheat(cheat.id)
    }

    override fun insertCheat(cheat: Cheat) {
        printLog(TAG, "Inserting cheat: " + cheat.name!!)
        database.cheatDAO().insertCheat(cheat)
    }
}
