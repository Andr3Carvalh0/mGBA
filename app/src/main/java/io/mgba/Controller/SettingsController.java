package io.mgba.Controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.google.common.base.Function;
import com.nononsenseapps.filepicker.Controllers.FilePickerUtils;

import java.util.HashMap;

import io.mgba.Constants;
import io.mgba.Controller.Interfaces.ISettingsController;
import io.mgba.Model.Interfaces.IPermissionManager;
import io.mgba.Model.System.PermissionManager;
import io.mgba.Model.System.PreferencesManager;
import io.mgba.R;
import io.mgba.UI.Fragments.Settings.AudioFragment;
import io.mgba.UI.Fragments.Settings.BiosFragment;
import io.mgba.UI.Fragments.Settings.EmulationFragment;
import io.mgba.UI.Fragments.Settings.StorageFragment;
import io.mgba.UI.Fragments.Settings.UIFragment;
import io.mgba.UI.Fragments.Settings.VideoFragment;
import io.mgba.mgba;
import permissions.dispatcher.PermissionRequest;

public class SettingsController implements ISettingsController {
    private static final String TAG = "Settings_Controller";
    private final AppCompatActivity context;
    private final HashMap<String, Function<String, PreferenceFragmentCompat>> router;
    private final IPermissionManager permissionService;

    private String id;

    public SettingsController(AppCompatActivity context) {
        this.context = context;
        this.permissionService = new PermissionManager(context);

        this.router = new HashMap<>();
        router.put(context.getString(R.string.settings_audio), (s) -> new AudioFragment());
        router.put(context.getString(R.string.settings_video), (s) -> new VideoFragment());
        router.put(context.getString(R.string.settings_emulation), (s) -> new EmulationFragment());
        router.put(context.getString(R.string.settings_bios), (s) -> new BiosFragment());
        router.put(context.getString(R.string.settings_paths), (s) -> new StorageFragment());
        router.put(context.getString(R.string.settings_customization), (s) -> new UIFragment());
    }

    @Override
    public void init(Bundle savedInstanceState) {
        id = savedInstanceState == null
                ? context.getIntent().getExtras().getString(Constants.ARG_SETTINGS_ID)
                : savedInstanceState.getString(Constants.ARG_SETTINGS_ID);
    }

    @Override
    public void onSaveInstance(Bundle outState) {
        outState.putString(Constants.ARG_SETTINGS_ID, id);
    }

    @Override
    public void setupToolbar() {
        if(context.getSupportActionBar() != null){
            context.getSupportActionBar().setTitle(id);
            context.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            context.getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public void setupFragment() {
        FragmentManager fm = context.getSupportFragmentManager();

        PreferenceFragmentCompat fragment = (PreferenceFragmentCompat) fm.findFragmentByTag(TAG + id);

        if (fragment == null)
            fragment = router.get(id).apply(id);

        fm.beginTransaction().replace(R.id.settings_container, fragment, TAG + id).commit();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == PermissionManager.DIR_CODE && resultCode == Activity.RESULT_OK) {
            String dir = FilePickerUtils.getSelectedDir(intent);
            processDirectory(dir);
        }
    }

    @Override
    public void showFilePicker() {
        permissionService.showFilePicker();
    }

    @Override
    public void showRationaleForStorage(PermissionRequest request) {
        permissionService.showRationaleForStorage(request);
    }

    @Override
    public String requestPreferencesValue(String key, String defaultValue) {
        return ((mgba)context.getApplication()).getPreference(key, defaultValue);
    }

    private void processDirectory(String dir){
        ((mgba)context.getApplication()).savePreference(PreferencesManager.GAMES_DIRECTORY, dir);
        FragmentManager fm = context.getSupportFragmentManager();
        StorageFragment fragment = (StorageFragment) fm.findFragmentByTag(TAG + id);
        fragment.changeGamesFolderSummary(dir);
    }
}
