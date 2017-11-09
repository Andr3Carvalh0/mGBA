package io.mgba.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.mikepenz.aboutlibraries.LibsBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.mgba.Adapters.TabViewPager;
import io.mgba.Data.DTOs.Game;
import io.mgba.Model.Interfaces.ILibrary;
import io.mgba.Model.Library;
import io.mgba.R;
import io.mgba.Views.BottomSheetView;
import io.mgba.Views.Interfaces.IBottomSheetView;
import io.mgba.mgba;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener, FloatingSearchView.OnMenuItemClickListener, FloatingSearchView.OnQueryChangeListener, FloatingSearchView.OnSearchListener, io.mgba.Activities.Interfaces.ILibrary, FloatingSearchView.OnFocusChangeListener {

    private static final int DEFAULT_PANEL = 1;
    private static final int SETTINGS_CODE = 738;

    @BindView(R.id.floating_search_view) FloatingSearchView mToolbar;
    @BindView(R.id.pager) ViewPager mViewPager;
    @BindView(R.id.bottomsheet) BottomSheetLayout mSheetDialog;
    @BindView(R.id.tabLayout) TabLayout mTabLayout;

    private ILibrary libraryService;

    private IBottomSheetView bottomSheetController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);

        ButterKnife.bind(this);

        libraryService = new Library((mgba)getApplication());

        prepareToolbar();
        prepareViewPager();
    }

    private void prepareToolbar() {
        mToolbar.setOnMenuItemClickListener(this);
        mToolbar.setOnSearchListener(this);
        mToolbar.setOnQueryChangeListener(this);
        mToolbar.setOnFocusChangeListener(this);
    }

    private void prepareViewPager() {
        mTabLayout.addTab(mTabLayout.newTab().setText(getString(R.string.Favorites)));
        mTabLayout.addTab(mTabLayout.newTab().setText(getString(R.string.GBA)));
        mTabLayout.addTab(mTabLayout.newTab().setText(getString(R.string.GBC)));
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        TabViewPager adapter = new TabViewPager(getSupportFragmentManager());

        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(DEFAULT_PANEL);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.addOnTabSelectedListener(this);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onActionMenuItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_about)
        {
            new LibsBuilder()
                    .withActivityTheme(R.style.AboutTheme)
                    .withAboutIconShown(true)
                    .withAboutVersionShown(true)
                    .withAboutDescription(getString(R.string.About_description))
                    .start(this);
        }

        if (item.getItemId() == R.id.action_settings)
        {
            Intent settings = new Intent(this, SettingsCategoriesActivity.class);
            startActivityForResult(settings, SETTINGS_CODE);
        }
    }

    @Override
    public void showBottomSheet(Game game) {
        if(bottomSheetController == null)
            bottomSheetController = new BottomSheetView(getApplicationContext());

        mSheetDialog.showWithSheetView(bottomSheetController.getView(mSheetDialog, game));
    }

    @Override
    public ILibrary getLibraryService() {
        return libraryService;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == SETTINGS_CODE && resultCode == Activity.RESULT_OK) {

        }
    }

    @Override
    public void onSuggestionClicked(SearchSuggestion searchSuggestion) {

    }

    @Override
    public void onSearchAction(String currentQuery) {
        //Not implemented
    }

    @Override
    public void onSearchTextChanged(String oldQuery, String newQuery) {

        if (!oldQuery.equals("") && newQuery.equals("")) {
            mToolbar.clearSuggestions();
        } else {
            mToolbar.showProgress();
            libraryService
                    .query(newQuery)
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(games -> {
                        mToolbar.swapSuggestions(games);
                        mToolbar.hideProgress();
                    });
        }
    }

    @Override
    public void onFocus() {
        onSearchTextChanged("", mToolbar.getQuery());
    }

    @Override
    public void onFocusCleared() {

    }
}
