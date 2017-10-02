package io.mgba.View.Activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import io.mgba.R;
import io.mgba.View.Activities.Interfaces.ISettings;
import io.mgba.View.Activities.Interfaces.ParentActivity;
import io.mgba.View.Fragments.SettingsFragment;

public class SettingsActivity extends ParentActivity implements ISettings {

    private static final String TAG = "Storage_Fragment";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        setupFragment();
        setupToolbar();
    }

    private void setupToolbar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void setupFragment() {
        FragmentManager fm = getSupportFragmentManager();

        SettingsFragment fragment = (SettingsFragment) fm.findFragmentByTag(TAG);

        if (fragment == null) {
            fragment = new SettingsFragment();
        }

        if (fragment != null) {
            fm.beginTransaction().replace(R.id.settings_container, fragment, TAG).commit();
        }
    }

    protected void processDirectory(Uri dir){
        System.out.println(dir);
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
}