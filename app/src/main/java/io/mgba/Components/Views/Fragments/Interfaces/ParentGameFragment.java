package io.mgba.Components.Views.Fragments.Interfaces;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.l4digital.fastscroll.FastScrollRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.mgba.Components.Views.Adapters.RecyclerView.LibraryAdapter;
import io.mgba.Data.DTOs.Game;
import io.mgba.R;


public abstract class ParentGameFragment extends Fragment implements ILibraryConsumer{

    @BindView(R.id.no_content_container)
    protected RelativeLayout mNoContentView;

    @BindView(R.id.content_container)
    protected RelativeLayout mMainView;

    @BindView(R.id.content_recyclerView)
    protected FastScrollRecyclerView mRecyclerView;

    protected View mView;
    private LibraryAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = prepareView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, mView);

        return mView;
    }

    protected void prepareRecyclerView(List<? extends Game> games) {
        if(games.size() == 0)
            return;


        mNoContentView.setVisibility(View.GONE);
        mMainView.setVisibility(View.VISIBLE);

        if(adapter == null) {
            FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(getContext());
            layoutManager.setFlexWrap(FlexWrap.WRAP);
            layoutManager.setJustifyContent(JustifyContent.SPACE_AROUND);

            mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
            adapter = new LibraryAdapter(games, this);
            mRecyclerView.setAdapter(adapter);
        }


        adapter.updateContent(games);
    }

    protected View prepareView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.games_fragment, container, false);
    }

    @Override
    public void consume(List<Game> list){
        if(isVisible()){
            setGameList(list);
            prepareRecyclerView(list);
        }else {

        }
    }

    protected abstract void setGameList(List<Game> list);
}
