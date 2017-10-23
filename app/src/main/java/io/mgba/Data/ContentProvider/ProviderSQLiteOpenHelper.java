package io.mgba.Data.ContentProvider;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.DefaultDatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;
import io.mgba.BuildConfig;
import io.mgba.Data.ContentProvider.base.BaseSQLiteOpenHelperCallbacks;
import io.mgba.Data.ContentProvider.game.GameColumns;

public class ProviderSQLiteOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = ProviderSQLiteOpenHelper.class.getSimpleName();

    public static final String DATABASE_FILE_NAME = "mgba.db";
    private static final int DATABASE_VERSION = 1;
    private static ProviderSQLiteOpenHelper sInstance;
    private final Context mContext;
    private final BaseSQLiteOpenHelperCallbacks mOpenHelperCallbacks;

    public static final String SQL_CREATE_TABLE_GAME = "CREATE TABLE IF NOT EXISTS "
            + GameColumns.TABLE_NAME + " ( "
            + GameColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + GameColumns.MD5 + " TEXT NOT NULL, "
            + GameColumns.NAME + " TEXT, "
            + GameColumns.DESCRIPTION + " TEXT, "
            + GameColumns.RELEASED + " TEXT, "
            + GameColumns.DEVELOPER + " TEXT, "
            + GameColumns.GENRE + " TEXT, "
            + GameColumns.COVER + " TEXT, "
            + GameColumns.ISFAVOURITE + " INTEGER DEFAULT 0 "
            + ", CONSTRAINT unique_filename UNIQUE (md5) ON CONFLICT REPLACE"
            + " );";


    public static ProviderSQLiteOpenHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = newInstance(context.getApplicationContext());
        }
        return sInstance;
    }

    private static ProviderSQLiteOpenHelper newInstance(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            return newInstancePreHoneycomb(context);
        }
        return newInstancePostHoneycomb(context);
    }


    /*
     * Pre Honeycomb.
     */
    private static ProviderSQLiteOpenHelper newInstancePreHoneycomb(Context context) {
        return new ProviderSQLiteOpenHelper(context);
    }

    private ProviderSQLiteOpenHelper(Context context) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION);
        mContext = context;
        mOpenHelperCallbacks = new BaseSQLiteOpenHelperCallbacks();
    }


    /*
     * Post Honeycomb.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private static ProviderSQLiteOpenHelper newInstancePostHoneycomb(Context context) {
        return new ProviderSQLiteOpenHelper(context, new DefaultDatabaseErrorHandler());
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private ProviderSQLiteOpenHelper(Context context, DatabaseErrorHandler errorHandler) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION, errorHandler);
        mContext = context;
        mOpenHelperCallbacks = new BaseSQLiteOpenHelperCallbacks();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        if (BuildConfig.DEBUG) Log.d(TAG, "onCreate");
        mOpenHelperCallbacks.onPreCreate(mContext, db);
        db.execSQL(SQL_CREATE_TABLE_GAME);
        mOpenHelperCallbacks.onPostCreate(mContext, db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        mOpenHelperCallbacks.onOpen(mContext, db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        mOpenHelperCallbacks.onUpgrade(mContext, db, oldVersion, newVersion);
    }
}
