package io.mgba.adapters

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import io.mgba.adapters.interfaces.SingleViewHolderAdapter
import io.mgba.data.settings.Settings
import io.mgba.R
import kotlinx.android.synthetic.main.category_element.view.*

class SettingsAdapter(settings: List<Settings>, context: Context, private val onClick: (Settings) -> Any, recyclerView: RecyclerView) : SingleViewHolderAdapter<Settings>(settings, recyclerView, context, R.layout.category_element, {v -> ViewHolder(v) }) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]

        (holder as ViewHolder).icon!!.setImageDrawable(mCtx.getDrawable(item.resource))
        holder.title!!.text = item.title

        holder.container!!.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        val mItem = items[getPositionBasedOnView(v)]
        onClick.invoke(mItem)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal var icon = view.setting_icon
        internal var title = view.setting_title
        internal var container = view.container
    }

    companion object {
        private val TAG = "mgba:SettingsAdapter"
    }
}
