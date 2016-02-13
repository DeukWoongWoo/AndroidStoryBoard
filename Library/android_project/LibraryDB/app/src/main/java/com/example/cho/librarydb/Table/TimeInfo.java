package com.example.cho.librarydb.Table;

/**
 * Created by cho on 2016-02-13.
 */
public class TimeInfo {
    private String _useTime;
    private String activityName;

    public TimeInfo(){

    }
    public TimeInfo(String _useTime,String activityName){
        this._useTime = _useTime;
        this.activityName = activityName;
    }
    public void setActivityName(String activityName){
        this.activityName=activityName;
    }
    public  String getActivityName(){
        return this.activityName;
    }

    public void setUseTime(String _useTime){
        this._useTime=_useTime;
    }
    public  String getUseTime(){
        return this._useTime;
    }

}
