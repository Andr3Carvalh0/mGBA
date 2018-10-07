package io.mgba.main

import android.app.Activity
import android.content.Intent
import android.view.MenuItem
import androidx.lifecycle.ViewModel
import com.mikepenz.aboutlibraries.LibsBuilder
import io.mgba.R
import io.mgba.utilities.device.ResourcesManager.getString

class MainViewModel : ViewModel() {

    fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent) {
        if (requestCode == SETTINGS_CODE && resultCode == Activity.RESULT_OK) {
            throw UnsupportedOperationException()
        }
    }

    fun onMenuItemSelected(item: MenuItem) {
        if (item.itemId == R.id.action_about) {
           /* val aboutPanel = LibsBuilder()
                    .withActivityTheme(R.style.AboutTheme)
                    .withAboutIconShown(true)
                    .withAboutVersionShown(true)
                    .withAboutDescription(getString(R.string.About_description))*/

            //view.startAboutPanel(aboutPanel)
        }

        if (item.itemId == R.id.action_settings) {
            //view.startActivityForResult(SettingsCategoriesActivity::class.java, SETTINGS_CODE)
        }
    }

   fun onSearchTextChanged(oldQuery: String, newQuery: String) {
        if (oldQuery != "" && newQuery == "") {
            //view.clearSuggestions()
        } else {
           // view.showProgress()
           // disposable.add(Library.query(newQuery)
                                 // .subscribeOn(Schedulers.computation())
                                 // .observeOn(AndroidSchedulers.mainThread())
                                 // .subscribe(Consumer<List<Game>> { view.showSuggestions(it) }))
        }
    }

    companion object {
        val DEFAULT_PANEL = 1
        private val SETTINGS_CODE = 738
    }
}
