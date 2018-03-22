package io.mgba.Adapters.Interfaces;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import java.util.LinkedList;
import java.util.List;

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener{
    private final RecyclerView instance;
    protected List<T> items = new LinkedList<>();
    protected Context mCtx;

    public BaseAdapter(Context context, RecyclerView recyclerView){
        this(new LinkedList<>(), recyclerView, context);
    }

    public BaseAdapter(List<T> items, RecyclerView recyclerView, Context context){
        this.items = items;
        this.instance = recyclerView;
        this.mCtx = context;
    }

    @Override
    public abstract RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType);

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