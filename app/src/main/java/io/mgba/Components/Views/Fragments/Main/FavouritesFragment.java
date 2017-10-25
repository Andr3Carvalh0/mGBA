package io.mgba.Components.Views.Fragments.Main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import io.mgba.Components.Views.Fragments.Interfaces.ParentGameFragment;
import io.mgba.Data.DTOs.Game;
import io.mgba.R;

public class FavouritesFragment extends ParentGameFragment {

    private List<Game> games;

    protected View prepareView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.favourites_fragment, container, false);
    }

    protected void setGameList(List<Game> list){
        this.games = games;
    }
}
