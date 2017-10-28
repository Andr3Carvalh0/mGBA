package io.mgba.Controllers.UI.Adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

import io.mgba.Constants;
import io.mgba.Controllers.UI.Activities.Interfaces.ILibrary;
import io.mgba.Controllers.UI.Fragments.Main.FavouritesFragment;
import io.mgba.Controllers.UI.Fragments.Main.GameboyAdvanceFragment;
import io.mgba.Controllers.UI.Fragments.Main.GameboyColorFragment;
import io.mgba.Controllers.UI.Fragments.Main.Interfaces.ILibraryConsumer;
import io.mgba.Data.Wrappers.LibraryLists;

import static io.mgba.mgba.printLog;

public class TabViewPager extends FragmentStatePagerAdapter{
    private final static String TAG = "TabPager";

    private final int mTabCount;
    private final FragmentManager fragmentManager;
    private final ILibraryConsumer fragments[];
    private final ILibrary callback;
    private final LibraryLists games;

    public TabViewPager(FragmentManager fm, int tabCount, ILibrary callback, LibraryLists games) {
        super(fm);
        printLog(TAG, "CTOR");
        this.fragmentManager = fm;
        this.fragments = new ILibraryConsumer[tabCount];
        this.mTabCount = tabCount;
        this.callback = callback;
        this.games = games;
    }


    @Override
    public Fragment instantiateItem(ViewGroup container, int position){
        Fragment fragment = getItem(position);

        if(!hasAlreadyCreated()) {
            FragmentTransaction trans = fragmentManager.beginTransaction();
            trans.add(container.getId(), fragment, "fragment:" + position);
            trans.commit();

            //Add games
            setGames();
        }

        setCallback();
        return fragment;
    }

    @Override
    public Fragment getItem(int position) {
        if(fragments[position] == null)
                createFragments();

        return (Fragment) fragments[position];
    }

    private void createFragments() {
        fragments[0] = fragmentManager.findFragmentByTag("fragment:0") == null
                                                ? new FavouritesFragment()
                                                : (ILibraryConsumer) fragmentManager.findFragmentByTag("fragment:0");

        fragments[1] = fragmentManager.findFragmentByTag("fragment:1") == null
                                                ? new GameboyAdvanceFragment()
                                                : (ILibraryConsumer) fragmentManager.findFragmentByTag("fragment:1");

        fragments[2] = fragmentManager.findFragmentByTag("fragment:2") == null
                ? new GameboyColorFragment()
                : (ILibraryConsumer) fragmentManager.findFragmentByTag("fragment:2");
    }

    private boolean hasAlreadyCreated(){
        return fragmentManager.findFragmentByTag("fragment:0") != null
                && fragmentManager.findFragmentByTag("fragment:1") != null
                && fragmentManager.findFragmentByTag("fragment:2") != null;
    }

    @Override
    public int getCount() {
        return mTabCount;
    }

    private void setCallback(){
        for (int i = 0; i < fragments.length; i++){
            fragments[i].setOnClickCallback(callback);
        }
    }

    private void setGames() {
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

    public void consume(LibraryLists list) {
        fragments[0].consume(list.getFavourites());
        fragments[1].consume(list.getGba());
        fragments[2].consume(list.getGbc());
    }
}
