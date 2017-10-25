package io.mgba.Components.Views.Adapters.RecyclerView;


import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.l4digital.fastscroll.FastScroller;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.mgba.Components.Views.Adapters.RecyclerView.Interfaces.BaseAdapter;
import io.mgba.Data.DTOs.Game;
import io.mgba.R;
import me.grantland.widget.AutofitHelper;

public class LibraryAdapter extends BaseAdapter implements FastScroller.SectionIndexer {
    private Fragment view;

    public LibraryAdapter(List<? extends Game> list, Fragment view) {
        super(list, R.layout.game, (v) -> new ViewHolder((View) v));
        this.view = view;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder)holder).gameTitle.setText(((Game)items.get(position)).getName());

        if(((Game)items.get(position)).getCoverURL() != null && !((Game)items.get(position)).getCoverURL().equals("null"))
            Glide.with(view)
                 .load(((Game)items.get(position)).getCoverURL())
                 .into(((ViewHolder)holder).gameCover);
    }

    public void updateContent(List<? extends Game> list){
        super.items = list;
        notifyDataSetChanged();
    }

    @Override
    public String getSectionText(int position) {
        return ((Game)items.get(position)).getName().substring(0, 1);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.card_preview)
        ImageView gameCover;

        @BindView(R.id.card_title)
        TextView gameTitle;

        @BindView(R.id.master_container)
        RelativeLayout masterContainer;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            //Make the textview auto-"fit-able"
            AutofitHelper.create(gameTitle);
        }
    }
}
