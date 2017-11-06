package io.mgba;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import io.mgba.Data.Remote.Interfaces.IRequest;
import io.mgba.Data.Remote.RetrofitClient;
import io.mgba.Services.Interfaces.ILibraryService;
import io.mgba.Services.Interfaces.IPreferencesService;
import io.mgba.Services.LibraryService;
import io.mgba.Services.System.PreferencesService;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;

public class mgba extends Application {

    public static IPreferencesService preferencesController;
    public static ILibraryService libraryService;
    private static IRequest webService;
    private ProgressDialog waitingDialog;

    public static void printLog(String tag, String message){
        if(BuildConfig.DEBUG)
            Log.v(tag, message);
    }

    public static void savePreference(String key, String value) {
        preferencesController.save(key, value);
    }

    public static void savePreference(String key, boolean value) {
        preferencesController.save(key, value);
    }

    public static String getPreference(String key, String defaultValue) {
        return preferencesController.get(key, defaultValue);
    }

    public static boolean getPreference(String key, boolean defaultValue) {
        return preferencesController.get(key, defaultValue);
    }

    public static synchronized IRequest getWebService(){
        if(webService == null)
            webService =  RetrofitClient.getClient(IRequest.BASE_URL).create(IRequest.class);

        return webService;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        preferencesController = new PreferencesService(this);
        libraryService = new LibraryService( this);
    }

    public String getDeviceLanguage(){
        //return Locale.getDefault().getISO3Language();
        return "eng";
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

    public boolean isInLandscape(){
       return getResources().getConfiguration().orientation == ORIENTATION_LANDSCAPE;
    }
}
