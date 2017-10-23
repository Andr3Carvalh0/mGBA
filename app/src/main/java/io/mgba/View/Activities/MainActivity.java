package io.mgba.View.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import com.arlib.floatingsearchview.FloatingSearchView;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.mikepenz.aboutlibraries.LibsBuilder;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.mgba.Controller.GamesController;
import io.mgba.Controller.PreferencesController;
import io.mgba.Data.DTOs.GameboyAdvanceGame;
import io.mgba.Data.DTOs.GameboyGame;
import io.mgba.Data.DTOs.Interface.Game;
import io.mgba.R;
import io.mgba.View.Activities.Interfaces.IMain;
import io.mgba.View.Adapters.TabViewPager;
import io.mgba.mgba;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener, FloatingSearchView.OnMenuItemClickListener, FloatingSearchView.OnQueryChangeListener, IMain {

    @BindView(R.id.floating_search_view) FloatingSearchView mToolbar;
    @BindView(R.id.pager) ViewPager mViewPager;
    @BindView(R.id.bottomsheet) BottomSheetLayout mSheetDialog;
    @BindView(R.id.tabLayout) TabLayout mTabLayout;

    private GamesController gamesController;
    private static final int DEFAULT_PANEL = 1;
    private TabViewPager adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);
        ButterKnife.bind(this);

        gamesController = new
                GamesController((((mgba)getApplication()).getPreference(PreferencesController.GAMES_DIRECTORY, "")), getApplicationContext());
        prepareToolbar();
        prepareViewPager();

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
    //The result will be pass via a broadcastreceiver with id value as 0
    public void getFavouritesAsync() {
    }

    @Override
    //The result will be pass via a broadcastreceiver with id value as 1
    public void getGBAGamesAsync() {
        gamesController.getGBAGames();
    }

    @Override
    //The result will be pass via a broadcastreceiver with id value as 2
    public void getGBGamesAsync() {
        gamesController.getGBGames();
    }

    @Override
    public int[] getItemsPerColumn() {
        return ((mgba)getApplication()).getItemsPerColumn();
    }

    @Override
    public int getDPWidth() {
        return ((mgba)getApplication()).getDPWidth();
    }
}
