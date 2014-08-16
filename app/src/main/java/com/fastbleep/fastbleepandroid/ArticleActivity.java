package com.fastbleep.fastbleepandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.fastbleep.fastbleepandroid.api.AsyncDownload;
import com.fastbleep.fastbleepandroid.api.FastbleepAPI;
import com.fastbleep.fastbleepandroid.api.IJsonHandler;

import org.json.JSONObject;


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
        Log.d("download", "In download method");
        String url = urlField.getText().toString();
        //new AsyncDownload(this, data).execute(url);

        Log.d("download", "about to call fastbleep api");
        new FastbleepAPI<ListViewHandler>().getArticlesByCategoryId(16);
    }

    public void gotoListView(View view) {
        Intent myIntent = new Intent(this, HttpActivity.class);
        String value = new String("hello new activity");
        myIntent.putExtra("key", value); //Optional parameters
        this.startActivity(myIntent);
    }


    class ListViewHandler implements IJsonHandler {
        public void handleResult(JSONObject jsonResponse) {
            data.setText(jsonResponse.toString());
        }
    }
}
