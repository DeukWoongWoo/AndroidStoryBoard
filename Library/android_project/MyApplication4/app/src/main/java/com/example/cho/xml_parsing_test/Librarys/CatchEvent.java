package com.example.cho.xml_parsing_test.Librarys;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by cho on 2016-02-15.
 */
public class CatchEvent implements UserLiporter{
    private String eventTime;
    private String activityName;

    public CatchEvent(){
    }
    public void getEvent(SQLiteDatabase db){
        TimeInfo timeInfo = new TimeInfo();
        eventTime = timeInfo.getCurrentTime();
        Log.e("-------------Event", activityName+" EventTime: "+eventTime);

        //DB저장 할 곳
    }
    public String getEventTime(){
        return eventTime;
    }

    @Override
    public void set(Context context) {
        activityName = context.getClass().getSimpleName();
    }

    @Override
    public void get(SQLiteDatabase db) {
        getEvent(db);
    }
}
