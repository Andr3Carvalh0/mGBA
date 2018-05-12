package io.mgba.Presenter.Interfaces;


import io.mgba.Data.Database.Game;
import io.mgba.UI.Activities.Interfaces.ILibrary;
import io.reactivex.functions.Consumer;

public interface IGamesPresenter {

    void onDestroy();

    int getPlatform();

    void loadGames(ILibrary databaseHelper);
    void onRefresh(ILibrary iLibrary);

    Consumer<Game> getOnClick();

}
