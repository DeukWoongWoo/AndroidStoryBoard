package com.example.cho.librarydb.Table;

/**
 * Created by cho on 2016-02-13.
 */
public class EventInfo {
    private String objectInfo;
    private String _eventTime;

    public EventInfo(){

    }
    public EventInfo(String objectInfo,String eventTime){
        this.objectInfo = objectInfo;
        this._eventTime = eventTime;
    }

    public void setObjectInfo(String objectInfo){
        this.objectInfo=objectInfo;
    }
    public  String getObjectInfo(){
        return this.objectInfo;
    }

    public void setEventTime(String eventTime){
        this._eventTime=eventTime;
    }
    public  String getEventTime(){
        return this._eventTime;
    }
}
