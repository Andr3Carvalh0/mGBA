package io.mgba.UI.Fragments.Main;

import android.os.Bundle;
import android.support.annotation.NonNull;
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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.mgba.Adapters.GameAdapter;
import io.mgba.Constants;
import io.mgba.Presenter.GamesPresenter;
import io.mgba.Presenter.Interfaces.IGamesPresenter;
import io.mgba.Data.Database.Game;
import io.mgba.R;
import io.mgba.UI.Fragments.Interfaces.IGamesFragment;

public class GameFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, IGamesFragment<Game> {

    private static final String TAG = "BaseFragment";

    @BindView(R.id.no_content_container)
    protected RelativeLayout noContentView;
    @BindView(R.id.content_recyclerView)
    protected TwoWayView recyclerView;
    @BindView(R.id.no_content_image)
    protected ImageView noContentImage;
    @BindView(R.id.no_content_message)
    protected TextView noContentMessage;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    private GameAdapter adapter;
    protected IGamesPresenter controller;
    protected View mView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controller = new GamesPresenter(this, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = prepareView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, mView);

        prepareDrawables();
        prepareRecyclerView();

        if(savedInstanceState != null)
            recyclerView.getLayoutManager()
                        .onRestoreInstanceState(savedInstanceState.getParcelable(Constants.MAIN_RECYCLER_CONTENT));

        showContent(false);
        loadGames();

        return mView;
    }

    protected void prepareRecyclerView() {
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.pink_accent_color),
                                                 getResources().getColor(R.color.colorPrimary),
                                                 getResources().getColor(R.color.green_accent_color),
                                                 getResources().getColor(R.color.yellow_accent_color),
                                                 getResources().getColor(R.color.cyan_accent_color));
        recyclerView.setHasFixedSize(true);
        adapter = new GameAdapter(this, getContext(), controller.getOnClick(), recyclerView);
        recyclerView.setAdapter(adapter);

    }

    protected void prepareDrawables() {
        noContentImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_videogame_asset_grey_500_48dp));
        noContentMessage.setText(R.string.no_games);
    }

    @Override
    public void showContent(boolean state) {
        noContentView.setVisibility(state ? View.GONE : View.VISIBLE);
        recyclerView.setVisibility(state ? View.VISIBLE : View.GONE);
    }

    protected View prepareView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.content_fragment, container, false);
    }

    protected void loadGames() {
        controller.loadGames(getILibrary());
    }

    private io.mgba.UI.Activities.Interfaces.ILibrary getILibrary(){
        return ((io.mgba.UI.Activities.Interfaces.ILibrary)getActivity());
    }

    @Override
    public void onRefresh() {
        controller.onRefresh(getILibrary());
    }

    @Override
    public void onStop() {
        super.onStop();
        controller.onDestroy();
    }

    @Override
    public void swapContent(List<Game> items) {
        adapter.swap(items);
    }

    @Override
    public void stopRefreshing() {
        swipeRefreshLayout.setRefreshing(false);
    }


    @Override
    public void handleItemClick(Game game) {
        getILibrary().showBottomSheet(game);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(Constants.MAIN_RECYCLER_CONTENT, recyclerView.getLayoutManager().onSaveInstanceState());

    }
}
