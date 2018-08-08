package io.mgba.model.interfaces


import io.mgba.data.database.Cheat
import io.mgba.data.database.Game

interface IDatabase {
    val favouritesGames: List<Game>
    val games: List<Game>
    fun getGamesForPlatform(platform: Int): List<Game>
    fun insert(vararg games: Game)
    fun delete()
    fun delete(game: Game)
    fun queryForGames(query: String): List<Game>

    fun getGamesCheats(key: Game): List<Cheat>
    fun deleteCheat(cheat: Cheat)
    fun insertCheat(cheat: Cheat)
}
