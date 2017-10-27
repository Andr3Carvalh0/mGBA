package io.mgba.Components.UI.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.mikepenz.aboutlibraries.LibsBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.mgba.Components.Services.ProcessingService;
import io.mgba.Components.UI.Activities.Interfaces.ILibrary;
import io.mgba.Components.UI.Adapters.TabViewPager;
import io.mgba.Controller.BottomSheetController;
import io.mgba.Controller.Interfaces.IBottomSheetController;
import io.mgba.Controller.Interfaces.ILibraryController;
import io.mgba.Controller.Interfaces.LibraryLists;
import io.mgba.Controller.LibraryController;
import io.mgba.Controller.PreferencesController;
import io.mgba.Data.DTOs.Game;
import io.mgba.R;
import io.mgba.mgba;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener, FloatingSearchView.OnMenuItemClickListener, FloatingSearchView.OnQueryChangeListener, ILibrary {

    private static final int DEFAULT_PANEL = 1;

    @BindView(R.id.floating_search_view) FloatingSearchView mToolbar;
    @BindView(R.id.pager) ViewPager mViewPager;
    @BindView(R.id.bottomsheet) BottomSheetLayout mSheetDialog;
    @BindView(R.id.tabLayout) TabLayout mTabLayout;
    private ILibraryController libraryController;
    private TabViewPager adapter;
    private IBottomSheetController bottomSheetController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);
        ButterKnife.bind(this);

        libraryController = new
                LibraryController((((mgba)getApplication()).getPreference(PreferencesController.GAMES_DIRECTORY, "")), getApplicationContext());

        prepareToolbar();
        prepareViewPager();
        prepareGames();

    }

    private void prepareGames(){
        if(!((mgba)getApplication()).isServiceRunning(ProcessingService.class)) {
            libraryController.prepareGames(this::onDoneFetch);
            adapter.setLoading();
        }
    }

    private void prepareToolbar() {
        mToolbar.setOnMenuItemClickListener(this);
    }

    private void prepareViewPager() {
        mTabLayout.addTab(mTabLayout.newTab().setText(getString(R.string.Favorites)));
        mTabLayout.addTab(mTabLayout.newTab().setText(getString(R.string.GBA)));
        mTabLayout.addTab(mTabLayout.newTab().setText(getString(R.string.GBC)));
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        adapter = new TabViewPager(getSupportFragmentManager(), mTabLayout.getTabCount(), this);

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
            startActivity(settings);
        }
    }

    @Override
    public void onSearchTextChanged(String oldQuery, String newQuery) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        libraryController.stop();
    }

    private Void onDoneFetch(LibraryLists lists){
        if(adapter != null)
            adapter.consume(lists);

        return null;
    }

    @Override
    public void showBottomSheet(Game game) {
        prepareBottomSheetController();
        mSheetDialog.showWithSheetView(bottomSheetController.getView(mSheetDialog, game));
    }

    private void prepareBottomSheetController(){
        if(bottomSheetController == null)
            bottomSheetController = new BottomSheetController(getApplicationContext());
    }
}
