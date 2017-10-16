package io.mgba.View.Fragments.Main;

import java.util.List;

import io.mgba.Data.DTOs.Interface.Game;
import io.mgba.View.Fragments.Interfaces.ParentGameFragment;

public class GameboyAdvanceFragment extends ParentGameFragment {

    @Override
    protected List<? extends Game> getGames() {
        return controller.getGBAGames();
    }
}
