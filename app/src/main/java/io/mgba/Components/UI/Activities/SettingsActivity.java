package io.mgba.Components.UI.Activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import io.mgba.Components.UI.Activities.Interfaces.ISettings;
import io.mgba.Components.UI.Activities.Interfaces.PreferencesActivity;
import io.mgba.Components.UI.Fragments.Settings.SettingsFragment;
import io.mgba.R;
import io.mgba.mgba;

public class SettingsActivity extends PreferencesActivity implements ISettings {

    private static final String TAG = "Storage_Fragment";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        setupFragment();
        setupToolbar();
    }

    private void setupToolbar() {
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void setupFragment() {
        FragmentManager fm = getSupportFragmentManager();

        SettingsFragment fragment = (SettingsFragment) fm.findFragmentByTag(TAG);

        if (fragment == null) {
            fragment = new SettingsFragment();
        }

        fm.beginTransaction().replace(R.id.settings_container, fragment, TAG).commit();
    }

    protected void processDirectory(String dir){
       super.processDirectory(dir);
        FragmentManager fm = getSupportFragmentManager();
        SettingsFragment fragment = (SettingsFragment) fm.findFragmentByTag(TAG);
        fragment.changeGamesFolderSummary(dir);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void requestStoragePermission() {
        invokeFilePicker();
    }

    @Override
    public String requestPreferencesValue(String key, String defaultValue) {
        return ((mgba)getApplication()).getPreference(key, defaultValue);
    }
}