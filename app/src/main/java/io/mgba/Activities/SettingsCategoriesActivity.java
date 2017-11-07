package io.mgba.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.mgba.Adapters.SettingsCategoriesAdapter;
import io.mgba.Constants;
import io.mgba.Data.Settings.SettingsCategory;
import io.mgba.R;

public class SettingsCategoriesActivity extends AppCompatActivity {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    private List<SettingsCategory> settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_categories);
        ButterKnife.bind(this);

        settings = new LinkedList<>();
        settings.add(new SettingsCategory(getResources().getString(R.string.settings_audio), R.drawable.ic_audiotrack_black_24dp));
        settings.add(new SettingsCategory(getResources().getString(R.string.settings_video), R.drawable.ic_personal_video_black_24dp));
        settings.add(new SettingsCategory(getResources().getString(R.string.settings_emulation), R.drawable.ic_gamepad_black_24dp));
        settings.add(new SettingsCategory(getResources().getString(R.string.settings_bios), R.drawable.ic_memory_black_24dp));
        settings.add(new SettingsCategory(getResources().getString(R.string.settings_paths), R.drawable.ic_folder_black_24dp));
        settings.add(new SettingsCategory(getResources().getString(R.string.settings_customization), R.drawable.ic_palette_black_24dp));

        setupToolbar();
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapter(new SettingsCategoriesAdapter(settings, this, (s) -> {
            Intent it = new Intent(getApplicationContext(), SettingsActivity.class);
            it.putExtra(Constants.ARG_SETTINGS_ID, s.getTitle());
            startActivity(it);
        }));
    }

    private void setupToolbar() {
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
