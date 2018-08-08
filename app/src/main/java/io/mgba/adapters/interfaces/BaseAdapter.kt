package io.mgba.adapters.interfaces

import android.content.Context
import android.view.View
import android.view.ViewGroup
import java.util.LinkedList

import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T>(items: List<T>, private val instance: RecyclerView, protected var mCtx: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), View.OnClickListener {
    protected var items: List<T> = LinkedList()

    constructor(context: Context, recyclerView: RecyclerView) : this(LinkedList<T>(), recyclerView, context) {}

    init {
        this.items = items
    }

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
}