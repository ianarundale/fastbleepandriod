package com.fastbleep.fastbleepnotes;

import android.app.ListActivity;
import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Fragment;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


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

    String[] numbers_digits = new String[]{"1", "2", "3", "4", "5", "6", "7",
            "8", "9", "10", "11", "12", "13", "14", "15"};

    private ArrayAdapter<String> adapter;

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        new CustomToast(getActivity(), numbers_digits[(int) id]);
        download();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        this.adapter = new ArrayAdapter<String>(inflater.getContext(), android.R.layout.simple_list_item_1, numbers_text);
        setListAdapter(this.adapter);

        download();

        return super.onCreateView(inflater, container, savedInstanceState);
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
        Log.d("ONTaskCompleted", "got the callback! boommmm");

        numbers_text.clear();

        try {
            JSONArray talkbackJson = result.getJSONArray("talkback");

            for (int i = 0; i < talkbackJson.length(); i++) {
                JSONObject jsonObj = talkbackJson.getJSONObject(i);
                Log.d("d", jsonObj.getString("title"));

                numbers_text.add(jsonObj.getString("title"));
            }

            this.adapter.notifyDataSetChanged();


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


}
