package com.example.cho.xml_parsing_test;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.cho.xml_parsing_test.Librarys.CatchActivity;
import com.example.cho.xml_parsing_test.Librarys.UserLiporter;

public class MainActivity3 extends AppCompatActivity {

    UserLiporter liporter = new CatchActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
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
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("Start!", "Main3---Start!");
    }

    @Override
    protected void onResume() {
        super.onResume();
        liporter.set(this);
        Log.i("Resume!", "Main3---Resume!");
    }

    @Override
    protected void onPause() {
        super.onPause();
        liporter.get(null);
        Log.i("Pause!","Main3---Pause!!");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("Stop!", "Main3---Stop!!");
    }


}
