/*
 * Copyright (C) 2014 Lucas Rocha
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.lucasr.twowayview.manager.interfaces;

import android.content.Context;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import org.lucasr.twowayview.utilities.ItemEntries;
import org.lucasr.twowayview.utilities.ItemEntry;
import org.lucasr.twowayview.utilities.Lanes;
import androidx.recyclerview.widget.RecyclerView;

import static org.lucasr.twowayview.manager.interfaces.TwoWayLayoutManager.Orientation.HORIZONTAL;

public abstract class BaseLayoutManager extends TwoWayLayoutManager {
    private final Rect mChildFrame = new Rect();
    protected final Rect mTempRect = new Rect();
    protected final Lanes.LaneInfo mTempLaneInfo = new Lanes.LaneInfo();
    private Lanes mLanes;
    private Lanes mLanesToRestore;
    private ItemEntries mItemEntries;
    private ItemEntries mItemEntriesToRestore;
    private int screenOrientation;

    protected BaseLayoutManager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    protected BaseLayoutManager(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    protected BaseLayoutManager(Orientation orientation) {
        super(orientation);
    }

    protected void pushChildFrame(ItemEntry entry, Rect childFrame, int lane, int laneSpan,
                        Direction direction) {
        final boolean shouldSetMargins = (direction == Direction.END &&
                                          entry != null && !entry.hasSpanMargins());

        for (int i = lane; i < lane + laneSpan; i++) {
            final int spanMargin;
            if (entry != null && direction != Direction.END) {
                spanMargin = entry.getSpanMargin(i - lane);
            } else {
                spanMargin = 0;
            }

            final int margin = mLanes.pushChildFrame(childFrame, i, spanMargin, direction);
            if (laneSpan > 1 && shouldSetMargins) {
                entry.setSpanMargin(i - lane, margin, laneSpan);
            }
        }
    }

    private void popChildFrame(ItemEntry entry, Rect childFrame, int lane, int laneSpan,
                               Direction direction) {
        for (int i = lane; i < lane + laneSpan; i++) {
            final int spanMargin;
            if (entry != null && direction != Direction.END) {
                spanMargin = entry.getSpanMargin(i - lane);
            } else {
                spanMargin = 0;
            }

            mLanes.popChildFrame(childFrame, i, spanMargin, direction);
        }
    }

    private void getDecoratedChildFrame(View child, Rect childFrame) {
        childFrame.left = getDecoratedLeft(child);
        childFrame.top = getDecoratedTop(child);
        childFrame.right = getDecoratedRight(child);
        childFrame.bottom = getDecoratedBottom(child);
    }

    public boolean isVertical() {
        return (getOrientation() == Orientation.VERTICAL);
    }

    public Lanes getLanes() {
        return mLanes;
    }

    protected void setItemEntryForPosition(int position, ItemEntry entry) {
        if(mItemEntries == null)
            mItemEntries = new ItemEntries();

        mItemEntries.putItemEntry(position, entry);
    }

    protected ItemEntry getItemEntryForPosition(int position) {
        return (mItemEntries != null ? mItemEntries.getItemEntry(position) : null);
    }

    private void clearItemEntries() {
        if (mItemEntries != null) {
            mItemEntries.clear();
        }
        invalidateItemLanesAfter(0);
        ensureLayoutState();
    }

    private void invalidateItemLanesAfter(int position) {
        if (mItemEntries != null) {
            mItemEntries.invalidateItemLanesAfter(position);
        }
    }

    private void offsetForAddition(int positionStart, int itemCount) {
        if (mItemEntries != null) {
            mItemEntries.offsetForAddition(positionStart, itemCount);
        }
    }

    private void offsetForRemoval(int positionStart, int itemCount) {
        if (mItemEntries != null) {
            mItemEntries.offsetForRemoval(positionStart, itemCount);
        }
    }

    private void requestMoveLayout() {
        if (getPendingScrollPosition() != RecyclerView.NO_POSITION) {
            return;
        }

        final int position = getFirstVisiblePosition();
        final View firstChild = findViewByPosition(position);
        final int offset = (firstChild != null ? getChildStart(firstChild) : 0);

        setPendingScrollPositionWithOffset(position, offset);
    }

    private boolean canUseLanes(Lanes lanes) {
        if (lanes == null) {
            return false;
        }

        final int laneCount = getLaneCount();
        final int laneSize = Lanes.calculateLaneSize(this, laneCount);

        return (lanes.getOrientation() == getOrientation() &&
                 lanes.getCount() == laneCount &&
                 lanes.getLaneSize() == laneSize);
    }

    private boolean ensureLayoutState() {
        final int laneCount = getLaneCount();
        if (laneCount == 0 || getWidth() == 0 || getHeight() == 0 || canUseLanes(mLanes)) {
            return false;
        }

        final Lanes oldLanes = mLanes;
        mLanes = new Lanes(this, laneCount);

        requestMoveLayout();

        if (mItemEntries == null) {
            mItemEntries = new ItemEntries();
        }

        if (oldLanes != null && oldLanes.getOrientation() == mLanes.getOrientation() &&
                oldLanes.getLaneSize() == mLanes.getLaneSize()) {
            invalidateItemLanesAfter(0);
        } else {
            mItemEntries.clear();
        }

        return true;
    }

    private void handleUpdate(int positionStart, int itemCountOrToPosition, UpdateOp cmd) {
        invalidateItemLanesAfter(positionStart);

        switch (cmd) {
            case ADD:
                offsetForAddition(positionStart, itemCountOrToPosition);
                break;

            case REMOVE:
                offsetForRemoval(positionStart, itemCountOrToPosition);
                break;

            case MOVE:
                offsetForRemoval(positionStart, 1);
                offsetForAddition(itemCountOrToPosition, 1);
                break;
        }

        if (positionStart + itemCountOrToPosition <= getFirstVisiblePosition()) {
            return;
        }

        if (positionStart <= getLastVisiblePosition()) {
            requestLayout();
        }
    }

    @Override
    public void offsetChildrenHorizontal(int offset) {
        if (!isVertical()) {
            mLanes.offset(offset);
        }

        super.offsetChildrenHorizontal(offset);
    }

    @Override
    public void offsetChildrenVertical(int offset) {
        super.offsetChildrenVertical(offset);

        if (isVertical()) {
            mLanes.offset(offset);
        }
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        final boolean restoringLanes = (mLanesToRestore != null);
        if (restoringLanes) {
            mLanes = mLanesToRestore;
            mItemEntries = mItemEntriesToRestore;

            mLanesToRestore = null;
            mItemEntriesToRestore = null;
        }

        final boolean refreshingLanes = ensureLayoutState();

        // Still not able to create lanes, nothing we can do here,
        // just bail for now.
        if (mLanes == null) {
            return;
        }

        //final int itemCount = state.getItemCount();


        final int anchorItemPosition = getAnchorItemPosition(state);

        // Only move layout if we're not restoring a layout state.
        if (anchorItemPosition > 0 && refreshingLanes && !restoringLanes)
            moveLayoutToPosition(anchorItemPosition, getPendingScrollOffset(), recycler, state);


        mLanes.reset(Direction.START);

        super.onLayoutChildren(recycler, state);
    }

    @Override
    protected void onLayoutScrapList(RecyclerView.Recycler recycler, RecyclerView.State state) {
        mLanes.save();
        super.onLayoutScrapList(recycler, state);
        mLanes.restore();
    }

    @Override
    public void onItemsAdded(RecyclerView recyclerView, int positionStart, int itemCount) {
        handleUpdate(positionStart, itemCount, UpdateOp.ADD);
        super.onItemsAdded(recyclerView, positionStart, itemCount);
    }

    @Override
    public void onItemsRemoved(RecyclerView recyclerView, int positionStart, int itemCount) {
        handleUpdate(positionStart, itemCount, UpdateOp.REMOVE);
        super.onItemsRemoved(recyclerView, positionStart, itemCount);
    }

    @Override
    public void onItemsUpdated(RecyclerView recyclerView, int positionStart, int itemCount) {
        handleUpdate(positionStart, itemCount, UpdateOp.UPDATE);
        super.onItemsUpdated(recyclerView, positionStart, itemCount);
    }

    @Override
    public void onItemsMoved(RecyclerView recyclerView, int from, int to, int itemCount) {
        handleUpdate(from, to, UpdateOp.MOVE);
        super.onItemsMoved(recyclerView, from, to, itemCount);
    }

    @Override
    public void onItemsChanged(RecyclerView recyclerView) {
        clearItemEntries();
        super.onItemsChanged(recyclerView);
    }

    @Override
    public Parcelable onSaveInstanceState() {
        final Parcelable superState = super.onSaveInstanceState();
        final LanedSavedState state = new LanedSavedState(superState);

        final int laneCount = (mLanes != null ? mLanes.getCount() : 0);
        state.lanes = new Rect[laneCount];
        for (int i = 0; i < laneCount; i++) {
            final Rect laneRect = new Rect();
            mLanes.getLane(i, laneRect);
            state.lanes[i] = laneRect;
        }

        state.screenOrientation = getScreenOrientation();
        state.orientation = getOrientation();
        state.laneSize = (mLanes != null ? mLanes.getLaneSize() : 0);
        state.itemEntries = mItemEntries;

        return state;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        final LanedSavedState ss = (LanedSavedState) state;

        if (ss.lanes != null && ss.laneSize > 0 && ss.screenOrientation == getScreenOrientation()) {
            mLanesToRestore = new Lanes(this, ss.orientation, ss.lanes, ss.laneSize);
            mItemEntriesToRestore = ss.itemEntries;
        }else {
            if(mItemEntries!=null) {
                mItemEntries.clear();
            }
            invalidateItemLanesAfter(0);
        }

        super.onRestoreInstanceState(ss.getSuperState());
    }

    @Override
    protected boolean canAddMoreViews(Direction direction, int limit) {
        if (direction == Direction.START) {
            return (mLanes.getInnerStart() > limit);
        } else {
            return (mLanes.getInnerEnd() < limit);
        }
    }

    private int getWidthUsed(View child) {
        if (!isVertical()) {
            return 0;
        }

        final int size = getLanes().getLaneSize() * getLaneSpanForChild(child);
        return getWidth() - getPaddingLeft() - getPaddingRight() - size;
    }

    private int getHeightUsed(View child) {
        if (isVertical()) {
            return 0;
        }

        final int size = getLanes().getLaneSize() * getLaneSpanForChild(child);
        return getHeight() - getPaddingTop() - getPaddingBottom() - size;
    }

    protected void measureChildWithMargins(View child) {
        measureChildWithMargins(child, getWidthUsed(child), getHeightUsed(child));
    }

    @Override
    protected void measureChild(View child, Direction direction) {
        cacheChildLaneAndSpan(child, direction);
        measureChildWithMargins(child);
    }

    @Override
    protected void layoutChild(View child, Direction direction) {
        getLaneForChild(mTempLaneInfo, child, direction);

        mLanes.getChildFrame(mChildFrame, getDecoratedMeasuredWidth(child),
                getDecoratedMeasuredHeight(child), mTempLaneInfo, direction);
        final ItemEntry entry = cacheChildFrame(child, mChildFrame);

        layoutDecorated(child, mChildFrame.left, mChildFrame.top, mChildFrame.right,
                mChildFrame.bottom);

        final RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) child.getLayoutParams();
        if (!lp.isItemRemoved()) {
            pushChildFrame(entry, mChildFrame, mTempLaneInfo.startLane,
                    getLaneSpanForChild(child), direction);
        }
    }

    @Override
    protected void detachChild(View child, Direction direction) {
        final int position = getPosition(child);
        getLaneForPosition(mTempLaneInfo, position, direction);
        getDecoratedChildFrame(child, mChildFrame);

        popChildFrame(getItemEntryForPosition(position), mChildFrame, mTempLaneInfo.startLane,
                getLaneSpanForChild(child), direction);
    }

    protected void getLaneForChild(Lanes.LaneInfo outInfo, View child, Direction direction) {
        getLaneForPosition(outInfo, getPosition(child), direction);
    }

    protected int getLaneSpanForChild(View child) {
        return 1;
    }

    public int getLaneSpanForPosition(int position) {
        return 1;
    }

    protected ItemEntry cacheChildLaneAndSpan(View child, Direction direction) {
        // Do nothing by default.
        return null;
    }

    protected ItemEntry cacheChildFrame(View child, Rect childFrame) {
        // Do nothing by default.
        return null;
    }

    @Override
    public boolean checkLayoutParams(RecyclerView.LayoutParams lp) {
        if (isVertical()) {
            return (lp.width == RecyclerView.LayoutParams.MATCH_PARENT);
        } else {
            return (lp.height == RecyclerView.LayoutParams.MATCH_PARENT);
        }
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        if (isVertical()) {
            return new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
        } else {
            return new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.MATCH_PARENT);
        }
    }

    @Override
    public RecyclerView.LayoutParams generateLayoutParams(ViewGroup.LayoutParams lp) {
        final RecyclerView.LayoutParams lanedLp = new RecyclerView.LayoutParams((MarginLayoutParams) lp);
        if (isVertical()) {
            lanedLp.width = RecyclerView.LayoutParams.MATCH_PARENT;
            lanedLp.height = lp.height;
        } else {
            lanedLp.width = lp.width;
            lanedLp.height = RecyclerView.LayoutParams.MATCH_PARENT;
        }

        return lanedLp;
    }

    @Override
    public RecyclerView.LayoutParams generateLayoutParams(Context c, AttributeSet attrs) {
        return new RecyclerView.LayoutParams(c, attrs);
    }

    protected abstract int getLaneCount();

    public abstract void getLaneForPosition(Lanes.LaneInfo outInfo, int position, Direction direction);

    protected abstract void moveLayoutToPosition(int position, int offset, RecyclerView.Recycler recycler, RecyclerView.State state);

    @Override
    public PointF computeScrollVectorForPosition(int targetPosition) {
        if (getChildCount() == 0) {
            return null;
        }
        final int firstChildPos = getPosition(getChildAt(0));
        final int direction = targetPosition < firstChildPos ? -1 : 1;
        if (getOrientation() == HORIZONTAL) {
            return new PointF(direction, 0);
        } else {
            return new PointF(0, direction);
        }
    }

    private int getScreenOrientation() {
        return screenOrientation;
    }

    public void setScreenOrientation(int orientation) {
        super.setmScreenOrientation(orientation);
    }

    private enum UpdateOp {
        ADD,
        REMOVE,
        UPDATE,
        MOVE
    }

    protected static class LanedSavedState extends SavedState {
        public static final Parcelable.Creator<LanedSavedState> CREATOR
                = new Parcelable.Creator<LanedSavedState>() {
            @Override
            public LanedSavedState createFromParcel(Parcel in) {
                return new LanedSavedState(in);
            }

            @Override
            public LanedSavedState[] newArray(int size) {
                return new LanedSavedState[size];
            }
        };
        private int screenOrientation;
        private Orientation orientation;
        private Rect[] lanes;
        private int laneSize;
        private ItemEntries itemEntries;

        LanedSavedState(Parcelable superState) {
            super(superState);
        }

        private LanedSavedState(Parcel in) {
            super(in);
            screenOrientation = in.readInt();
            orientation = Orientation.values()[in.readInt()];
            laneSize = in.readInt();

            final int laneCount = in.readInt();
            if (laneCount > 0) {
                lanes = new Rect[laneCount];
                for (int i = 0; i < laneCount; i++) {
                    final Rect lane = new Rect();
                    lane.readFromParcel(in);
                    lanes[i] = lane;
                }
            }

            final int itemEntriesCount = in.readInt();
            if (itemEntriesCount > 0) {
                itemEntries = new ItemEntries();
                for (int i = 0; i < itemEntriesCount; i++) {
                    final ItemEntry entry = in.readParcelable(getClass().getClassLoader());
                    itemEntries.restoreItemEntry(i, entry);
                }
            }
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(screenOrientation);
            out.writeInt(orientation.ordinal());
            out.writeInt(laneSize);

            final int laneCount = (lanes != null ? lanes.length : 0);
            out.writeInt(laneCount);

            for (int i = 0; i < laneCount; i++) {
                lanes[i].writeToParcel(out, Rect.PARCELABLE_WRITE_RETURN_VALUE);
            }

            final int itemEntriesCount = (itemEntries != null ? itemEntries.size() : 0);
            out.writeInt(itemEntriesCount);

            for (int i = 0; i < itemEntriesCount; i++) {
                out.writeParcelable(itemEntries.getItemEntry(i), flags);
            }
        }
    }
}
