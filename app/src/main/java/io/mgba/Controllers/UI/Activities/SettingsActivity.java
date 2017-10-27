package io.mgba.Controllers.UI.Activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.nononsenseapps.filepicker.Controllers.FilePickerUtils;

import io.mgba.Controllers.UI.Activities.Interfaces.ISettings;
import io.mgba.Controllers.UI.Fragments.Settings.SettingsFragment;
import io.mgba.R;
import io.mgba.Services.Interfaces.IPermissionService;
import io.mgba.Services.System.PermissionService;
import io.mgba.Services.System.PreferencesService;
import io.mgba.mgba;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class SettingsActivity extends AppCompatActivity implements ISettings {

    private static final String TAG = "Storage_Fragment";
    private IPermissionService permissionService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        permissionService = new PermissionService(this);
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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void requestStoragePermission() {
        SettingsActivityPermissionsDispatcher.showFilePickerWithPermissionCheck(this);
    }

    @Override
    public String requestPreferencesValue(String key, String defaultValue) {
        return ((mgba)getApplication()).getPreference(key, defaultValue);
    }

    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void showFilePicker() {
        permissionService.showFilePicker();
    }

    @OnShowRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void showRationaleForStorage(final PermissionRequest request) {
        permissionService.showRationaleForStorage(request);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        SettingsActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == PermissionService.DIR_CODE && resultCode == Activity.RESULT_OK) {
            String dir = FilePickerUtils.getSelectedDir(intent);
            processDirectory(dir);
        }
    }

    private void processDirectory(String dir){
        ((mgba)getApplication()).savePreference(PreferencesService.GAMES_DIRECTORY, dir);
        FragmentManager fm = getSupportFragmentManager();
        SettingsFragment fragment = (SettingsFragment) fm.findFragmentByTag(TAG);
        fragment.changeGamesFolderSummary(dir);
    }

}