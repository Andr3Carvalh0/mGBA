package io.mgba.ui.fragments.main

import androidx.core.content.ContextCompat
import io.mgba.R
import kotlinx.android.synthetic.main.games_list_view.*

class FavouritesFragment : GameFragment() {

    override fun prepareDrawables() {
        noContentIcon.setImageDrawable(ContextCompat.getDrawable(context!!, R.drawable.ic_favorite_grey_500_48dp))
        noContentMessage.setText(R.string.no_favourites)
    }
}
