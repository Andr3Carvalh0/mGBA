package io.mgba.settings.categories

import androidx.lifecycle.ViewModel
import java.util.HashMap
import io.mgba.data.settings.Settings

class SettingsCategoriesViewModel: ViewModel() {

    companion object {
        val ARG_SETTINGS_ID = "settings_id_args"
    }

    fun onClick(settings: Settings) {
        val extras = HashMap<String, String>()
        extras[ARG_SETTINGS_ID] = settings.title

        //view.startActivity(SettingsPanelActivity::class.java, extras)
    }
}
