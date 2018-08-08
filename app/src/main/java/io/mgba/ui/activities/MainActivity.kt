package io.mgba.ui.activities

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.MenuItem
import com.arlib.floatingsearchview.FloatingSearchView
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion
import com.google.android.material.tabs.TabLayout
import com.mikepenz.aboutlibraries.LibsBuilder
import androidx.appcompat.app.AppCompatActivity
import io.mgba.adapters.TabViewPager
import io.mgba.Constants
import io.mgba.presenter.interfaces.IMainPresenter
import io.mgba.presenter.MainPresenter
import io.mgba.model.interfaces.ILibrary
import io.mgba.presenter.MainPresenter.Companion.DEFAULT_PANEL
import io.mgba.R
import io.mgba.ui.activities.interfaces.IMainView
import io.mgba.utilities.IDependencyInjector
import kotlinx.android.synthetic.main.activity_library.*

class MainActivity : AppCompatActivity(), TabLayout.OnTabSelectedListener, io.mgba.ui.activities.interfaces.ILibrary, FloatingSearchView.OnMenuItemClickListener, FloatingSearchView.OnQueryChangeListener, FloatingSearchView.OnSearchListener, IMainView {

    private var controller: IMainPresenter? = null

    override val libraryService: ILibrary
        get() = controller!!.iLibrary!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_library)

        controller = MainPresenter(this, application as IDependencyInjector)

        prepareToolbar()
        prepareViewPager()

        if (savedInstanceState != null) {
            floating_search_view.onRestoreInstanceState(savedInstanceState.getParcelable<Parcelable>(Constants.MAIN_TOOLBAR_STATE))
            pager.onRestoreInstanceState(savedInstanceState.getParcelable<Parcelable>(Constants.MAIN_VIEWPAGE_STATE))
        }
    }

    private fun prepareToolbar() {
        floating_search_view.setOnMenuItemClickListener(this)
        floating_search_view.setOnSearchListener(this)
        floating_search_view.setOnQueryChangeListener(this)
    }

    private fun prepareViewPager() {
        tabLayout!!.addTab(tabLayout!!.newTab().setText(getString(R.string.Favorites)))
        tabLayout!!.addTab(tabLayout!!.newTab().setText(getString(R.string.GBA)))
        tabLayout!!.addTab(tabLayout!!.newTab().setText(getString(R.string.GBC)))
        tabLayout!!.tabGravity = TabLayout.GRAVITY_FILL

        val adapter = TabViewPager(supportFragmentManager)

        pager.adapter = adapter
        pager.currentItem = DEFAULT_PANEL

        pager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(this)
    }

    override fun onTabSelected(tab: TabLayout.Tab) {
        pager.currentItem = tab.position
    }

    override fun onTabUnselected(tab: TabLayout.Tab) {

    }

    override fun onTabReselected(tab: TabLayout.Tab) {
        pager.currentItem = tab.position
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        controller!!.onActivityResult(requestCode, resultCode, intent!!)
    }

    override fun onStop() {
        super.onStop()
        controller!!.onDestroy()
    }

    override fun onSearchTextChanged(oldQuery: String, newQuery: String) {
        controller!!.onSearchTextChanged(oldQuery, newQuery)
    }

    override fun onActionMenuItemSelected(item: MenuItem) {
        controller!!.onMenuItemSelected(item)
    }

    override fun clearSuggestions() {
        floating_search_view.clearSuggestions()
    }

    override fun showSuggestions(list: List<SearchSuggestion>) {
        floating_search_view.swapSuggestions(list)
        floating_search_view.hideProgress()
    }

    override fun startAboutPanel(aboutPanel: LibsBuilder) {
        aboutPanel.start(this)
    }

    override fun startActivityForResult(activity: Class<out AppCompatActivity>, code: Int) {
        val it = Intent(this, activity)
        startActivityForResult(it, code)
    }

    override fun showProgress() {
        floating_search_view.showProgress()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(Constants.MAIN_TOOLBAR_STATE, floating_search_view.onSaveInstanceState())
        outState.putParcelable(Constants.MAIN_VIEWPAGE_STATE, pager.onSaveInstanceState())
    }

    override fun onSuggestionClicked(searchSuggestion: SearchSuggestion) {

    }

    override fun onSearchAction(currentQuery: String) {

    }

}
