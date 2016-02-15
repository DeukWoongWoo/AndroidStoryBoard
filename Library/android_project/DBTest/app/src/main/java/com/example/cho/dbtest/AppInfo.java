package com.example.cho.dbtest;

/**
 * Created by cho on 2016-02-12.
 */
public class AppInfo {
    private String _appName;
    private String userId;

    public AppInfo(){
    }

    public AppInfo(String appName,String userId){
        this._appName=appName;
        this.userId=userId;
    }
    public void setAppName(String appName){
        this._appName=appName;
    }
    public  String getAppName(){
        return this._appName;
    }
    public void setUserId(String userId){
        this.userId=userId;
    }
    public  String getUserId(){
        return this.userId;
    }
}
