package com.example.cho.librarydb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.cho.librarydb.LibraryFunction.DataForm;
import com.example.cho.librarydb.Table.ActivityInfo;
import com.example.cho.librarydb.Table.AppInfo;
import com.example.cho.librarydb.Table.ObjectInfo;
import com.example.cho.librarydb.Table.TableList;
import com.example.cho.librarydb.Table.UserInfo;

/**
 * Created by cho on 2016-02-13.
 */
public class TableHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME = "LibraryDB.db";
    private String activityName;



    public TableHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
        this.activityName = context.getClass().getSimpleName();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String[]  CREATE_PRODUCTS_TABLE = TableList.tableList();
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

    public void add(ManageTable manageTable,String ...activityName){
        SQLiteDatabase db = this.getWritableDatabase();
        setUserIdAndAppName(db);
        addable(db, this.activityName);
        if(activityName.length!=0)
            manageTable.add(db,activityName[0]);
        else
            manageTable.add(db);
        db.close();
    }
    private void setUserIdAndAppName(SQLiteDatabase db){
        UserInfo userInfo = new UserInfo(Names.userId);
        AppInfo appInfo = new AppInfo(Names.appName,Names.userId);
        if(!userInfo.find(db,Names.userId))
            userInfo.add(db);
        if(!appInfo.find(db,Names.appName))
            appInfo.add(db);
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
            activityInfo.add(db);
        }
    }
    public void checkDB(){
        SQLiteDatabase db =this.getWritableDatabase();
        String query = "Select ObjectInfo.activityName , EventInfo.objectInfo , EventInfo._eventTime " +
                "FROM " + "EventInfo , ObjectInfo" + " WHERE " +
                "EventInfo.objectInfo = ObjectInfo._objectInfo";
        Cursor cursor = db.rawQuery(query, null);
        Log.e("data size","SIZE!! : "+cursor.getCount());
        if(cursor.moveToFirst()){
            while(cursor.moveToNext()){
                Log.e("Query~!!", cursor.getString(0) + "   " + cursor.getString(1) + "   " + cursor.getString(2));
                }
                   }
    }



    public boolean postDataFromDB(ManageTable manageTable) {
        SQLiteDatabase db = this.getWritableDatabase();
        manageTable.postData(db);
        db.close();
        return true;
    }
}
