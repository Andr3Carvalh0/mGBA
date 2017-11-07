package io.mgba.Fragments.Settings;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import io.mgba.R;

public class BiosFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.bios_settings);
    }

}
