package com.example.cho.librarydb.Table;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.cho.librarydb.ManageTable;

/**
 * Created by cho on 2016-02-13.
 */
public class EventInfo implements ManageTable{
    private String objectInfo;
    private String _eventTime;
    private String primaryKey="_eventTime";
    private String tableName = getClass().getSimpleName();

    public EventInfo(){

    }
    public EventInfo(String objectInfo,String eventTime){
        this.objectInfo = objectInfo;
        this._eventTime = eventTime;

    }

    public void setObjectInfo(String objectInfo){
        this.objectInfo=objectInfo;
    }
    public  String getObjectInfo(){
        return this.objectInfo;
    }

    public void setEventTime(String eventTime){
        this._eventTime=eventTime;
    }
    public  String getEventTime(){
        return this._eventTime;
    }

    public String getPrimaryKey(){
        return this.primaryKey;
    }
    public String getTableName(){return this.tableName;}

    @Override
    public void add(SQLiteDatabase db) {

        ObjectInfo objectInfo = new ObjectInfo();
        if(!objectInfo.find(db, getObjectInfo())){
            //없으면 생성을 해줘야겟다.
            objectInfo.setObjectInfo(getObjectInfo());
            objectInfo.add(db);
        }

        ContentValues values = new ContentValues();
        values.put("_eventTime", getEventTime());
        values.put("objectInfo",getObjectInfo());
        db.insert("EventInfo", null, values);
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
