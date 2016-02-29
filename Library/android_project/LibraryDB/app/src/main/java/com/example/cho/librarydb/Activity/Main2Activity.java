package com.example.cho.librarydb.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.cho.librarydb.LibraryFunction.CatchError;
import com.example.cho.librarydb.LibraryFunction.CatchEvent;
import com.example.cho.librarydb.LibraryFunction.CurrentTime;
import com.example.cho.librarydb.LibraryFunction.UserLiporter;
import com.example.cho.librarydb.LibraryFunction.ViewTest;
import com.example.cho.librarydb.R;

import java.io.IOException;


public class Main2Activity extends AppCompatActivity implements View.OnClickListener{

    UserLiporter userLiporter  = new CatchEvent();
    int currentLayoutId=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Intent intent=new Intent(this.getIntent());

        String s=intent.getStringExtra("text");

        if(s.equals("a"))
            setContentView(R.layout.a);
        else
           setContentView(R.layout.b);

        Button button = (Button)findViewById(R.id.buttonA);
        button.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.buttonA:
                if(currentLayoutId == R.layout.a)
                    Log.e("LayoutA","Button!!");
                break;
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        currentLayoutId=layoutResID;
        super.setContentView(layoutResID);
    }

    @Override
    protected void onResume() {
        super.onResume();
        userLiporter.set(this,currentLayoutId);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

}
