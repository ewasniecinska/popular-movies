package com.popular_movies.android.popular_movies.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by ewasniecinska on 16.04.2018.
 */

public class MovieProvider extends ContentProvider {


    public static final String PROVIDER_NAME = "com.popular_movies.android.popular_movies.data";
    public static final Uri CONTENT_URI = Uri.parse("content://" + PROVIDER_NAME);
    public static final int MOVIES = 1;
    public static final int MOVIE_ID = 2;
    public static final UriMatcher uriMatcher = getUriMatcher();

    SQLiteHelper database = null;

    private static UriMatcher getUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "movies", MOVIES);
        uriMatcher.addURI(PROVIDER_NAME, "movies/#", MOVIE_ID);
        return uriMatcher;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)){
            case MOVIES:
                return "vnd.android.cursor.dir/vnd.com.popular_movies.android.popular_movies.data.movies";

            case MOVIE_ID:
                return "vnd.android.cursor.item/vnd.com.popular_movies.android.popular_movies.data.movies";
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        database = new SQLiteHelper(context);
        return true;
    }


    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        String id = null;
        if(uriMatcher.match(uri) == MOVIE_ID) {
            id = uri.getPathSegments().get(1);
        }
        return database.getMovie(id, strings, s, strings1, s1);
    }



    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        try {
            long id = database.insertMovie(contentValues);
            Uri returnUri = ContentUris.withAppendedId(CONTENT_URI, id);
            return returnUri;
        } catch(Exception e) {
            return null;
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        String id = null;
        if(uriMatcher.match(uri) == MOVIE_ID) {
            //Delete is for one single image. Get the ID from the URI.
            id = uri.getPathSegments().get(1);
        }

        database.deleteMovie(Integer.parseInt(s));
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
