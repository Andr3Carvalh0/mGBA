package io.mgba.Presenter;

import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;

import org.lucasr.twowayview.layout.TwoWayView;

import io.mgba.Adapters.GameAdapter;
import io.mgba.Constants;
import io.mgba.Presenter.Interfaces.IGamesPresenter;
import io.mgba.Data.Database.Game;
import io.mgba.Data.Platform;
import io.mgba.R;
import io.mgba.UI.Fragments.Interfaces.IGamesFragment;
import io.mgba.mgba;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class GamesPresenter implements IGamesPresenter {
    private final IGamesFragment<Game> view;
    private CompositeDisposable disposable = new CompositeDisposable();
    private Platform platform;

    public GamesPresenter(Fragment context, IGamesFragment<Game> view) {
        this.view = view;
        this.platform = (Platform) context.getArguments().getSerializable(Constants.ARG_PLATFORM);
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
                .subscribe(games -> disposable.add(
                                    updateView()
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(n -> {
                                        view.swapContent(games);
                                        view.showContent(games.size() > 0);
                                    })
                            )
                ));
    }

    // A do nothing kinda of method just so I can update UI.
    // On a rx thread that need to be non UI.
    private Single<Boolean> updateView(){
        Single<Boolean> ret = Single.create(s -> s.onSuccess(true));
        return ret.doOnError(mgba::report);
    }

    @Override
    public void onRefresh(io.mgba.UI.Activities.Interfaces.ILibrary iLibrary) {
        disposable.add(iLibrary.getLibraryService().reloadGames(platform)
                .subscribeOn(Schedulers.computation())
                .subscribe(games -> disposable.add(
                                    updateView()
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(n -> {
                                        view.swapContent(games);
                                        view.stopRefreshing();
                                    })
                        )
                ));
    }

    @Override
    public Consumer<Game> getOnClick() {
        return view::handleItemClick;
    }
}
