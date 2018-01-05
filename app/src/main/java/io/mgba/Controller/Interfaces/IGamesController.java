package io.mgba.Controller.Interfaces;

import android.support.v4.widget.SwipeRefreshLayout;

import org.lucasr.twowayview.layout.TwoWayView;

import io.mgba.Data.Database.Game;
import io.mgba.Data.Platform;
import io.mgba.UI.Activities.Interfaces.ILibrary;
import io.reactivex.functions.Consumer;

public interface IGamesController {

    void onDestroy();

    Platform getPlatform();

    void loadGames(ILibrary databaseHelper, Consumer<Boolean> showContent);

    void showOnClick(Game game, ILibrary iLibrary);

    void onRefresh(ILibrary iLibrary, SwipeRefreshLayout mSwipeRefreshLayout);

    void prepareRecyclerView(SwipeRefreshLayout mSwipeRefreshLayout, TwoWayView mRecyclerView, SwipeRefreshLayout.OnRefreshListener onRefreshListener,  Consumer<Game> onClick);
}
