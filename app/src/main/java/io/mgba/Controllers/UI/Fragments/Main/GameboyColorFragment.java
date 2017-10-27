package io.mgba.Controllers.UI.Fragments.Main;

import java.util.List;

import io.mgba.Controllers.UI.Fragments.Main.Interfaces.ParentGameFragment;
import io.mgba.Data.DTOs.Game;
import io.mgba.Data.Wrappers.LibraryLists;


public class GameboyColorFragment extends ParentGameFragment {
    @Override
    protected List<Game> fetchGameList(LibraryLists libraryLists) {
        return libraryLists.getGbc();
    }
}
