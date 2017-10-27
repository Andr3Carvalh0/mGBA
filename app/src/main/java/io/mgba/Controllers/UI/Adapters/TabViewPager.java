package io.mgba.Controllers.UI.Adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import io.mgba.Constants;
import io.mgba.Controllers.UI.Activities.Interfaces.ILibrary;
import io.mgba.Controllers.UI.Fragments.Main.FavouritesFragment;
import io.mgba.Controllers.UI.Fragments.Main.GameboyAdvanceFragment;
import io.mgba.Controllers.UI.Fragments.Main.GameboyColorFragment;
import io.mgba.Controllers.UI.Fragments.Main.Interfaces.ILibraryConsumer;
import io.mgba.Data.Wrappers.LibraryLists;

public class TabViewPager extends FragmentStatePagerAdapter{

    private final int mTabCount;
    private final ILibraryConsumer fragments[] = {new FavouritesFragment(), new GameboyAdvanceFragment(), new GameboyColorFragment()};

    public TabViewPager(FragmentManager fm, int tabCount, ILibrary callback) {
        super(fm);
        this.mTabCount = tabCount;
        this.setCallback(callback);
    }

    public TabViewPager(FragmentManager fm, int tabCount, ILibrary callback, LibraryLists games) {
        super(fm);
        this.mTabCount = tabCount;
        this.setGames(games);
        this.setCallback(callback);

    }

    private void setGames(LibraryLists games) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(Constants.GAMES_INTENT, games.getFavourites());
        ((Fragment)fragments[0]).setArguments(bundle);

        bundle = new Bundle();
        bundle.putParcelableArrayList(Constants.GAMES_INTENT, games.getGba());
        ((Fragment)fragments[1]).setArguments(bundle);

        bundle = new Bundle();
        bundle.putParcelableArrayList(Constants.GAMES_INTENT, games.getGbc());
        ((Fragment)fragments[2]).setArguments(bundle);
    }

    private void setCallback(ILibrary callback){
        for (int i = 0; i < fragments.length; i++){
            fragments[i].setOnClickCallback(callback);
        }
    }

    @Override
    public Fragment getItem(int position) {
        if (position > fragments.length)
            throw new RuntimeException("TabViewPager is trying to access a invalid position: " + position);

        return (Fragment) fragments[position];
    }

    @Override
    public int getCount() {
        return mTabCount;
    }

    public void setLoading(){
        fragments[0].setLoading();
        fragments[1].setLoading();
        fragments[2].setLoading();
    }

    public void consume(LibraryLists list) {
        fragments[0].consume(list.getFavourites());
        fragments[1].consume(list.getGba());
        fragments[2].consume(list.getGbc());
    }
}
