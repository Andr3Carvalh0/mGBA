package io.mgba;

import android.app.Activity;
import android.app.Application;
import android.util.DisplayMetrics;

import io.mgba.Controller.PreferencesManager;

public class mgba extends Application {
    private DisplayMetrics metrics;
    private PreferencesManager preferencesManager;


    public long getDeviceWidth(Activity activity){
        activity.getWindowManager().getDefaultDisplay().getMetrics(getDisplayMetrics());
        return metrics.widthPixels;
    }


    public void savePreference(String key, String value) {
        PreferencesManager pm = getPreferencesManager();
        pm.save(key, value);
    }

    private PreferencesManager getPreferencesManager(){
        if(preferencesManager == null){
            preferencesManager = new PreferencesManager(getApplicationContext());
        }
        return preferencesManager;
    }

    private DisplayMetrics getDisplayMetrics(){
        if(metrics == null)
            metrics = new DisplayMetrics();

        return metrics;
    }

    public String getPreference(String key, String defaultValue) {
        PreferencesManager pm = getPreferencesManager();
        return pm.get(key, defaultValue);
    }
}
