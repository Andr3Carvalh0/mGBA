package org.lucasr.twowayview.decorator;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup.MarginLayoutParams;

import org.lucasr.twowayview.R;
import org.lucasr.twowayview.manager.interfaces.BaseLayoutManager;

import androidx.recyclerview.widget.RecyclerView;


public class DividerItemDecoration extends RecyclerView.ItemDecoration {
    private final ItemSpacingOffsets mItemSpacing;

    private final Drawable mVerticalDivider;
    private final Drawable mHorizontalDivider;

    public DividerItemDecoration(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DividerItemDecoration(Context context, AttributeSet attrs, int defStyle) {
        final TypedArray a =
                context.obtainStyledAttributes(attrs, R.styleable.twowayview_DividerItemDecoration, defStyle, 0);

        final Drawable divider = a.getDrawable(R.styleable.twowayview_DividerItemDecoration_android_divider);
        if (divider != null) {
            mVerticalDivider = mHorizontalDivider = divider;
        } else {
            mVerticalDivider = a.getDrawable(R.styleable.twowayview_DividerItemDecoration_twowayview_verticalDivider);
            mHorizontalDivider = a.getDrawable(R.styleable.twowayview_DividerItemDecoration_twowayview_horizontalDivider);
        }

        a.recycle();

        mItemSpacing = createSpacing(mVerticalDivider, mHorizontalDivider);
    }

    public DividerItemDecoration(Drawable divider) {
        this(divider, divider);
    }

    public DividerItemDecoration(Drawable verticalDivider, Drawable horizontalDivider) {
        mVerticalDivider = verticalDivider;
        mHorizontalDivider = horizontalDivider;
        mItemSpacing = createSpacing(mVerticalDivider, mHorizontalDivider);
    }

    private static ItemSpacingOffsets createSpacing(Drawable verticalDivider,
                                                    Drawable horizontalDivider) {
        final int verticalSpacing;
        if (horizontalDivider != null) {
            verticalSpacing = horizontalDivider.getIntrinsicHeight();
        } else {
            verticalSpacing = 0;
        }

        final int horizontalSpacing;
        if (verticalDivider != null) {
            horizontalSpacing = verticalDivider.getIntrinsicWidth();
        } else {
            horizontalSpacing = 0;
        }

        final ItemSpacingOffsets spacing = new ItemSpacingOffsets(verticalSpacing, horizontalSpacing);
        spacing.setAddSpacingAtEnd(true);

        return spacing;
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent) {
        final BaseLayoutManager lm = (BaseLayoutManager) parent.getLayoutManager();

        final int rightWithPadding = parent.getWidth() - parent.getPaddingRight();
        final int bottomWithPadding = parent.getHeight() - parent.getPaddingBottom();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);

            final int childLeft = lm.getDecoratedLeft(child);
            final int childTop = lm.getDecoratedTop(child);
            final int childRight = lm.getDecoratedRight(child);
            final int childBottom = lm.getDecoratedBottom(child);

            final MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

            final int bottomOffset = childBottom - child.getBottom() - lp.bottomMargin;
            if (bottomOffset > 0 && childBottom < bottomWithPadding) {
                final int left = childLeft;
                final int top = childBottom - bottomOffset;
                final int right = childRight;
                final int bottom = top + mHorizontalDivider.getIntrinsicHeight();

                mHorizontalDivider.setBounds(left, top, right, bottom);
                mHorizontalDivider.draw(c);
            }

            final int rightOffset = childRight - child.getRight() - lp.rightMargin;
            if (rightOffset > 0 && childRight < rightWithPadding) {
                final int left = childRight - rightOffset;
                final int top = childTop;
                final int right = left + mVerticalDivider.getIntrinsicWidth();
                final int bottom = childBottom;

                mVerticalDivider.setBounds(left, top, right, bottom);
                mVerticalDivider.draw(c);
            }
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
        mItemSpacing.getItemOffsets(outRect, itemPosition, parent);
    }
}
