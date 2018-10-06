package io.mgba.adapters.interfaces

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.lucasr.twowayview.listeners.SectionIndexer

abstract class BaseAdapter<T>(protected var items: List<T>, private val instance: RecyclerView, protected var context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), View.OnClickListener, SectionIndexer {

    abstract override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder

    protected fun getPositionBasedOnView(v: View): Int {
        return instance.getChildAdapterPosition(v)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun swap(items: List<T>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun getLetterForItem(position: Int): String {
        if(items.isNotEmpty() && items.size < position)
            return items.toString()
                        .toCharArray()[0]
                        .toUpperCase()
                        .toString()

        return "?"
    }

}