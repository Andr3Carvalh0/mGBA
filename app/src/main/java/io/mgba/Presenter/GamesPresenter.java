package io.mgba.Presenter;


import io.mgba.Presenter.Interfaces.IGamesPresenter;
import io.mgba.Data.Database.Game;
import io.mgba.Data.Platform;
import io.mgba.UI.Fragments.Interfaces.IGamesFragment;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class GamesPresenter implements IGamesPresenter {
    private final IGamesFragment<Game> view;
    private CompositeDisposable disposable = new CompositeDisposable();
    private Platform platform;

    public GamesPresenter(Platform platform, IGamesFragment<Game> view) {
        this.view = view;
        this.platform = platform;
    }

    @Override
    public void onDestroy() {
        disposable.dispose();
    }

    @Override
    public Platform getPlatform() {
        return platform;
    }

    @Override
    public void loadGames(io.mgba.UI.Activities.Interfaces.ILibrary databaseHelper) {
        disposable.add(databaseHelper.getLibraryService().prepareGames(platform)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(games -> {
                    view.swapContent(games);
                    view.showContent(games.size() > 0);
                }));
    }

    @Override
    public void onRefresh(io.mgba.UI.Activities.Interfaces.ILibrary iLibrary) {
        disposable.add(iLibrary.getLibraryService().reloadGames(platform)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(games -> {
                            view.swapContent(games);
                            view.stopRefreshing();
                        })
                );
    }

    @Override
    public Consumer<Game> getOnClick() {
        return view::handleItemClick;
    }
}
