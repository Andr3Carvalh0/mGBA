package io.mgba.settings.categories.recyclerView

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import io.mgba.data.settings.Settings
import io.mgba.R
import io.mgba.base.BaseAdapter
import kotlinx.android.synthetic.main.settings_category_element.view.*

class SettingsAdapter(val context: Context, val onClick: (Settings) -> Unit) : BaseAdapter<Settings>(R.layout.settings_category_element) {

    override fun prepareViewHolder(parent: ViewGroup, viewType: Int): BaseAdapter.ViewHolder<Settings> {
        return ViewHolder(LayoutInflater.from(context).inflate(layout, parent, false), context, onClick)
    }

    class ViewHolder(itemView: View, val context: Context, val onClick: (Settings) -> Unit) : BaseAdapter.ViewHolder<Settings>(itemView) {
        override fun bind(item: Settings) {
            val icon = itemView.icon
            val title = itemView.title

            itemView.container.setOnClickListener { onClick.invoke(item) }
            icon.setImageDrawable(ContextCompat.getDrawable(context, item.resource))
            title.text = item.title
        }
    }

    companion object {
        private val TAG = "mgba:SettingsAdapter"
    }
}
