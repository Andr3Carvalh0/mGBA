package io.mgba.Data.ContentProvider.game;


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
     * File name
     * Can be {@code null}.
     */
    @Nullable
    String getFilename();

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
     * The year when the game was releases
     * Can be {@code null}.
     */
    @Nullable
    Integer getYear();

    /**
     * The game title from ROM
     * Can be {@code null}.
     */
    @Nullable
    String getGtitle();

    /**
     * The game code from ROM
     * Can be {@code null}.
     */
    @Nullable
    String getGcode();

    /**
     * The game maker code from ROM
     * Can be {@code null}.
     */
    @Nullable
    String getGmaker();

    /**
     * Tells if the game is a favourite
     * Can be {@code null}.
     */
    @Nullable
    Integer getIsfavourite();
}
