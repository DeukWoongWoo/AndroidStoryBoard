package com.example.cho.librarydb.LibraryFunction;


import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by cho on 2016-02-16.
 */
public class DataForm {

    public static JSONObject getEventData(String userId,String appName,
                                   String activityName,String objectName,
                                   String eventTime){
        JSONObject job = new JSONObject();
        try {
            job.put("user_id","a");
            job.put("app_name","a");
            job.put("activity_name","a");
            job.put("event_type","button");
            job.put("object_name","a");
            job.put("occur_time","2011/10/30 11:22:33");
            Log.e("EventData",job.toString());
        }catch (JSONException e){
            e.printStackTrace();
        }
        return job;
    }

    public static JSONObject getActivityData(String userId,String appName,
                                          String activityName,String activityStartTime,
                                          String activityEndTime){
        JSONObject job = new JSONObject();
        try {

            job.put("user_id","a");
            job.put("app_name","a");
            job.put("activity_name","a");
            job.put("during_time_start","2011/10/30 11:22:33");
            job.put("during_time_end","2011/11/30 11:11:11");
            Log.e("EventData", job.toString());
        }catch (JSONException e){
            e.printStackTrace();
        }
        return job;
    }

    public static JSONObject getErrorData(String userId,String appName,
                                          String activityName,String objectName,
                                          String errorTime){
        JSONObject job = new JSONObject();
        try {
            job.put("user_id",userId);
            job.put("app_name",appName);
            job.put("activity_name",activityName);
            job.put("object_name",objectName);
            job.put("occur_time",errorTime);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return job;
    }
}
