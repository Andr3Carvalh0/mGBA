package io.mgba.Controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.mikepenz.aboutlibraries.LibsBuilder;

import io.mgba.Adapters.TabViewPager;
import io.mgba.Controller.Interfaces.IMainController;
import io.mgba.Data.Database.Game;
import io.mgba.Model.Interfaces.ILibrary;
import io.mgba.R;
import io.mgba.UI.Activities.SettingsCategoriesActivity;
import io.mgba.UI.Views.BottomSheetView;
import io.mgba.UI.Views.Interfaces.IBottomSheetView;
import io.mgba.mgba;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MainController implements IMainController, FloatingSearchView.OnMenuItemClickListener, FloatingSearchView.OnQueryChangeListener, FloatingSearchView.OnSearchListener, FloatingSearchView.OnFocusChangeListener{
    private static final int DEFAULT_PANEL = 1;
    private static final int SETTINGS_CODE = 738;

    private final AppCompatActivity context;
    private IBottomSheetView bottomSheetController;
    private FloatingSearchView mToolbar;
    private CompositeDisposable disposable = new CompositeDisposable();

    public MainController(@NonNull AppCompatActivity context) {
        this.context = context;
    }

    @Override
    public void prepareToolbar(FloatingSearchView mToolbar) {
        this.mToolbar = mToolbar;
        mToolbar.setOnMenuItemClickListener(this);
        mToolbar.setOnSearchListener(this);
        mToolbar.setOnQueryChangeListener(this);
        mToolbar.setOnFocusChangeListener(this);
    }

    @Override
    public void prepareTabLayout(TabLayout mTabLayout, ViewPager mViewPager, TabLayout.OnTabSelectedListener listener) {
        mTabLayout.addTab(mTabLayout.newTab().setText(context.getString(R.string.Favorites)));
        mTabLayout.addTab(mTabLayout.newTab().setText(context.getString(R.string.GBA)));
        mTabLayout.addTab(mTabLayout.newTab().setText(context.getString(R.string.GBC)));
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        TabViewPager adapter = new TabViewPager(context.getSupportFragmentManager());

        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(DEFAULT_PANEL);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.addOnTabSelectedListener(listener);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == SETTINGS_CODE && resultCode == Activity.RESULT_OK) {

        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab, ViewPager mViewPager) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onActionMenuItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_about)
        {
            new LibsBuilder()
                    .withActivityTheme(R.style.AboutTheme)
                    .withAboutIconShown(true)
                    .withAboutVersionShown(true)
                    .withAboutDescription(context.getString(R.string.About_description))
                    .start(context);
        }

        if (item.getItemId() == R.id.action_settings)
        {
            Intent settings = new Intent(context, SettingsCategoriesActivity.class);
            context.startActivityForResult(settings, SETTINGS_CODE);
        }
    }

    @Override
    public void showBottomSheet(Game game, BottomSheetLayout mSheetDialog) {
        if(bottomSheetController == null)
            bottomSheetController = new BottomSheetView(context.getApplicationContext());

        mSheetDialog.showWithSheetView(bottomSheetController.getView(mSheetDialog, game));
    }

    @Override
    public void onDestroy() {
        if(disposable != null)
            disposable.dispose();
    }

    @Override
    public ILibrary getILibrary() {
        return ((mgba)context.getApplication()).getLibrary();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

    @Override
    public void onSearchTextChanged(String oldQuery, String newQuery) {

        if (!oldQuery.equals("") && newQuery.equals("")) {
            mToolbar.clearSuggestions();
        } else {
            mToolbar.showProgress();
            disposable.add(getILibrary()
                    .query(newQuery)
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(games -> {
                        mToolbar.swapSuggestions(games);
                        mToolbar.hideProgress();
                    }));
        }
    }

    @Override
    public void onFocus() {
        onSearchTextChanged("", mToolbar.getQuery());
    }

    @Override
    public void onFocusCleared() {

    }

    @Override
    public void onSuggestionClicked(SearchSuggestion searchSuggestion) {

    }

    @Override
    public void onSearchAction(String currentQuery) {

    }
}
