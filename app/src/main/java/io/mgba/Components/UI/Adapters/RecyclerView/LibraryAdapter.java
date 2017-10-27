package io.mgba.Components.UI.Adapters.RecyclerView;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.common.base.Function;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.mgba.Components.UI.Adapters.RecyclerView.Interfaces.BaseAdapter;
import io.mgba.Data.DTOs.Game;
import io.mgba.R;
import me.grantland.widget.AutofitHelper;

public class LibraryAdapter extends BaseAdapter {
    private Fragment view;
    private Function<Game, Void> onClick;

    public LibraryAdapter(List<? extends Game> list, Fragment view, Context ctx, Function<Game, Void> onClick) {
        super(list, R.layout.game, (v) -> new ViewHolder((View) v), ctx);
        this.view = view;
        this.onClick = onClick;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Game mItem = (Game)items.get(position);

        ((ViewHolder) holder).gameTitle.setText(mItem.getName());
        Glide.with(view).load(mItem.getCoverURL()).into(((ViewHolder) holder).gameCover);

        //Click Event
        ((ViewHolder) holder).gameTitle.setOnClickListener(v -> onClick.apply(mItem));
        ((ViewHolder) holder).gameCover.setOnClickListener(v -> onClick.apply(mItem));
        ((ViewHolder) holder).masterContainer.setOnClickListener(v -> onClick.apply(mItem));
    }

    public void updateContent(List<? extends Game> list){
        super.items = list;
        notifyItemRangeInserted(0, items.size());
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
