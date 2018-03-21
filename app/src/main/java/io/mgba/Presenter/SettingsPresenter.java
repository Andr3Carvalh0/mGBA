package io.mgba.Presenter;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import io.mgba.Constants;
import io.mgba.Presenter.Interfaces.ISettingsPresenter;
import io.mgba.Data.Settings.Settings;
import io.mgba.R;
import io.mgba.UI.Activities.Interfaces.ISettingsView;
import io.mgba.UI.Activities.SettingsPanelActivity;
import io.mgba.Utils.IResourcesManager;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

public class SettingsPresenter implements ISettingsPresenter {
    private final List<Settings> settings;
    private final ISettingsView view;

    public SettingsPresenter(@NonNull ISettingsView view, @NonNull IResourcesManager resourcesManager) {
        this.view = view;
        this.settings = new LinkedList<>();
        settings.add(new Settings(resourcesManager.getString(R.string.settings_audio), R.drawable.ic_audiotrack_black_24dp));
        settings.add(new Settings(resourcesManager.getString(R.string.settings_video), R.drawable.ic_personal_video_black_24dp));
        settings.add(new Settings(resourcesManager.getString(R.string.settings_emulation), R.drawable.ic_gamepad_black_24dp));
        settings.add(new Settings(resourcesManager.getString(R.string.settings_bios), R.drawable.ic_memory_black_24dp));
        settings.add(new Settings(resourcesManager.getString(R.string.settings_paths), R.drawable.ic_folder_black_24dp));
        settings.add(new Settings(resourcesManager.getString(R.string.settings_customization), R.drawable.ic_palette_black_24dp));
    }


    @Override
    public List<Settings> getSettings() {
        return settings;
    }

    @Override
    public Consumer<Settings> getOnClick() {
        return s -> {
            HashMap<String, String> extras = new HashMap<>();
            extras.put(Constants.ARG_SETTINGS_ID, s.getTitle());

            view.startActivity(SettingsPanelActivity.class, extras);
        };
    }
}
