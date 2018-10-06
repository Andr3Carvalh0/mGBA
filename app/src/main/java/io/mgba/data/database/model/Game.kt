package io.mgba.data.database.model


import android.os.Parcel
import android.os.Parcelable
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion
import java.io.File
import java.util.Objects
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import io.mgba.Constants
import io.mgba.model.io.FilesManager

@Entity(tableName = "Games")
class Game : Parcelable, SearchSuggestion {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @NonNull
    var file: File? = null
    @ColumnInfo
    var platform: Int = 0

    @ColumnInfo
    private var name: String? = null

    @ColumnInfo
    var description: String? = null

    @ColumnInfo
    var released: String? = null

    @ColumnInfo
    var developer: String? = null

    @ColumnInfo
    var genre: String? = null

    @ColumnInfo
    var coverURL: String? = null

    @ColumnInfo
    var mD5: String? = null

    @ColumnInfo(name = "favourite")
    var isFavourite = false

    val isAdvanced: Boolean
        get() = platform == Constants.PLATFORM_GBA


    constructor()

    @Ignore
    constructor(path: String, name: String, description: String, released: String, developer: String, genre: String, coverURL: String, MD5: String, favourite: Boolean, platform: Int) {
        this.file = File(path)
        this.name = name
        this.description = description
        this.released = released
        this.developer = developer
        this.genre = genre
        this.coverURL = coverURL
        this.mD5 = MD5
        this.isFavourite = favourite
        this.platform = platform
    }

    @Ignore
    constructor(path: String, platform: Int) {
        this.file = File(path)
        this.platform = platform
    }

    @Ignore
    protected constructor(`in`: Parcel) {
        this.file = `in`.readSerializable() as File
        this.name = `in`.readString()
        this.description = `in`.readString()
        this.released = `in`.readString()
        this.developer = `in`.readString()
        this.genre = `in`.readString()
        this.coverURL = `in`.readString()
        this.mD5 = `in`.readString()
        this.isFavourite = `in`.readByte().toInt() != 0
        this.platform = `in`.readInt()
    }

    fun getName(): String? {
        if (name == null)
            setName(FilesManager.getFileWithoutExtension(file!!))

        return name
    }

    fun setName(name: String?) {
        this.name = name
    }

    override fun getBody(): String? {
        return getName()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeSerializable(this.file)
        dest.writeString(this.name)
        dest.writeString(this.description)
        dest.writeString(this.released)
        dest.writeString(this.developer)
        dest.writeString(this.genre)
        dest.writeString(this.coverURL)
        dest.writeString(this.mD5)
        dest.writeByte(if (this.isFavourite) 1.toByte() else 0.toByte())
        dest.writeInt(this.platform)
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o !is Game) return false

        val game = o as Game?

        return file == game!!.file && platform == game.platform
    }

    override fun hashCode(): Int {
        return Objects.hash(file, platform, name, description, released, developer, genre, coverURL, mD5, isFavourite)
    }

    fun needsUpdate(): Boolean {
        return coverURL == null
    }

    //Probably not correct to put it on the DTO...
    fun compare(dbVersion: Game) {
        if (name == null)
            setName(dbVersion.getName())

        if (description == null)
            description = dbVersion.description

        if (released == null)
            released = dbVersion.released

        if (developer == null)
            developer = dbVersion.developer

        if (genre == null)
            genre = dbVersion.genre

        if (coverURL == null)
            coverURL = dbVersion.coverURL

        if (mD5 == null)
            mD5 = dbVersion.mD5

        if (!isFavourite)
            isFavourite = dbVersion.isFavourite
    }

    companion object {

        val CREATOR: Parcelable.Creator<Game> = object : Parcelable.Creator<Game> {
            override fun createFromParcel(source: Parcel): Game {
                return Game(source)
            }

            override fun newArray(size: Int): Array<Game?> {
                return arrayOfNulls(size)
            }
        }
    }
}
