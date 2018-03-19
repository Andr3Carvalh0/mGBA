package io.mgba.Presenter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import com.arlib.floatingsearchview.FloatingSearchView;
import com.mikepenz.aboutlibraries.LibsBuilder;

import javax.inject.Inject;

import io.mgba.Presenter.Interfaces.IMainPresenter;
import io.mgba.Data.Database.Game;
import io.mgba.Model.Interfaces.ILibrary;
import io.mgba.R;
import io.mgba.UI.Activities.Interfaces.IMainView;
import io.mgba.UI.Activities.SettingsCategoriesActivity;
import io.mgba.mgba;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MainPresenter implements IMainPresenter {
    public static final int DEFAULT_PANEL = 1;
    private static final int SETTINGS_CODE = 738;
    private final IMainView mainView;

    @Inject
    ILibrary library;

    private final AppCompatActivity context;
    private CompositeDisposable disposable = new CompositeDisposable();

    public MainPresenter(@NonNull AppCompatActivity context, @NonNull IMainView mainView) {
        this.context = context;
        this.mainView = mainView;
        ((mgba)context.getApplication()).inject(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == SETTINGS_CODE && resultCode == Activity.RESULT_OK) {

        }
    }

    @Override
    public void onMenuItemSelected(MenuItem item) {
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
    public void showBottomSheet(Game game) {
        mainView.showGameInformation(game);
    }

    @Override
    public void onDestroy() {
        disposable.dispose();
    }

    @Override
    public ILibrary getILibrary() {
        return library;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

    @Override
    public void onSearchTextChanged(String oldQuery, String newQuery, FloatingSearchView mToolbar) {
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
}
