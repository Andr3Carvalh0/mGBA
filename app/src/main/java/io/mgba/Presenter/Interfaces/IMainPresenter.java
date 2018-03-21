package io.mgba.Presenter.Interfaces;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.flipboard.bottomsheet.BottomSheetLayout;

import io.mgba.Data.Database.Game;
import io.mgba.Model.Interfaces.ILibrary;

public interface IMainPresenter {

    void onActivityResult(int requestCode, int resultCode, Intent intent);
    void showBottomSheet(Game game);
    void onDestroy();
    ILibrary getILibrary();
    void onMenuItemSelected(MenuItem item);
    void onSearchTextChanged(String oldQuery, String newQuery);

}
