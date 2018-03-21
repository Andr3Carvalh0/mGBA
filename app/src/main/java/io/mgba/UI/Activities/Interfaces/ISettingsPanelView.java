package io.mgba.UI.Activities.Interfaces;

import android.support.v7.preference.PreferenceFragmentCompat;

public interface ISettingsPanelView {
    PreferenceFragmentCompat findFragment(String id);
    void switchFragment(PreferenceFragmentCompat fragment, String tag);
    String getPreference(String key, String defaultValue);
}
