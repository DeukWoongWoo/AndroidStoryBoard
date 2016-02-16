package com.example.cho.librarydb.LibraryFunction;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by cho on 2016-02-15.
 */
public interface UserLiporter {

    public void set(Context context);
    public void get(SQLiteDatabase db);
}
