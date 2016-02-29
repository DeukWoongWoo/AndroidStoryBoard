package com.example.cho.librarydb.LibraryFunction;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;

/**
 * Created by cho on 2016-02-15.
 */
public interface UserLiporter {

    void set(Context context);
    void set(Context context,int layoutId);
    void get(int layoutId,String objectName);


}
