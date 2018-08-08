package io.mgba.model.io

import android.content.Context
import io.mgba.data.database.Cheat
import io.mgba.data.database.Database
import io.mgba.data.database.Game
import io.mgba.model.interfaces.IDatabase
import io.mgba.mgba.Companion.printLog

class LocalDB : IDatabase {
    private var db: Database? = null

    override val favouritesGames: List<Game>
        get() = db!!.gameDao().favouritesGames

    override val games: List<Game>
        get() {
            printLog(TAG, "Getting all games")
            return db!!.gameDao().games
        }

    constructor(context: Context) {
        db = Database.getInstance(context)
    }

    constructor(database: Database) {
        db = database
    }

    override fun getGamesForPlatform(platform: Int): List<Game> {
        return db!!.gameDao().getGamesForPlatform(platform)
    }

    override fun insert(vararg games: Game) {
        db!!.gameDao().insert(*games)
    }

    override fun delete() {
        printLog(TAG, " deleting everything in db")
        db!!.gameDao().deleteAll()
    }

    override fun delete(game: Game) {
        printLog(TAG, game.getName() + " removing from db")
        db!!.gameDao().delete(game)
    }

    override fun queryForGames(query: String): List<Game> {
        printLog(TAG, "Querying db for $query")
        return db!!.gameDao().getGames(query)
    }

    override fun getGamesCheats(key: Game): List<Cheat> {
        printLog(TAG, "Getting all cheats for " + key.getName())
        return db!!.cheatDAO().getGamesCheats(key.file!!)
    }

    override fun deleteCheat(cheat: Cheat) {
        printLog(TAG, "Deleting cheat: " + cheat.name!!)
        db!!.cheatDAO().deleteCheat(cheat.id)
    }

    override fun insertCheat(cheat: Cheat) {
        printLog(TAG, "Inserting cheat: " + cheat.name!!)
        db!!.cheatDAO().insertCheat(cheat)
    }

    companion object {
        private val TAG = "LocalDBSer"
    }
}
