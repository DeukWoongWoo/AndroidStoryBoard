package com.example.cho.librarydb.Table;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.cho.librarydb.ManageTable;

/**
 * Created by cho on 2016-02-13.
 */
public class ErrorInfo implements ManageTable{
    private String _errorTime;
    private String errorLog;
    private String objectInfo;
    private String primaryKey="_errorTime";
    private String tableName = getClass().getSimpleName();


    public ErrorInfo(){

    }
    public ErrorInfo(String _errorTime,String errorLog,String objectInfo){
        this.objectInfo = objectInfo;
        this._errorTime = _errorTime;
        this.errorLog = errorLog;

    }

    public void setErrorTime(String _errorTime){
        this._errorTime = _errorTime;
    }
    public void setErrorLog(String errorLog){
        this.errorLog = errorLog;
    }
    public void setObjectInfo(String objectInfo){
        this.objectInfo = objectInfo;
    }

    public String getErrorTime(){
        return this._errorTime ;
    }
    public String getErrorLog(){
        return this.errorLog ;
    }
    public String getObjectInfo(){
        return this.objectInfo ;
    }

    public String getPrimaryKey(){
        return this.primaryKey;
    }
    public String getTableName(){return this.tableName;}

    @Override
    public void add(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put("_errorTime", getErrorTime());
        values.put("objectInfo",getObjectInfo());
        values.put("errorLog",getErrorLog());
        db.insert("ErrorInfo", null, values);
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
