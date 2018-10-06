package io.mgba.main.fragments

import androidx.lifecycle.ViewModel
import io.mgba.presenter.interfaces.IGamesPresenter
import io.mgba.data.local.database.model.Game
import io.mgba.ui.fragments.interfaces.IGamesFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class GamesPresenter: ViewModel() {
    private val disposable = CompositeDisposable()

    fun onClick(game: Game) {
        view.handleItemClick(game)
    }

    fun onDestroy() {
        disposable.dispose()
    }

    fun loadGames(databaseHelper: io.mgba.ui.activities.interfaces.ILibrary) {
        disposable.add(databaseHelper.libraryService.prepareGames(platform)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { games ->
                    view.swapContent(games)
                    view.showContent(games.isNotEmpty())
                })
    }

    fun onRefresh(iLibrary: io.mgba.ui.activities.interfaces.ILibrary) {
        disposable.add(iLibrary.libraryService.reloadGames(platform)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { games ->
                    view.swapContent(games)
                    view.stopRefreshing()
                }
        )
    }
}
