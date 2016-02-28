package com.example.cho.librarydb.LibraryFunction;

import android.util.Log;
import android.view.View;

import com.example.cho.librarydb.R;

import java.util.Locale;

/**
 * Created by cho on 2016-02-26.
 */
public class ViewTest {
    int currentView;

    public ViewTest(){

    }
    public void set(int view){
        currentView = view;
        Log.e("ViewTest view a", String.valueOf(view));
        Log.e("ViewTest view a", String.valueOf(currentView));
        Log.e("ViewTest R.layout.a", String.valueOf(R.layout.a));
        Log.e("ViewTest R.layout.b", String.valueOf(R.layout.b));
    }
    public void get(int view){
        if(view == currentView){
            Log.e("collect!!!","collect!!!");
            Log.e("getView","GetView   "+view);
        }
        else{
            Log.e("No!!!!!!!!!","No!!!!!!!!!!!!");
            Log.e("getView","GetView   "+view);
        }
    }
}
