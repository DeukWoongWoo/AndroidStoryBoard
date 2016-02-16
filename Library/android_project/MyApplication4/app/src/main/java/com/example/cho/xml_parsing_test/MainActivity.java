package com.example.cho.xml_parsing_test;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.cho.xml_parsing_test.Librarys.CatchActivity;
import com.example.cho.xml_parsing_test.Librarys.CatchEvent;
import com.example.cho.xml_parsing_test.Librarys.UserLiporter;


public class MainActivity extends AppCompatActivity {

    UserLiporter activityLiporter = new CatchActivity();
    UserLiporter eventLiporter = new CatchEvent();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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


        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MainActivity2.class));
            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventLiporter.get(null);
                startActivity(new Intent(MainActivity.this, MainActivity3.class));
            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.i("Start!", "Main---Start!");
    }

    @Override
    protected void onResume() {
        super.onResume();c
        Log.i("Resume!", "Main---Resume!");
        activityLiporter.set(this);
        eventLiporter.set(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("Pause!","Main---Pause!!");
        activityLiporter.get(null);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("Stop!","Main---Stop!!");
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
