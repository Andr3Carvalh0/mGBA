package io.mgba.Activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.google.common.base.Function;
import com.nononsenseapps.filepicker.Controllers.FilePickerUtils;

import java.util.HashMap;

import io.mgba.Activities.Interfaces.ISettings;
import io.mgba.Constants;
import io.mgba.Fragments.Settings.AudioFragment;
import io.mgba.Fragments.Settings.BiosFragment;
import io.mgba.Fragments.Settings.EmulationFragment;
import io.mgba.Fragments.Settings.StorageFragment;
import io.mgba.Fragments.Settings.UIFragment;
import io.mgba.Fragments.Settings.VideoFragment;
import io.mgba.Model.Interfaces.IPermissionManager;
import io.mgba.Model.System.PermissionManager;
import io.mgba.Model.System.PreferencesManager;
import io.mgba.R;
import io.mgba.mgba;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class SettingsActivity extends AppCompatActivity implements ISettings {
    private static final String TAG = "Storage_Fragment";

    private static HashMap<String, Function<String, PreferenceFragmentCompat>> router;
    private IPermissionManager permissionService;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        router = new HashMap<>();
        router.put(getString(R.string.settings_audio), (s) -> new AudioFragment());
        router.put(getString(R.string.settings_video), (s) -> new VideoFragment());
        router.put(getString(R.string.settings_emulation), (s) -> new EmulationFragment());
        router.put(getString(R.string.settings_bios), (s) -> new BiosFragment());
        router.put(getString(R.string.settings_paths), (s) -> new StorageFragment());
        router.put(getString(R.string.settings_customization), (s) -> new UIFragment());

        id = savedInstanceState == null
                    ? getIntent().getExtras().getString(Constants.ARG_SETTINGS_ID)
                    : savedInstanceState.getString(Constants.ARG_SETTINGS_ID);

        permissionService = new PermissionManager(this);
        setupFragment();
        setupToolbar();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(Constants.ARG_SETTINGS_ID, id);
        super.onSaveInstanceState(outState);
    }

    private void setupToolbar() {
        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle(id);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void setupFragment() {
        FragmentManager fm = getSupportFragmentManager();

        PreferenceFragmentCompat fragment = (PreferenceFragmentCompat) fm.findFragmentByTag(TAG + id);

        if (fragment == null)
            fragment = router.get(id).apply(id);

        fm.beginTransaction().replace(R.id.settings_container, fragment, TAG + id).commit();
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
        if (requestCode == PermissionManager.DIR_CODE && resultCode == Activity.RESULT_OK) {
            String dir = FilePickerUtils.getSelectedDir(intent);
            processDirectory(dir);
        }
    }

    private void processDirectory(String dir){
        ((mgba)getApplication()).savePreference(PreferencesManager.GAMES_DIRECTORY, dir);
        FragmentManager fm = getSupportFragmentManager();
        StorageFragment fragment = (StorageFragment) fm.findFragmentByTag(TAG + id);
        fragment.changeGamesFolderSummary(dir);
    }

}