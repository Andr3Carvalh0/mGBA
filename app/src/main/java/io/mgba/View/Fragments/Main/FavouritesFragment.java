package io.mgba.View.Fragments.Main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.mgba.R;
import io.mgba.View.Fragments.Interfaces.ParentFragment;

public class FavouritesFragment extends ParentFragment {

    protected View prepareView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.favourites_fragment, container, false);
    }
}
