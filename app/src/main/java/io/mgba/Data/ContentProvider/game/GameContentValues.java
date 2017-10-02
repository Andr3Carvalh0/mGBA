package io.mgba.Data.ContentProvider.game;

import android.content.Context;
import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.Nullable;
import io.mgba.Data.ContentProvider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code game} table.
 */
@SuppressWarnings({"ConstantConditions", "unused"})
public class GameContentValues extends AbstractContentValues<GameContentValues> {
    @Override
    protected Uri baseUri() {
        return GameColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable GameSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param context The context to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(Context context, @Nullable GameSelection where) {
        return context.getContentResolver().update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * File name
     */
    public GameContentValues putFilename(@Nullable String value) {
        mContentValues.put(GameColumns.FILENAME, value);
        return this;
    }

    public GameContentValues putFilenameNull() {
        mContentValues.putNull(GameColumns.FILENAME);
        return this;
    }

    /**
     * The game name returned by the server
     */
    public GameContentValues putName(@Nullable String value) {
        mContentValues.put(GameColumns.NAME, value);
        return this;
    }

    public GameContentValues putNameNull() {
        mContentValues.putNull(GameColumns.NAME);
        return this;
    }

    /**
     * A brief description of the game
     */
    public GameContentValues putDescription(@Nullable String value) {
        mContentValues.put(GameColumns.DESCRIPTION, value);
        return this;
    }

    public GameContentValues putDescriptionNull() {
        mContentValues.putNull(GameColumns.DESCRIPTION);
        return this;
    }

    /**
     * The year when the game was releases
     */
    public GameContentValues putYear(@Nullable Integer value) {
        mContentValues.put(GameColumns.YEAR, value);
        return this;
    }

    public GameContentValues putYearNull() {
        mContentValues.putNull(GameColumns.YEAR);
        return this;
    }

    /**
     * The game title from ROM
     */
    public GameContentValues putGtitle(@Nullable String value) {
        mContentValues.put(GameColumns.GTITLE, value);
        return this;
    }

    public GameContentValues putGtitleNull() {
        mContentValues.putNull(GameColumns.GTITLE);
        return this;
    }

    /**
     * The game code from ROM
     */
    public GameContentValues putGcode(@Nullable String value) {
        mContentValues.put(GameColumns.GCODE, value);
        return this;
    }

    public GameContentValues putGcodeNull() {
        mContentValues.putNull(GameColumns.GCODE);
        return this;
    }

    /**
     * The game maker code from ROM
     */
    public GameContentValues putGmaker(@Nullable String value) {
        mContentValues.put(GameColumns.GMAKER, value);
        return this;
    }

    public GameContentValues putGmakerNull() {
        mContentValues.putNull(GameColumns.GMAKER);
        return this;
    }

    /**
     * Tells if the game is a favourite
     */
    public GameContentValues putIsfavourite(@Nullable Integer value) {
        mContentValues.put(GameColumns.ISFAVOURITE, value);
        return this;
    }

    public GameContentValues putIsfavouriteNull() {
        mContentValues.putNull(GameColumns.ISFAVOURITE);
        return this;
    }
}
