package io.mgba.model.system

import android.annotation.SuppressLint
import android.content.Context.MODE_PRIVATE
import io.mgba.mgba
import io.mgba.model.interfaces.IPreferencesManager

/**
 * Controller responsible with the interaction of the SharedPreferences.
 * It handles the saving/loading of the preferences.
 */
@SuppressLint("StaticFieldLeak")
object PreferencesManager : IPreferencesManager {

    val GAMES_DIRECTORY = "game_dir"
    val SETUP_DONE = "setup_done"

    private val preferences = mgba.context.getSharedPreferences("MeBeSafe", MODE_PRIVATE)
    private val editor = preferences.edit()

    override fun save(key: String, value: String) {
        editor.putString(key, value)
        editor.apply()
    }

    override fun save(key: String, value: Boolean) {
        editor.putBoolean(key, value)
        editor.apply()
    }

    override fun get(key: String, defaultValue: String): String = preferences.getString(key, defaultValue)!!
    override fun get(key: String, defaultValue: Boolean): Boolean = preferences.getBoolean(key, defaultValue)
}
