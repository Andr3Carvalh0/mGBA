package io.mgba.UI.Fragments.Main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.lucasr.twowayview.layout.TwoWayView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.mgba.Controller.GamesController;
import io.mgba.Controller.Interfaces.IGamesController;
import io.mgba.Data.Database.Game;
import io.mgba.R;

public class GameFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "BaseFragment";

    @BindView(R.id.no_content_container)
    protected RelativeLayout mNoContentView;

    @BindView(R.id.content_recyclerView)
    protected TwoWayView mRecyclerView;

    @BindView(R.id.no_content_image)
    protected ImageView noContentImage;

    @BindView(R.id.no_content_message)
    protected TextView noContentMessage;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    protected IGamesController controller;
    protected View mView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controller = new GamesController(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = prepareView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, mView);

        prepareDrawables();
        prepareRecyclerView();

        showContent(false);
        loadGames();

        return mView;
    }

    protected void prepareRecyclerView() {
        controller.prepareRecyclerView(mSwipeRefreshLayout, mRecyclerView, this, this::onClick);
    }

    protected void prepareDrawables() {
        noContentImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_videogame_asset_grey_500_48dp));
        noContentMessage.setText(R.string.no_games);
    }

    protected void showContent(boolean v) {
        mNoContentView.setVisibility(v ? View.GONE : View.VISIBLE);
        mRecyclerView.setVisibility(v ? View.VISIBLE : View.GONE);
    }

    protected View prepareView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.content_fragment, container, false);
    }

    protected void loadGames() {
        controller.loadGames(getILibrary(), this::showContent);
    }

    private io.mgba.UI.Activities.Interfaces.ILibrary getILibrary(){
        return ((io.mgba.UI.Activities.Interfaces.ILibrary)getActivity());
    }

    private void onClick(Game game) {
        controller.showOnClick(game, getILibrary());
    }

    @Override
    public void onRefresh() {
        controller.onRefresh(getILibrary(), mSwipeRefreshLayout);
    }
}
