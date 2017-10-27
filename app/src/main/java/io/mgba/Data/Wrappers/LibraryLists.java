package io.mgba.Data.Wrappers;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import io.mgba.Data.DTOs.Game;

public class LibraryLists implements Parcelable {
    public static final Parcelable.Creator<LibraryLists> CREATOR = new Parcelable.Creator<LibraryLists>() {
        @Override
        public LibraryLists createFromParcel(Parcel source) {
            return new LibraryLists(source);
        }

        @Override
        public LibraryLists[] newArray(int size) {
            return new LibraryLists[size];
        }
    };
    private final List<Game> favourites;
    private final List<Game> gba;
    private final List<Game> gbc;

    public LibraryLists(List<Game> favourites, List<Game> gba, List<Game> gbc) {
        this.favourites = favourites;
        this.gba = gba;
        this.gbc = gbc;
    }

    protected LibraryLists(Parcel in) {
        this.favourites = in.createTypedArrayList(Game.CREATOR);
        this.gba = in.createTypedArrayList(Game.CREATOR);
        this.gbc = in.createTypedArrayList(Game.CREATOR);
    }

    public List<Game> getFavourites() {
        return favourites;
    }

    public List<Game> getGba() {
        return gba;
    }

    public List<Game> getGbc() {
        return gbc;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.favourites);
        dest.writeTypedList(this.gba);
        dest.writeTypedList(this.gbc);
    }
}
