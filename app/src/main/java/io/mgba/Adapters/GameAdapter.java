package io.mgba.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.mgba.Adapters.Interfaces.BaseAdapter;
import io.mgba.Data.DTOs.Game;
import io.mgba.R;
import io.mgba.Utils.GlideUtils;
import io.mgba.mgba;
import io.reactivex.functions.Consumer;

public class GameAdapter extends BaseAdapter<Game>{
    private static final String TAG = "GameAdapter";
    private final Consumer<Game> onClick;
    private Fragment view;

    public GameAdapter(Fragment fragment, Context context, Consumer<Game> onClick) {
        super(R.layout.game, ViewHolder::new, context);
        this.view = fragment;
        this.onClick = onClick;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Game mItem = items.get(position);

        ((ViewHolder) holder).gameTitle.setVisibility(mItem.getCoverURL() == null ? View.VISIBLE : View.GONE);
        ((ViewHolder) holder).gameCover.setVisibility(mItem.getCoverURL() != null ? View.VISIBLE : View.GONE);

        if(mItem.getCoverURL() == null){
            ((ViewHolder) holder).gameTitle.setText(mItem.getName());
        }else{
            GlideUtils.init(view, mItem.getCoverURL())
                      .setPlaceholders(R.drawable.placeholder, R.drawable.error)
                      .build(((ViewHolder) holder).gameCover);
        }

        //Click Event
        ((ViewHolder) holder).masterContainer.setOnClickListener(v -> {
            try {
                onClick.accept(mItem);
            } catch (Exception e) {
                mgba.printLog(TAG, "Cannot execute consumer callable");
            }
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.card_preview)
        ImageView gameCover;

        @BindView(R.id.card_title)
        TextView gameTitle;

        @BindView(R.id.master_container)
        RelativeLayout masterContainer;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
