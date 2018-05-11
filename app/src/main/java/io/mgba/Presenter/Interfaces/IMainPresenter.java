package io.mgba.Presenter.Interfaces;

import android.content.Intent;
import android.view.MenuItem;
import io.mgba.Model.Interfaces.ILibrary;

public interface IMainPresenter {

    void onActivityResult(int requestCode, int resultCode, Intent intent);
    void onDestroy();
    ILibrary getILibrary();
    void onMenuItemSelected(MenuItem item);
    void onSearchTextChanged(String oldQuery, String newQuery);

}
