package io.mgba.Services.Interfaces;

import com.google.common.base.Function;

import java.util.ArrayList;

import io.mgba.Controller.Interfaces.LibraryLists;
import io.mgba.Data.DTOs.Game;

public interface ILibraryService {
    void getGames(Function<LibraryLists, Void> callback);
    void finalize(ArrayList<Game> gameList);
}
