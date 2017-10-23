package io.mgba.Data.ContentProvider.game;

import android.net.Uri;
import android.provider.BaseColumns;
import io.mgba.Data.ContentProvider.Provider;

/**
 * A game.
 */
@SuppressWarnings("unused")
public class GameColumns implements BaseColumns {
    public static final String TABLE_NAME = "game";
    public static final Uri CONTENT_URI = Uri.parse(Provider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    /**
     * The gamefile's md5
     */
    public static final String MD5 = "md5";

    /**
     * The game name returned by the server
     */
    public static final String NAME = "name";

    /**
     * A brief description of the game
     */
    public static final String DESCRIPTION = "description";

    /**
     * The date when the game was releases
     */
    public static final String RELEASED = "released";

    /**
     * The developer of the game
     */
    public static final String DEVELOPER = "developer";

    /**
     * The game's genre
     */
    public static final String GENRE = "genre";

    /**
     * The game's cover
     */
    public static final String COVER = "cover";

    /**
     * Tells if the game is a favourite
     */
    public static final String ISFAVOURITE = "isFavourite";


    public static final String DEFAULT_ORDER = null;

    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            MD5,
            NAME,
            DESCRIPTION,
            RELEASED,
            DEVELOPER,
            GENRE,
            COVER,
            ISFAVOURITE
    };

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(MD5) || c.contains("." + MD5)) return true;
            if (c.equals(NAME) || c.contains("." + NAME)) return true;
            if (c.equals(DESCRIPTION) || c.contains("." + DESCRIPTION)) return true;
            if (c.equals(RELEASED) || c.contains("." + RELEASED)) return true;
            if (c.equals(DEVELOPER) || c.contains("." + DEVELOPER)) return true;
            if (c.equals(GENRE) || c.contains("." + GENRE)) return true;
            if (c.equals(COVER) || c.contains("." + COVER)) return true;
            if (c.equals(ISFAVOURITE) || c.contains("." + ISFAVOURITE)) return true;
        }
        return false;
    }

}
