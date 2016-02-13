package com.example.cho.librarydb.Table;

/**
 * Created by cho on 2016-02-13.
 */
public class ErrorInfo {
    private String _errorTime;
    private String _errorLog;
    private String objectInfo;


    public ErrorInfo(){

    }
    public ErrorInfo(String _errorTime,String _errorLog,String objectInfo){
        this.objectInfo = objectInfo;
        this._errorTime = _errorTime;
        this._errorLog = _errorLog;
    }

    public void setErrorTime(String _errorTime){
        this._errorTime = _errorTime;
    }
    public void setErrorLog(String _errorLog){
        this._errorLog = _errorLog;
    }
    public void setObjectInfo(String objectInfo){
        this.objectInfo = objectInfo;
    }

    public String getErrorTime(){
        return this._errorTime ;
    }
    public String getErrorLog(){
        return this._errorLog ;
    }
    public String getObjectInfo(){
        return this.objectInfo ;
    }

}
