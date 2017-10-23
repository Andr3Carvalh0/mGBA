package io.mgba.Data.ContentProvider.game;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import io.mgba.Data.ContentProvider.base.BaseModel;

/**
 * A game.
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public interface GameModel extends BaseModel {

    /**
     * Primary key.
     */
    long getId();

    /**
     * The gamefile's md5
     * Cannot be {@code null}.
     */
    @NonNull
    String getMd5();

    /**
     * The game name returned by the server
     * Can be {@code null}.
     */
    @Nullable
    String getName();

    /**
     * A brief description of the game
     * Can be {@code null}.
     */
    @Nullable
    String getDescription();

    /**
     * The date when the game was releases
     * Can be {@code null}.
     */
    @Nullable
    String getReleased();

    /**
     * The developer of the game
     * Can be {@code null}.
     */
    @Nullable
    String getDeveloper();

    /**
     * The game's genre
     * Can be {@code null}.
     */
    @Nullable
    String getGenre();

    /**
     * The game's cover
     * Can be {@code null}.
     */
    @Nullable
    String getCover();

    /**
     * Tells if the game is a favourite
     * Can be {@code null}.
     */
    @Nullable
    Boolean getIsfavourite();
}
