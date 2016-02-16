package com.example.cho.librarydb.LibraryFunction;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.cho.librarydb.Table.ActivityInfo;
import com.example.cho.librarydb.Table.TimeInfo;
import com.example.cho.librarydb.TableHandler;

/**
 * Created by cho on 2016-02-15.
 */
public class CatchActivity implements UserLiporter{
    private String activitySatrtTime;
    private String activityEndTime;
    private String activityName;
    private Context context;

    public CatchActivity(){
    }
    public void setCatchActivity(){
        CurrentTime currentTime = new CurrentTime();
        activitySatrtTime=currentTime.getCurrentTime();
    }
    public void getCatchActivity(SQLiteDatabase db){
        CurrentTime currentTime = new CurrentTime();
        activityEndTime = currentTime.getCurrentTime();
        Log.e("----------Activity",activityName+"EndTime: " + activityEndTime);
        //DB저장 부분
        TableHandler tableHandler = new TableHandler(context,null,null,1);
        TimeInfo timeInfo = new TimeInfo(activitySatrtTime,activityEndTime,activityName);
        tableHandler.add(timeInfo);
    }

    @Override
    public void set(Context context) {
        activityName =context.getClass().getSimpleName();
        this.context = context;
        setCatchActivity();
        Log.e("----------Activity",activityName+"StartTime: "+activitySatrtTime);
    }

    @Override
    public void get(SQLiteDatabase db) {
        getCatchActivity(db);
    }
}
