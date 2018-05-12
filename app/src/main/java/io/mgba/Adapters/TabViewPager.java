package io.mgba.Adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import io.mgba.Constants;
import io.mgba.UI.Fragments.Main.FavouritesFragment;
import io.mgba.UI.Fragments.Main.GameFragment;

import static io.mgba.mgba.printLog;

public class TabViewPager extends FragmentStatePagerAdapter {
    private final static String TAG = "mgba:TabPager";
    private final int[] platforms = {Constants.PLATFORM_FAVS, Constants.PLATFORM_GBA, Constants.PLATFORM_GBC};

    public TabViewPager(FragmentManager fm) {
        super(fm);
        printLog(TAG, "CTOR");
    }

    @Override
    public Fragment getItem(int position) {
        Bundle args = new Bundle();
        args.putInt(Constants.ARG_PLATFORM, platforms[position]);

        final GameFragment fragment = position == 0 ? new FavouritesFragment() : new GameFragment();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public int getCount() {
        return platforms.length;
    }
}
