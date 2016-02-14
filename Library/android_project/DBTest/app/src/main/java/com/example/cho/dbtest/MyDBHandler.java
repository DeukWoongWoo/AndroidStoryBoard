package com.example.cho.dbtest;

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

    public static final String TABLE_NAME2="AppInfo";
    public static final String COLUMN_APPID = "_appName";
    public static final String COLUMN_USERID="userId";


    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {




        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " TEXT PRIMARY KEY" + ")";
        db.execSQL(CREATE_PRODUCTS_TABLE);

/*
        String CREATE_PRODUCTS_TABLE2 = "CREATE TABLE " + TABLE_NAME2 + "(" +
                COLUMN_APPID + " TEXT PRIMARY KEY," +
                COLUMN_USERID+" TEXT"+
                ")";
*/

        String CREATE_PRODUCTS_TABLE2 = "CREATE TABLE " + TABLE_NAME2 + "(" +
                COLUMN_APPID + " TEXT PRIMARY KEY," +
                COLUMN_USERID+" TEXT,"+
                "FOREIGN KEY("+COLUMN_USERID+")"+ " REFERENCES "+TABLE_NAME+"("+COLUMN_ID+")"+ " ON DELETE CASCADE"+
                ")";
        db.execSQL(CREATE_PRODUCTS_TABLE2);
        db.execSQL("PRAGMA foreign_keys = ON");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.execSQL("PRAGMA foreign_keys = ON");
    }

    public void makeTable(SQLiteDatabase db){
        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " TEXT PRIMARY KEY" + ")";
        db.execSQL(CREATE_PRODUCTS_TABLE);
    }

    public void addUserInfo(UserInfo userInfo) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, userInfo.getUserId());
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
            String tmp =String.valueOf(userInfo.getUserId());

            db.delete(TABLE_NAME, COLUMN_ID + " = ?",
                    new String[] { tmp });
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }


    public void addAppInfo(AppInfo appInfo) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_APPID,appInfo.getAppName());
        values.put(COLUMN_USERID,appInfo.getUserId());

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TABLE_NAME2, null, values);
        db.close();
    }

    public AppInfo findAppInfo(String appName) {
        String query = "Select * FROM " + TABLE_NAME2 + " WHERE " +
                COLUMN_APPID + " =  \"" + appName + "\"";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        AppInfo appInfo = new AppInfo();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            appInfo.setAppName(cursor.getString(0));
            appInfo.setUserId(cursor.getString(1));
            cursor.close();
        } else {
            appInfo = null;
        }
        db.close();
        return appInfo;
    }

    public boolean deleteAppInfo(String appName) {

        boolean result = false;

        String query = "Select * FROM " + TABLE_NAME2 + " WHERE " +
                COLUMN_APPID + " =  \"" + appName + "\"";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        AppInfo appInfo = new AppInfo();

        if (cursor.moveToFirst()) {
            appInfo.setAppName(cursor.getString(0));
            //String tmp  = String.valueOf(appInfo.getAppName());
            db.delete(TABLE_NAME2, COLUMN_APPID + " = ?",
                    new String[] { String.valueOf(appInfo.getAppName()) });
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }


}
