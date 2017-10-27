package io.mgba.Controllers.UI.Fragments.Main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import io.mgba.Controllers.UI.Fragments.Main.Interfaces.ParentGameFragment;
import io.mgba.Data.DTOs.Game;
import io.mgba.Data.Wrappers.LibraryLists;
import io.mgba.R;

public class FavouritesFragment extends ParentGameFragment {

    @Override
    protected List<Game> fetchGameList(LibraryLists libraryLists) {
        return libraryLists.getFavourites();
    }

    protected View prepareView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.favourites_fragment, container, false);
    }

}
