package com.fastbleep.fastbleepandroid.api;

import android.content.Context;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by ianar on 14/08/2014.
 */
public class FastbleepAPI {

    String baseUrl = "http://stage.fastbleep.com/api/";


    public String[] getArticlesByCategoryId(int id) {
        String requestUrl = baseUrl + "/revisionnotes/getArticles/" + Integer.toString(id);

       new AsyncDownload<ListViewHandler>().execute(requestUrl);

        JSONArray result = new JSONArray();
        String[] myStringArray = new String[3];

        return myStringArray;
    }

    // constructArticleListViewUICallback(JSON)

    // getArticle

    //constructArticleUICallback(JSON)
}

class ListViewHandler implements IJsonHandler {
    public void handleResult(){

    }
}


class ViewArticleHandler implements IJsonHandler {

}