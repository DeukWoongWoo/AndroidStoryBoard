package com.example.cho.realdbtest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by cho on 2016-02-12.
 */
public class MyDBHandler extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "LibraryDB.db";
    public static final String TABLE_NAME = "UserInfo";
    public static final String COLUMN_ID = "_userId";

    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " +
                TABLE_NAME + "("
                + COLUMN_ID + " TEXT PRIMARY KEY" + ")";
        db.execSQL(CREATE_PRODUCTS_TABLE);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }



    public void addUserInfo(UserInfo userInfo) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_ID,userInfo.getUserId());
        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public UserInfo findUserInfo(String userId) {
        String query = "Select * FROM " + TABLE_NAME + " WHERE " +
                COLUMN_ID + " =  \"" + userId + "\"";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        UserInfo userInfo = new UserInfo();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            userInfo.setUserId(cursor.getString(0));
            cursor.close();
        } else {
            userInfo = null;
        }
        db.close();
        return userInfo;
    }

    public boolean deleteUserInfo(String userId) {

        boolean result = false;

        String query = "Select * FROM " + TABLE_NAME + " WHERE " +
                COLUMN_ID + " =  \"" + userId + "\"";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        UserInfo userInfo = new UserInfo();

        if (cursor.moveToFirst()) {
            userInfo.setUserId(cursor.getString(0));
            db.delete(TABLE_NAME, COLUMN_ID + " = ?",
                    new String[] { String.valueOf(userInfo.getUserId()) });
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }
}
