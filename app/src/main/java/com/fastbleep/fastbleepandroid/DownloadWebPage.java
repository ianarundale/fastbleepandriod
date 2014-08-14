package com.fastbleep.fastbleepandroid;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadWebPage extends AsyncTask <String, Void, String> {

    private TextView dataField;
    private Context context;

    public DownloadWebPage(Context context, TextView dataField) {
        this.context = context;
        this.dataField = dataField;
    }


    //check Internet conenction.
    private void checkInternetConenction() {
        ConnectivityManager check = (ConnectivityManager) this.context.getSystemService(context.CONNECTIVITY_SERVICE);
        if (check != null) {
            NetworkInfo[] info = check.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        Toast.makeText(context, "Internet is connected",
                                Toast.LENGTH_SHORT).show();
                    }

        } else {
            Toast.makeText(context, "not conencted to internet",
                    Toast.LENGTH_SHORT).show();
        }
    }


    protected void onPreExecute() {
        checkInternetConenction();
    }

    @Override
    protected String doInBackground(String... arg0) {
        try {
            String link = (String) arg0[0];
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
    }

    @Override
    protected void onPostExecute(String result) {
        this.dataField.setText(result);
    }
}