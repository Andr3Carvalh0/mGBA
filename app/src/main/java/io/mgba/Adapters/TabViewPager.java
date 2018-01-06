package io.mgba.Adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import io.mgba.Constants;
import io.mgba.Data.Platform;
import io.mgba.UI.Fragments.Main.FavouritesFragment;
import io.mgba.UI.Fragments.Main.GameFragment;

import static io.mgba.mgba.printLog;

public class TabViewPager extends FragmentPagerAdapter {
    private final static String TAG = "mgba:TabPager";

    private final Platform[] platforms = {Platform.FAVS, Platform.GBA, Platform.GBC};

    public TabViewPager(FragmentManager fm) {
        super(fm);
        printLog(TAG, "CTOR");
    }

    @Override
    public Fragment getItem(int position) {
        Bundle args = new Bundle();
        args.putSerializable(Constants.ARG_PLATFORM, platforms[position]);

        final GameFragment fragment = position == 0 ? new FavouritesFragment() : new GameFragment();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public int getCount() {
        return platforms.length;
    }
}
