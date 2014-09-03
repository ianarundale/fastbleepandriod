package com.fastbleep.fastbleepnotes;

import android.app.LauncherActivity;
import android.app.ListActivity;
import android.app.ListFragment;
import android.content.Intent;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Fragment;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NotesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NotesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotesFragment extends ListFragment implements OnTaskCompleted {

    ArrayList<String> numbers_text = new ArrayList<String>();
    ArrayList<RowItem> rowItems = new ArrayList<RowItem>();
    Boolean mAlreadyLoaded = false;
    CustomAdapter listAdapter;

    String[] numbers_digits = new String[]{"1", "2", "3", "4", "5", "6", "7",
            "8", "9", "10", "11", "12", "13", "14", "15"};

    private ArrayAdapter<String> adapter;

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        RowItem item = (RowItem) l.getAdapter().getItem(position);

        //new CustomToast(getActivity(), item.getTitle());
        //new CustomToast(getActivity(), Integer.toString(item.getId()));

        Intent viewArticle = new Intent();
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

            download();
            listAdapter = new CustomAdapter(getActivity(), rowItems);
            setListAdapter(listAdapter);
        }


        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void onStart() {
        if (!mAlreadyLoaded) {
            setListShown(false);
            mAlreadyLoaded = true;
        }

        super.onStart();
    }

    public void download() {

        String articlesUrl = "http://stage.fastbleep.com/api/revisionnotes/getArticles/38";

        Log.d("download", "about to call fastbleep api");

        URL url = null;
        try {
            url = new URL(articlesUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        new ApiCall(this).execute(articlesUrl);
    }

    @Override
    public void onTaskCompleted(JSONObject result) {
        List<RowItem> notesList = new ArrayList();

        Log.d("ONTaskCompleted", "got the callback! boommmm");

        try {
            JSONArray talkbackJson = result.getJSONArray("talkback");

            for (int i = 0; i < talkbackJson.length(); i++) {
                JSONObject jsonObj = talkbackJson.getJSONObject(i);
                int id = (jsonObj.getInt("id"));
                String title = new String(jsonObj.getString("title"));
                String content = new String(jsonObj.getString("content"));
                RowItem rowItem = new RowItem(id, title, content);

                Log.v("Log", rowItem.getTitle());

                notesList.add(rowItem);
            }

            listAdapter.updateDataSet(notesList);
            listAdapter.notifyDataSetChanged();

            setListShown(true);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

