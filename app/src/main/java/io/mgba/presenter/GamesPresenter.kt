package io.mgba.presenter

import io.mgba.presenter.interfaces.IGamesPresenter
import io.mgba.data.database.model.Game
import io.mgba.ui.fragments.interfaces.IGamesFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class GamesPresenter(override val platform: Int, private val view: IGamesFragment<Game>) : IGamesPresenter {
    private val disposable = CompositeDisposable()

    override fun onClick(game: Game) {
        view.handleItemClick(game)
    }

    override fun onDestroy() {
        disposable.dispose()
    }

    override fun loadGames(databaseHelper: io.mgba.ui.activities.interfaces.ILibrary) {
        disposable.add(databaseHelper.libraryService.prepareGames(platform)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { games ->
                    view.swapContent(games)
                    view.showContent(games.size > 0)
                })
    }

    override fun onRefresh(iLibrary: io.mgba.ui.activities.interfaces.ILibrary) {
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
