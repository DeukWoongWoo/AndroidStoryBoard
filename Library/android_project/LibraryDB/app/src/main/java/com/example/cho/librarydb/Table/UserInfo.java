package com.example.cho.librarydb.Table;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.cho.librarydb.ManageTable;

/**
 * Created by cho on 2016-02-12.
 */
public class UserInfo implements ManageTable {
    private String _userId;
    private static String primaryKey="_userId";
    private String tableName= getClass().getSimpleName();

    public UserInfo(){
    Log.i("Constructer! ","Constructer!");
    }

    public UserInfo(String userId){
        this._userId=userId;
        Log.i("Constructer!!!!!!! ","Constructer!!!!!!!!");
    }

    public void setUserId(String userId){
        this._userId=userId;
    }
    public  String getUserId(){
        return this._userId;
    }

    public String getPrimaryKey(){
        return this.primaryKey;
    }
    public String getTableName(){return this.tableName;}

    @Override
    public void add(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put("_userId", getUserId());
        db.insert("UserInfo", null, values);
        db.close();
    }


    @Override
    public boolean find(SQLiteDatabase db,String field) {

        boolean result = false;
        String query = "Select * FROM " + getTableName() + " WHERE " +
                getPrimaryKey() + " =  \"" + field + "\"";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            result=true;
            cursor.close();
        }
        db.close();
        return result;
    }

    @Override
    public boolean delete(SQLiteDatabase db,String field) {
        boolean result = false;

        String query = "Select * FROM " + getTableName() + " WHERE " +
                getPrimaryKey() + " =  \"" + field + "\"";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            db.delete(getTableName(), getPrimaryKey() + " = ?",
                    new String[] { cursor.getString(0) });
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }
}
