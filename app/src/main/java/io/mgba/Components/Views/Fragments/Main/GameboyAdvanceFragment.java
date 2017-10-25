package io.mgba.Components.Views.Fragments.Main;

import java.util.List;

import io.mgba.Components.Views.Fragments.Interfaces.ParentGameFragment;
import io.mgba.Data.DTOs.Game;

public class GameboyAdvanceFragment extends ParentGameFragment {

    private List<Game> games;

    protected void setGameList(List<Game> list){
        this.games = games;
    }
}
