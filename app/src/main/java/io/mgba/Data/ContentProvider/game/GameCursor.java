package io.mgba.Data.ContentProvider.game;

// @formatter:off

import android.database.Cursor;
import android.support.annotation.NonNull;
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
     * The gamefile's md5
     * Cannot be {@code null}.
     */
    @NonNull
    @Override
    public String getMd5() {
        String res = getStringOrNull(GameColumns.MD5);
        if (res == null)
            throw new NullPointerException("The value of 'md5' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * The gamefile's path
     * Cannot be {@code null}.
     */
    @NonNull
    @Override
    public String getPath() {
        String res = getStringOrNull(GameColumns.PATH);
        if (res == null)
            throw new NullPointerException("The value of 'path' in the database was null, which is not allowed according to the model definition");
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
     * The date when the game was releases
     * Can be {@code null}.
     */
    @Nullable
    @Override
    public String getReleased() {
        String res = getStringOrNull(GameColumns.RELEASED);
        return res;
    }

    /**
     * The developer of the game
     * Can be {@code null}.
     */
    @Nullable
    @Override
    public String getDeveloper() {
        String res = getStringOrNull(GameColumns.DEVELOPER);
        return res;
    }

    /**
     * The game's genre
     * Can be {@code null}.
     */
    @Nullable
    @Override
    public String getGenre() {
        String res = getStringOrNull(GameColumns.GENRE);
        return res;
    }

    /**
     * The game's cover
     * Can be {@code null}.
     */
    @Nullable
    @Override
    public String getCover() {
        String res = getStringOrNull(GameColumns.COVER);
        return res;
    }

    /**
     * Tells if the game is a favourite
     * Can be {@code null}.
     */
    @Nullable
    @Override
    public Boolean getIsfavourite() {
        Boolean res = getBooleanOrNull(GameColumns.ISFAVOURITE);
        return res;
    }

    /**
     * The game's platform
     * Can be {@code null}.
     */
    @Nullable
    @Override
    public Integer getPlatform() {
        Integer res = getIntegerOrNull(GameColumns.PLATFORM);
        return res;
    }
}
