package com.example.cho.librarydb.LibraryFunction;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.TabLayout;
import android.util.Log;

import com.example.cho.librarydb.HttpAsyncTaskJson;
import com.example.cho.librarydb.ManageTable;
import com.example.cho.librarydb.Names;
import com.example.cho.librarydb.Network;
import com.example.cho.librarydb.Table.ErrorInfo;
import com.example.cho.librarydb.Table.UserInfo;
import com.example.cho.librarydb.TableHandler;

/**
 * Created by cho on 2016-02-14.
 */
public class CatchError implements UserLiporter{

    private String activityName;
    private Context context;
    public String object;
    public CatchError(){

    }


    @Override
    public void set(Context context) {
        Log.e("--------------Error","Error Set!!!!");
        this.context = context;
        activityName = this.context.getClass().getSimpleName();
        Thread.setDefaultUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
    }

    public class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
        @Override
        public void uncaughtException(Thread thread, Throwable ex) {
            CurrentTime timeInfo = new CurrentTime();
            String errorTime = timeInfo.getCurrentTime();
            String errorLog = ex.getMessage();
            Log.e("--------------Error",activityName+" ErrorTime : "+errorTime);
            Log.e("--------------Error", activityName + " ErrorLog: " + errorLog);
            TableHandler tableHandler = new TableHandler(context, null, null, 1);
            ErrorInfo errorInfo = new ErrorInfo(errorTime, errorLog, object);
            if(Network.isNetWork((Activity) context)) {
                HttpAsyncTaskJson httpAsyncTaskJson = new HttpAsyncTaskJson();
                httpAsyncTaskJson.execute(DataForm.getErrorData(
                        Names.userId,Names.appName, activityName,object,errorTime));
                tableHandler.postDataFromDB(errorInfo);
            }else {
                //DB저장하는 곳(ErroInfo)
                tableHandler.add(errorInfo,activityName);
            }

            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(10);

        }
    }
    @Override
    public void get(String objectName) {
        Log.e("--------------Error","Error Get!!!!");
        this.object = objectName;

    }
}
