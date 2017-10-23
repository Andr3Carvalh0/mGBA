package io.mgba;

import android.app.Application;
import android.util.DisplayMetrics;
import io.mgba.Controller.DisplayController;
import io.mgba.Controller.PreferencesController;
import io.mgba.Services.Utils.GameService;

public class mgba extends Application {
    private PreferencesController preferencesController;
    private DisplayController displayController;
    private GameService gameService;

    public int[] getItemsPerColumn(){
        prepareMetricsCalculator();
        return displayController.calculate(getDeviceWidth());
    }

    private long getDeviceWidth(){
        final DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

    public void savePreference(String key, String value) {
        preparePreferencesManager();

        preferencesController.save(key, value);
    }

    private void preparePreferencesManager(){
        if(preferencesController == null){
            preferencesController = new PreferencesController(getApplicationContext());
        }
    }

    private void prepareMetricsCalculator(){
        if(displayController == null){
            displayController = new DisplayController(getApplicationContext());
        }
    }

    public String getPreference(String key, String defaultValue) {
        preparePreferencesManager();

        return preferencesController.get(key, defaultValue);
    }

    public int getDPWidth() {
        return (int) DisplayController.convertPixelsToDp(getDeviceWidth(), getApplicationContext());
    }

    public synchronized GameService getGameService(){
        if(gameService == null)
            gameService = new GameService(getApplicationContext());

        return gameService;
    }
}
