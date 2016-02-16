package com.example.cho.librarydb.LibraryFunction;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.TabLayout;
import android.util.Log;

import com.example.cho.librarydb.ManageTable;
import com.example.cho.librarydb.Table.ErrorInfo;
import com.example.cho.librarydb.Table.UserInfo;
import com.example.cho.librarydb.TableHandler;

/**
 * Created by cho on 2016-02-14.
 */
public class CatchError implements UserLiporter{

    private String activityName;
    private Context context;
    private String objectName;
    public CatchError(){

    }
    public CatchError(Context context){

    }
    private void processCatchError(SQLiteDatabase db){
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable ex) {
                CurrentTime timeInfo = new CurrentTime();
                String errorTime = timeInfo.getCurrentTime();
                String errorLog = ex.getMessage();
                Log.e("--------------Error",activityName+" ErrorTime : "+errorTime);
                Log.e("--------------Error",activityName+" ErrorLog: "+errorLog);
                //DB저장하는 곳(ErroInfo)
                TableHandler tableHandler = new TableHandler(context,null,null,1);
                ErrorInfo errorInfo = new ErrorInfo(errorTime,errorLog,objectName);
                tableHandler.add(errorInfo);

            }
        });
    }

    @Override
    public void set(Context context) {
        this.context = context;
        activityName = context.getClass().getSimpleName();
    }

    @Override
    public void get(SQLiteDatabase db) {
        processCatchError(db);
    }
}
