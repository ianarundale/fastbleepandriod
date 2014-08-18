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

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;


public class ArticleActivity extends Activity implements OnTaskCompleted {

    private EditText urlField;
    private TextView data;

    // getMenuInflator.inflate();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        urlField = (EditText) findViewById(R.id.editText1);
        data = (TextView) findViewById(R.id.textView2);
    }

    public void download(View view) {
        //String url = urlField.getText().toString();
        //String articlesUrl = "http://stage.fastbleep.com/api/revisionnotes/getArticles/16";

        String articlesUrl = "http://stage.fastbleep.com/api/revisionnotes/getArticles/38";

        //new AsyncDownload(this, data).execute(url);

        Log.d("download", "about to call fastbleep api");

        URL url = null;
        try {
            url = new URL(articlesUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        new ApiCall(this).execute(articlesUrl);


        //new FastbleepAPI<ListViewHandler>().getArticlesByCategoryId(16);
    }

    public void gotoListView(View view) {
        Intent myIntent = new Intent(this, HttpActivity.class);
        String value = new String("hello new activity");
        myIntent.putExtra("key", value); //Optional parameters
        this.startActivity(myIntent);
    }

    @Override
    public void onTaskCompleted(JSONObject result) {
        Log.d("ONTaskCompleted", "got the callback! boommmm");

        try {
            JSONArray talkbackJson = result.getJSONArray("talkback");

            for (int i = 0; i < talkbackJson.length(); i++) {
                JSONObject jsonObj = talkbackJson.getJSONObject(i);
                Log.d("d", jsonObj.getString("titlex"));
            }

            /*Iterator<String> keysIterator = talkbackJson.keys();
            while (keysIterator.hasNext()) {
                String keyStr = (String) keysIterator.next();
                String valueStr = talkbackJson.getString(keyStr);

                Log.d("log", keyStr + "  " + valueStr);
            }*/

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /*class ListViewHandler implements IJsonHandler {
        public void handleResult(JSONObject jsonResponse) {
            data.setText(jsonResponse.toString());
        }
    }*/
}
