package com.popular_movies.android.popular_movies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ewasniecinska on 17.03.2018.
 */


public class DbHelper extends SQLiteOpenHelper {

    private static final String TAG = "DbHelper";

    // Database Info
    private static final String DATABASE_NAME = "FavMovies";
    private static final int DATABASE_VERSION = 1;

    //userdetail Table Columns
    private static final String movieId = "movieId";

    public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_FAV_MOVIES = "CREATE TABLE " + DATABASE_NAME +
                "(" +
                movieId + " INTEGER PRIMARY KEY " +
                ")";

        db.execSQL(CREATE_FAV_MOVIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
