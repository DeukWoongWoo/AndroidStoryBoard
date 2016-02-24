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
            job.put("user_id",userId);
            job.put("app_name",appName);
            job.put("activity_name",activityName);
            job.put("event_type","button");
            job.put("object_name",objectName);
            job.put("occur_time",eventTime);
           // Log.e("EventData",job.toString());
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

            job.put("user_id",userId);
            job.put("app_name",appName);
            job.put("activity_name",activityName);
            job.put("during_time_start",activityStartTime);
            job.put("during_time_end",activityEndTime);
            Log.e("ActivityData", job.toString());
        }catch (JSONException e){
            e.printStackTrace();
        }
        return job;
    }

    public static JSONObject getErrorData(String userId,String appName,
                                          String activityName,String objectName,
                                          String errorTime,String errorLog){
        JSONObject job = new JSONObject();
        try {
            job.put("user_id",userId);
            job.put("app_name",appName);
            job.put("activity_name",activityName);
            job.put("object_name",objectName);
            job.put("occur_time",errorTime);
            job.put("error_log",errorLog);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return job;
    }
}
