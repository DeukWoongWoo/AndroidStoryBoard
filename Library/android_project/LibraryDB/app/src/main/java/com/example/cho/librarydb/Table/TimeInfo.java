package com.example.cho.librarydb.Table;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.cho.librarydb.ManageTable;

/**
 * Created by cho on 2016-02-13.
 */
public class TimeInfo implements ManageTable{
    private String _activityStartTime;
    private String activityEndTime;
    private String activityName;
    private String primaryKey="_activityStartTime";
    private String tableName = getClass().getSimpleName();

    public TimeInfo(){

    }
    public TimeInfo(String activityName,String _activityStartTime,String activityEndTime){
        this._activityStartTime = _activityStartTime;
        this.activityEndTime = activityEndTime;
        this.activityName = activityName;
    }
    public void setActivityName(String activityName){
        this.activityName=activityName;
    }
    public  String getActivityName(){
        return this.activityName;
    }

    public void setTime(String _activityStartTime,String activityEndTime){
        this._activityStartTime = _activityStartTime;
        this.activityEndTime=activityEndTime;
    }
    public  String getActivityStartTime(){
        return this._activityStartTime;
    }

    public String getActivityEndTime() {
        return this.activityEndTime;
    }

    public String getPrimaryKey(){
        return this.primaryKey;
    }
    public String getTableName(){return this.tableName;}

    @Override
    public void add(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put("_activityStartTime", getActivityStartTime());
        values.put("activityEndTime",getActivityEndTime());
        values.put("activityName",getActivityName());
        db.insert("TimeInfo", null, values);
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
