package com.fastbleep.fastbleepforandroid.fragments;


import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.fastbleep.fastbleepforandroid.ArticleAdapter;
import com.fastbleep.fastbleepforandroid.FastbleepDatabaseController;
import com.fastbleep.fastbleepforandroid.ViewArticleActivity;
import com.fastbleep.fastbleepforandroid.models.Article;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link android.app.Fragment} subclass.
 */
public class FavoritesFragment extends ListFragment {

    List<Article> rowItems = new ArrayList<Article>();
    Boolean mAlreadyLoaded = false;
    ArticleAdapter listAdapter;


    public FavoritesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        Article item = (Article) l.getAdapter().getItem(position);

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
            listAdapter = new ArticleAdapter(getActivity(), rowItems);
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

        ArrayList<Article> articles = new ArrayList<Article>(db.getAllFavorites());


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
