package io.mgba.presenter.interfaces


import io.mgba.data.database.model.Game
import io.mgba.ui.activities.interfaces.ILibrary

interface IGamesPresenter {

    val platform: Int

    fun onClick(game: Game): Any
    fun onDestroy()

    fun loadGames(databaseHelper: ILibrary)
    fun onRefresh(iLibrary: ILibrary)

}
