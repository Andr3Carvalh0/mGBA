package io.mgba.View.Adapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import io.mgba.View.Activities.Interfaces.ILibrary;
import io.mgba.View.Fragments.Interfaces.BaseGameFragment;
import io.mgba.View.Fragments.Main.FavouritesFragment;
import io.mgba.View.Fragments.Main.GameboyAdvanceFragment;
import io.mgba.View.Fragments.Main.GameboyColorFragment;

public class TabViewPager extends FragmentStatePagerAdapter{

    private final int mTabCount;
    private final BaseGameFragment fragments[] = new BaseGameFragment[3];

    public TabViewPager(FragmentManager fm, int tabCount, ILibrary controller) {
        super(fm);
        this.mTabCount = tabCount;
        fragments[0] = new FavouritesFragment();
        fragments[0].setController(controller);

        fragments[1] = new GameboyAdvanceFragment();
        fragments[1].setController(controller);

        fragments[2] = new GameboyColorFragment();
        fragments[2].setController(controller);
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
