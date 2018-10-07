package io.mgba.data

import androidx.lifecycle.LiveData
import io.mgba.data.local.Database
import io.mgba.data.remote.interfaces.getGamesService
import io.mgba.mgba
import io.mgba.data.local.model.Game
import io.mgba.utilities.device.getDeviceLanguage
import io.mgba.utilities.runOnBackground

object Library {

    private val gamesRepository = Database.getInstance(mgba.context).gameDao()
    private val cheatsRepository = Database.getInstance(mgba.context).cheatDAO()
    private val gamesService = getGamesService()


    fun save(game: Game) { runOnBackground { gamesRepository.insert(game) } }
    fun remove(game: Game) { runOnBackground { gamesRepository.delete(game) } }
    fun clear() { runOnBackground { gamesRepository.clearLibrary() }}

    fun query(query: String): LiveData<List<Game>> = gamesRepository.query(query)

    fun monitorGameboyAdvanced(): LiveData<List<Game>> = gamesRepository.monitorGameboyAdvancedGames()
    fun monitorGameboyColor(): LiveData<List<Game>> = gamesRepository.monitorGameboyColorGames()
    fun monitorFavourites(): LiveData<List<Game>> = gamesRepository.monitorFavouriteGames()

    fun populateGame(id: String) {
        val game = gamesRepository.get(id)

        game.mD5?.let {
            try {
                gamesService.getGameInformation(it, getDeviceLanguage())
                                                       .execute()
                                                       .body()
                                                       ?.let {
                                                            game.cover = it.cover
                                                            game.description = it.description
                                                            game.name = it.name
                                                            game.released = it.released
                                                            game.developer = it.developer
                                                            game.genre = it.genre
                                                       }

                gamesRepository.update(game)

            } catch (e: Exception) {
                mgba.report(e)
            }

        }


    }


}
