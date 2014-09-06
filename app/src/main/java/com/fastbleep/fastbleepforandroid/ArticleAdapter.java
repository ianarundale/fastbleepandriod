package com.fastbleep.fastbleepforandroid;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fastbleep.fastbleepforandroid.models.Article;

import java.util.List;

/**
 * Created by ianar on 03/09/2014.
 */
public class ArticleAdapter extends BaseAdapter {


    Context context;
    List<Article> rowItems;

    public ArticleAdapter(Context context, List<Article> rowItems) {
        this.context = context;
        this.rowItems = rowItems;
    }

    public void updateDataSet(List<Article> rowItems){
        this.rowItems = rowItems;
    }

    @Override
    public int getCount() {
        return rowItems.size();
    }

    @Override
    public Object getItem(int position) {
        return rowItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return rowItems.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.list_item, null);
        }

        TextView txtTitle = (TextView) convertView.findViewById(R.id.title);

        Article row_pos = rowItems.get(position);

        // setting the image resource and title
        txtTitle.setText(row_pos.getTitle());

        return convertView;
    }

}
