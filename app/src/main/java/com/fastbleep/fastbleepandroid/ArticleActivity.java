package com.fastbleep.fastbleepandroid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class ArticleActivity extends Activity {

    private EditText urlField;
    private TextView data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        urlField = (EditText) findViewById(R.id.editText1);
        data = (TextView) findViewById(R.id.textView2);
    }

    public void download(View view) {
        String url = urlField.getText().toString();
        new DownloadWebPage(this, data).execute(url);
    }

    public void gotoListView(View view) {
        Intent myIntent = new Intent(this, ListActivity.class);
        String value = new String("hello new activity");
        myIntent.putExtra("key", value); //Optional parameters
        this.startActivity(myIntent);
    }
}
