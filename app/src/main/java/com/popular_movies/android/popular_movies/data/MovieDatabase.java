package com.popular_movies.android.popular_movies.data;

/**
 * Created by ewasniecinska on 15.04.2018.
 */

public class MovieDatabase {

    public static final String TABLE_NAME = "movies";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_SCORE = "score";
    public static final String COLUMN_OVERVIEW = "overview";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_POSTER = "poster";

    private int id;
    private String note;
    private String timestamp;

    // Create table
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY,"
                    + COLUMN_TITLE + " TEXT,"
                    + COLUMN_SCORE + " TEXT,"
                    + COLUMN_OVERVIEW + " TEXT,"
                    + COLUMN_DATE + " TEXT,"
                    + COLUMN_POSTER+ " TEXT"
                    + ")";

    public MovieDatabase(int id, String note, String timestamp) {
        this.id = id;
        this.note = note;
        this.timestamp = timestamp;
    }

}
