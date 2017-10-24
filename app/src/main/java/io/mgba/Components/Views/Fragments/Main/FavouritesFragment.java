package io.mgba.Components.Views.Fragments.Main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.mgba.Components.Views.Fragments.Interfaces.ParentGameFragment;
import io.mgba.R;

public class FavouritesFragment extends ParentGameFragment {

    protected View prepareView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.favourites_fragment, container, false);
    }

}
