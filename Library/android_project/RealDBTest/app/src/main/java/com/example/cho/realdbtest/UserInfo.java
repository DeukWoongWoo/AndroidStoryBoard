package com.example.cho.realdbtest;

/**
 * Created by cho on 2016-02-12.
 */
public class UserInfo {
    //UserID
    private String _userId;

    public UserInfo(){
    }

    public UserInfo(String userId){
        this._userId=userId;
    }

    public void setUserId(String userId){
        this._userId=userId;
    }
    public  String getUserId(){
        return this._userId;
    }
}
