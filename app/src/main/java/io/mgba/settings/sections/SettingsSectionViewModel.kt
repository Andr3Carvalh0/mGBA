package io.mgba.settings.sections

import androidx.lifecycle.ViewModel
import java.util.HashMap
import io.mgba.data.settings.Settings
import io.mgba.utilities.Constants.ARG_SETTINGS_ID

class SettingsSectionViewModel: ViewModel() {

    fun onClick(settings: Settings) {
        val extras = HashMap<String, String>()
        extras[ARG_SETTINGS_ID] = settings.title

        //view.startActivity(SettingsPanelActivity::class.java, extras)
    }
}
