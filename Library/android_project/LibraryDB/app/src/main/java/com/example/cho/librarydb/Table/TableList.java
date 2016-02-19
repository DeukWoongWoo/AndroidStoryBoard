package com.example.cho.librarydb.Table;

/**
 * Created by cho on 2016-02-18.
 */
public class TableList {
    public static String[] tableList(){
        String[] table = new String[7];
        table[0] =
                createTableName("UserInfo")
                        +setField("_userId", "TEXT")+","
                        +setPrimaryKey("_userId")
                        +")";
        table[1] =
                createTableName("AppInfo")
                        +setField("_appName","TEXT")+","
                        +setField("userId","TEXT")+","
                        +setPrimaryKey("_appName")+","
                        +setForeignKey("userId", "UserInfo", "_userId")
                        +setForeignKeyOption("DELETE CASCADE")
                        +")";
        table[2] =
                createTableName("ActivityInfo")
                        +setField("_activityName", "TEXT")+","
                        +setField("appName","TEXT")+","
                        +setPrimaryKey("_activityName")+","
                        +setForeignKey("appName","AppInfo","_appName")
                        +setForeignKeyOption("DELETE CASCADE")
                        +")";

        table[3]=
                createTableName("TimeInfo")
                        +setField("_activityStartTime", "TEXT")+","
                        +setField("activityEndTime", "TEXT")+","
                        +setField("activityName","TEXT")+","
                        +setField("idx","INTEGER")+","
                        +setPrimaryKey("idx")+","
                        +setForeignKey("activityName","ActivityInfo","_activityName")
                        +setForeignKeyOption("DELETE CASCADE")
                        +")";

        table[4]=
                createTableName("ObjectInfo")
                        +setField("_objectInfo","TEXT")+","
                        +setField("activityName","TEXT")+","
                        +setPrimaryKey("_objectInfo")+","
                        +setForeignKey("activityName", "ActivityInfo", "_activityName")
                        +setForeignKeyOption("DELETE CASCADE")
                        +")";

        table[5]=
                createTableName("ErrorInfo")
                        +setField("_errorTime","TEXT")+","
                        +setField("errorLog","TEXT")+","
                        +setField("objectInfo","TEXT")+","
                        +setPrimaryKey("_errorTime")+","
                        +setForeignKey("objectInfo","ObjectInfo","_objectInfo")
                        +setForeignKeyOption("DELETE CASCADE")
                        +")";

        table[6]=
                createTableName("EventInfo")
                        +setField("_eventTime","TEXT")+","
                        +setField("objectInfo","TEXT")+","
                        +setField("idx","INTEGER")+","
                        +setPrimaryKey("idx")+","
                        +setForeignKey("objectInfo","ObjectInfo","_objectInfo")
                        +setForeignKeyOption("DELETE CASCADE")
                        +")";

        return table;
    }


    private static String createTableName(String tableName){
        return "CREATE TABLE "+tableName+"(";
    }
    private static String setField(String field,String type){
        return field+" "+type;
    }
    private static String setPrimaryKey(String field){
        return "PRIMARY KEY ("+field+")";
    }
    private static String setForeignKey(String foreignkey,String referenceTable,String referenceField){
        return "FOREIGN KEY("+foreignkey+") REFERENCES "+referenceTable+"("+referenceField+")";
    }
    private static String setForeignKeyOption(String option){
        return "ON "+option;
    }
}
