package io.mgba.model.system

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import io.mgba.model.interfaces.IPreferencesManager

/**
 * Controller responsible with the interaction of the SharedPreferences.
 * It handles the saving/loading of the preferences.
 */
class PreferencesManager(private val context: Context) : IPreferencesManager {
    private var editor: SharedPreferences.Editor? = null
    private var sharedPreferences: SharedPreferences? = null

    private val sharePreferencesEditor: SharedPreferences.Editor
        get() {
            if (editor == null) {
                val sh = sharePreferences
                editor = sharedPreferences!!.edit()
            }

            return editor!!
        }

    private val sharePreferences: SharedPreferences
        get() {
            if (sharedPreferences == null) {
                sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            }

            return sharedPreferences!!
        }

    override fun save(key: String, value: String) {
        val ed = sharePreferencesEditor

        ed.putString(key, value)
        ed.apply()
    }

    override fun save(key: String, value: Boolean) {
        val ed = sharePreferencesEditor

        ed.putBoolean(key, value)
        ed.apply()
    }

    override fun get(key: String, defaultValue: String): String {
        val prefs = sharePreferences
        return prefs.getString(key, defaultValue)!!
    }

    override fun get(key: String, defaultValue: Boolean): Boolean {
        val prefs = sharePreferences
        return prefs.getBoolean(key, defaultValue)
    }

    companion object {
        val GAMES_DIRECTORY = "game_dir"
        val SETUP_DONE = "setup_done"
    }
}
