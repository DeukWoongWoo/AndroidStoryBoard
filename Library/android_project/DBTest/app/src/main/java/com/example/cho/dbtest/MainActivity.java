package com.example.cho.dbtest;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView viewUserInfoId;
    EditText editUserInfoId;

    TextView viewAppInfoId;
    EditText editAppInfoId;
    TextView viewAppInfoName;
    EditText editAppInfoName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewUserInfoId = (TextView) findViewById(R.id.ViewUserInfoId);
        editUserInfoId = (EditText) findViewById(R.id.EditUserInfoId);

        viewAppInfoId = (TextView) findViewById(R.id.ViewAppInfoId);
        editAppInfoId = (EditText) findViewById(R.id.EditAppInfoId);
        viewAppInfoName = (TextView) findViewById(R.id.ViewAppInfoName);
        editAppInfoName = (EditText) findViewById(R.id.EditAppInfoName);


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



    public void newAppInfo (View view) {
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        String getName = editAppInfoName.getText().toString();
        String getId=editAppInfoId.getText().toString();
        AppInfo appInfo =
                new AppInfo(getName,getId);

        dbHandler.addAppInfo(appInfo);
        editAppInfoName.setText("");
        editAppInfoId.setText("");
    }

    public void lookupAppInfo (View view) {
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);

        AppInfo appInfo =
                dbHandler.findAppInfo(editAppInfoName.getText().toString());

        if (appInfo != null) {
            viewAppInfoName.setText(String.valueOf(appInfo.getAppName()));
            viewAppInfoId.setText(String.valueOf(appInfo.getUserId()));
        } else {
            viewAppInfoName.setText("No Match Found");
        }
    }

    public void removeAppInfo (View view) {
        MyDBHandler dbHandler = new MyDBHandler(this, null,
                null, 1);

        boolean result = dbHandler.deleteAppInfo(
                editAppInfoName.getText().toString());

        if (result)
        {
            viewAppInfoName.setText("Record Deleted");
            viewAppInfoId.setText("");
        }
        else
            viewAppInfoName.setText("No Match Found");
    }




    public void newUserInfo (View view) {
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        String getId = editUserInfoId.getText().toString();
        UserInfo userInfo =
                new UserInfo(getId);

        dbHandler.addUserInfo(userInfo);
        editUserInfoId.setText("");
    }

    public void lookupUserInfo (View view) {
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);

        UserInfo userInfo = dbHandler.findUserInfo(editUserInfoId.getText().toString());

        if (userInfo != null) {
            viewUserInfoId.setText(String.valueOf(userInfo.getUserId()));
        } else {
            viewUserInfoId.setText("No Match Found");
        }
    }

    public void removeUserInfo (View view) {
        MyDBHandler dbHandler = new MyDBHandler(this, null,
                null, 1);

        boolean result = dbHandler.deleteUserInfo(
                editUserInfoId.getText().toString());

        if (result)
        {
            viewUserInfoId.setText("Record Deleted");
            editUserInfoId.setText("");
        }
        else
            viewUserInfoId.setText("No Match Found");
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
