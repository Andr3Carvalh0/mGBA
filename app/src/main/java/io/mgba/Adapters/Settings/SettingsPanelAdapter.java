package io.mgba.Adapters.Settings;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.List;

import io.mgba.Adapters.Interfaces.MultipleViewHolderAdapter;
import io.mgba.Adapters.Settings.Interfaces.SettingsPanel;
import io.reactivex.functions.BiFunction;

public class SettingsPanelAdapter extends MultipleViewHolderAdapter<SettingsPanel> {
    private static final String TAG = "mgba:SettingsAdapter";

    public SettingsPanelAdapter(@NonNull Context context, @NonNull RecyclerView recyclerView,
                                @NonNull HashMap<Integer, Integer> layoutRouter,
                                @NonNull BiFunction<Integer, View, RecyclerView.ViewHolder> holderCreator) {
        super(context, recyclerView, layoutRouter, holderCreator);
    }

    public SettingsPanelAdapter(@NonNull List<SettingsPanel> items, @NonNull RecyclerView recyclerView,
                                @NonNull Context context, @NonNull HashMap<Integer, Integer> layoutRouter,
                                @NonNull BiFunction<Integer, View, RecyclerView.ViewHolder> holderCreator) {
        super(items, recyclerView, context, layoutRouter, holderCreator);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SettingsPanel item = items.get(position);
        item.draw(holder);
    }

    @Override
    public void onClick(View v) {
        final SettingsPanel item = items.get(getPositionBasedOnView(v));
        item.onClick();
    }

}
