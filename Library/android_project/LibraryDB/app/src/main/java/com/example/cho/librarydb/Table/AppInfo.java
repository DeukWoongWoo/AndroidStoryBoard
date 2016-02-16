package com.example.cho.librarydb.Table;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.cho.librarydb.ManageTable;

/**
 * Created by cho on 2016-02-12.
 */
public class AppInfo implements ManageTable{
    private String _appName;
    private String userId;
    private String primaryKey="_appName";
    private String tableName = getClass().getSimpleName();;
    
    public AppInfo(){
    }

    public AppInfo(String appName,String userId){
        this._appName=appName;
        this.userId=userId;

    }
    public void setAppName(String appName){
        this._appName=appName;
    }
    public  String getAppName(){
        return this._appName;
    }
    public void setUserId(String userId){
        this.userId=userId;
    }
    public  String getUserId(){
        return this.userId;
    }

    public String getPrimaryKey(){
        return this.primaryKey;
    }
    public String getTableName(){return this.tableName;}

    @Override
    public void add(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put("_appName", getAppName());
        values.put("userId",getUserId());
        db.insert("AppInfo", null, values);
        db.close();
    }


    @Override
    public boolean find(SQLiteDatabase db, String field) {

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
    public boolean delete(SQLiteDatabase db, String field) {
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
