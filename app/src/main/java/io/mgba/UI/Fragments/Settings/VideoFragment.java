package io.mgba.UI.Fragments.Settings;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import io.mgba.R;

public class VideoFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.video_settings);
    }

}
