package io.mgba.UI.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.mgba.Adapters.SettingsAdapter;
import io.mgba.Presenter.SettingsPresenter;
import io.mgba.Presenter.Interfaces.ISettingsPresenter;
import io.mgba.R;
import io.mgba.UI.Activities.Interfaces.ISettingsView;
import io.mgba.Utils.IResourcesManager;

public class SettingsActivity extends AppCompatActivity implements ISettingsView {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private ISettingsPresenter controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ButterKnife.bind(this);
        controller = new SettingsPresenter(this, (IResourcesManager) getApplication());

        setupToolbar();
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new SettingsAdapter(controller.getSettings(),
                                                              getApplicationContext(),
                                                              controller.getOnClick(),
                                                              recyclerView));
    }

    private void setupToolbar() {
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void startActivity(Class<? extends AppCompatActivity> activity, HashMap<String, String> extras) {
        Intent it = new Intent(this, activity);

        for(String key : extras.keySet())
            it.putExtra(key, extras.get(key));

        startActivity(it);
    }
}
