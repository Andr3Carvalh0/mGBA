package io.mgba;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import io.mgba.Data.Remote.Interfaces.IRequest;
import io.mgba.Data.Remote.RetrofitClient;
import io.mgba.Services.Interfaces.ILibraryService;
import io.mgba.Services.Interfaces.IPreferencesService;
import io.mgba.Services.LibraryService;
import io.mgba.Services.System.PreferencesService;

public class mgba extends Application {


    private IPreferencesService preferencesController;
    private IRequest webService;
    private ILibraryService libraryService;

    public void savePreference(String key, String value) {
        preparePreferencesManager();
        preferencesController.save(key, value);
    }

    public void savePreference(String key, boolean value) {
        preparePreferencesManager();
        preferencesController.save(key, value);
    }

    private void preparePreferencesManager(){
        if(preferencesController == null){
            preferencesController = new PreferencesService(getApplicationContext());
        }
    }

    public String getPreference(String key, String defaultValue) {
        preparePreferencesManager();

        return preferencesController.get(key, defaultValue);
    }

    public boolean getPreference(String key, boolean defaultValue) {
        preparePreferencesManager();

        return preferencesController.get(key, defaultValue);
    }

    public synchronized IRequest getWebService(){
        if(webService == null)
            webService =  RetrofitClient.getClient(IRequest.BASE_URL).create(IRequest.class);

        return webService;
    }

    public boolean isServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);

        if(manager != null) {
            for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
                if (serviceClass.getName().equals(service.service.getClassName())) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean hasWifiConnection(){
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo current = connManager.getActiveNetworkInfo();
        return current != null && current.getType() == ConnectivityManager.TYPE_WIFI && current.isConnected();
    }

    public String getDeviceLanguage(){
        //return Locale.getDefault().getISO3Language();
        return "eng";
    }

    public ILibraryService getLibraryController() {
        if(libraryService == null)
            libraryService = new LibraryService(getPreference(PreferencesService.GAMES_DIRECTORY, ""), getApplicationContext());

        return libraryService;
    }
}
