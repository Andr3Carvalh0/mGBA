package io.mgba.main.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.mgba.data.local.database.model.Game
import io.mgba.R
import io.mgba.base.BaseAdapter
import io.mgba.utilities.GlideUtils
import kotlinx.android.synthetic.main.game_item.view.*


class GameAdapter(val context: Context, val onClick: (Game) -> Unit) : BaseAdapter<Game>(R.layout.game_item) {

    override fun prepareViewHolder(parent: ViewGroup, viewType: Int): BaseAdapter.ViewHolder<Game> {
        return ViewHolder(LayoutInflater.from(context).inflate(layout, parent, false), context, onClick)
    }

    class ViewHolder(itemView: View, val context: Context, val onClick: (Game) -> Unit) : BaseAdapter.ViewHolder<Game>(itemView) {
        override fun bind(item: Game) {
            val cover = itemView.cover
            val title = itemView.title

            itemView.container.setOnClickListener { onClick.invoke(item) }

            title.visibility = if (item.coverURL == null) View.VISIBLE else View.GONE
            cover.visibility = if (item.coverURL != null) View.VISIBLE else View.GONE

            if (item.coverURL == null) {
                title.text = item.getName()
            } else {
                item.coverURL?.let {
                    GlideUtils.init(itemView, it)
                            .setPlaceholders(R.drawable.placeholder, R.drawable.error)
                            .build(cover)
                }
            }
        }
    }

    companion object {
        private val TAG = "mgba:SettingsAdapter"
    }
}