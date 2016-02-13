package com.example.cho.librarydb.Table;

/**
 * Created by cho on 2016-02-12.
 */
public class ActivityInfo {
    private String _activityName;
    private String _objectName;
    private String appName;

    public ActivityInfo(){
    }
    public ActivityInfo(String _activityName,String _objectName,String appName){
        this._activityName = _activityName;
        this._objectName = _objectName;
        this.appName = appName;
    }
    public void setActivityName(String _activityName){
        this._activityName = _activityName;
    }
    public void setObjectName(String _objectName){
        this._objectName = _objectName;
    }
    public void setAppName(String appName){
        this.appName = appName;
    }

    public String getActivityName(){
        return this._activityName ;
    }
    public String getObjectName(){
        return this._objectName ;
    }
    public String getAppName(){
        return this.appName ;
    }
}
