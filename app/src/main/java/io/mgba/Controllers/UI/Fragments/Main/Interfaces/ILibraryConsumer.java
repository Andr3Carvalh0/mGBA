package io.mgba.Controllers.UI.Fragments.Main.Interfaces;

import java.util.List;

import io.mgba.Controllers.UI.Activities.Interfaces.ILibrary;
import io.mgba.Data.DTOs.Game;

public interface ILibraryConsumer {
    void consume(List<Game> list);
    void setOnClickCallback(ILibrary callback);
    void setLoading();
}
