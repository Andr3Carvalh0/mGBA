package io.mgba.View.Fragments.Main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import io.mgba.Data.DTOs.Interface.Game;
import io.mgba.R;
import io.mgba.View.Activities.Interfaces.ILibrary;
import io.mgba.View.Fragments.Interfaces.BaseGameFragment;

public class FavouritesFragment extends BaseGameFragment {



    protected View prepareView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.favourites_fragment, container, false);
    }

    @Override
    protected List<? extends Game> getGames() {
        return getLibraryController().getFavourites();
    }
}
