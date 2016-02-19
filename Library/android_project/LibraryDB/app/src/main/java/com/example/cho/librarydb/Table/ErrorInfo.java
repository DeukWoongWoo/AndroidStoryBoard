package com.example.cho.librarydb.Table;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.cho.librarydb.HttpAsyncTaskJson;
import com.example.cho.librarydb.LibraryFunction.DataForm;
import com.example.cho.librarydb.ManageTable;
import com.example.cho.librarydb.Names;

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
    public void add(SQLiteDatabase db,String ...arg) {
        String activityName =arg[0];
        ObjectInfo objectInfo = new ObjectInfo();
        if(!objectInfo.find(db, getObjectInfo())){
            objectInfo.setActivityName(activityName);
            objectInfo.setObjectInfo(getObjectInfo());
            objectInfo.add(db);
        }

        ContentValues values = new ContentValues();
        values.put("_errorTime", getErrorTime());
        values.put("errorLog",getErrorLog());
        values.put("objectInfo",getObjectInfo());
        db.insert("ErrorInfo", null, values);
      //  db.close();
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
   //     db.close();
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
    //    db.close();
        return result;
    }

    @Override
    public boolean postData(SQLiteDatabase db) {
        HttpAsyncTaskJson httpAsyncTaskJson = new HttpAsyncTaskJson();
        String objectName = null;
        String query = "Select ObjectInfo.activityName , ErrorInfo.objectInfo , ErrorInfo._eventTime " +
                "FROM " + "ErrorInfo , ObjectInfo" + " WHERE " +
                "ErrorInfo.objectInfo = ObjectInfo._objectInfo";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            while(cursor.moveToNext()){
                objectName = cursor.getString(1);
                Log.e("Query~!!", cursor.getString(0) + "   " + cursor.getString(1) + "   " + cursor.getString(2));
                httpAsyncTaskJson.execute(DataForm.getErrorData(
                        Names.userId, Names.appName, cursor.getString(0), cursor.getString(1), cursor.getString(2)));
            }
            ObjectInfo obj = new ObjectInfo();
            obj.delete(db,objectName);
        }

        return false;
    }
}
