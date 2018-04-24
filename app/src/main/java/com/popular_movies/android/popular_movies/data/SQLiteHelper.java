package com.popular_movies.android.popular_movies.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

/**
 * Created by ewasniecinska on 15.04.2018.
 */

public class SQLiteHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "movies_database";

    public SQLiteHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(MovieDatabase.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieDatabase.TABLE_NAME);

        onCreate(sqLiteDatabase);
    }

    public long insertMovie(ContentValues values){
        SQLiteDatabase db = this.getWritableDatabase();

        // insert row
        long row = db.insert(MovieDatabase.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return row;
    }

    public void deleteMovie(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (db == null) {
            return;
        }
        db.delete(MovieDatabase.TABLE_NAME, "id = ?", new String[] {String.valueOf(id)});
        db.close();
    }

    public Cursor getAll() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db == null) {
            return null;
        }
        return db.rawQuery("select * from movies", null);
    }

    public Cursor getMovie(String id, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder sqliteQueryBuilder = new SQLiteQueryBuilder();
        sqliteQueryBuilder.setTables(MovieDatabase.TABLE_NAME);

        if(id != null) {
            sqliteQueryBuilder.appendWhere("id" + " = " + id);
        }

        Cursor cursor = sqliteQueryBuilder.query(getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder);
        return cursor;
    }

    public boolean CheckIsDataAlreadyInDBorNot(String TableName,
                                                      String dbfield, int fieldValue) {
        SQLiteDatabase db = this.getReadableDatabase();
        String Query = "Select * from " + TableName + " where " + dbfield + " = " + fieldValue;
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }



}
