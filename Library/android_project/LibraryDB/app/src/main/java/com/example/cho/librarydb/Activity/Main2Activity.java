package com.example.cho.librarydb.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.cho.librarydb.LibraryFunction.CatchError;
import com.example.cho.librarydb.LibraryFunction.CurrentTime;
import com.example.cho.librarydb.LibraryFunction.UserLiporter;
import com.example.cho.librarydb.LibraryFunction.ViewTest;
import com.example.cho.librarydb.R;

public class Main2Activity extends AppCompatActivity {
    UserLiporter userLiporterError = new CatchError();
    ViewTest viewTest = new ViewTest();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=new Intent(this.getIntent());

        String s=intent.getStringExtra("text");
        viewTest=new ViewTest();
        Log.e("dataAA", String.valueOf(R.layout.a));
        Log.e("dataBB", String.valueOf(R.layout.b));
        if(s.equals("a"))
            setContentView(R.layout.a);
        else
            setContentView(R.layout.b);

        Log.e("buttonId", String.valueOf(R.id.button));



//        View vi = findViewById(android.R.id.content);
  //      Log.e("this", String.valueOf(vi.getId()));

        Button button = (Button)findViewById(R.id.buttonA);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("buttonId", String.valueOf(R.id.button));
                Log.e("getWindow()", String.valueOf(getWindow().getDecorView().getId()));
                Log.e("getWindowManager()", String.valueOf(getWindowManager().getDefaultDisplay().getDisplayId()));
                Log.e("getCurrentFocus()", String.valueOf(getCurrentFocus().getRootView().getId()));


                switch (v.getId()){
                    case R.layout.a :
                        Log.e("Click A", String.valueOf(v.getId()));

                        break;
                    case R.layout.b:
                        Log.e("Click B", String.valueOf(v.getId()));

                        break;

                    default:
                        Log.e("Different!", String.valueOf(v.getId()));
                        break;
                }

               // viewTest.get(R.layout.a);
            }
        });

    }

    public class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
        @Override
        public void uncaughtException(Thread thread, Throwable ex) {
            CurrentTime timeInfo = new CurrentTime();
            String errorTime = timeInfo.getCurrentTime();
            String errorLog = ex.getMessage();
            Log.e("--------------Error", this.getClass().getSimpleName() + " ErrorTime : " + errorTime);
            Log.e("--------------Error", this.getClass().getSimpleName() + " ErrorLog: " + errorLog);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(10);

        }
    }
    @Override
    protected void onResume() {
        super.onResume();
       // Thread.setDefaultUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
       // Log.e("focusView", String.valueOf(getCurrentFocus().getId()));
        viewTest.set(R.layout.a);
         //userLiporterError.set(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
       // userLiporterError.get("activity");
    }




}
