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
import io.mgba.DI.Components.DaggerModelComponent;
import io.mgba.DI.Components.ModelComponent;
import io.mgba.DI.Modules.ModelModule;
import io.mgba.Data.Remote.Interfaces.IRequest;
import io.mgba.Data.Remote.RetrofitClient;
import io.mgba.Model.Interfaces.IPreferencesManager;
import io.mgba.Model.Library;
import io.mgba.Model.System.PreferencesManager;
import io.mgba.Presenter.IntroPresenter;
import io.mgba.Presenter.MainPresenter;

public class mgba extends Application {

    private final static List<String> SUPPORTED_LANGUAGES = new LinkedList<>();

    static {
        SUPPORTED_LANGUAGES.add("eng");
    }

    private ModelComponent modelComponent;

    private IPreferencesManager preferencesController;

    private IRequest webController;
    private ProgressDialog waitingDialog;

    private ModelComponent initModelComponent_Dagger(mgba application){
            return DaggerModelComponent.builder()
                    .modelModule(new ModelModule(application, application.getPreference(PreferencesManager.GAMES_DIRECTORY, "")))
                    .build();
    }

    public void savePreference(String key, String value) {
        preparePreferencesController();
        preferencesController.save(key, value);
    }

    public void savePreference(String key, boolean value) {
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
        if(webController == null)
            webController = RetrofitClient.getClient(IRequest.BASE_URL).create(IRequest.class);
        return webController;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        modelComponent = initModelComponent_Dagger(this);
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

    public void inject(IntroPresenter target) {
        modelComponent.inject(target);
    }

    public void inject(MainPresenter target) {
        modelComponent.inject(target);
    }

    public void inject(Library library) {
        modelComponent.inject(library);
    }

    public static void report(Throwable error) {
        if(BuildConfig.DEBUG) {

            Log.e(" --- An error occurred:", error.getMessage());
            Log.e(" --- StackTrace:", error.getStackTrace().toString());

        }

    }

    public static void printLog(String tag, String message){
        if(BuildConfig.DEBUG)
            Log.v(tag, message);
    }


}
