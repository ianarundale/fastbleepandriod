package com.fastbleep.fastbleepandroid.api;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fastbleep.fastbleepandroid.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ianar on 14/08/2014.
 */
public class FastbleepAPI <IJsonHandler> {
    private Context context;
    private com.fastbleep.fastbleepandroid.api.IJsonHandler handler;
    private String baseUrl = "http://stage.fastbleep.com/api/";


    public String[] getArticlesByCategoryId(int id) {
        String url = baseUrl + "/revisionnotes/getArticles/" + Integer.toString(id);

        //new AsyncHttpRequest(context).execute(url);
        new HttpAsyncTask(context).execute(url);
        JSONArray result = new JSONArray();
        String[] myStringArray = new String[3];

        return myStringArray;
    }

    protected JSONObject GET(String urlString) {
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
                response += data + "\n";
            }

            JSONObject jsonResponse = new JSONObject(response);
            return jsonResponse;
        } catch (Exception e) {
            // TODO: improve error handling here
            return new JSONObject();
        }
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(String... urls) {
            return GET(urls[0]);
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(JSONObject jsonResponse) {
            Log.d("result", jsonResponse.toString());
            Toast.makeText(context, "Received!", Toast.LENGTH_LONG).show();

            handler.handleResult(jsonResponse);

            // TODO: do something here to inform the activity it need to update its view

            //EditText urlField = (EditText) context.findViewById(R.id.editText1);
            //etResponse.setText(result);
        }
    }

}

