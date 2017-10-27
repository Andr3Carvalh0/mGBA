package io.mgba.Components.UI.Fragments.Settings;

import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

import io.mgba.Components.UI.Activities.Interfaces.ISettings;
import io.mgba.Controller.PreferencesController;
import io.mgba.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    Preference gamesFolder;
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.app_preferences);

        gamesFolder = findPreference("games_folder");
        final String gameDir = ((ISettings) getActivity()).requestPreferencesValue(PreferencesController.GAMES_DIRECTORY, getContext().getString(R.string.prefs_not_set_folder_summary));

        gamesFolder.setSummary(gameDir);
        gamesFolder.setOnPreferenceClickListener(preference -> {
            ((ISettings)getActivity()).requestStoragePermission();
            return true;
        });

    }

    public void changeGamesFolderSummary(String newDesc){
        gamesFolder.setSummary(newDesc);
    }
}
