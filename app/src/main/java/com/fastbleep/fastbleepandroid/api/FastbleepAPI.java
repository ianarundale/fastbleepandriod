package com.fastbleep.fastbleepandroid.api;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class FastbleepAPI<IJsonHandler> {
    private Context context;
    private IJsonHandler handler;
    private String baseUrl = "http://stage.fastbleep.com/api/";

    public void getArticlesByCategoryId(int id) {
        String url = baseUrl + "/revisionnotes/getArticles/" + Integer.toString(id);
        url = "http://stage.fastbleep.com/api/revisionnotes/getArticles/16";
        new HttpAsyncTask().execute(url);
    }

    protected String GET(String urlString) {
        try {
            URL url = new URL(urlString);
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
            String response = "";
            while ((data = reader.readLine()) != null) {
                Log.d("Reading new line", "new GET data line");
                Log.d("Reading new line data", data.toString());
                response += data + "\n";
            }

            return response;
        } catch (Exception e) {
            // TODO: improve error handling here
            return new String();
        }
    }

    private class HttpAsyncTask<IJsonHandler> extends AsyncTask<String, Void, String> {

        IJsonHandler handler;

        @Override
        protected String doInBackground(String... args) {
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
                String webPage = "";
                while ((data = reader.readLine()) != null) {
                    webPage += data + "\n";
                }
                return webPage;
            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }

            //return GET(urls[0]);
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String jsonResponse) {
            Log.d("result", jsonResponse.toString());


            JSONObject jsonResponse2 = new JSONObject();

            try {
                jsonResponse2 = new JSONObject(jsonResponse);
            } catch (Exception e) {
                Log.e("Exception", e.toString());
            }


            //handler.handleResult(jsonResponse2);

            // TODO: do something here to inform the activity it need to update its view

            //EditText urlField = (EditText) context.findViewById(R.id.editText1);
            //etResponse.setText(result);
        }
    }

}

