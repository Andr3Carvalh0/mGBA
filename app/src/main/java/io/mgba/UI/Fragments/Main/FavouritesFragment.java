package io.mgba.UI.Fragments.Main;

import io.mgba.R;

public class FavouritesFragment extends GameFragment {

    @Override
    protected void prepareDrawables() {
        noContentImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_grey_500_48dp));
        noContentMessage.setText(R.string.no_favourites);
    }
}
