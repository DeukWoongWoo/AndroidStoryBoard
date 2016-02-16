package com.example.cho.librarydb.LibraryFunction;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by cho on 2016-02-15.
 */
public class CatchActivity implements UserLiporter{
    private String activitySatrtTime;
    private String activityEndTime;
    private String activityName;

    public CatchActivity(){
    }
    public void setCatchActivity(){
        TimeInfo timeInfo = new TimeInfo();
        activitySatrtTime=timeInfo.getCurrentTime();
    }
    public void getCatchActivity(SQLiteDatabase db){
        TimeInfo timeInfo = new TimeInfo();
        activityEndTime = timeInfo.getCurrentTime();
        Log.e("----------Activity",activityName+"EndTime: " + activityEndTime);
        //DB저장 부분

    }

    @Override
    public void set(Context context) {
        activityName =context.getClass().getSimpleName();
        setCatchActivity();
        Log.e("----------Activity",activityName+"StartTime: "+activitySatrtTime);
    }

    @Override
    public void get(SQLiteDatabase db) {
        getCatchActivity(db);
    }
}
