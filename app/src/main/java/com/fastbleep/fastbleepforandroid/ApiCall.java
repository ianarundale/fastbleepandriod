package com.fastbleep.fastbleepforandroid;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiCall extends AsyncTask <String, Void, String> {
    private IOnTaskCompleted listener;
    private String result;

    public ApiCall(IOnTaskCompleted listener) {
        this.listener = listener;
    }

    protected void onProgressUpdate(Integer... progress) {
        // update progress here
    }

    // called after doInBackground finishes
    protected void onPostExecute(String result) {
        //Log.v("result, yay!", this.result);
        // put result into a json object
        try {
            JSONObject jsonObject = new JSONObject(this.result);
            // call callback
            listener.onTaskCompleted(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected String doInBackground(String... args) {
        Log.d("bg", "doinBG");
        String webPage = "";

        try {
            String link = (String) args[0];
            URL url = new URL(link);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader
                    (is, "UTF-8"));
            String data = null;
            while ((data = reader.readLine()) != null) {
                webPage += data + "\n";
                Log.d("bg", "getting data " + data);
            }
            this.result = webPage;
            return webPage;
        } catch (Exception e) {
            return new String("Exception: " + e.getMessage());
        }
    }
}