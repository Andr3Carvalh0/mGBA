package io.mgba.ui.fragments.settings

import android.os.Bundle

import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import io.mgba.utilities.PreferencesManager
import io.mgba.R
import io.mgba.ui.activities.interfaces.ISettings

class StorageFragment : PreferenceFragmentCompat() {

    lateinit var gamesFolder: Preference
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.storage_settings)

        gamesFolder = findPreference("games_folder")
        val gameDir = (activity as ISettings).requestPreferencesValue(PreferencesManager.GAMES_DIRECTORY, context!!.getString(R.string.prefs_not_set_folder_summary))

        gamesFolder.summary = gameDir
        gamesFolder.setOnPreferenceClickListener { preference ->
            (activity as ISettings).requestStoragePermission()
            true
        }

    }

    fun changeGamesFolderSummary(newDesc: String) {
        gamesFolder.summary = newDesc
    }
}
