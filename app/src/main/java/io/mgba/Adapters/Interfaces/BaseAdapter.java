package io.mgba.Adapters.Interfaces;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.common.base.Function;

import java.util.LinkedList;
import java.util.List;

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener{
    protected final int layout;
    private final Function<View, RecyclerView.ViewHolder> generateViewHolder;
    private final RecyclerView instance;
    protected List<T> items = new LinkedList<>();
    protected Context mCtx;

    public BaseAdapter(int layout, Function<View, RecyclerView.ViewHolder> generateViewHolder, Context context, RecyclerView recyclerView){
        this(new LinkedList<>(), layout, generateViewHolder, recyclerView, context);
    }

    public BaseAdapter(List<T> items, int layout, Function<View, RecyclerView.ViewHolder> generateViewHolder, RecyclerView recyclerView, Context context){
        this.items = items;
        this.layout = layout;
        this.generateViewHolder = generateViewHolder;
        this.instance = recyclerView;
        this.mCtx = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return generateViewHolder.apply(inflatedView);
    }

    protected int getPositionBasedOnView(View v){
        return instance.getChildAdapterPosition(v);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void swap(List<T> items) {
        this.items = items;
        notifyDataSetChanged();
    }
}