package io.mgba.Data.Database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

import java.io.File;

import io.mgba.Data.Platform;
import io.mgba.Model.IO.FilesManager;

@Entity(tableName = "Games")
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

    @PrimaryKey
    @ColumnInfo(name = "id")
    private final File file;

    @ColumnInfo()
    private final Platform platform;

    @ColumnInfo()
    private String name = null;

    @ColumnInfo()
    private String description = null;

    @ColumnInfo()
    private String released = null;

    @ColumnInfo()
    private String developer = null;

    @ColumnInfo()
    private String genre = null;

    @ColumnInfo()
    private String coverURL = null;

    @ColumnInfo()
    private String MD5 = null;

    @ColumnInfo()
    private boolean favourite = false;

    @Ignore
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

    @Ignore
    public Game(String path, Platform platform) {
        this.file = new File(path);
        this.platform = platform;
    }

    @Ignore
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
            setName(FilesManager.getFileWithoutExtension(file));

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Game)) return false;

        Game game = (Game) o;

        return getFile().equals(game.getFile()) && getPlatform() == game.getPlatform();
    }

    @Override
    public int hashCode() {
        int result = getFile().hashCode();
        result = 31 * result + getPlatform().hashCode();
        return result;
    }

    public boolean needsUpdate() {
        return coverURL == null;
    }

    //Probably not correct to put it on the DTO...
    public void compare(Game dbVersion){
        if(name == null)
            setName(dbVersion.getName());

        if(description == null)
            setDescription(dbVersion.getDescription());

        if(released == null)
            setReleased(dbVersion.getReleased());

        if(developer == null)
            setDeveloper(dbVersion.getDeveloper());

        if(genre == null)
            setGenre(dbVersion.getGenre());

        if(coverURL == null)
            setCoverURL(dbVersion.getCoverURL());

        if(MD5 == null)
            setMD5(dbVersion.getMD5());

        if(!isFavourite())
            setFavourite(dbVersion.isFavourite());
    }
}
