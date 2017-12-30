package io.mgba.Activities.Interfaces;

import io.mgba.Data.Database.Game;

public interface ILibrary {
    void showBottomSheet(Game game);
    io.mgba.Model.Interfaces.ILibrary getLibraryService();
}
