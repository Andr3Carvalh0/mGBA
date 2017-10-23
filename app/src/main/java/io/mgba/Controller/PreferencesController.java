package io.mgba.Controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Controller responsible with the interaction of the SharedPreferences.
 * It handles the saving/loading of the preferences.
 */
public class PreferencesController {
    public static final String GAMES_DIRECTORY = "game_dir";
    private final Context mContext;
    private SharedPreferences.Editor editor;
    private SharedPreferences shared;

    public PreferencesController(Context context) {
        this.mContext = context;
    }

    public void save(String key, String value) {
        SharedPreferences.Editor ed = getSharePreferencesEditor();

        ed.putString(key, value);
        ed.apply();
    }

    public String get(String key, String defaultValue){
        SharedPreferences prefs = getSharePreferences();
        return prefs.getString(key, defaultValue);
    }

    private SharedPreferences.Editor getSharePreferencesEditor() {
        if(editor == null){
            SharedPreferences sh = getSharePreferences();
            editor = shared.edit();
        }

        return editor;
    }

    private SharedPreferences getSharePreferences() {
        if(shared == null){
            shared = PreferenceManager.getDefaultSharedPreferences(mContext);
        }

        return shared;
    }
}
