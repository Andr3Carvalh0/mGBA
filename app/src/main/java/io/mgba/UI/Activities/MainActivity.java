package io.mgba.UI.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.flipboard.bottomsheet.BottomSheetLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.mgba.Adapters.TabViewPager;
import io.mgba.Presenter.Interfaces.IMainPresenter;
import io.mgba.Presenter.MainPresenter;
import io.mgba.Data.Database.Game;
import io.mgba.Model.Interfaces.ILibrary;
import io.mgba.R;

import static io.mgba.Presenter.MainPresenter.DEFAULT_PANEL;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener, io.mgba.UI.Activities.Interfaces.ILibrary,  FloatingSearchView.OnMenuItemClickListener, FloatingSearchView.OnQueryChangeListener, FloatingSearchView.OnSearchListener{

    @BindView(R.id.floating_search_view) FloatingSearchView toolbar;
    @BindView(R.id.pager) ViewPager viewPager;
    @BindView(R.id.bottomsheet) BottomSheetLayout sheetDialog;
    @BindView(R.id.tabLayout) TabLayout tabLayout;

    private IMainPresenter controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);

        ButterKnife.bind(this);
        controller = new MainPresenter(this);

        prepareToolbar();
        prepareViewPager();
    }

    private void prepareToolbar() {
        toolbar.setOnMenuItemClickListener(this);
        toolbar.setOnSearchListener(this);
        toolbar.setOnQueryChangeListener(this);
    }

    private void prepareViewPager() {
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.Favorites)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.GBA)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.GBC)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        TabViewPager adapter = new TabViewPager(getSupportFragmentManager());

        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(DEFAULT_PANEL);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(this);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void showBottomSheet(Game game) {
        controller.showBottomSheet(game, sheetDialog);
    }

    @Override
    public ILibrary getLibraryService() {
        return controller.getILibrary();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        controller.onActivityResult(requestCode, resultCode, intent);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    protected void onStop() {
        super.onStop();
        controller.onDestroy();
    }

    @Override
    public void onSearchTextChanged(String oldQuery, String newQuery) {
        controller.onSearchTextChanged(oldQuery, newQuery, toolbar);
    }

    @Override
    public void onSuggestionClicked(SearchSuggestion searchSuggestion) {

    }

    @Override
    public void onSearchAction(String currentQuery) {

    }

    @Override
    public void onActionMenuItemSelected(MenuItem item) {

    }
}
