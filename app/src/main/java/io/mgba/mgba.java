package io.mgba;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import io.mgba.Controllers.UI.Activities.SplashActivity;
import io.mgba.Data.Remote.Interfaces.IRequest;
import io.mgba.Data.Remote.RetrofitClient;
import io.mgba.Services.Interfaces.ILibraryService;
import io.mgba.Services.Interfaces.IPreferencesService;
import io.mgba.Services.LibraryService;
import io.mgba.Services.System.PreferencesService;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class mgba extends Application {


    private IPreferencesService preferencesController;
    private IRequest webService;
    private ILibraryService libraryService;
    private ProgressDialog waitingDialog;

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


    /**
     * Take from :
     * https://github.com/JakeWharton/ProcessPhoenix
     * Used when we change the storage path on the settings activity.
     */
    public void restartApplication(AppCompatActivity activity){
        showDialog(activity, getString(R.string.restart_title), getString(R.string.restart_description), getString(R.string.restart_positive),
                    getString(R.string.Cancel), (dialog, which) -> restart(), (dialog, which) -> dialog.cancel());
    }

    private void restart(){
        Intent intent = new Intent(getApplicationContext(), SplashActivity.class);
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK); // In case we are called with non-Activity context.
        getApplicationContext().startActivity(intent);
        if (getApplicationContext() instanceof Activity) {
            ((Activity) getApplicationContext()).finish();
        }
        Runtime.getRuntime().exit(0); // Kill kill kill!
    }

    public void showProgressDialog(AppCompatActivity activity){
        waitingDialog = ProgressDialog.show(activity, getString(R.string.Progress_Title), getString(R.string.Progress_desc), true, false);
    }

    public void stopProgressDialog(){
        if(waitingDialog != null){
            waitingDialog.dismiss();
        }
    }

    public void showDialog(AppCompatActivity activity, String title, String desc, String positive_button, String negative_button,
                           DialogInterface.OnClickListener positive_click, DialogInterface.OnClickListener negative_click){
        new AlertDialog.Builder(activity)
                .setTitle(title)
                .setMessage(desc)
                .setCancelable(false)
                .setPositiveButton(positive_button, positive_click)
                .setNegativeButton(negative_button, negative_click)
                .show();
    }
}
