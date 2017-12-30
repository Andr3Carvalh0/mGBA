package io.mgba;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import io.mgba.Data.Remote.Interfaces.IRequest;
import io.mgba.Data.Remote.RetrofitClient;
import io.mgba.Model.IO.LocalDB;
import io.mgba.Model.Interfaces.IPreferencesManager;
import io.mgba.Model.System.PreferencesManager;

public class mgba extends Application {

    private final static List<String> SUPPORTED_LANGUAGES = new LinkedList<>();

    static {
        SUPPORTED_LANGUAGES.add("eng");
    }

    public IPreferencesManager preferencesController;
    private ProgressDialog waitingDialog;

    public static void printLog(String tag, String message){
        if(BuildConfig.DEBUG)
            Log.v(tag, message);
    }

    public synchronized void savePreference(String key, String value) {
        preparePreferencesController();
        preferencesController.save(key, value);
    }

    public synchronized void savePreference(String key, boolean value) {
        preparePreferencesController();
        preferencesController.save(key, value);
    }

    public String getPreference(String key, String defaultValue) {
        preparePreferencesController();
        return preferencesController.get(key, defaultValue);
    }

    public boolean getPreference(String key, boolean defaultValue) {
        preparePreferencesController();
        return preferencesController.get(key, defaultValue);
    }

    private synchronized void preparePreferencesController(){
        if(preferencesController == null)
            preferencesController = new PreferencesManager(this);
    }

    public synchronized IRequest getWebService(){
        return RetrofitClient.getClient(IRequest.BASE_URL).create(IRequest.class);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        preferencesController = new PreferencesManager(this);
    }

    public String getDeviceLanguage(){
        final String iso = Locale.getDefault().getISO3Language();

        if(SUPPORTED_LANGUAGES.contains(iso))
            return iso;

        //If its not valid return the first language supported
        return SUPPORTED_LANGUAGES.get(0);
    }

    public void showProgressDialog(AppCompatActivity activity){
        waitingDialog = ProgressDialog.show(activity, getString(R.string.Progress_Title), getString(R.string.Progress_desc), true, false);
    }

    public void stopProgressDialog(){
        if(waitingDialog != null){
            waitingDialog.dismiss();
        }
    }

    public void showDialog(AppCompatActivity activity, String title, String desc, String positive_button,
                           String negative_button, DialogInterface.OnClickListener positive_click,
                           DialogInterface.OnClickListener negative_click){

        new AlertDialog.Builder(activity)
                .setTitle(title)
                .setMessage(desc)
                .setCancelable(false)
                .setPositiveButton(positive_button, positive_click)
                .setNegativeButton(negative_button, negative_click)
                .show();
    }

    public boolean isConnectedToWeb(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public LocalDB getLocalDatabase() {
        return new LocalDB(this);
    }
}
