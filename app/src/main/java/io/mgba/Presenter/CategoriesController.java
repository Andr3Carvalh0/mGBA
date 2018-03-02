package io.mgba.Presenter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.LinkedList;
import java.util.List;

import io.mgba.Adapters.SettingsCategoriesAdapter;
import io.mgba.Constants;
import io.mgba.Presenter.Interfaces.ICategoriesController;
import io.mgba.Data.Settings.SettingsCategory;
import io.mgba.R;
import io.mgba.UI.Activities.SettingsActivity;

public class CategoriesController implements ICategoriesController {
    private final List<SettingsCategory> settings;
    private final AppCompatActivity context;

    public CategoriesController(AppCompatActivity context) {
        this.context = context;

        this.settings = new LinkedList<>();
        settings.add(new SettingsCategory(context.getResources().getString(R.string.settings_audio), R.drawable.ic_audiotrack_black_24dp));
        settings.add(new SettingsCategory(context.getResources().getString(R.string.settings_video), R.drawable.ic_personal_video_black_24dp));
        settings.add(new SettingsCategory(context.getResources().getString(R.string.settings_emulation), R.drawable.ic_gamepad_black_24dp));
        settings.add(new SettingsCategory(context.getResources().getString(R.string.settings_bios), R.drawable.ic_memory_black_24dp));
        settings.add(new SettingsCategory(context.getResources().getString(R.string.settings_paths), R.drawable.ic_folder_black_24dp));
        settings.add(new SettingsCategory(context.getResources().getString(R.string.settings_customization), R.drawable.ic_palette_black_24dp));
    }

    @Override
    public void setupToolbar() {
        if (context.getSupportActionBar() != null){
            context.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            context.getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public void setupRecyclerView(RecyclerView recyclerview) {
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(context));
        recyclerview.setAdapter(new SettingsCategoriesAdapter(settings, context, (s) -> {
            Intent it = new Intent(context.getApplicationContext(), SettingsActivity.class);
            it.putExtra(Constants.ARG_SETTINGS_ID, s.getTitle());
            context.startActivity(it);
        }, recyclerview));
    }
}
