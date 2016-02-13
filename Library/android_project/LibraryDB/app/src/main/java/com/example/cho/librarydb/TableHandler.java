package com.example.cho.librarydb;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.cho.librarydb.Table.AppInfo;
import com.example.cho.librarydb.Table.UserInfo;

/**
 * Created by cho on 2016-02-13.
 */
public class TableHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME = "LibraryDB.db";


    public TableHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Table Create
        String[]  CREATE_PRODUCTS_TABLE = tableList();
        for(int i=0;i<CREATE_PRODUCTS_TABLE.length;i++)
            db.execSQL(CREATE_PRODUCTS_TABLE[i]);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Table Upgrade
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.execSQL("PRAGMA foreign_keys = ON");
    }

    private String[] tableList(){
        String[] table = new String[7];
        table[0] =
                createTableName("UserInfo")
                +setField("_userInfo", "TEXT")+","
                +setPrimaryKey("_userInfo")
                +")";
        table[1] =
                createTableName("AppInfo")
                +setField("_appName","TEXT")+","
                +setField("userId","TEXT")+","
                +setPrimaryKey("_appName")+","
                +setForeignKey("userId", "UserInfo", "_userId")
                +setForeignKeyOption("DELETE CASCADE")
                +")";
        table[2] =
                createTableName("ActivityInfo")
                +setField("_activityName", "TEXT")+","
                +setField("_objectName","TEXT")+","
                +setField("appName","TEXT")+","
                +setPrimaryKey("_activityName,_objectName")+","
                +setForeignKey("appName","AppInfo","_appName")
                +setForeignKeyOption("DELETE CASCADE")
                +")";
        table[3]=
                createTableName("TimeInfo")
                +setField("_useTime","TEXT")+","
                +setField("activityName","TEXT")+","
                +setPrimaryKey("_useTime")+","
                +setForeignKey("activityName","ActivityInfo","_activityName")
                +setForeignKeyOption("DELETE CASCADE")
                +")";

        table[4]=
                createTableName("ObjectInfo")
                +setField("objectName","TEXT")+","
                +setField("_objectInfo","TEXT")+","
                +setPrimaryKey("_objectInfo")+","
                +setForeignKey("objectName", "ActivityInfo", "_objectName")
                +setForeignKeyOption("DELETE CASCADE")
                +")";

        table[5]=
                createTableName("ErrorInfo")
                +setField("_errorTime","TEXT")+","
                +setField("_errorLog","TEXT")+","
                +setField("objectInfo","TEXT")+","
                +setPrimaryKey("_errorTime,_errorLog")+","
                +setForeignKey("objectInfo","ObjectInfo","_objectInfo")
                +setForeignKeyOption("DELETE CASCADE")
                +")";

        table[6]=
                createTableName("EventInfo")
                +setField("objectInfo","TEXT")+","
                +setField("_eventTime","TEXT")+","
                +setPrimaryKey("_eventTime")+","
                +setForeignKey("objectInfo","ObjectInfo","_objectInfo")
                +setForeignKeyOption("DELETE CASCADE")
                +")";

        return table;
    }



    public void addUserInfo(UserInfo userInfo) {

        ContentValues values = new ContentValues();
        values.put("_userInfo", userInfo.getUserId());
        SQLiteDatabase db = this.getWritableDatabase();

        db.insert("UserInfo", null, values);
        db.close();
    }
    public void addAppInfo(AppInfo appInfo) {

        ContentValues values = new ContentValues();
        values.put("_appName",appInfo.getAppName());
        values.put("userId",appInfo.getUserId());

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert("AppInfo", null, values);
        db.close();
    }


    private String createTableName(String tableName){
        return "CREATE TABLE "+tableName+"(";
    }
    private String setField(String field,String type){
        return field+" "+type;
    }
    private String setPrimaryKey(String field){
        return "PRIMARY KEY ("+field+")";
    }
    private String setForeignKey(String foreignkey,String referenceTable,String referenceField){
        return "FOREIGN KEY("+foreignkey+") REFERENCES "+referenceTable+"("+referenceField+")";
    }
    private String setForeignKeyOption(String option){
        return "ON "+option;
    }

    public void addInfo(){}
}
