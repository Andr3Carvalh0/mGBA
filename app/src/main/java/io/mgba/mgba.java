package io.mgba;

import android.app.Application;
import android.util.DisplayMetrics;
import io.mgba.Controller.MetricsCalculator;
import io.mgba.Controller.PreferencesManager;

public class mgba extends Application {
    private PreferencesManager preferencesManager;
    private MetricsCalculator metricsCalculator;

    public int[] getItemsPerColumn(){
        prepareMetricsCalculator();
        return metricsCalculator.calculate(getDeviceWidth());
    }

    private long getDeviceWidth(){
        final DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

    private long getDeviceHeight(){
        final DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        return displayMetrics.heightPixels;
    }

    private long getDeviceDensity(){
        final DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        return displayMetrics.densityDpi;
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

    private void prepareMetricsCalculator(){
        if(metricsCalculator == null){
            metricsCalculator = new MetricsCalculator(getApplicationContext());
        }
    }

    public String getPreference(String key, String defaultValue) {
        preparePreferencesManager();

        return preferencesManager.get(key, defaultValue);
    }

    public int getDPWidth() {
        return (int) MetricsCalculator.convertPixelsToDp(getDeviceWidth(), getApplicationContext());
    }
}
