package com.example.cho.librarydb;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by cho on 2016-02-16.
 */
public class HttpAsyncTaskJson extends AsyncTask<JSONObject,Integer,String>{

    @Override
    protected String doInBackground(JSONObject... params) {
        try {
            JSONObject job = params[0];
            String url = "http://210.118.64.134:3000/getpost/app/activity/use";
            URL obj = new URL(url);
            HttpURLConnection conn = (HttpURLConnection)obj.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.connect();

            OutputStream os = conn.getOutputStream();
            os.write(job.toString().getBytes());
            os.flush();
            os.close();
            String response;

            int responseCode = conn.getResponseCode();
            Log.e("Response Code", String.valueOf(responseCode));
            if(responseCode == HttpURLConnection.HTTP_OK) {

                InputStream is = conn.getInputStream();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] byteBuffer = new byte[1024];
                byte[] byteData = null;
                int nLength = 0;
                while((nLength = is.read(byteBuffer, 0, byteBuffer.length)) != -1) {
                    baos.write(byteBuffer, 0, nLength);
                }
                byteData = baos.toByteArray();

                response = new String(byteData);
                Log.i("response", "DATA response = " + response);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
