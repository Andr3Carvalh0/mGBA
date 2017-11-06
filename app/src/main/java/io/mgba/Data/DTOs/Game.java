package io.mgba.Data.DTOs;

import android.os.Parcel;
import android.os.Parcelable;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

import java.io.File;

import io.mgba.Data.Platform;
import io.mgba.Services.IO.FilesService;

public class Game implements Parcelable, SearchSuggestion {

    public static final Creator<Game> CREATOR = new Creator<Game>() {
        @Override
        public Game createFromParcel(Parcel source) {
            return new Game(source);
        }

        @Override
        public Game[] newArray(int size) {
            return new Game[size];
        }
    };
    private final File file;
    private final Platform platform;
    private String name;
    private String description;
    private String released;
    private String developer;
    private String genre;
    private String coverURL = null;
    private String MD5;
    private boolean favourite;

    public Game(String path, String name, String description, String released, String developer, String genre, String coverURL, String MD5, boolean favourite, Platform platform) {
        this.file = new File(path);
        this.name = name;
        this.description = description;
        this.released = released;
        this.developer = developer;
        this.genre = genre;
        this.coverURL = coverURL;
        this.MD5 = MD5;
        this.favourite = favourite;
        this.platform = platform;
    }

    public Game(String path, Platform platform) {
        this.file = new File(path);
        this.platform = platform;
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
        int tmpPlatform = in.readInt();
        this.platform = tmpPlatform == -1 ? null : Platform.values()[tmpPlatform];
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

    public Platform getPlatform() {
        return platform;
    }

    public boolean isAdvanced() {
        return platform.getValue() == Platform.GBA.getValue();
    }

    @Override
    public String getBody() {
        return getName();
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
        dest.writeInt(this.platform == null ? -1 : this.platform.ordinal());
    }
}
