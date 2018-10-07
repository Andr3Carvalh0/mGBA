package io.mgba.data.settings

import io.mgba.widgets.RecyclerViewItem

class Settings(val title: String, val resource: Int): RecyclerViewItem {
    override fun getLetterForItem(): String = title.substring(0, 1).toUpperCase()
}
