package io.mgba.Presenter.Interfaces;

import android.support.v4.widget.SwipeRefreshLayout;

import org.lucasr.twowayview.layout.TwoWayView;

import io.mgba.Data.Database.Game;
import io.mgba.Data.Platform;
import io.mgba.UI.Activities.Interfaces.ILibrary;
import io.reactivex.functions.Consumer;

public interface IGamesPresenter {

    void onDestroy();

    Platform getPlatform();

    void loadGames(ILibrary databaseHelper);
    void onRefresh(ILibrary iLibrary);

    Consumer<Game> getOnClick();

}
