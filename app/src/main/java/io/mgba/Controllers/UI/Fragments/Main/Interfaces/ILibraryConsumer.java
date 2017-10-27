package io.mgba.Controllers.UI.Fragments.Main.Interfaces;

import java.util.ArrayList;

import io.mgba.Controllers.UI.Activities.Interfaces.ILibrary;
import io.mgba.Data.DTOs.Game;

public interface ILibraryConsumer {
    void consume(ArrayList<Game> list);
    void setOnClickCallback(ILibrary callback);
    void setLoading();
}
