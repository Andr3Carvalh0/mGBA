package io.mgba.View.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import io.mgba.View.Activities.Interfaces.IMain;
import io.mgba.View.Fragments.Interfaces.ParentGameFragment;
import io.mgba.View.Fragments.Main.FavouritesFragment;
import io.mgba.View.Fragments.Main.GameboyAdvanceFragment;
import io.mgba.View.Fragments.Main.GameboyColorFragment;

public class TabViewPager extends FragmentStatePagerAdapter{

    private final int mTabCount;
    private final ParentGameFragment fragments[];
    private final IMain mainController;

    public TabViewPager(FragmentManager fm, int tabCount, IMain controller) {
        super(fm);
        this.mTabCount = tabCount;
        this.fragments = new ParentGameFragment[tabCount];
        this.mainController = controller;
    }

    @Override
    public Fragment getItem(int position) {
        if (position > fragments.length)
            throw new RuntimeException("TabViewPager is trying to access a invalid position: " + position);

        if(fragments[position] == null){
            switch (position){
                case 0:
                    fragments[0] = new FavouritesFragment();
                    break;
                case 1:
                    fragments[1] = new GameboyAdvanceFragment();
                    break;
                case 2:
                    fragments[2] = new GameboyColorFragment();
                    break;
            }
            fragments[position].setController(mainController);
        }

        return fragments[position];
    }

    @Override
    public int getCount() {
        return mTabCount;
    }
}
