package io.mgba;

import android.app.Activity;
import android.app.Application;
import android.util.DisplayMetrics;

import io.mgba.Controller.PreferencesManager;

public class mgba extends Application {
    private DisplayMetrics metrics;
    private PreferencesManager preferencesManager;


    public long getDeviceWidth(Activity activity){
        prepareDisplayMetrics();

        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }


    public void savePreference(String key, String value) {
        preparePreferencesManager();

        preferencesManager.save(key, value);
    }

    private void preparePreferencesManager(){
        if(preferencesManager == null){
            preferencesManager = new PreferencesManager(getApplicationContext());
        }
    }

    private void prepareDisplayMetrics(){
        if(metrics == null)
            metrics = new DisplayMetrics();

    }

    public String getPreference(String key, String defaultValue) {
        preparePreferencesManager();

        return preferencesManager.get(key, defaultValue);
    }
}
