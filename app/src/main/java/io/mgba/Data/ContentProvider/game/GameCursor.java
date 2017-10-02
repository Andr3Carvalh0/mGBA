package io.mgba.Data.ContentProvider.game;

import android.database.Cursor;
import android.support.annotation.Nullable;

import io.mgba.Data.ContentProvider.base.AbstractCursor;


/**
 * Cursor wrapper for the {@code game} table.
 */
@SuppressWarnings({"WeakerAccess", "unused", "UnnecessaryLocalVariable"})
public class GameCursor extends AbstractCursor implements GameModel {
    public GameCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    @Override
    public long getId() {
        Long res = getLongOrNull(GameColumns._ID);
        if (res == null)
            throw new NullPointerException("The value of '_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * File name
     * Can be {@code null}.
     */
    @Nullable
    @Override
    public String getFilename() {
        String res = getStringOrNull(GameColumns.FILENAME);
        return res;
    }

    /**
     * The game name returned by the server
     * Can be {@code null}.
     */
    @Nullable
    @Override
    public String getName() {
        String res = getStringOrNull(GameColumns.NAME);
        return res;
    }

    /**
     * A brief description of the game
     * Can be {@code null}.
     */
    @Nullable
    @Override
    public String getDescription() {
        String res = getStringOrNull(GameColumns.DESCRIPTION);
        return res;
    }

    /**
     * The year when the game was releases
     * Can be {@code null}.
     */
    @Nullable
    @Override
    public Integer getYear() {
        Integer res = getIntegerOrNull(GameColumns.YEAR);
        return res;
    }

    /**
     * The game title from ROM
     * Can be {@code null}.
     */
    @Nullable
    @Override
    public String getGtitle() {
        String res = getStringOrNull(GameColumns.GTITLE);
        return res;
    }

    /**
     * The game code from ROM
     * Can be {@code null}.
     */
    @Nullable
    @Override
    public String getGcode() {
        String res = getStringOrNull(GameColumns.GCODE);
        return res;
    }

    /**
     * The game maker code from ROM
     * Can be {@code null}.
     */
    @Nullable
    @Override
    public String getGmaker() {
        String res = getStringOrNull(GameColumns.GMAKER);
        return res;
    }

    /**
     * Tells if the game is a favourite
     * Can be {@code null}.
     */
    @Nullable
    @Override
    public Integer getIsfavourite() {
        Integer res = getIntegerOrNull(GameColumns.ISFAVOURITE);
        return res;
    }
}
