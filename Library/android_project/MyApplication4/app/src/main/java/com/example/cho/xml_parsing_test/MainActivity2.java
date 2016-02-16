package com.example.cho.xml_parsing_test;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.cho.xml_parsing_test.Librarys.CatchActivity;
import com.example.cho.xml_parsing_test.Librarys.CatchError;
import com.example.cho.xml_parsing_test.Librarys.UserLiporter;

public class MainActivity2 extends AppCompatActivity {
    final String name = this.getClass().getSimpleName();
    UserLiporter activityLipoter = new CatchActivity();
    UserLiporter errorLipoter = new CatchError();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main22);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        final TextView textView = (TextView)findViewById(R.id.textView);
        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText("액티비티 이름: " + name);
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.i("Start!", "Main2---Start!");
    }

    @Override
    protected void onResume() {
        super.onResume();
        activityLipoter.set(this);
        errorLipoter.set(this);
        errorLipoter.get(null);
        Log.i("Resume!", "Main2---Resume!");
    }

    @Override
    protected void onPause() {
        activityLipoter.get(null);
        super.onPause();
        Log.i("Pause!", "Main2---Pause!!");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("Stop!", "Main2---Stop!!");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}


