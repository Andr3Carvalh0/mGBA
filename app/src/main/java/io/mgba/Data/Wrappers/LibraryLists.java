package io.mgba.Data.Wrappers;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

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
    private final ArrayList<Game> favourites;
    private final ArrayList<Game> gba;
    private final ArrayList<Game> gbc;

    public LibraryLists(ArrayList<Game> favourites, ArrayList<Game> gba, ArrayList<Game> gbc) {
        this.favourites = favourites;
        this.gba = gba;
        this.gbc = gbc;
    }

    protected LibraryLists(Parcel in) {
        this.favourites = in.createTypedArrayList(Game.CREATOR);
        this.gba = in.createTypedArrayList(Game.CREATOR);
        this.gbc = in.createTypedArrayList(Game.CREATOR);
    }

    public ArrayList<Game> getFavourites() {
        return favourites;
    }

    public ArrayList<Game> getGba() {
        return gba;
    }

    public ArrayList<Game> getGbc() {
        return gbc;
    }

    public ArrayList<Game> getLibrary(){
        ArrayList<Game> lib = new ArrayList<>();
        lib.addAll(gba);
        lib.addAll(gbc);

        return lib;
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

    public boolean isEmpty() {
        return favourites.size() == 0 && gba.size() == 0 && gbc.size() == 0;
    }
}
