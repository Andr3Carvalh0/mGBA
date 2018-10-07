package io.mgba.settings.categories

import androidx.lifecycle.ViewModel
import java.util.HashMap
import androidx.preference.PreferenceFragmentCompat
import io.mgba.R
import io.mgba.data.settings.Settings
import io.mgba.ui.fragments.settings.AudioFragment
import io.mgba.ui.fragments.settings.BiosFragment
import io.mgba.ui.fragments.settings.EmulationFragment
import io.mgba.ui.fragments.settings.StorageFragment
import io.mgba.ui.fragments.settings.UIFragment
import io.mgba.ui.fragments.settings.VideoFragment
import io.mgba.utilities.Constants.ARG_SETTINGS_ID
import io.mgba.utilities.device.ResourcesManager.getString

class SettingsCategoriesViewModel(val title: String): ViewModel(){

    fun onClick(settings: Settings) {
        val extras = HashMap<String, String>()
        extras[ARG_SETTINGS_ID] = settings.title

        //view.startActivity(SettingsPanelActivity::class.java, extras)
    }
}
