package io.mgba.Adapters.Interfaces;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.HashMap;
import java.util.List;

import io.mgba.mgba;
import io.reactivex.functions.BiFunction;

public abstract class MultipleViewHolderAdapter<T extends IViewHolderCreator> extends BaseAdapter<T> {

    private final HashMap<Integer, Integer> layoutRouter;
    private final BiFunction<Integer, View, RecyclerView.ViewHolder> holderCreator;

    @Override
    public int getItemViewType(int position) {
        final IViewHolderCreator item = items.get(position);
        return item.getID();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout = layoutRouter.get(viewType);
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);

        RecyclerView.ViewHolder holder = null;

        try {
            holder = holderCreator.apply(layout, view);
        } catch (Exception e) {
            mgba.report(e);
        }

        return holder;
    }

    public MultipleViewHolderAdapter(@NonNull Context context, @NonNull RecyclerView recyclerView,
                                     @NonNull HashMap<Integer, Integer> layoutRouter,
                                     @NonNull BiFunction<Integer, View, RecyclerView.ViewHolder> holderCreator) {
        super(context, recyclerView);
        this.layoutRouter = layoutRouter;
        this.holderCreator = holderCreator;
    }

    public MultipleViewHolderAdapter(@NonNull List<T> items, @NonNull RecyclerView recyclerView, @NonNull Context context,
                                     @NonNull HashMap<Integer, Integer> layoutRouter,
                                     @NonNull BiFunction<Integer, View, RecyclerView.ViewHolder> holderCreator) {
        super(items, recyclerView, context);
        this.layoutRouter = layoutRouter;
        this.holderCreator = holderCreator;
    }
}
