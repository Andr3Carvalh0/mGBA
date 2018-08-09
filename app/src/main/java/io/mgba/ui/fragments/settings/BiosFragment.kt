package io.mgba.ui.fragments.settings

import android.os.Bundle

import androidx.preference.PreferenceFragmentCompat
import io.mgba.R

class BiosFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.bios_settings)
    }

}
