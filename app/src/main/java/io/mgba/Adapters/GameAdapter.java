package io.mgba.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.mgba.Adapters.Interfaces.BaseAdapter;
import io.mgba.Data.DTOs.Game;
import io.mgba.R;
import io.mgba.mgba;
import io.reactivex.functions.Consumer;

public class GameAdapter extends BaseAdapter{
    private static final String TAG = "GameAdapter";
    private final Consumer<Game> onClick;
    private Fragment view;

    public GameAdapter(Fragment fragment, Context context, Consumer<Game> onClick) {
        super(R.layout.game, (v) -> new ViewHolder((View) v), context);
        this.view = fragment;
        this.onClick = onClick;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Game mItem = (Game)items.get(position);

        if(mItem.getCoverURL() == null){
            ((ViewHolder) holder).gameTitle.setText(mItem.getName());

        }else{
            ((ViewHolder) holder).gameTitle.setVisibility(View.GONE);

            Glide.with(view)
                    .load(mItem.getCoverURL())
                    .apply(new RequestOptions().placeholder(R.drawable.placeholder).error(R.drawable.error))
                    .into(((ViewHolder) holder).gameCover);
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
        }
    }
}
