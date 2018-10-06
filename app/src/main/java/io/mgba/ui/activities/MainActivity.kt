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
import kotlinx.android.synthetic.main.activity_library.*

class MainActivity(override val libraryService: ILibrary) : AppCompatActivity(), TabLayout.OnTabSelectedListener, io.mgba.ui.activities.interfaces.ILibrary, FloatingSearchView.OnMenuItemClickListener, FloatingSearchView.OnQueryChangeListener, FloatingSearchView.OnSearchListener, IMainView {

    private lateinit var controller: IMainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_library)

        controller = MainPresenter(this)

        prepareToolbar()
        prepareViewPager()

        if (savedInstanceState != null) {
            floatingSearchView.onRestoreInstanceState(savedInstanceState.getParcelable<Parcelable>(Constants.MAIN_TOOLBAR_STATE))
            pager.onRestoreInstanceState(savedInstanceState.getParcelable<Parcelable>(Constants.MAIN_VIEWPAGE_STATE))
        }
    }

    private fun prepareToolbar() {
        floatingSearchView.setOnMenuItemClickListener(this)
        floatingSearchView.setOnSearchListener(this)
        floatingSearchView.setOnQueryChangeListener(this)
    }

    private fun prepareViewPager() {
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.Favorites)))
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.GBA)))
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.GBC)))
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL

        val adapter = TabViewPager(supportFragmentManager)

        pager.adapter = adapter
        pager.currentItem = DEFAULT_PANEL

        pager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(this)
    }

    override fun onTabSelected(tab: TabLayout.Tab) {
        pager.currentItem = tab.position
    }

    override fun onTabReselected(tab: TabLayout.Tab) {
        pager.currentItem = tab.position
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        if(intent != null) controller.onActivityResult(requestCode, resultCode, intent)
    }

    override fun onStop() {
        super.onStop()
        controller.onDestroy()
    }

    override fun onTabUnselected(p0: TabLayout.Tab?) {}
    override fun onSuggestionClicked(searchSuggestion: SearchSuggestion?) {}
    override fun onSearchAction(currentQuery: String?) {}

    override fun onSearchTextChanged(oldQuery: String, newQuery: String) {
        controller.onSearchTextChanged(oldQuery, newQuery)
    }

    override fun onActionMenuItemSelected(item: MenuItem) {
        controller.onMenuItemSelected(item)
    }

    override fun clearSuggestions() {
        floatingSearchView.clearSuggestions()
    }

    override fun showSuggestions(list: List<SearchSuggestion>) {
        floatingSearchView.swapSuggestions(list)
        floatingSearchView.hideProgress()
    }

    override fun startAboutPanel(aboutPanel: LibsBuilder) {
        aboutPanel.start(this)
    }

    override fun startActivityForResult(activity: Class<out AppCompatActivity>, code: Int) {
        val it = Intent(this, activity)
        startActivityForResult(it, code)
    }

    override fun showProgress() {
        floatingSearchView.showProgress()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(Constants.MAIN_TOOLBAR_STATE, floatingSearchView.onSaveInstanceState())
        outState.putParcelable(Constants.MAIN_VIEWPAGE_STATE, pager.onSaveInstanceState())
    }

}
