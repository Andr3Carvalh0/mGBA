package io.mgba.Data.ContentProvider.game;

// @formatter:off

import android.content.ContentResolver;
import android.content.Context;
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

    public GameSelection md5(String... value) {
        addEquals(GameColumns.MD5, value);
        return this;
    }

    public GameSelection md5Not(String... value) {
        addNotEquals(GameColumns.MD5, value);
        return this;
    }

    public GameSelection md5Like(String... value) {
        addLike(GameColumns.MD5, value);
        return this;
    }

    public GameSelection md5Contains(String... value) {
        addContains(GameColumns.MD5, value);
        return this;
    }

    public GameSelection md5StartsWith(String... value) {
        addStartsWith(GameColumns.MD5, value);
        return this;
    }

    public GameSelection md5EndsWith(String... value) {
        addEndsWith(GameColumns.MD5, value);
        return this;
    }

    public GameSelection orderByMd5(boolean desc) {
        orderBy(GameColumns.MD5, desc);
        return this;
    }

    public GameSelection orderByMd5() {
        orderBy(GameColumns.MD5, false);
        return this;
    }

    public GameSelection path(String... value) {
        addEquals(GameColumns.PATH, value);
        return this;
    }

    public GameSelection pathNot(String... value) {
        addNotEquals(GameColumns.PATH, value);
        return this;
    }

    public GameSelection pathLike(String... value) {
        addLike(GameColumns.PATH, value);
        return this;
    }

    public GameSelection pathContains(String... value) {
        addContains(GameColumns.PATH, value);
        return this;
    }

    public GameSelection pathStartsWith(String... value) {
        addStartsWith(GameColumns.PATH, value);
        return this;
    }

    public GameSelection pathEndsWith(String... value) {
        addEndsWith(GameColumns.PATH, value);
        return this;
    }

    public GameSelection orderByPath(boolean desc) {
        orderBy(GameColumns.PATH, desc);
        return this;
    }

    public GameSelection orderByPath() {
        orderBy(GameColumns.PATH, false);
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

    public GameSelection released(String... value) {
        addEquals(GameColumns.RELEASED, value);
        return this;
    }

    public GameSelection releasedNot(String... value) {
        addNotEquals(GameColumns.RELEASED, value);
        return this;
    }

    public GameSelection releasedLike(String... value) {
        addLike(GameColumns.RELEASED, value);
        return this;
    }

    public GameSelection releasedContains(String... value) {
        addContains(GameColumns.RELEASED, value);
        return this;
    }

    public GameSelection releasedStartsWith(String... value) {
        addStartsWith(GameColumns.RELEASED, value);
        return this;
    }

    public GameSelection releasedEndsWith(String... value) {
        addEndsWith(GameColumns.RELEASED, value);
        return this;
    }

    public GameSelection orderByReleased(boolean desc) {
        orderBy(GameColumns.RELEASED, desc);
        return this;
    }

    public GameSelection orderByReleased() {
        orderBy(GameColumns.RELEASED, false);
        return this;
    }

    public GameSelection developer(String... value) {
        addEquals(GameColumns.DEVELOPER, value);
        return this;
    }

    public GameSelection developerNot(String... value) {
        addNotEquals(GameColumns.DEVELOPER, value);
        return this;
    }

    public GameSelection developerLike(String... value) {
        addLike(GameColumns.DEVELOPER, value);
        return this;
    }

    public GameSelection developerContains(String... value) {
        addContains(GameColumns.DEVELOPER, value);
        return this;
    }

    public GameSelection developerStartsWith(String... value) {
        addStartsWith(GameColumns.DEVELOPER, value);
        return this;
    }

    public GameSelection developerEndsWith(String... value) {
        addEndsWith(GameColumns.DEVELOPER, value);
        return this;
    }

    public GameSelection orderByDeveloper(boolean desc) {
        orderBy(GameColumns.DEVELOPER, desc);
        return this;
    }

    public GameSelection orderByDeveloper() {
        orderBy(GameColumns.DEVELOPER, false);
        return this;
    }

    public GameSelection genre(String... value) {
        addEquals(GameColumns.GENRE, value);
        return this;
    }

    public GameSelection genreNot(String... value) {
        addNotEquals(GameColumns.GENRE, value);
        return this;
    }

    public GameSelection genreLike(String... value) {
        addLike(GameColumns.GENRE, value);
        return this;
    }

    public GameSelection genreContains(String... value) {
        addContains(GameColumns.GENRE, value);
        return this;
    }

    public GameSelection genreStartsWith(String... value) {
        addStartsWith(GameColumns.GENRE, value);
        return this;
    }

    public GameSelection genreEndsWith(String... value) {
        addEndsWith(GameColumns.GENRE, value);
        return this;
    }

    public GameSelection orderByGenre(boolean desc) {
        orderBy(GameColumns.GENRE, desc);
        return this;
    }

    public GameSelection orderByGenre() {
        orderBy(GameColumns.GENRE, false);
        return this;
    }

    public GameSelection cover(String... value) {
        addEquals(GameColumns.COVER, value);
        return this;
    }

    public GameSelection coverNot(String... value) {
        addNotEquals(GameColumns.COVER, value);
        return this;
    }

    public GameSelection coverLike(String... value) {
        addLike(GameColumns.COVER, value);
        return this;
    }

    public GameSelection coverContains(String... value) {
        addContains(GameColumns.COVER, value);
        return this;
    }

    public GameSelection coverStartsWith(String... value) {
        addStartsWith(GameColumns.COVER, value);
        return this;
    }

    public GameSelection coverEndsWith(String... value) {
        addEndsWith(GameColumns.COVER, value);
        return this;
    }

    public GameSelection orderByCover(boolean desc) {
        orderBy(GameColumns.COVER, desc);
        return this;
    }

    public GameSelection orderByCover() {
        orderBy(GameColumns.COVER, false);
        return this;
    }

    public GameSelection isfavourite(Boolean value) {
        addEquals(GameColumns.ISFAVOURITE, toObjectArray(value));
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

    public GameSelection platform(Integer... value) {
        addEquals(GameColumns.PLATFORM, value);
        return this;
    }

    public GameSelection platformNot(Integer... value) {
        addNotEquals(GameColumns.PLATFORM, value);
        return this;
    }

    public GameSelection platformGt(int value) {
        addGreaterThan(GameColumns.PLATFORM, value);
        return this;
    }

    public GameSelection platformGtEq(int value) {
        addGreaterThanOrEquals(GameColumns.PLATFORM, value);
        return this;
    }

    public GameSelection platformLt(int value) {
        addLessThan(GameColumns.PLATFORM, value);
        return this;
    }

    public GameSelection platformLtEq(int value) {
        addLessThanOrEquals(GameColumns.PLATFORM, value);
        return this;
    }

    public GameSelection orderByPlatform(boolean desc) {
        orderBy(GameColumns.PLATFORM, desc);
        return this;
    }

    public GameSelection orderByPlatform() {
        orderBy(GameColumns.PLATFORM, false);
        return this;
    }
}
