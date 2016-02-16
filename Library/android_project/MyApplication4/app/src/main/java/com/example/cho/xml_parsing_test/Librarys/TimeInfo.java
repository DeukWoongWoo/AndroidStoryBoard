package com.example.cho.xml_parsing_test.Librarys;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by cho on 2016-02-14.
 */
public class TimeInfo {
    private String currentTime;

    public TimeInfo(){
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdfNow = new SimpleDateFormat("yyy/MM/dd HH:mm:ss");
        this.currentTime= sdfNow.format(date);
    }

    public String getCurrentTime() {
        return this.currentTime;
    }
}
