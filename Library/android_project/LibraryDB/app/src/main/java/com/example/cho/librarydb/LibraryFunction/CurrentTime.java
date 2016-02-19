package com.example.cho.librarydb.LibraryFunction;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by cho on 2016-02-14.
 */
public class CurrentTime {
    private String currentTime;

    public CurrentTime(){
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdfNow = new SimpleDateFormat("yyy/MM/dd HH:mm:ss");
        this.currentTime= sdfNow.format(date);
    }

    public String getCurrentTime() {
        return this.currentTime;
    }
}
