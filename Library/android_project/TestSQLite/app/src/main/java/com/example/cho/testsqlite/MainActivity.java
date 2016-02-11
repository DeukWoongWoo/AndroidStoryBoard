package com.example.cho.testsqlite;

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
    EditText productBox;
    EditText quantityBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        idView = (TextView)findViewById(R.id.productID);
        productBox = (EditText)findViewById(R.id.productName);
        quantityBox = (EditText)findViewById(R.id.productQuantity);




        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    public void newProduct(View view){
        DBHandler dbHandler = new DBHandler(this,null,null,1);
        int quantity =
                Integer.parseInt(quantityBox.getText().toString());
        Product product=
                new Product(productBox.getText().toString(),quantity);
        dbHandler.addProduct(product);
        productBox.setText("");
        quantityBox.setText("");
    }

    public void lookupProduct(View view){
        DBHandler dbHandler = new DBHandler(this,null,null,1);

        Product product = dbHandler.findProduct(productBox.getText().toString());
        if(product != null){
            idView.setText(String.valueOf(product.getId()));
            quantityBox.setText(String.valueOf(product.getQuantity()));
        }else{
            idView.setText("No Match Found");
        }
    }

    public void removeProduct(View view){
        DBHandler dbHandler = new DBHandler(this,null,null,1);
        boolean result = dbHandler.deleteProduct(productBox.getText().toString());
        if(result){
            idView.setText("Record Deleted");
            productBox.setText("");
            quantityBox.setText("");
        }else{
            idView.setText("No Match Found");
        }
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
