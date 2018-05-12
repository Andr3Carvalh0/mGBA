package io.mgba.Model.Interfaces;

import java.util.List;

import io.mgba.Data.Database.Game;
import io.reactivex.Single;

public interface ILibrary {
    Single<List<Game>> prepareGames(int platform);
    Single<List<Game>> query(String query);

    Single<List<Game>> reloadGames(int... platform);
    Single<List<Game>> reloadGames(String path, int... platform);
}
