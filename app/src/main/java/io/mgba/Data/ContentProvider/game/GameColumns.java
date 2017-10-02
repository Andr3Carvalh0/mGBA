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
     * File name
     */
    public static final String FILENAME = "filename";

    /**
     * The game name returned by the server
     */
    public static final String NAME = "name";

    /**
     * A brief description of the game
     */
    public static final String DESCRIPTION = "description";

    /**
     * The year when the game was releases
     */
    public static final String YEAR = "year";

    /**
     * The game title from ROM
     */
    public static final String GTITLE = "gtitle";

    /**
     * The game code from ROM
     */
    public static final String GCODE = "gcode";

    /**
     * The game maker code from ROM
     */
    public static final String GMAKER = "gmaker";

    /**
     * Tells if the game is a favourite
     */
    public static final String ISFAVOURITE = "isFavourite";


    public static final String DEFAULT_ORDER = null;

    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            FILENAME,
            NAME,
            DESCRIPTION,
            YEAR,
            GTITLE,
            GCODE,
            GMAKER,
            ISFAVOURITE
    };

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(FILENAME) || c.contains("." + FILENAME)) return true;
            if (c.equals(NAME) || c.contains("." + NAME)) return true;
            if (c.equals(DESCRIPTION) || c.contains("." + DESCRIPTION)) return true;
            if (c.equals(YEAR) || c.contains("." + YEAR)) return true;
            if (c.equals(GTITLE) || c.contains("." + GTITLE)) return true;
            if (c.equals(GCODE) || c.contains("." + GCODE)) return true;
            if (c.equals(GMAKER) || c.contains("." + GMAKER)) return true;
            if (c.equals(ISFAVOURITE) || c.contains("." + ISFAVOURITE)) return true;
        }
        return false;
    }

}
