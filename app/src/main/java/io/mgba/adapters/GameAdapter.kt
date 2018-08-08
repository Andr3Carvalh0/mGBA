package io.mgba.adapters

import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import io.mgba.adapters.interfaces.SingleViewHolderAdapter
import io.mgba.data.database.Game
import io.mgba.R
import io.mgba.utilities.GlideUtils
import kotlinx.android.synthetic.main.game.view.*

class GameAdapter(private val view: Fragment, context: Context, private val onClick: (Game) -> Any, recyclerView: RecyclerView) : SingleViewHolderAdapter<Game>(context, recyclerView, R.layout.game,  { v -> ViewHolder(v) }) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val mItem = items[position]

        (holder as ViewHolder).gameTitle.visibility = if (mItem.coverURL == null) View.VISIBLE else View.GONE
        holder.gameCover.visibility = if (mItem.coverURL != null) View.VISIBLE else View.GONE

        if (mItem.coverURL == null) {
            holder.gameTitle.text = mItem.getName()
        } else {
            GlideUtils.init(view, mItem.coverURL!!)
                    .setPlaceholders(R.drawable.placeholder, R.drawable.error)
                    .build(holder.gameCover)
        }

        holder.masterContainer.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        onClick.invoke(items[getPositionBasedOnView(v)])
    }

    internal class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var gameCover = itemView.card_preview
        internal var gameTitle = itemView.card_title
        internal var masterContainer = itemView.master_container
    }

    companion object {
        private val TAG = "mgba:GameAdapter"
    }
}
