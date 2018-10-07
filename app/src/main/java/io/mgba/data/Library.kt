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


    private fun save(game: Game) { runOnBackground { gamesRepository.insert(game) } }
    private fun remove(game: Game) { runOnBackground { gamesRepository.delete(game) } }

    private fun query(query: String): LiveData<List<Game>> = gamesRepository.query(query)

    private fun monitorGameboyAdvanced(): LiveData<List<Game>> = gamesRepository.monitorGameboyAdvancedGames()
    private fun monitorGameboyColor(): LiveData<List<Game>> = gamesRepository.monitorGameboyColorGames()
    private fun monitorFavourites(): LiveData<List<Game>> = gamesRepository.monitorFavouriteGames()


    private fun searchWeb(game: Game) {
        try {
            val json = getGamesService().getGameInformation(game.mD5!!, getDeviceLanguage())
                                             .execute()
                                             .body()
        } catch (e: Exception) {
            mgba.report(e)
        }

    }


}
