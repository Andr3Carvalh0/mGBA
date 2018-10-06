package io.mgba.ui.fragments.interfaces

import io.mgba.data.local.database.model.Game

interface IGamesFragment<T> {
    fun swapContent(items: List<T>)
    fun stopRefreshing()
    fun showContent(state: Boolean)
    fun handleItemClick(game: Game)
}
