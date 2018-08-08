package io.mgba.ui.fragments.main

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import io.mgba.adapters.GameAdapter
import io.mgba.Constants
import io.mgba.presenter.GamesPresenter
import io.mgba.presenter.interfaces.IGamesPresenter
import io.mgba.data.database.Game
import io.mgba.R
import io.mgba.ui.fragments.interfaces.IGamesFragment
import io.mgba.ui.views.GameInformationView
import io.mgba.mgba
import kotlinx.android.synthetic.main.content_fragment.*

open class GameFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener, IGamesFragment<Game> {
    private var adapter: GameAdapter? = null
    protected var controller: IGamesPresenter? = null

    private val iLibrary: io.mgba.ui.activities.interfaces.ILibrary
        get() = activity as io.mgba.ui.activities.interfaces.ILibrary


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val mView = prepareView(inflater, container)

        if(arguments == null)
            mgba.report(Exception("Game Fragment creation failed because args are null"))

        val platform = arguments!!.getInt(Constants.ARG_PLATFORM)
        controller = GamesPresenter(platform, this)

        prepareDrawables()
        prepareRecyclerView()

        if (savedInstanceState != null)
            main_contentlist.layoutManager!!
                    .onRestoreInstanceState(savedInstanceState.getParcelable<Parcelable>(Constants.MAIN_RECYCLER_CONTENT))

        showContent(false)
        loadGames()

        return mView
    }

    protected fun prepareRecyclerView() {
        main_refresh.setOnRefreshListener(this)
        main_refresh.setColorSchemeColors(resources.getColor(R.color.pink_accent_color),
                resources.getColor(R.color.colorPrimary),
                resources.getColor(R.color.green_accent_color),
                resources.getColor(R.color.yellow_accent_color),
                resources.getColor(R.color.cyan_accent_color))
        main_contentlist.setHasFixedSize(true)
        adapter = GameAdapter(this, context!!, {controller!!.onClick(it)}, main_contentlist)
        main_contentlist.adapter = adapter

    }

    protected open fun prepareDrawables() {
        main_image_content.setImageDrawable(resources.getDrawable(R.drawable.ic_videogame_asset_grey_500_48dp))
        main_text_content.setText(R.string.no_games)
    }

    override fun showContent(state: Boolean) {
        main_nocontent_container.visibility = if (state) View.GONE else View.VISIBLE
        main_contentlist.visibility = if (state) View.VISIBLE else View.GONE
    }

    private fun prepareView(inflater: LayoutInflater?, container: ViewGroup?): View {
        return inflater!!.inflate(R.layout.content_fragment, container, false)
    }

    private fun loadGames() {
        controller!!.loadGames(iLibrary)
    }

    override fun onRefresh() {
        controller!!.onRefresh(iLibrary)
    }

    override fun onStop() {
        super.onStop()
        controller!!.onDestroy()
    }

    override fun swapContent(items: List<Game>) {
        showContent(items.isNotEmpty())
        adapter!!.swap(items)
    }

    override fun stopRefreshing() {
        main_refresh.isRefreshing = false
    }


    override fun handleItemClick(game: Game) {
        val sheet = GameInformationView.newInstance(game)
        sheet.show(fragmentManager, TAG + "_SHEET")
    }

    override fun onSaveInstanceState(@NonNull outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(Constants.MAIN_RECYCLER_CONTENT, main_contentlist.layoutManager!!.onSaveInstanceState())

    }

    companion object {
        private const val TAG = "BaseFragment"
    }
}
