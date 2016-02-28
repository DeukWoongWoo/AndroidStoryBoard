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


        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //userLiporterEvent.get("button");//event기능 사용
                //userLiporterError.get("button");//error기능 사용
                Intent intent=new Intent(MainActivity.this,Main2Activity.class);

                intent.putExtra("text","b");
                startActivity(intent);

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
       // userLiporterError.set(this);
       // userLiporterEvent.set(this);//event기능 셋팅
       // userLiporterActivity.set(this);//액티비티 기능셋팅
    }

    @Override
    protected void onPause() {
        super.onPause();
        //userLiporterActivity.get("asd");//액티비티기능 사용
    }


}
