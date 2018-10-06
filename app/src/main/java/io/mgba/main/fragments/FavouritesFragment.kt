package io.mgba.main.fragments

import androidx.core.content.ContextCompat
import io.mgba.R
import kotlinx.android.synthetic.main.games_list_view.*

class FavouritesFragment : GameFragment() {

    override fun prepareDrawables() {
        noContentIcon.setImageDrawable(ContextCompat.getDrawable(context!!, R.drawable.ic_favorite))
        noContentMessage.setText(R.string.no_favourites)
    }
}
