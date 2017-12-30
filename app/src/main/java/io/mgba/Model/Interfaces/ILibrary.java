package io.mgba.Model.Interfaces;

import java.util.List;

import io.mgba.Data.Database.Game;
import io.mgba.Data.Platform;
import io.reactivex.Observable;

public interface ILibrary {
    Observable<List<Game>> prepareGames(Platform platform);
    Observable<List<Game>> reloadGames(Platform... platform);
    Observable<List<Game>> query(String query);
}
