package io.mgba.View.Activities;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import io.mgba.View.Fragments.FavouritesFragment;
import io.mgba.View.Fragments.GameboyAdvanceFragment;
import io.mgba.View.Fragments.GameboyColorFragment;
import io.mgba.View.Fragments.Interfaces.ParentFragment;

public class TabViewPager extends FragmentStatePagerAdapter{

    private final int mTabCount;
    private static final ParentFragment fragments[] = {new FavouritesFragment(), new GameboyAdvanceFragment(), new GameboyColorFragment()};

    public TabViewPager(FragmentManager fm, int tabCount) {
        super(fm);
        this.mTabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        if (position > fragments.length)
            throw new RuntimeException("TabViewPager is trying to access a invalid position: " + position);

        return fragments[position];
    }

    @Override
    public int getCount() {
        return mTabCount;
    }
}
