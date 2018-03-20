package io.mgba.UI.Activities;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import io.mgba.Presenter.Interfaces.ISettingsPanelPresenter;
import io.mgba.Presenter.SettingsPanelPresenter;
import io.mgba.R;
import io.mgba.UI.Activities.Interfaces.ISettings;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class SettingsPanelActivity extends AppCompatActivity implements ISettings {

    private static final String TAG = "Storage_Fragment";
    private ISettingsPanelPresenter controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        controller = new SettingsPanelPresenter(this);

        controller.init(savedInstanceState);
        setupFragment();
        setupToolbar();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        controller.onSaveInstance(outState);
        super.onSaveInstanceState(outState);
    }

    private void setupToolbar() {
        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle(controller.getTitle());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void setupFragment() {
        controller.setupFragment();
    }

    @Override
    public String requestPreferencesValue(String key, String defaultValue) {
        return controller.requestPreferencesValue(key, defaultValue);
    }

    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void showFilePicker() {
        controller.showFilePicker();
    }

    @OnShowRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void showRationaleForStorage(final PermissionRequest request) {
        controller.showRationaleForStorage(request);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        controller.onActivityResult(requestCode, resultCode, intent);
    }

    @Override
    public void requestStoragePermission() {
        SettingsPanelActivityPermissionsDispatcher.showFilePickerWithPermissionCheck(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        SettingsPanelActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}