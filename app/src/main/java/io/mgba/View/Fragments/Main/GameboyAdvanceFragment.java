package io.mgba.View.Fragments.Main;

import java.util.List;

import io.mgba.Data.DTOs.Interface.Game;
import io.mgba.View.Activities.Interfaces.ILibrary;
import io.mgba.View.Fragments.Interfaces.BaseGameFragment;

public class GameboyAdvanceFragment extends BaseGameFragment {

    @Override
    protected List<? extends Game> getGames() {

        return getLibraryController().getGBAGames();
    }
}
