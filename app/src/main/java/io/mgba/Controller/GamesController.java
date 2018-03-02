package io.mgba.Controller;

import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;

import org.lucasr.twowayview.layout.TwoWayView;

import java.util.LinkedList;
import java.util.List;

import io.mgba.Adapters.GameAdapter;
import io.mgba.Constants;
import io.mgba.Controller.Interfaces.IGamesController;
import io.mgba.Data.Database.Game;
import io.mgba.Data.Platform;
import io.mgba.R;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class GamesController implements IGamesController {
    private final Fragment context;
    private CompositeDisposable disposable = new CompositeDisposable();
    private Platform platform;
    private GameAdapter adapter;

    public GamesController(Fragment context) {
        this.context = context;
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
    public void loadGames(io.mgba.UI.Activities.Interfaces.ILibrary databaseHelper, Consumer<Boolean> showContent) {
        disposable.add(databaseHelper.getLibraryService().prepareGames(platform)
                .subscribeOn(Schedulers.io())
                .subscribe(games -> disposable.add(
                                    updateView()
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(n -> {
                                        adapter.swap(games);
                                        showContent.accept(games.size() > 0);

                                    })
                            )
                ));
    }

    // A do nothing kinda of method just so I can update UI.
    // On a rx thread that need to be non UI.
    private Single<Boolean> updateView(){
        return Single.create(s -> s.onSuccess(true));
    }

    @Override
    public void showOnClick(Game game, io.mgba.UI.Activities.Interfaces.ILibrary iLibrary) {
        iLibrary.showBottomSheet(game);
    }

    @Override
    public void onRefresh(io.mgba.UI.Activities.Interfaces.ILibrary iLibrary, SwipeRefreshLayout mSwipeRefreshLayout) {
        disposable.add(iLibrary.getLibraryService().reloadGames(platform)
                .subscribeOn(Schedulers.computation())
                .subscribe(games -> disposable.add(
                                    updateView()
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(n -> {
                                        adapter.swap(games);
                                        mSwipeRefreshLayout.setRefreshing(false);
                                    })
                        )
                ));
    }

    @Override
    public void prepareRecyclerView(SwipeRefreshLayout mSwipeRefreshLayout, TwoWayView mRecyclerView, SwipeRefreshLayout.OnRefreshListener onRefreshListener, Consumer<Game> onClick) {
        mSwipeRefreshLayout.setOnRefreshListener(onRefreshListener);
        mSwipeRefreshLayout.setColorSchemeColors(context.getResources().getColor(R.color.pink_accent_color),
                context.getResources().getColor(R.color.colorPrimary),
                context.getResources().getColor(R.color.green_accent_color),
                context.getResources().getColor(R.color.yellow_accent_color),
                context.getResources().getColor(R.color.cyan_accent_color));
        mRecyclerView.setHasFixedSize(true);
        adapter = new GameAdapter(context, context.getContext(), onClick, mRecyclerView);
        mRecyclerView.setAdapter(adapter);
    }
}
