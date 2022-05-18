package com.example.sqlite_project.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.sqlite_project.model.Baby;

public class SQLiteDB extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Baby.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    public static final String TABLE_BABY = "baby";
    public static final String COLUMN_BABY_ID = "baby_id";
    public static final String COLUMN_BABY_NAME = "baby_name";
    public static final String COLUMN_BABY_GENDER = "baby_gender";
    public static final String COLUMN_BABY_DOB = "baby_dob";
    public static final String COLUMN_BABY_ISSUE = "baby_issue";

    private static final String CREATE_BABY =
            "CREATE TABLE " + TABLE_BABY + " (" +
                    COLUMN_BABY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_BABY_NAME + TEXT_TYPE + COMMA_SEP +
                    COLUMN_BABY_GENDER + TEXT_TYPE + COMMA_SEP +
                    COLUMN_BABY_DOB + TEXT_TYPE + COMMA_SEP +
                    COLUMN_BABY_ISSUE + TEXT_TYPE + " )";

    private static final String DELETE_BABY =
            "DROP TABLE IF EXISTS " + TABLE_BABY;

    public SQLiteDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BABY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETE_BABY);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void create(Baby baby){
        // Gets the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(COLUMN_BABY_NAME, baby.getName());
        values.put(COLUMN_BABY_GENDER, baby.getGender());
        values.put(COLUMN_BABY_DOB, baby.getDob());
        values.put(COLUMN_BABY_ISSUE, baby.getIssue());

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                TABLE_BABY,
                null,
                values);
    }

    public Cursor retrieve(){
        SQLiteDatabase db = getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                COLUMN_BABY_ID,
                COLUMN_BABY_NAME,
                COLUMN_BABY_GENDER,
                COLUMN_BABY_DOB,
                COLUMN_BABY_ISSUE };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                COLUMN_BABY_NAME + " ASC";

        Cursor c = db.query(
                TABLE_BABY,                    // The table to query
                projection,                                 // The columns to return
                null,                                       // The columns for the WHERE clause
                null,                                       // The values for the WHERE clause
                null,                                       // don't group the rows
                null,                                       // don't filter by row groups
                sortOrder                                   // The sort order
        );

        return c;
    }

    public void update(Baby baby){
        SQLiteDatabase db = getReadableDatabase();

        // New value for one column
        ContentValues values = new ContentValues();
        values.put(COLUMN_BABY_NAME, baby.getName());
        values.put(COLUMN_BABY_GENDER, baby.getGender());
        values.put(COLUMN_BABY_DOB, baby.getDob());
        values.put(COLUMN_BABY_ISSUE, baby.getIssue());

        // Which row to update, based on the ID
        String selection = COLUMN_BABY_ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(baby.getId()) };

        int count = db.update(
                TABLE_BABY,
                values,
                selection,
                selectionArgs);
    }

    public void delete(int id){
        SQLiteDatabase db = getReadableDatabase();

        // Define 'where' part of query.
        String selection = COLUMN_BABY_ID + " LIKE ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = { String.valueOf(id) };
        // Issue SQL statement.
        db.delete(TABLE_BABY, selection, selectionArgs);
    }
}
