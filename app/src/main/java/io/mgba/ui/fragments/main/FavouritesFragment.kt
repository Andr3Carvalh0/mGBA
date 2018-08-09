package io.mgba.ui.fragments.main

import androidx.core.content.ContextCompat
import io.mgba.R
import kotlinx.android.synthetic.main.content_fragment.*

class FavouritesFragment : GameFragment() {

    override fun prepareDrawables() {
        main_image_content.setImageDrawable(ContextCompat.getDrawable(context!!, R.drawable.ic_favorite_grey_500_48dp))
        main_text_content.setText(R.string.no_favourites)
    }
}
