package io.mgba.View.Adapters.RecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.l4digital.fastscroll.FastScroller;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.mgba.Data.DTOs.Interface.Game;
import io.mgba.R;
import io.mgba.View.Adapters.RecyclerView.Interfaces.BaseAdapter;

public class LibraryAdapter extends BaseAdapter implements FastScroller.SectionIndexer {

    public LibraryAdapter(List<? extends Game> list, int cover_size) {
        super(list, R.layout.game, (v) -> new ViewHolder((View) v, cover_size));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder)holder).gameTitle.setText(((Game)items.get(position)).getName());
    }

    @Override
    public String getSectionText(int position) {
        return ((Game)items.get(position)).getName().substring(0, 1);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.card_preview)
        ImageView gameCover;

        @BindView(R.id.card_title)
        TextView gameTitle;

        @BindView(R.id.master_container)
        RelativeLayout masterContainer;

        ViewHolder(View itemView, int cover_size) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            ViewGroup.LayoutParams params = masterContainer.getLayoutParams();

            params.height = cover_size;
            params.width = cover_size;
        }

    }
}
