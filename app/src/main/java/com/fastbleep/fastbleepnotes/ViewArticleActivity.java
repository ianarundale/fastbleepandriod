package com.fastbleep.fastbleepnotes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;


public class ViewArticleActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String content = intent.getStringExtra("content");
        content = "<html><head><style> body { font-family: \\\"Helvetica Neue\\\",Helvetica,Arial,sans-serif; font-size: 16px;} a { color: black; text-decoration: none; } img{ max-width: 100% } </style></head><body>" + content + "</body></html>";
        setContentView(R.layout.activity_view_article);
        getActionBar().setTitle(title);
        WebView webView = (WebView) this.findViewById(R.id.webView);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadDataWithBaseURL(null, content, "text/html", "utf-8", "");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.view_article, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_add_fav) {
            // TODO: Save the article into a NoSQL database at this point please :)
            Toast.makeText(this, "Favorite added", Toast.LENGTH_SHORT)
                    .show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
