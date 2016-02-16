package com.example.cho.librarydb.LibraryFunction;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.cho.librarydb.Table.EventInfo;
import com.example.cho.librarydb.TableHandler;

/**
 * Created by cho on 2016-02-15.
 */
public class CatchEvent implements UserLiporter{
    private String eventTime;
    private String activityName;
    private Context context;
    private String objectName;

    public CatchEvent(){
    }
    public void getEvent(SQLiteDatabase db){
        CurrentTime timeInfo = new CurrentTime();
        eventTime = timeInfo.getCurrentTime();
        Log.e("-------------Event", activityName + " EventTime: " + eventTime);
        //DB저장 할 곳
        TableHandler tableHandler = new TableHandler(context,null,null,1);
        EventInfo eventInfo = new EventInfo(objectName,eventTime);
        tableHandler.add(eventInfo);
    }
    public String getEventTime(){
        return eventTime;
    }

    @Override
    public void set(Context context) {
        activityName = context.getClass().getSimpleName();
        this.context =context;
    }

    @Override
    public void get(SQLiteDatabase db) {
        getEvent(db);
    }
}
