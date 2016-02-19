package com.example.cho.librarydb;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by cho on 2016-02-16.
 */
public class Network{

    public Network(){

    }
    public static Boolean isNetWork(Activity activity){

        ConnectivityManager manager = (ConnectivityManager) activity.getSystemService (Context.CONNECTIVITY_SERVICE);
        boolean isMobileAvailable = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isAvailable();
        boolean isMobileConnect = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
        boolean isWifiAvailable = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isAvailable();
        boolean isWifiConnect = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();

        if ((isWifiAvailable && isWifiConnect) || (isMobileAvailable && isMobileConnect)){
            return true;
        }else{
            return false;
        }
    }


}
