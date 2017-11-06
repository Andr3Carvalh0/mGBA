package io.mgba.Data.ContentProvider.game;

// @formatter:off

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
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
     * The gamefile's md5
     */
    public GameContentValues putMd5(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("md5 must not be null");
        mContentValues.put(GameColumns.MD5, value);
        return this;
    }


    /**
     * The gamefile's path
     */
    public GameContentValues putPath(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("path must not be null");
        mContentValues.put(GameColumns.PATH, value);
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
     * The date when the game was releases
     */
    public GameContentValues putReleased(@Nullable String value) {
        mContentValues.put(GameColumns.RELEASED, value);
        return this;
    }

    public GameContentValues putReleasedNull() {
        mContentValues.putNull(GameColumns.RELEASED);
        return this;
    }

    /**
     * The developer of the game
     */
    public GameContentValues putDeveloper(@Nullable String value) {
        mContentValues.put(GameColumns.DEVELOPER, value);
        return this;
    }

    public GameContentValues putDeveloperNull() {
        mContentValues.putNull(GameColumns.DEVELOPER);
        return this;
    }

    /**
     * The game's genre
     */
    public GameContentValues putGenre(@Nullable String value) {
        mContentValues.put(GameColumns.GENRE, value);
        return this;
    }

    public GameContentValues putGenreNull() {
        mContentValues.putNull(GameColumns.GENRE);
        return this;
    }

    /**
     * The game's cover
     */
    public GameContentValues putCover(@Nullable String value) {
        mContentValues.put(GameColumns.COVER, value);
        return this;
    }

    public GameContentValues putCoverNull() {
        mContentValues.putNull(GameColumns.COVER);
        return this;
    }

    /**
     * Tells if the game is a favourite
     */
    public GameContentValues putIsfavourite(@Nullable Boolean value) {
        mContentValues.put(GameColumns.ISFAVOURITE, value);
        return this;
    }

    public GameContentValues putIsfavouriteNull() {
        mContentValues.putNull(GameColumns.ISFAVOURITE);
        return this;
    }

    /**
     * The game's platform
     */
    public GameContentValues putPlatform(@Nullable Integer value) {
        mContentValues.put(GameColumns.PLATFORM, value);
        return this;
    }

    public GameContentValues putPlatformNull() {
        mContentValues.putNull(GameColumns.PLATFORM);
        return this;
    }
}
