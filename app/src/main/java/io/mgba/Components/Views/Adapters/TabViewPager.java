package io.mgba.Components.Views.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import io.mgba.Components.Views.Fragments.Interfaces.ParentGameFragment;
import io.mgba.Components.Views.Fragments.Main.FavouritesFragment;
import io.mgba.Components.Views.Fragments.Main.GameboyAdvanceFragment;
import io.mgba.Components.Views.Fragments.Main.GameboyColorFragment;

public class TabViewPager extends FragmentStatePagerAdapter{

    private final int mTabCount;
    private final ParentGameFragment fragments[] = {new FavouritesFragment(), new GameboyAdvanceFragment(), new GameboyColorFragment()};

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
