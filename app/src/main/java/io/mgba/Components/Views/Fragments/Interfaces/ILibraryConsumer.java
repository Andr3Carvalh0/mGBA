package io.mgba.Components.Views.Fragments.Interfaces;

import java.util.List;

import io.mgba.Components.Views.Activities.Interfaces.ILibrary;
import io.mgba.Data.DTOs.Game;

public interface ILibraryConsumer {
    void consume(List<Game> list);
    void setOnClickCallback(ILibrary callback);
    void setLoading();
}
