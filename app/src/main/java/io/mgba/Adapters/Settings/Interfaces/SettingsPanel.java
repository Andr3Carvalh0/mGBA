package io.mgba.Adapters.Settings.Interfaces;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class SettingsPanel implements IViewHolderCreator{
    private final int id;

    protected SettingsPanel(int id) {
        this.id = id;
    }

    public abstract void draw(RecyclerView.ViewHolder holder);

    public abstract void onClick();

    @Override
    public int getID(){
        return id;
    }

    protected static abstract class SettingsViewHolder extends RecyclerView.ViewHolder{
        public SettingsViewHolder(View view) {
            super(view);
        }
    }
}
