package com.example.cho.librarydb.LibraryFunction;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by cho on 2016-02-14.
 */
public class CatchError implements UserLiporter{

    private String activityName;
    public CatchError(){

    }
    public CatchError(Context context){

    }
    public void processCatchError(){
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable ex) {
                TimeInfo timeInfo = new TimeInfo();
                String errorTime = timeInfo.getCurrentTime();
                String errorLog = ex.getMessage();
                Log.e("--------------Error",activityName+" ErrorTime : "+errorTime);
                Log.e("--------------Error",activityName+" ErrorLog: "+errorLog);
                //DB저장하는 곳(ErroInfo)
            }
        });
    }

    @Override
    public void set(Context context) {
        activityName = context.getClass().getSimpleName();
    }

    @Override
    public void get(SQLiteDatabase db) {
        processCatchError();
    }
}
