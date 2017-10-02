package io.mgba.Data.ContentProvider.game;

import android.content.Context;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.CursorLoader;

import io.mgba.Data.ContentProvider.base.AbstractSelection;


/**
 * Selection for the {@code game} table.
 */
@SuppressWarnings({"unused", "WeakerAccess", "Recycle"})
public class GameSelection extends AbstractSelection<GameSelection> {
    @Override
    protected Uri baseUri() {
        return GameColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code GameCursor} object, which is positioned before the first entry, or null.
     */
    public GameCursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new GameCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, null)}.
     */
    public GameCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null);
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param context The context to use for the query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code GameCursor} object, which is positioned before the first entry, or null.
     */
    public GameCursor query(Context context, String[] projection) {
        Cursor cursor = context.getContentResolver().query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new GameCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(context, null)}.
     */
    public GameCursor query(Context context) {
        return query(context, null);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public CursorLoader getCursorLoader(Context context, String[] projection) {
        return new CursorLoader(context, uri(), projection, sel(), args(), order()) {
            @Override
            public Cursor loadInBackground() {
                return new GameCursor(super.loadInBackground());
            }
        };
    }


    public GameSelection id(long... value) {
        addEquals("game." + GameColumns._ID, toObjectArray(value));
        return this;
    }

    public GameSelection idNot(long... value) {
        addNotEquals("game." + GameColumns._ID, toObjectArray(value));
        return this;
    }

    public GameSelection orderById(boolean desc) {
        orderBy("game." + GameColumns._ID, desc);
        return this;
    }

    public GameSelection orderById() {
        return orderById(false);
    }

    public GameSelection filename(String... value) {
        addEquals(GameColumns.FILENAME, value);
        return this;
    }

    public GameSelection filenameNot(String... value) {
        addNotEquals(GameColumns.FILENAME, value);
        return this;
    }

    public GameSelection filenameLike(String... value) {
        addLike(GameColumns.FILENAME, value);
        return this;
    }

    public GameSelection filenameContains(String... value) {
        addContains(GameColumns.FILENAME, value);
        return this;
    }

    public GameSelection filenameStartsWith(String... value) {
        addStartsWith(GameColumns.FILENAME, value);
        return this;
    }

    public GameSelection filenameEndsWith(String... value) {
        addEndsWith(GameColumns.FILENAME, value);
        return this;
    }

    public GameSelection orderByFilename(boolean desc) {
        orderBy(GameColumns.FILENAME, desc);
        return this;
    }

    public GameSelection orderByFilename() {
        orderBy(GameColumns.FILENAME, false);
        return this;
    }

    public GameSelection name(String... value) {
        addEquals(GameColumns.NAME, value);
        return this;
    }

    public GameSelection nameNot(String... value) {
        addNotEquals(GameColumns.NAME, value);
        return this;
    }

    public GameSelection nameLike(String... value) {
        addLike(GameColumns.NAME, value);
        return this;
    }

    public GameSelection nameContains(String... value) {
        addContains(GameColumns.NAME, value);
        return this;
    }

    public GameSelection nameStartsWith(String... value) {
        addStartsWith(GameColumns.NAME, value);
        return this;
    }

    public GameSelection nameEndsWith(String... value) {
        addEndsWith(GameColumns.NAME, value);
        return this;
    }

    public GameSelection orderByName(boolean desc) {
        orderBy(GameColumns.NAME, desc);
        return this;
    }

    public GameSelection orderByName() {
        orderBy(GameColumns.NAME, false);
        return this;
    }

    public GameSelection description(String... value) {
        addEquals(GameColumns.DESCRIPTION, value);
        return this;
    }

    public GameSelection descriptionNot(String... value) {
        addNotEquals(GameColumns.DESCRIPTION, value);
        return this;
    }

    public GameSelection descriptionLike(String... value) {
        addLike(GameColumns.DESCRIPTION, value);
        return this;
    }

    public GameSelection descriptionContains(String... value) {
        addContains(GameColumns.DESCRIPTION, value);
        return this;
    }

    public GameSelection descriptionStartsWith(String... value) {
        addStartsWith(GameColumns.DESCRIPTION, value);
        return this;
    }

    public GameSelection descriptionEndsWith(String... value) {
        addEndsWith(GameColumns.DESCRIPTION, value);
        return this;
    }

    public GameSelection orderByDescription(boolean desc) {
        orderBy(GameColumns.DESCRIPTION, desc);
        return this;
    }

    public GameSelection orderByDescription() {
        orderBy(GameColumns.DESCRIPTION, false);
        return this;
    }

    public GameSelection year(Integer... value) {
        addEquals(GameColumns.YEAR, value);
        return this;
    }

    public GameSelection yearNot(Integer... value) {
        addNotEquals(GameColumns.YEAR, value);
        return this;
    }

    public GameSelection yearGt(int value) {
        addGreaterThan(GameColumns.YEAR, value);
        return this;
    }

    public GameSelection yearGtEq(int value) {
        addGreaterThanOrEquals(GameColumns.YEAR, value);
        return this;
    }

    public GameSelection yearLt(int value) {
        addLessThan(GameColumns.YEAR, value);
        return this;
    }

    public GameSelection yearLtEq(int value) {
        addLessThanOrEquals(GameColumns.YEAR, value);
        return this;
    }

    public GameSelection orderByYear(boolean desc) {
        orderBy(GameColumns.YEAR, desc);
        return this;
    }

    public GameSelection orderByYear() {
        orderBy(GameColumns.YEAR, false);
        return this;
    }

    public GameSelection gtitle(String... value) {
        addEquals(GameColumns.GTITLE, value);
        return this;
    }

    public GameSelection gtitleNot(String... value) {
        addNotEquals(GameColumns.GTITLE, value);
        return this;
    }

    public GameSelection gtitleLike(String... value) {
        addLike(GameColumns.GTITLE, value);
        return this;
    }

    public GameSelection gtitleContains(String... value) {
        addContains(GameColumns.GTITLE, value);
        return this;
    }

    public GameSelection gtitleStartsWith(String... value) {
        addStartsWith(GameColumns.GTITLE, value);
        return this;
    }

    public GameSelection gtitleEndsWith(String... value) {
        addEndsWith(GameColumns.GTITLE, value);
        return this;
    }

    public GameSelection orderByGtitle(boolean desc) {
        orderBy(GameColumns.GTITLE, desc);
        return this;
    }

    public GameSelection orderByGtitle() {
        orderBy(GameColumns.GTITLE, false);
        return this;
    }

    public GameSelection gcode(String... value) {
        addEquals(GameColumns.GCODE, value);
        return this;
    }

    public GameSelection gcodeNot(String... value) {
        addNotEquals(GameColumns.GCODE, value);
        return this;
    }

    public GameSelection gcodeLike(String... value) {
        addLike(GameColumns.GCODE, value);
        return this;
    }

    public GameSelection gcodeContains(String... value) {
        addContains(GameColumns.GCODE, value);
        return this;
    }

    public GameSelection gcodeStartsWith(String... value) {
        addStartsWith(GameColumns.GCODE, value);
        return this;
    }

    public GameSelection gcodeEndsWith(String... value) {
        addEndsWith(GameColumns.GCODE, value);
        return this;
    }

    public GameSelection orderByGcode(boolean desc) {
        orderBy(GameColumns.GCODE, desc);
        return this;
    }

    public GameSelection orderByGcode() {
        orderBy(GameColumns.GCODE, false);
        return this;
    }

    public GameSelection gmaker(String... value) {
        addEquals(GameColumns.GMAKER, value);
        return this;
    }

    public GameSelection gmakerNot(String... value) {
        addNotEquals(GameColumns.GMAKER, value);
        return this;
    }

    public GameSelection gmakerLike(String... value) {
        addLike(GameColumns.GMAKER, value);
        return this;
    }

    public GameSelection gmakerContains(String... value) {
        addContains(GameColumns.GMAKER, value);
        return this;
    }

    public GameSelection gmakerStartsWith(String... value) {
        addStartsWith(GameColumns.GMAKER, value);
        return this;
    }

    public GameSelection gmakerEndsWith(String... value) {
        addEndsWith(GameColumns.GMAKER, value);
        return this;
    }

    public GameSelection orderByGmaker(boolean desc) {
        orderBy(GameColumns.GMAKER, desc);
        return this;
    }

    public GameSelection orderByGmaker() {
        orderBy(GameColumns.GMAKER, false);
        return this;
    }

    public GameSelection isfavourite(Integer... value) {
        addEquals(GameColumns.ISFAVOURITE, value);
        return this;
    }

    public GameSelection isfavouriteNot(Integer... value) {
        addNotEquals(GameColumns.ISFAVOURITE, value);
        return this;
    }

    public GameSelection isfavouriteGt(int value) {
        addGreaterThan(GameColumns.ISFAVOURITE, value);
        return this;
    }

    public GameSelection isfavouriteGtEq(int value) {
        addGreaterThanOrEquals(GameColumns.ISFAVOURITE, value);
        return this;
    }

    public GameSelection isfavouriteLt(int value) {
        addLessThan(GameColumns.ISFAVOURITE, value);
        return this;
    }

    public GameSelection isfavouriteLtEq(int value) {
        addLessThanOrEquals(GameColumns.ISFAVOURITE, value);
        return this;
    }

    public GameSelection orderByIsfavourite(boolean desc) {
        orderBy(GameColumns.ISFAVOURITE, desc);
        return this;
    }

    public GameSelection orderByIsfavourite() {
        orderBy(GameColumns.ISFAVOURITE, false);
        return this;
    }
}
