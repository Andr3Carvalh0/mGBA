package io.mgba.Presenter;

import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;
import com.mikepenz.aboutlibraries.LibsBuilder;
import javax.inject.Inject;
import io.mgba.Presenter.Interfaces.IMainPresenter;
import io.mgba.Data.Database.Game;
import io.mgba.Model.Interfaces.ILibrary;
import io.mgba.R;
import io.mgba.UI.Activities.Interfaces.IMainView;
import io.mgba.UI.Activities.SettingsActivity;
import io.mgba.Utils.IDependencyInjector;
import io.mgba.Utils.IResourcesManager;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MainPresenter implements IMainPresenter {
    public static final int DEFAULT_PANEL = 1;
    private static final int SETTINGS_CODE = 738;

    @Inject ILibrary library;
    private CompositeDisposable disposable = new CompositeDisposable();
    private final IMainView view;
    @Inject IResourcesManager resourcesManager;

    public MainPresenter(@NonNull IMainView mainView, @NonNull IDependencyInjector dependencyInjector) {
        this.view = mainView;
        dependencyInjector.inject(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == SETTINGS_CODE && resultCode == Activity.RESULT_OK) {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public void onMenuItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_about)
        {
            final LibsBuilder aboutPanel = new LibsBuilder()
                    .withActivityTheme(R.style.AboutTheme)
                    .withAboutIconShown(true)
                    .withAboutVersionShown(true)
                    .withAboutDescription(resourcesManager.getString(R.string.About_description));

            view.startAboutPanel(aboutPanel);
        }

        if (item.getItemId() == R.id.action_settings)
        {
            view.startActivityForResult(SettingsActivity.class, SETTINGS_CODE);
        }
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
    public void onSearchTextChanged(String oldQuery, String newQuery) {
        if (!oldQuery.equals("") && newQuery.equals("")) {
            view.clearSuggestions();
        } else {
            view.showProgress();
            disposable.add(getILibrary()
                    .query(newQuery)
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(view::showSuggestions));
        }
    }
}
