package com.example.cho.librarydb.Activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.cho.librarydb.LibraryFunction.CatchError;
import com.example.cho.librarydb.LibraryFunction.CurrentTime;
import com.example.cho.librarydb.LibraryFunction.UserLiporter;
import com.example.cho.librarydb.R;

public class Main2Activity extends AppCompatActivity {
    UserLiporter userLiporterError = new CatchError();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final TextView textView = (TextView)findViewById(R.id.textView);
        Button bt1 = (Button) findViewById(R.id.button3);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText("완성!!");
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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


         userLiporterError.set(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
       // userLiporterError.get("activity");
    }
}
