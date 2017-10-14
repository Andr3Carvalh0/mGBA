package io.mgba.View.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import com.arlib.floatingsearchview.FloatingSearchView;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.mikepenz.aboutlibraries.LibsBuilder;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.mgba.R;
import io.mgba.View.Activities.Interfaces.ParentActivity;
import io.mgba.View.Adapters.TabViewPager;

public class LibraryActivity extends ParentActivity implements TabLayout.OnTabSelectedListener, FloatingSearchView.OnMenuItemClickListener, FloatingSearchView.OnQueryChangeListener {

    @BindView(R.id.floating_search_view) FloatingSearchView mToolbar;
    @BindView(R.id.pager) ViewPager mViewPager;
    @BindView(R.id.bottomsheet) BottomSheetLayout mSheetDialog;

    private static final int DEFAULT_PANEL = 1;
    private TabViewPager adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);
        ButterKnife.bind(this);

        prepareToolbar();
        prepareViewPager();
    }

    private void prepareToolbar() {
        mToolbar.setOnMenuItemClickListener(this);
    }

    private void prepareViewPager() {
        TabLayout mTabLayout = (TabLayout) findViewById(R.id.tabLayout);

        mTabLayout.addTab(mTabLayout.newTab().setText(getString(R.string.Favorites)));
        mTabLayout.addTab(mTabLayout.newTab().setText(getString(R.string.GBA)));
        mTabLayout.addTab(mTabLayout.newTab().setText(getString(R.string.GBC)));
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        mViewPager = (ViewPager) findViewById(R.id.pager);

        adapter = new TabViewPager(getSupportFragmentManager(), mTabLayout.getTabCount());

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
}
