package io.mgba.Adapters.Settings;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.mgba.Adapters.Interfaces.BaseAdapter;
import io.mgba.Adapters.Interfaces.SingleViewHolderAdapter;
import io.mgba.Data.Settings.Settings;
import io.mgba.R;
import io.mgba.mgba;
import io.reactivex.functions.Consumer;

public class SettingsAdapter extends SingleViewHolderAdapter<Settings> {
    private static final String TAG = "mgba:SettingsAdapter";
    private final Consumer<Settings> onClick;

    public SettingsAdapter(List<Settings> settings, Context context, Consumer<Settings> onClick, RecyclerView recyclerView) {
        super(settings, recyclerView, context, R.layout.category_element, ViewHolder::new);
        this.onClick = onClick;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Settings item = items.get(position);

        ((ViewHolder) holder).icon.setImageDrawable(mCtx.getDrawable(item.getResource()));
        ((ViewHolder) holder).title.setText(item.getTitle());

        ((ViewHolder) holder).container.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final Settings mItem = items.get(getPositionBasedOnView(v));

        try {
            onClick.accept(mItem);
        } catch (Exception e) {
            mgba.printLog(TAG, "Cannot execute consumer callable");
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.setting_icon)
        ImageView icon;
        @BindView(R.id.setting_title)
        TextView title;
        @BindView(R.id.container)
        RelativeLayout container;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
