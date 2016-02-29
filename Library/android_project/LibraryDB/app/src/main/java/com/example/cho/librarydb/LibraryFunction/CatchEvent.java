package com.example.cho.librarydb.LibraryFunction;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.util.Log;
import android.view.View;

import com.example.cho.librarydb.HttpAsyncTaskJson;
import com.example.cho.librarydb.Names;
import com.example.cho.librarydb.Network;
import com.example.cho.librarydb.Table.EventInfo;
import com.example.cho.librarydb.TableHandler;

import java.io.File;
import java.util.Properties;

/**
 * Created by cho on 2016-02-15.
 */
public class CatchEvent implements UserLiporter{
    private String eventTime;
    private String activityName;
    private Context context;
    private int currentLayoutId=0;
    private String fileName;

    public CatchEvent(){
    }
    public void getEvent(String objectName){
        CurrentTime timeInfo = new CurrentTime();
        eventTime = timeInfo.getCurrentTime();
       // Log.e("-------------Event", activityName + " EventTime: " + eventTime);
        TableHandler tableHandler = new TableHandler(context,null,null,1);
        EventInfo eventInfo = new EventInfo(objectName,eventTime);

        if(Network.isNetWork((Activity) context)) {
            HttpAsyncTaskJson httpAsyncTaskJson = new HttpAsyncTaskJson("http://210.118.64.134:3000/getpost/app/activity/object/use");
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
    public void set(Context context,int layoutId) {
        currentLayoutId=layoutId;
        this.activityName = context.getClass().getSimpleName();
        this.context =context;
        String xmlPath =context.getResources().getString(currentLayoutId);
        fileName = getXmlFileName(xmlPath);


    }


    @Override
    public void get(int layoutId,String objectName) {
        if(currentLayoutId == layoutId)
            getEvent(objectName);
    }

    private String getXmlFileName(String xmlPath){
        String dir = "res/layout/";
        char[] dirArray= dir.toCharArray();
        char [] pathArray = xmlPath.toCharArray();
        char [] name = new char[xmlPath.length()-dir.length()];
        for(int i=0;i<xmlPath.length()-dir.length();i++)
                name[i]=pathArray[i+dir.length()];

        String fileName = new String(name,0,name.length);
        return fileName;
    }


}
