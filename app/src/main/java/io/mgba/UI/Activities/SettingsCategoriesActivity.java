package io.mgba.UI.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.mgba.Presenter.CategoriesController;
import io.mgba.Presenter.Interfaces.ICategoriesController;
import io.mgba.R;

public class SettingsCategoriesActivity extends AppCompatActivity {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private ICategoriesController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_categories);

        ButterKnife.bind(this);
        controller = new CategoriesController(this);

        setupToolbar();
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        controller.setupRecyclerView(recyclerView);
    }

    private void setupToolbar() {
        controller.setupToolbar();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
