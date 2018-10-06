package org.lucasr.twowayview.utilities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by André Carvalho on 13/08/2018
 */
public class ItemEntry implements Parcelable {
    public static final Creator<ItemEntry> CREATOR
            = new Creator<ItemEntry>() {
        @Override
        public ItemEntry createFromParcel(Parcel in) {
            return new ItemEntry(in);
        }

        @Override
        public ItemEntry[] newArray(int size) {
            return new ItemEntry[size];
        }
    };
    public int startLane;
    public int anchorLane;
    private int[] spanMargins;



    public ItemEntry(int startLane, int anchorLane) {
        this.startLane = startLane;
        this.anchorLane = anchorLane;
    }

    public ItemEntry(Parcel in) {
        startLane = in.readInt();
        anchorLane = in.readInt();

        final int marginCount = in.readInt();
        if (marginCount > 0) {
            spanMargins = new int[marginCount];
            for (int i = 0; i < marginCount; i++) {
                spanMargins[i] = in.readInt();
            }
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(startLane);
        out.writeInt(anchorLane);

        final int marginCount = (spanMargins != null ? spanMargins.length : 0);
        out.writeInt(marginCount);

        for (int i = 0; i < marginCount; i++) {
            out.writeInt(spanMargins[i]);
        }
    }

    public void setLane(Lanes.LaneInfo laneInfo) {
        startLane = laneInfo.startLane;
        anchorLane = laneInfo.anchorLane;
    }

    public void invalidateLane() {
        startLane = Lanes.NO_LANE;
        anchorLane = Lanes.NO_LANE;
        spanMargins = null;
    }

    public boolean hasSpanMargins() {
        return (spanMargins != null);
    }

    public int getSpanMargin(int index) {
        if (spanMargins == null) {
            return 0;
        }

        return spanMargins[index];
    }

    public void setSpanMargin(int index, int margin, int span) {
        if (spanMargins == null) {
            spanMargins = new int[span];
        }

        spanMargins[index] = margin;
    }
}