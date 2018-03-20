package io.mgba.Adapters.Interfaces;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.common.base.Function;

import java.util.List;

public abstract class SingleViewHolderAdapter<T> extends BaseAdapter<T> {
    protected final int layout;
    private final Function<View, RecyclerView.ViewHolder> createView;


    public SingleViewHolderAdapter(Context context, RecyclerView recyclerView, int layout, Function<View, RecyclerView.ViewHolder> createView) {
        super(context, recyclerView);
        this.layout = layout;
        this.createView = createView;
    }

    public SingleViewHolderAdapter(List<T> items, RecyclerView recyclerView, Context context, int layout, Function<View, RecyclerView.ViewHolder> createView) {
        super(items, recyclerView, context);
        this.layout = layout;
        this.createView = createView;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return createView.apply(inflatedView);
    }

}
