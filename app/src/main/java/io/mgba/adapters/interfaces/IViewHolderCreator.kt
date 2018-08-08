package io.mgba.adapters.interfaces


import androidx.recyclerview.widget.RecyclerView

interface IViewHolderCreator {
    val id: Int
    fun draw(holder: RecyclerView.ViewHolder)
    fun onClick()
}
