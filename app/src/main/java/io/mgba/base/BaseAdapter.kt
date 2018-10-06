package io.mgba.base

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.mgba.widgets.RecyclerViewItem
import kotlin.collections.ArrayList
import org.lucasr.twowayview.listeners.SectionIndexer

abstract class BaseAdapter<T>(var layout: Int) : RecyclerView.Adapter<BaseAdapter.ViewHolder<T>>() where T: RecyclerViewItem{

    var data: ArrayList<T> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<T> {
        return prepareViewHolder(parent, viewType)
    }

    abstract fun prepareViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<T>

    override fun onBindViewHolder(holder: ViewHolder<T>, position: Int) = holder.bind(data[position])

    override fun getItemCount() = data.size

    fun removeItem(item: T) {
        val pos = getItemPosition(item)
        data.remove(item)

        if(pos != -1)
            notifyItemRemoved(pos)
    }

    fun addItem(item: T) {
        data.add(item)
        notifyItemChanged(data.size)
    }

    fun addItems(items: ArrayList<T>) {
        val size = data.size
        data.addAll(items)
        notifyItemRangeChanged(size - 1 , items.size)
    }

    fun changeItems(items: ArrayList<T>) {
        data = items
        notifyDataSetChanged()
    }

    private fun getItemPosition(item: T): Int {
        for ((i, it) in data.withIndex()){
            if(it == item)
                return i
        }

        return -1
    }

    fun removeItems() {
        data.clear()
        notifyDataSetChanged()
    }

    abstract class ViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {

        open fun bind(item: T) = with(itemView) {}
    }
}