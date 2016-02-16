package com.example.cho.librarydb.Table;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.cho.librarydb.ManageTable;

/**
 * Created by cho on 2016-02-13.
 */
public class ObjectInfo implements ManageTable{
    private String activityName;
    private String _objectInfo;
    private String primaryKey="_objectInfo";
    private String tableName = getClass().getSimpleName();


    public ObjectInfo(){
    }
    public ObjectInfo(String activityName ,String _objectInfo){
        this.activityName = activityName;
        this._objectInfo = _objectInfo;

    }

    public void setObjectInfo(String _objectInfo){
        this._objectInfo = _objectInfo;
    }
    public void setActivityName(String activityName){
        this.activityName = activityName;
    }
    public String getObjectInfo(){
        return this._objectInfo ;
    }
    public String getActivityName(){
        return this.activityName ;
    }


    public String getPrimaryKey(){
        return this.primaryKey;
    }
    public String getTableName(){return this.tableName;}

    @Override
    public void add(SQLiteDatabase db) {

        ContentValues values = new ContentValues();
        values.put("_objectInfo", getObjectInfo());
        values.put("activityName",getActivityName());
        db.insert("ObjectInfo", null, values);
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
