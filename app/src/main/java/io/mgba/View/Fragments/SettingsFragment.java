package io.mgba.View.Fragments;

import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import io.mgba.R;
import io.mgba.View.Activities.Interfaces.ISettings;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.app_preferences);

        Preference screen = (Preference) findPreference("games_folder");
        screen.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                ((ISettings)getActivity()).requestStoragePermission();
                return true;
            }
        });

    }
}
