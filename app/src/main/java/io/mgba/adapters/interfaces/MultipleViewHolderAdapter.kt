package io.mgba.adapters.interfaces

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import java.util.HashMap
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView

abstract class MultipleViewHolderAdapter<T : IViewHolderCreator> : BaseAdapter<T> {

    private val layoutRouter: HashMap<Int, Int>
    private val holderCreator: (Int, View) -> RecyclerView.ViewHolder

    override fun getItemViewType(position: Int): Int {
        val item = items[position]
        return item.id
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layout = layoutRouter[viewType]
        val view = LayoutInflater.from(parent.context).inflate(layout!!, parent, false)

        return holderCreator.invoke(layout, view)
    }

    constructor(@NonNull context: Context, @NonNull recyclerView: RecyclerView,
                @NonNull layoutRouter: HashMap<Int, Int>,
                @NonNull holderCreator: (Int, View) -> RecyclerView.ViewHolder) : super(emptyList(), recyclerView, context) {
        this.layoutRouter = layoutRouter
        this.holderCreator = holderCreator
    }

    constructor(@NonNull items: List<T>, @NonNull recyclerView: RecyclerView, @NonNull context: Context,
                @NonNull layoutRouter: HashMap<Int, Int>,
                @NonNull holderCreator: (Int, View) -> RecyclerView.ViewHolder) : super(items, recyclerView, context) {
        this.layoutRouter = layoutRouter
        this.holderCreator = holderCreator
    }
}
