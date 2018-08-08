package io.mgba.adapters.interfaces

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class SingleViewHolderAdapter<T> : BaseAdapter<T> {
    protected val layout: Int
    private val createView: (View) -> RecyclerView.ViewHolder


    constructor(context: Context, recyclerView: RecyclerView, layout: Int, createView: (View) -> RecyclerView.ViewHolder) : super(context, recyclerView) {
        this.layout = layout
        this.createView = createView
    }

    constructor(items: List<T>, recyclerView: RecyclerView, context: Context, layout: Int, createView: (View) -> RecyclerView.ViewHolder) : super(items, recyclerView, context) {
        this.layout = layout
        this.createView = createView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return createView.invoke(inflatedView)
    }

}
