package com.fastbleep.fastbleepandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.fastbleep.fastbleepandroid.api.AsyncDownload;


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
        new AsyncDownload(this, data).execute(url);

        // use return

        //new FastbleepAPI().getArticlesByCategoryId(6);
    }

    public void gotoListView(View view) {
        Intent myIntent = new Intent(this, ListActivity.class);
        String value = new String("hello new activity");
        myIntent.putExtra("key", value); //Optional parameters
        this.startActivity(myIntent);
    }

    // function parseJson
}
