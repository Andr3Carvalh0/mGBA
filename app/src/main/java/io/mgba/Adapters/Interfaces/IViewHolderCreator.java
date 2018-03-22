package io.mgba.Adapters.Interfaces;

import android.support.v7.widget.RecyclerView;

public interface IViewHolderCreator {
    void draw(RecyclerView.ViewHolder holder);
    void onClick();
    int getID();
}