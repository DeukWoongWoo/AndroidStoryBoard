package com.example.cho.realdbtest;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView idView;
    EditText userInfoBox;

    TextView appNameView;
    TextView appUserIdView;
    EditText appNameBox;
    EditText appUserIdBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        idView = (TextView) findViewById(R.id.ViewUserId);
        userInfoBox = (EditText) findViewById(R.id.EditUserId);

        appNameView = (TextView) findViewById(R.id.ViewAppName);
        appNameBox = (EditText) findViewById(R.id.AppName);
        appUserIdView = (TextView) findViewById(R.id.ViewUserId2);
        appUserIdBox = (EditText) findViewById(R.id.UserId);
        

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
        MyDBHandler2 dbHandler = new MyDBHandler2(this, null, null, 1);
        String getName = appNameBox.getText().toString();
        String getId=appUserIdBox.getText().toString();
        AppInfo appInfo =
                new AppInfo(getName,getId);

        dbHandler.addAppInfo(appInfo);
        appNameBox.setText("");
        appUserIdBox.setText("");
    }

    public void lookupAppInfo (View view) {
        MyDBHandler2 dbHandler = new MyDBHandler2(this, null, null, 1);

        AppInfo appInfo =
                dbHandler.findAppInfo(appNameBox.getText().toString());

        if (appInfo != null) {
            appNameView.setText(String.valueOf(appInfo.getAppName()));
            appUserIdView.setText(String.valueOf(appInfo.getUserId()));
        } else {
            appNameView.setText("No Match Found");
        }
    }

    public void removeAppInfo (View view) {
        MyDBHandler2 dbHandler = new MyDBHandler2(this, null,
                null, 1);

        boolean result = dbHandler.deleteAppInfo(
                appNameBox.getText().toString());

        if (result)
        {
            appNameView.setText("Record Deleted");
            appUserIdView.setText("");
        }
        else
            appNameView.setText("No Match Found");
    }


    
    public void newUserInfo (View view) {
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        String getId = userInfoBox.getText().toString();
        UserInfo userInfo =
                new UserInfo(getId);

        dbHandler.addUserInfo(userInfo);
        userInfoBox.setText("");
    }

    public void lookupUserInfo (View view) {
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);

        UserInfo userInfo = dbHandler.findUserInfo(userInfoBox.getText().toString());

        if (userInfo != null) {
            idView.setText(String.valueOf(userInfo.getUserId()));
        } else {
            idView.setText("No Match Found");
        }
    }

    public void removeUserInfo (View view) {
        MyDBHandler dbHandler = new MyDBHandler(this, null,
                null, 1);

        boolean result = dbHandler.deleteUserInfo(
                userInfoBox.getText().toString());

        if (result)
        {
            idView.setText("Record Deleted");
            userInfoBox.setText("");
        }
        else
            idView.setText("No Match Found");
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
