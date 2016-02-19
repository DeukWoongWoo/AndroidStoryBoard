package com.example.cho.librarydb.LibraryFunction;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.example.cho.librarydb.HttpAsyncTaskJson;
import com.example.cho.librarydb.Names;
import com.example.cho.librarydb.Network;
import com.example.cho.librarydb.Table.EventInfo;
import com.example.cho.librarydb.TableHandler;

/**
 * Created by cho on 2016-02-15.
 */
public class CatchEvent implements UserLiporter{
    private String eventTime;
    private String activityName;
    private Context context;


    public CatchEvent(){
    }
    public void getEvent(String objectName){
        CurrentTime timeInfo = new CurrentTime();
        eventTime = timeInfo.getCurrentTime();
        Log.e("-------------Event", activityName + " EventTime: " + eventTime);
        TableHandler tableHandler = new TableHandler(context,null,null,1);
        EventInfo eventInfo = new EventInfo(objectName,eventTime);

        if(Network.isNetWork((Activity) context)) {
            HttpAsyncTaskJson httpAsyncTaskJson = new HttpAsyncTaskJson();
            httpAsyncTaskJson.execute(DataForm.getEventData(
                    Names.userId,Names.appName, activityName,objectName,eventTime));
            tableHandler.postDataFromDB(eventInfo);
        }else{
            tableHandler.add(eventInfo,activityName);
        }


    }
    public String getEventTime(){
        return eventTime;
    }

    @Override
    public void set(Context context) {
        this.activityName = context.getClass().getSimpleName();
        this.context =context;
    }

    @Override
    public void get(String objectName) {
        getEvent(objectName);
    }
}
