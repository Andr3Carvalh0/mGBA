package io.mgba.Presenter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;
import com.google.common.base.Function;
import com.nononsenseapps.filepicker.Controllers.FilePickerUtils;
import java.util.HashMap;
import io.mgba.Constants;
import io.mgba.Presenter.Interfaces.ISettingsPanelPresenter;
import io.mgba.Model.Interfaces.IPermissionManager;
import io.mgba.Model.System.PermissionManager;
import io.mgba.R;
import io.mgba.UI.Activities.Interfaces.ISettingsPanelView;
import io.mgba.UI.Fragments.Settings.AudioFragment;
import io.mgba.UI.Fragments.Settings.BiosFragment;
import io.mgba.UI.Fragments.Settings.EmulationFragment;
import io.mgba.UI.Fragments.Settings.StorageFragment;
import io.mgba.UI.Fragments.Settings.UIFragment;
import io.mgba.UI.Fragments.Settings.VideoFragment;
import io.mgba.Utils.IResourcesManager;
import io.reactivex.annotations.NonNull;
import permissions.dispatcher.PermissionRequest;

public class SettingsPanelPresenter implements ISettingsPanelPresenter {
    private static final String TAG = "Settings_Controller";

    private final HashMap<String, Function<String, PreferenceFragmentCompat>> router;
    private final IPermissionManager permissionService;
    private final ISettingsPanelView view;
    private String id;

    public SettingsPanelPresenter(@NonNull IPermissionManager permissionManager, @NonNull ISettingsPanelView view,
                                  @NonNull String id, @NonNull IResourcesManager resourcesManager) {
        this.permissionService = permissionManager;
        this.view = view;
        this.id = id;

        this.router = new HashMap<>();
        router.put(resourcesManager.getString(R.string.settings_audio), (s) -> new AudioFragment());
        router.put(resourcesManager.getString(R.string.settings_video), (s) -> new VideoFragment());
        router.put(resourcesManager.getString(R.string.settings_emulation), (s) -> new EmulationFragment());
        router.put(resourcesManager.getString(R.string.settings_bios), (s) -> new BiosFragment());
        router.put(resourcesManager.getString(R.string.settings_paths), (s) -> new StorageFragment());
        router.put(resourcesManager.getString(R.string.settings_customization), (s) -> new UIFragment());
    }

    @Override
    public void onSaveInstance(Bundle outState) {
        outState.putString(Constants.ARG_SETTINGS_ID, id);
    }

    @Override
    public void setupFragment() {
        PreferenceFragmentCompat fragment = view.findFragment(TAG + id);

        if (fragment == null)
            fragment = router.get(id).apply(id);

        view.switchFragment(fragment, TAG + id);

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
        return view.getPreference(key, defaultValue);
    }

    @Override
    public String getTitle() {
        return id;
    }

    private void processDirectory(String dir){
        StorageFragment fragment = (StorageFragment) view.findFragment(TAG + id);
        fragment.changeGamesFolderSummary(dir);
    }
}
