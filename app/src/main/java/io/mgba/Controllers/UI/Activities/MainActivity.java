package io.mgba.Controllers.UI.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.mikepenz.aboutlibraries.LibsBuilder;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.mgba.Constants;
import io.mgba.Controllers.UI.Activities.Interfaces.ILibrary;
import io.mgba.Controllers.UI.Activities.Interfaces.LibraryActivity;
import io.mgba.Controllers.UI.Adapters.TabViewPager;
import io.mgba.Controllers.UI.Views.BottomSheetView;
import io.mgba.Controllers.UI.Views.Interfaces.IBottomSheetView;
import io.mgba.Data.DTOs.Game;
import io.mgba.Data.Wrappers.LibraryLists;
import io.mgba.R;
import io.mgba.mgba;

public class MainActivity extends LibraryActivity implements TabLayout.OnTabSelectedListener, FloatingSearchView.OnMenuItemClickListener, FloatingSearchView.OnQueryChangeListener, FloatingSearchView.OnSearchListener, ILibrary {

    private static final int DEFAULT_PANEL = 1;
    private static final int SETTINGS_CODE = 738;

    @BindView(R.id.floating_search_view) FloatingSearchView mToolbar;
    @BindView(R.id.pager) ViewPager mViewPager;
    @BindView(R.id.bottomsheet) BottomSheetLayout mSheetDialog;
    @BindView(R.id.tabLayout) TabLayout mTabLayout;

    private TabViewPager adapter;
    private IBottomSheetView bottomSheetController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);

        ButterKnife.bind(this);
        LibraryLists games = libraryController.getCachedList();


        prepareToolbar();
        prepareViewPager(games);
    }

    private void prepareToolbar() {
        mToolbar.setOnMenuItemClickListener(this);
        mToolbar.setOnSearchListener(this);
        mToolbar.setOnQueryChangeListener(this);
    }

    private void prepareViewPager(LibraryLists games) {
        mTabLayout.addTab(mTabLayout.newTab().setText(getString(R.string.Favorites)));
        mTabLayout.addTab(mTabLayout.newTab().setText(getString(R.string.GBA)));
        mTabLayout.addTab(mTabLayout.newTab().setText(getString(R.string.GBC)));
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        adapter = new TabViewPager(getSupportFragmentManager(), mTabLayout.getTabCount(), this, games);

        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(DEFAULT_PANEL);
        mViewPager.setOffscreenPageLimit(mTabLayout.getTabCount() - 1);

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
                    //start the activity
                    .withActivityTheme(R.style.AboutTheme)
                    .withAboutIconShown(true)
                    .withAboutVersionShown(true)
                    .withAboutDescription(getString(R.string.About_description))
                    .start(this);
        }

        if (item.getItemId() == R.id.action_settings)
        {
            Intent settings = new Intent(this, SettingsActivity.class);
            startActivityForResult(settings, SETTINGS_CODE);
        }
    }

    @Override
    public void showBottomSheet(Game game) {
        prepareBottomSheetController();
        mSheetDialog.showWithSheetView(bottomSheetController.getView(mSheetDialog, game));

        if(((mgba)getApplication()).isInLandscape()) {
            mSheetDialog.expandSheet();
        }
    }

    private void prepareBottomSheetController(){
        if(bottomSheetController == null)
            bottomSheetController = new BottomSheetView(getApplicationContext());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == SETTINGS_CODE && resultCode == Activity.RESULT_OK) {
            if(intent.getExtras().getBoolean(Constants.RELOAD_LIBRARY))
                reloadGames(Constants.GAME_PATH);
        }
    }

    private void reloadGames(String newPath){
        ((mgba)getApplication()).showProgressDialog(this);
        super.libraryController.updateFileServicePath(newPath);
        super.prepareGames(this::onDoneFetch);
    }

    private Void onDoneFetch(LibraryLists lists){
        if(adapter != null)
            adapter.consume(lists);

        ((mgba)getApplication()).stopProgressDialog();
        return null;
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

            List<Game> suggestions = new LinkedList<>();

            if (!(newQuery == null || newQuery.length() == 0)) {
                for (Game game : libraryController.getCachedList().getLibrary()) {
                    if (game.getName()
                            .toLowerCase().contains(newQuery.toLowerCase())) {
                        suggestions.add(game);
                    }
                }
            }

            mToolbar.swapSuggestions(suggestions);
            mToolbar.hideProgress();
        }
    }
}
