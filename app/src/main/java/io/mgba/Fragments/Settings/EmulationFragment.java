package io.mgba.Fragments.Settings;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import io.mgba.R;

public class EmulationFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.emulation_settings);

    }

}
