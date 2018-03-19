package io.mgba.UI.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.mgba.Adapters.SettingsCategoriesAdapter;
import io.mgba.Presenter.CategoriesPresenter;
import io.mgba.Presenter.Interfaces.ICategoriesPresenter;
import io.mgba.R;

public class SettingsCategoriesActivity extends AppCompatActivity {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private ICategoriesPresenter controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_categories);

        ButterKnife.bind(this);
        controller = new CategoriesPresenter(this);

        setupToolbar();
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new SettingsCategoriesAdapter(controller.getSettings(),
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
}
