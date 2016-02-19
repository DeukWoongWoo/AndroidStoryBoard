package com.example.cho.librarydb.LibraryFunction;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.example.cho.librarydb.HttpAsyncTaskJson;
import com.example.cho.librarydb.Names;
import com.example.cho.librarydb.Network;
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
    public void getCatchActivity(){
        CurrentTime currentTime = new CurrentTime();
        activityEndTime = currentTime.getCurrentTime();
        Log.e("----------Activity",activityName+"EndTime: " + activityEndTime);
        TableHandler tableHandler = new TableHandler(context, null, null, 1);
        TimeInfo timeInfo = new TimeInfo(activityName,activitySatrtTime, activityEndTime);
        if(Network.isNetWork((Activity) context)) {
            HttpAsyncTaskJson httpAsyncTaskJson = new HttpAsyncTaskJson();
            httpAsyncTaskJson.execute(DataForm.getActivityData(
                    Names.userId, Names.appName, activityName, activitySatrtTime, activityEndTime));
            tableHandler.postDataFromDB(timeInfo);
        }else { //DB저장 부분

            tableHandler.add(timeInfo);
        }
    }

    @Override
    public void set(Context context) {
        this.activityName =context.getClass().getSimpleName();
        this.context = context;
        setCatchActivity();
        Log.e("----------Activity",activityName+"StartTime: "+activitySatrtTime);
    }

    @Override
    public void get(String objectName) {
        getCatchActivity();
    }
}
