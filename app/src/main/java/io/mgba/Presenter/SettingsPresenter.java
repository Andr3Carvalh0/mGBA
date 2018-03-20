package io.mgba.Presenter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import java.util.LinkedList;
import java.util.List;

import io.mgba.Constants;
import io.mgba.Presenter.Interfaces.ISettingsPresenter;
import io.mgba.Data.Settings.Settings;
import io.mgba.R;
import io.mgba.UI.Activities.SettingsPanelActivity;
import io.reactivex.functions.Consumer;

public class SettingsPresenter implements ISettingsPresenter {
    private final List<Settings> settings;
    private final AppCompatActivity context;

    public SettingsPresenter(AppCompatActivity context) {
        this.context = context;

        this.settings = new LinkedList<>();
        settings.add(new Settings(context.getResources().getString(R.string.settings_audio), R.drawable.ic_audiotrack_black_24dp));
        settings.add(new Settings(context.getResources().getString(R.string.settings_video), R.drawable.ic_personal_video_black_24dp));
        settings.add(new Settings(context.getResources().getString(R.string.settings_emulation), R.drawable.ic_gamepad_black_24dp));
        settings.add(new Settings(context.getResources().getString(R.string.settings_bios), R.drawable.ic_memory_black_24dp));
        settings.add(new Settings(context.getResources().getString(R.string.settings_paths), R.drawable.ic_folder_black_24dp));
        settings.add(new Settings(context.getResources().getString(R.string.settings_customization), R.drawable.ic_palette_black_24dp));
    }


    @Override
    public List<Settings> getSettings() {
        return settings;
    }

    @Override
    public Consumer<Settings> getOnClick() {
        return s -> {
            Intent it = new Intent(context.getApplicationContext(), SettingsPanelActivity.class);
            it.putExtra(Constants.ARG_SETTINGS_ID, s.getTitle());
            context.startActivity(it);
        };
    }
}
