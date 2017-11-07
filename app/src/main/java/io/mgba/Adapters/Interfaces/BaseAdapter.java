package io.mgba.Adapters.Interfaces;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.common.base.Function;

import java.util.LinkedList;
import java.util.List;

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected final int layout;
    private final Function<View, RecyclerView.ViewHolder> generateViewHolder;
    protected List<T> items = new LinkedList<>();
    protected Context ctx;

    public BaseAdapter(int layout, Function<View, RecyclerView.ViewHolder> generateViewHolder, Context context){
        this.layout = layout;
        this.generateViewHolder = generateViewHolder;
        this.ctx = context;
    }


    public BaseAdapter(List<T> items, int layout, Function<View, RecyclerView.ViewHolder> generateViewHolder, Context context){
        this.items = items;
        this.layout = layout;
        this.generateViewHolder = generateViewHolder;
        this.ctx = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return generateViewHolder.apply(inflatedView);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void swap(List<T> games) {
        items = games;
        notifyDataSetChanged();
    }
}