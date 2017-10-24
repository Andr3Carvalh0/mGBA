package io.mgba;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import io.mgba.Controller.PreferencesController;
import io.mgba.Data.Remote.Interfaces.IRequest;
import io.mgba.Data.Remote.RetrofitClient;
import io.mgba.Services.ProcessingGameService;

public class mgba extends Application {
    private PreferencesController preferencesController;
    private ProcessingGameService gameService;
    private IRequest webService;

    public void savePreference(String key, String value) {
        preparePreferencesManager();

        preferencesController.save(key, value);
    }

    private void preparePreferencesManager(){
        if(preferencesController == null){
            preferencesController = new PreferencesController(getApplicationContext());
        }
    }

    public String getPreference(String key, String defaultValue) {
        preparePreferencesManager();

        return preferencesController.get(key, defaultValue);
    }

    public synchronized ProcessingGameService getGameService(){
        if(gameService == null)
            gameService = new ProcessingGameService( this);

        return gameService;
    }

    public synchronized IRequest getWebService(){
        if(webService == null)
            webService =  RetrofitClient.getClient(IRequest.BASE_URL).create(IRequest.class);

        return webService;
    }

    public boolean isServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
