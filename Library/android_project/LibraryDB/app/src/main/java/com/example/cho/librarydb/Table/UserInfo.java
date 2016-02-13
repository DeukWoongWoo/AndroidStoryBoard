package com.example.cho.librarydb.Table;

/**
 * Created by cho on 2016-02-12.
 */
public class UserInfo {
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
