package io.mgba.Services.Interfaces;

import java.util.List;

import io.mgba.Data.DTOs.Game;
import io.mgba.Data.Platform;
import rx.Observable;

public interface ILibraryService {
    Observable<List<Game>> prepareGames(Platform platform);
    Observable<List<Game>> reloadGames(Platform platform);
}
