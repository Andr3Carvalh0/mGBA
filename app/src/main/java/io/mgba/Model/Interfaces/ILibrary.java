package io.mgba.Model.Interfaces;

import java.util.List;

import io.mgba.Data.Database.Game;
import io.mgba.Data.Platform;
import io.reactivex.Single;

public interface ILibrary {
    Single<List<Game>> prepareGames(Platform platform);
    Single<List<Game>> reloadGames(Platform... platform);
    Single<List<Game>> query(String query);
}
