package io.mgba.View.Fragments.Interfaces;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.mgba.Data.DTOs.Interface.Game;
import io.mgba.R;
import io.mgba.View.Activities.Interfaces.ILibrary;

public abstract class BaseGameFragment extends Fragment {

    @BindView(R.id.no_content_container)
    protected RelativeLayout mNoContentView;

    @BindView(R.id.content_container)
    protected RelativeLayout mMainView;

    @BindView(R.id.content_recyclerView)
    protected RecyclerView mRecyclerView;

    protected View mView;
    protected ILibrary controller;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
            setViewContent();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = prepareView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, mView);


        return mView;
    }

    private void setViewContent() {
        final List<? extends Game> games = getGames();

        if(games.size() != 0){}


    }

    protected View prepareView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.games_fragment, container, false);
    }

    protected ILibrary getLibraryController(){
        return controller;
    }

    protected abstract List<? extends Game> getGames();

    public void setController(@NonNull ILibrary controller) {
        this.controller = controller;
    }
}
