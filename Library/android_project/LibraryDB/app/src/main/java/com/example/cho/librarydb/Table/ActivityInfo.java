package com.example.cho.librarydb.Table;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.cho.librarydb.ManageTable;

/**
 * Created by cho on 2016-02-12.
 */
public class ActivityInfo implements ManageTable{
    private String _activityName;
    private String appName;
    private String primaryKey="_activityName";
    private String tableName = getClass().getSimpleName();

    public ActivityInfo(){
    }
    public ActivityInfo(String _activityName,String appName){
        this._activityName = _activityName;
        this.appName = appName;
    }
    public void setActivityName(String _activityName){
        this._activityName = _activityName;
    }
    public void setAppName(String appName){
        this.appName = appName;
    }

    public String getActivityName(){
        return this._activityName ;
    }
    public String getAppName(){
        return this.appName ;
    }

    public String getPrimaryKey(){
        return this.primaryKey;
    }
    public String getTableName(){return this.tableName;}

    @Override
    public void add(SQLiteDatabase db) {

        ContentValues values = new ContentValues();
        values.put("_activityName", getActivityName());
        values.put("appName",getAppName());
        db.insert("ActivityInfo", null, values);
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
