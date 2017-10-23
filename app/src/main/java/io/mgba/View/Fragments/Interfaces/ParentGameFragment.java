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
import io.mgba.Data.DTOs.Interface.Game;
import io.mgba.R;
import io.mgba.View.Activities.Interfaces.IMain;
import io.mgba.View.Adapters.RecyclerView.LibraryAdapter;
import io.mgba.View.Decorations.EqualSpacingItemDecoration;


public abstract class ParentGameFragment extends Fragment{

    @BindView(R.id.no_content_container)
    protected RelativeLayout mNoContentView;

    @BindView(R.id.content_container)
    protected RelativeLayout mMainView;

    @BindView(R.id.content_recyclerView)
    protected FastScrollRecyclerView mRecyclerView;

    protected View mView;
    protected IMain controller;

    @Override
    public void onResume() {
        super.onResume();

        mNoContentView.setVisibility(View.VISIBLE);
        mMainView.setVisibility(View.GONE);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = prepareView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, mView);

        return mView;
    }

    private void prepareRecyclerView(List<? extends Game> games) {
        int[] settings = controller.getItemsPerColumn();

        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), settings[0], GridLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(new LibraryAdapter(games, settings[1]));
        mRecyclerView.addItemDecoration(
                new EqualSpacingItemDecoration(
                    getResources().getDimensionPixelSize(R.dimen.game_space_borders),
                    EqualSpacingItemDecoration.GRID
                ));
    }

    protected View prepareView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.games_fragment, container, false);
    }

    public void setController(@NonNull IMain controller) {
        this.controller = controller;
    }
}
