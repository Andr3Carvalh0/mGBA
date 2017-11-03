package io.mgba.Controllers.UI.Fragments.Main.Interfaces;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.lucasr.twowayview.layout.TwoWayView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.mgba.Constants;
import io.mgba.Controllers.UI.Activities.Interfaces.ILibrary;
import io.mgba.Controllers.UI.Adapters.RecyclerView.LibraryAdapter;
import io.mgba.Data.DTOs.Game;
import io.mgba.R;

import static io.mgba.mgba.printLog;

public abstract class BaseGameFragment extends Fragment implements ILibraryConsumer {

    private static final String TAG = "BaseFragment";
    @BindView(R.id.no_content_container)
    protected RelativeLayout mNoContentView;

    @BindView(R.id.content_recyclerView)
    protected TwoWayView mRecyclerView;

    @BindView(R.id.no_content_image)
    protected ImageView noContentImage;

    @BindView(R.id.no_content_message)
    protected TextView noContentMessage;

    protected View mView;
    private LibraryAdapter adapter;
    private ArrayList<Game> games = new ArrayList<>();
    private ILibrary onClickCallback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = prepareView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, mView);

        prepareDrawables();
        showContent(false);

        Bundle args = getArguments();

        if (args != null) {
            consume((ArrayList<Game>) args.get(Constants.GAMES_INTENT));
        }

        return mView;
    }

    protected void prepareDrawables() {
        noContentImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_videogame_asset_grey_500_48dp));
        noContentMessage.setText(R.string.no_games);
    }

    protected void prepareRecyclerView(ArrayList<? extends Game> games) {
        if (games.size() == 0)
            return;

        showContent(true);

        if (adapter == null) {
            mRecyclerView.setHasFixedSize(true);
            adapter = new LibraryAdapter(games, this, getContext(), this::onItemClick);
            mRecyclerView.setAdapter(adapter);
        }

        adapter.updateContent(games);
    }

    protected void showContent(boolean val){
        mNoContentView.setVisibility(val ? View.GONE : View.VISIBLE);
        mRecyclerView.setVisibility(val ? View.VISIBLE : View.GONE);
    }

    protected View prepareView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.content_fragment, container, false);
    }

    @Override
    public void consume(ArrayList<Game> list) {
        setGameList(list);
        prepareRecyclerView(list);

    }

    @Override
    public void setLoading() {

    }

    @Override
    public void setOnClickCallback(ILibrary callback) {
        onClickCallback = callback;
    }

    private Void onItemClick(Game game) {
        printLog(TAG, "onClickCalled!");
        if (onClickCallback != null)
            onClickCallback.showBottomSheet(game);

        return null;
    }

    protected void setGameList(ArrayList<Game> list) {
        this.games = list;
    }

}
