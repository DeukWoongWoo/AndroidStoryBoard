package com.example.cho.librarydb.LibraryFunction;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Looper;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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
            TableHandler tableHandler = new TableHandler(context, null, null, 1);
            ErrorInfo errorInfo = new ErrorInfo(errorTime, errorLog, object);
            if(Network.isNetWork((Activity) context)) {
                HttpAsyncTaskJson httpAsyncTaskJson = new HttpAsyncTaskJson("http://210.118.64.134:3000/getpost/app/activity/object/error/use");
                httpAsyncTaskJson.execute(DataForm.getErrorData(
                        Names.userId,Names.appName, activityName,object,errorTime,errorLog));
                //tableHandler.postDataFromDB(errorInfo);
            }else {
                tableHandler.add(errorInfo,activityName);
            }

            new Thread(){
                @Override
                public void run() {
                    Looper.prepare();
                    Toast.makeText(context,"에러가 발생했습니다.",Toast.LENGTH_SHORT).show();
                    Looper.loop();;
                }
            }.start();
            try{
                Thread.sleep(2000);
            }catch (InterruptedException e) {
                e.printStackTrace();
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
