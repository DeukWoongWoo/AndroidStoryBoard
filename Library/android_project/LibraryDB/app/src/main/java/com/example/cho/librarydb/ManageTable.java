package com.example.cho.librarydb;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by cho on 2016-02-14.
 */
public interface ManageTable {

    public void add(SQLiteDatabase db);
    public Object find(SQLiteDatabase db,String field);
    public boolean delete(SQLiteDatabase db,String field);
}
