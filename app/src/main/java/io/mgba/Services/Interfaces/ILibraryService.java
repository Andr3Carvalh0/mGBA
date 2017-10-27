package io.mgba.Services.Interfaces;

import com.google.common.base.Function;

import io.mgba.Data.Wrappers.LibraryLists;

public interface ILibraryService {
    void prepareGames(Function<LibraryLists, Void> callback);
    void stop();
    void updateFileServicePath(String path);

}
