package io.mgba.main.fragments

import androidx.lifecycle.ViewModel
import io.mgba.data.local.model.Game

class GamesPresenter: ViewModel() {

    fun onClick(game: Game) {
        //view.handleItemClick(game)
    }


    fun loadGames() {
        /*disposable.add(databaseHelper.libraryService.prepareGames(platform)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { games ->
                    view.swapContent(games)
                    view.showContent(games.isNotEmpty())
                })*/
    }

    fun onRefresh() {
       /* disposable.add(iLibrary.libraryService.reloadGames(platform)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { games ->
                    view.swapContent(games)
                    view.stopRefreshing()
                }
        )*/
    }
}
