package io.mgba.ui.activities.interfaces


import androidx.preference.PreferenceFragmentCompat

interface ISettingsPanelView {
    fun findFragment(id: String): PreferenceFragmentCompat
    fun switchFragment(fragment: PreferenceFragmentCompat, tag: String)
    fun getPreference(key: String, defaultValue: String): String
}
