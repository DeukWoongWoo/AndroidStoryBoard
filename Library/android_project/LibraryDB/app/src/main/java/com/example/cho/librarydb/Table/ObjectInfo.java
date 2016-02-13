package com.example.cho.librarydb.Table;

/**
 * Created by cho on 2016-02-13.
 */
public class ObjectInfo {
    private String objectName;
    private String _objectInfo;


    public ObjectInfo(){
    }
    public ObjectInfo(String objectName ,String _objectInfo){
        this.objectName = objectName;
        this._objectInfo = _objectInfo;
    }

    public void setObjectInfo(String _objectInfo){
        this._objectInfo = _objectInfo;
    }
    public void setObjectName(String objectName){
        this.objectName = objectName;
    }
    public String getObjectInfo(){
        return this._objectInfo ;
    }
    public String getObjectName(){
        return this.objectName ;
    }
}
