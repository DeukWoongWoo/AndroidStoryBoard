package com.example.cho.librarydb.Activity;


import android.app.Activity;
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
import android.widget.Button;
import android.widget.TextView;


import com.example.cho.librarydb.HttpAsyncTaskJson;
import com.example.cho.librarydb.LibraryFunction.CatchActivity;
import com.example.cho.librarydb.LibraryFunction.CatchError;
import com.example.cho.librarydb.LibraryFunction.CatchEvent;
import com.example.cho.librarydb.LibraryFunction.CurrentTime;
import com.example.cho.librarydb.LibraryFunction.DataForm;
import com.example.cho.librarydb.LibraryFunction.UserLiporter;
import com.example.cho.librarydb.ManageTable;
import com.example.cho.librarydb.Names;
import com.example.cho.librarydb.Network;
import com.example.cho.librarydb.R;
import com.example.cho.librarydb.Table.ActivityInfo;
import com.example.cho.librarydb.Table.AppInfo;
import com.example.cho.librarydb.Table.ErrorInfo;
import com.example.cho.librarydb.Table.EventInfo;
import com.example.cho.librarydb.Table.ObjectInfo;
import com.example.cho.librarydb.Table.TimeInfo;
import com.example.cho.librarydb.Table.UserInfo;
import com.example.cho.librarydb.TableHandler;


public class MainActivity extends AppCompatActivity {

    UserLiporter userLiporterEvent = new CatchEvent();
    UserLiporter userLiporterActivity = new CatchActivity();
    UserLiporter userLiporterError= new CatchError();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TableHandler tableHandler = new TableHandler(this,null,null,1);
        final TextView textView = (TextView) findViewById(R.id.textView);
        Button bt1 = (Button) findViewById(R.id.button);

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

                userLiporterEvent.get("button");
                //  userLiporterActivity.get("button");
                //startActivity(new Intent(MainActivity.this, Main2Activity.class));

            }
        });
        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tableHandler.checkDB();
            }
        });
        findViewById(R.id.button5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ManageTable obj = new ObjectInfo();
                tableHandler.delete(obj,"button");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        userLiporterEvent.set(this);
        userLiporterActivity.set(this);

    }

    @Override
    protected void onPause() {
        super.onPause();

        userLiporterActivity.get(null);

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
