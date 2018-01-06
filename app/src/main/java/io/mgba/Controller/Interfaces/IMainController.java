package io.mgba.Controller.Interfaces;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.flipboard.bottomsheet.BottomSheetLayout;

import io.mgba.Data.Database.Game;
import io.mgba.Model.Interfaces.ILibrary;

public interface IMainController  {
    void prepareToolbar(FloatingSearchView mToolbar);

    void prepareTabLayout(TabLayout mTabLayout, ViewPager mViewPager, TabLayout.OnTabSelectedListener listener);

    void onActivityResult(int requestCode, int resultCode, Intent intent);

    void onTabSelected(TabLayout.Tab tab, ViewPager mViewPager);

    void showBottomSheet(Game game, BottomSheetLayout mSheetDialog);

    void onDestroy();

    ILibrary getILibrary();

    void onSaveInstanceState(Bundle outState);
}
