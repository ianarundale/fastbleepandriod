package com.fastbleep.fastbleepnotes;


import android.app.ListFragment;
import android.content.Intent;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesFragment extends ListFragment {

    List<RowItem> rowItems = new ArrayList<RowItem>();
    Boolean mAlreadyLoaded = false;
    CustomAdapter listAdapter;


    public FavoritesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        RowItem item = (RowItem) l.getAdapter().getItem(position);

        Intent intent = new Intent(getActivity(), ViewArticleActivity.class);

        intent.putExtra("id", item.getId());
        intent.putExtra("title", item.getTitle());
        intent.putExtra("content", item.getContent());

        startActivity(intent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (savedInstanceState == null && !mAlreadyLoaded) {
            listAdapter = new CustomAdapter(getActivity(), rowItems);
            setListAdapter(listAdapter);
        }


        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void onStart() {
        setListShown(false);
        mAlreadyLoaded = true;
        getFavoritesFromDatabase();

        super.onStart();
    }

    private void getFavoritesFromDatabase() {
        FastbleepDatabaseController db = new FastbleepDatabaseController(this.getActivity());

        ArrayList<RowItem> articles = new ArrayList<RowItem>(db.getAllFavorites());


        rowItems = articles;

        try {
            listAdapter.updateDataSet(db.getAllFavorites());
            listAdapter.notifyDataSetChanged();
            setListShown(true);

        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }
}
