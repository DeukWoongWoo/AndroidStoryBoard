package com.example.cho.librarydb.Table;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.cho.librarydb.ManageTable;

/**
 * Created by cho on 2016-02-13.
 */
public class TimeInfo implements ManageTable{
    private String _useTime;
    private String activityName;
    private String primaryKey="_useTime";
    private String tableName = getClass().getSimpleName();

    public TimeInfo(){

    }
    public TimeInfo(String _useTime,String activityName){
        this._useTime = _useTime;
        this.activityName = activityName;
    }
    public void setActivityName(String activityName){
        this.activityName=activityName;
    }
    public  String getActivityName(){
        return this.activityName;
    }

    public void setUseTime(String _useTime){
        this._useTime=_useTime;
    }
    public  String getUseTime(){
        return this._useTime;
    }

    public String getPrimaryKey(){
        return this.primaryKey;
    }
    public String getTableName(){return this.tableName;}

    @Override
    public void add(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put("_useTime", getUseTime());
        values.put("activityName",getActivityName());
        db.insert("TimeInfo", null, values);
        db.close();
    }

    @Override
    public Object find(SQLiteDatabase db, String field) {
        return null;
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
