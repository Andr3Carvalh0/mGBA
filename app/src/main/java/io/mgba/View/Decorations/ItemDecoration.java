package io.mgba.View.Decorations;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import io.mgba.Controller.MetricsCalculator;

public class ItemDecoration extends RecyclerView.ItemDecoration {

    private int mSizeGridSpacingPx;
    private int mGridSize;
    private int sidePadding;

    public ItemDecoration(int gridSpacingPx, int gridSize, int parentWidth, int elemSize, Context mContext) {
        this.mSizeGridSpacingPx = gridSpacingPx;
        this.mGridSize = gridSize;

        int tmp = (gridSize - 1) * gridSpacingPx;

        float rest = ((float)(parentWidth - tmp) / (float)elemSize);
        int decimal = (int)rest;
        float frac =  rest - decimal;
        this.sidePadding = (int)MetricsCalculator.convertDpToPixel(frac / 2, mContext);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int itemPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewAdapterPosition();

        outRect.top = 0;
        outRect.bottom = 0;
        outRect.right = 0;
        outRect.left = 0;

        if (itemPosition >= mGridSize)
            outRect.top = 2* mSizeGridSpacingPx;

        //The first of every row
        if(itemPosition % mGridSize == mGridSize - 1) {
            outRect.right = sidePadding;
        }else {
            outRect.right = mSizeGridSpacingPx;
        }


        //The first of every row
        if(itemPosition % mGridSize == 0)
            outRect.left = sidePadding;

    }
}