package io.mgba.Model.System;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import io.mgba.Model.Interfaces.IPreferencesManager;

/**
 * Controller responsible with the interaction of the SharedPreferences.
 * It handles the saving/loading of the preferences.
 */
public class PreferencesManager implements IPreferencesManager {
    public static final String GAMES_DIRECTORY = "game_dir";
    public static final String SETUP_DONE = "setup_done";

    private final Context mContext;
    private SharedPreferences.Editor editor;
    private SharedPreferences shared;

    public PreferencesManager(Context context) {
        this.mContext = context;
    }

    @Override
    public void save(String key, String value) {
        SharedPreferences.Editor ed = getSharePreferencesEditor();

        ed.putString(key, value);
        ed.apply();
    }

    @Override
    public void save(String key, boolean value) {
        SharedPreferences.Editor ed = getSharePreferencesEditor();

        ed.putBoolean(key, value);
        ed.apply();
    }

    @Override
    public String get(String key, String defaultValue){
        SharedPreferences prefs = getSharePreferences();
        return prefs.getString(key, defaultValue);
    }

    @Override
    public boolean get(String key, boolean defaultValue) {
        SharedPreferences prefs = getSharePreferences();
        return prefs.getBoolean(key, defaultValue);
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
