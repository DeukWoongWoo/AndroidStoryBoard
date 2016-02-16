package com.example.cho.librarydb;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.cho.librarydb.Table.ActivityInfo;
import com.example.cho.librarydb.Table.AppInfo;
import com.example.cho.librarydb.Table.UserInfo;

/**
 * Created by cho on 2016-02-13.
 */
public class TableHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME = "LibraryDB.db";
    private String activityName;
    public String dbPath ;


    public TableHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
        activityName = context.getClass().getSimpleName();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        dbPath = db.getPath();
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
                +setField("_userId", "TEXT")+","
                +setPrimaryKey("_userId")
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
                +setField("appName","TEXT")+","
                +setPrimaryKey("_activityName")+","
                +setForeignKey("appName","AppInfo","_appName")
                +setForeignKeyOption("DELETE CASCADE")
                +")";

        table[3]=
                createTableName("TimeInfo")
                +setField("_activityStartTime","TEXT")+","
                +setField("activityEndTime","TEXT")+","
                +setField("activityName","TEXT")+","
                +setPrimaryKey("_activityStartTime")+","
                +setForeignKey("activityName","ActivityInfo","_activityName")
                +setForeignKeyOption("DELETE CASCADE")
                +")";

        table[4]=
                createTableName("ObjectInfo")
                +setField("_objectInfo","TEXT")+","
                +setField("activityName","TEXT")+","
                +setPrimaryKey("_objectInfo")+","
                +setForeignKey("activityName", "ActivityInfo", "_activityName")
                +setForeignKeyOption("DELETE CASCADE")
                +")";

        table[5]=
                createTableName("ErrorInfo")
                +setField("_errorTime","TEXT")+","
                +setField("objectInfo","TEXT")+","
                +setField("errorLog","TEXT")+","
                +setPrimaryKey("_errorTime")+","
                +setForeignKey("objectInfo","ObjectInfo","_objectInfo")
                +setForeignKeyOption("DELETE CASCADE")
                +")";

        table[6]=
                createTableName("EventInfo")
                +setField("_eventTime","TEXT")+","
                +setField("objectInfo","TEXT")+","
                +setPrimaryKey("_eventTime")+","
                +setForeignKey("objectInfo","ObjectInfo","_objectInfo")
                +setForeignKeyOption("DELETE CASCADE")
                +")";

        return table;
    }

    public void add(ManageTable manageTable){
        SQLiteDatabase db = this.getWritableDatabase();
        addable(db,activityName);
        manageTable.add(db);
    }
    public boolean find(ManageTable manageTable,String field){
        SQLiteDatabase db = this.getWritableDatabase();
        return manageTable.find(db, field);
    }

    public void delete(ManageTable manageTable,String field){
        SQLiteDatabase db = this.getWritableDatabase();
        manageTable.delete(db, field);
    }
    private void addable(SQLiteDatabase db,String activityName){
        ActivityInfo activityInfo = new ActivityInfo();
        if(!activityInfo.find(db,activityName)){
            activityInfo.setActivityName(activityName);
            activityInfo.setAppName(Names.appName);
        }
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

}
