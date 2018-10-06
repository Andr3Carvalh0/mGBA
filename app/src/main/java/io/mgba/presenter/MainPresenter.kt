package io.mgba.presenter

import android.app.Activity
import android.content.Intent
import android.view.MenuItem
import com.mikepenz.aboutlibraries.LibsBuilder
import io.mgba.data.database.model.Game
import io.mgba.presenter.interfaces.IMainPresenter
import io.mgba.R
import io.mgba.model.Library
import io.mgba.ui.activities.interfaces.IMainView
import io.mgba.ui.activities.SettingsActivity
import io.mgba.utilities.ResourcesManager.getString
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

class MainPresenter(private val view: IMainView) : IMainPresenter {

    private val disposable = CompositeDisposable()
    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent) {
        if (requestCode == SETTINGS_CODE && resultCode == Activity.RESULT_OK) {
            throw UnsupportedOperationException()
        }
    }

    override fun onMenuItemSelected(item: MenuItem) {
        if (item.itemId == R.id.action_about) {
            val aboutPanel = LibsBuilder()
                    .withActivityTheme(R.style.AboutTheme)
                    .withAboutIconShown(true)
                    .withAboutVersionShown(true)
                    .withAboutDescription(getString(R.string.About_description))

            view.startAboutPanel(aboutPanel)
        }

        if (item.itemId == R.id.action_settings) {
            view.startActivityForResult(SettingsActivity::class.java, SETTINGS_CODE)
        }
    }


    override fun onDestroy() {
        disposable.dispose()
    }

    override fun onSearchTextChanged(oldQuery: String, newQuery: String) {
        if (oldQuery != "" && newQuery == "") {
            view.clearSuggestions()
        } else {
            view.showProgress()
            disposable.add(Library.query(newQuery)
                                  .subscribeOn(Schedulers.computation())
                                  .observeOn(AndroidSchedulers.mainThread())
                                  .subscribe(Consumer<List<Game>> { view.showSuggestions(it) }))
        }
    }

    companion object {
        val DEFAULT_PANEL = 1
        private val SETTINGS_CODE = 738
    }
}
