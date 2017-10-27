package io.mgba.Data.DTOs;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;

import io.mgba.Services.IO.FilesService;

public class Game implements Parcelable {

    public static final Parcelable.Creator<Game> CREATOR = new Parcelable.Creator<Game>() {
        @Override
        public Game createFromParcel(Parcel source) {
            return new Game(source);
        }

        @Override
        public Game[] newArray(int size) {
            return new Game[size];
        }
    };
    private File file;
    private String name;
    private String description;
    private String released;
    private String developer;
    private String genre;
    private String coverURL;
    private String MD5;
    private boolean favourite;

    public Game(File file) {
        this.file = file;
    }

    protected Game(Parcel in) {
        this.file = (File) in.readSerializable();
        this.name = in.readString();
        this.description = in.readString();
        this.released = in.readString();
        this.developer = in.readString();
        this.genre = in.readString();
        this.coverURL = in.readString();
        this.MD5 = in.readString();
        this.favourite = in.readByte() != 0;
    }

    public String getName() {
        if(name == null)
            setName(FilesService.getFileWithoutExtension(file));

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReleased() {
        return released;
    }

    public void setReleased(String released) {
        this.released = released;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getCoverURL() {
        return coverURL;
    }

    public void setCoverURL(String coverURL) {
        this.coverURL = coverURL;
    }

    public File getFile() {
        return file;
    }

    public String getMD5() {
        return MD5;
    }

    public void setMD5(String MD5) {
        this.MD5 = MD5;
    }

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(this.file);
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeString(this.released);
        dest.writeString(this.developer);
        dest.writeString(this.genre);
        dest.writeString(this.coverURL);
        dest.writeString(this.MD5);
        dest.writeByte(this.favourite ? (byte) 1 : (byte) 0);
    }

    public boolean isAdvanced() {
        return FilesService.GBA_FILES_SUPPORTED
                .contains(FilesService.getFileExtension(getFile()));
    }
}
