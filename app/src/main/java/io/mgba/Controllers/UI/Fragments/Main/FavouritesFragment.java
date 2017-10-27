package io.mgba.Controllers.UI.Fragments.Main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.mgba.Controllers.UI.Fragments.Main.Interfaces.BaseGameFragment;
import io.mgba.R;

public class FavouritesFragment extends BaseGameFragment {

    protected View prepareView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.favourites_fragment, container, false);
    }

}
