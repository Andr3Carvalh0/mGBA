package io.mgba.UI.Fragments.Interfaces;

import java.util.List;

import io.mgba.Data.Database.Game;

public interface IGamesFragment<T> {
    void swapContent(List<T> items);
    void stopRefreshing();
    void showContent(boolean state);
    void handleItemClick(Game game);
}
