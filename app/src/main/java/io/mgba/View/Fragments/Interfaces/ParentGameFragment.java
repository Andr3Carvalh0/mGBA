package io.mgba.View.Fragments.Interfaces;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.l4digital.fastscroll.FastScrollRecyclerView;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.mgba.Controller.MetricsCalculator;
import io.mgba.Data.DTOs.GameboyAdvanceGame;
import io.mgba.Data.DTOs.Interface.Game;
import io.mgba.R;
import io.mgba.View.Activities.Interfaces.ILibrary;
import io.mgba.View.Activities.Interfaces.IMetrics;
import io.mgba.View.Adapters.RecyclerView.LibraryAdapter;
import io.mgba.View.Decorations.ItemDecoration;

public abstract class ParentGameFragment extends Fragment{

    @BindView(R.id.no_content_container)
    protected RelativeLayout mNoContentView;

    @BindView(R.id.content_container)
    protected RelativeLayout mMainView;

    @BindView(R.id.content_recyclerView)
    protected FastScrollRecyclerView mRecyclerView;

    protected View mView;
    protected ILibrary controller;
    private IMetrics metricsController;
    private List<Game> games = new LinkedList<>();

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
            fetchLibrary();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (games != null && games.size() != 0) {
            mNoContentView.setVisibility(View.GONE);
            mMainView.setVisibility(View.VISIBLE);
            prepareRecyclerView(games);
        }else{
            mNoContentView.setVisibility(View.VISIBLE);
            mMainView.setVisibility(View.GONE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = prepareView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, mView);

        return mView;
    }

    private void fetchLibrary() {
        games = (List<Game>) getGames();
    }

    private void prepareRecyclerView(List<? extends Game> games) {
        int[] settings = metricsController.getItemsPerColumn();

        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), settings[0]));
        mRecyclerView.setAdapter(new LibraryAdapter(games, settings[1]));
        mRecyclerView.addItemDecoration
                (new ItemDecoration((int)MetricsCalculator.convertPixelsToDp(
                                    getResources().getDimensionPixelSize(R.dimen.game_space_borders), getContext()),
                                    settings[0], metricsController.getDPWidth(),
                                    settings[2], getContext()));
    }

    protected View prepareView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.games_fragment, container, false);
    }

    protected abstract List<? extends Game> getGames();

    public void setController(@NonNull ILibrary controller) {
        this.controller = controller;
    }

    public void setMetricsController(@NonNull IMetrics metricsController) {
        this.metricsController = metricsController;
    }

}
