package com.fastbleep.fastbleepforandroid;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;


public class MapsFragment extends FragmentActivity implements IOnTaskCompleted {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_maps);
        setUpMapIfNeeded();

        getNearbyPlaces();


        // get the current location
    }


    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }


    @Override
    public void onTaskCompleted(JSONObject jsonResponse) {

        Log.d("log", "on task completed");

        try {
            JSONArray results = jsonResponse.getJSONArray("results");

            for (int i = 0; i < results.length(); i++) {
                JSONObject destination = results.getJSONObject(i);

                String name = new String(destination.getString("name"));
                String vicinity = new String(destination.getString("vicinity"));

                JSONObject location = destination.getJSONObject("geometry").getJSONObject("location");

                String lat = new String(location.getString("lat"));
                String lng = new String(location.getString("lng"));

                Log.d("log ", name + " " + lat + " " + lng + " " + vicinity);
            }
        } catch (Exception e) {
            Log.d("log", e.getMessage());
        }
    }

    public void getNearbyPlaces() {

        // Get the current device location
        LocationManager locationManager;
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);   //default

        String provider;

        provider = locationManager.getBestProvider(criteria, false);

        // the last known location of this provider
        Location location = locationManager.getLastKnownLocation(provider);


        String lat;
        String lng;
        if(location != null){
            lat = String.valueOf(location.getLatitude());
            lng = String.valueOf(location.getLongitude());
        } else {
            // default to London
            lat = "51.5033630";
            lng = "-0.1276250";
        }

        // append the current location to the google maps URL
        String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?rankby=distance&types=hospital&sensor=true&key=AIzaSyDdvHoGGT7Joh240-3iwVdub8Y0VMtqzQ8&location=";


        url = url + lat + "," + lng;

        Log.d("log", url);

        new ApiCall(this).execute(url);
    }



}


/**
 * A simple {@link android.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.fastbleep.fastbleepforandroid.MapsFragment} interface
 * to handle interaction events.
 * Use the {@link com.fastbleep.fastbleepforandroid.MapsFragment} factory method to
 * create an instance of this fragment.

 public class MapsFragment extends Fragment implements IOnTaskCompleted {

 ArrayList<Article> Articles = new ArrayList<Article>();
 Boolean mAlreadyLoaded = false;
 ArticleAdapter listAdapter;

 String[] numbers_digits = new String[]{"1", "2", "3", "4", "5", "6", "7",
 "8", "9", "10", "11", "12", "13", "14", "15"};

 private ArrayAdapter<String> adapter;

 @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
 Bundle savedInstanceState) {

 // show google map in the fragment

 // get the users GPS location

 // call the google maps API with GPS data

 // parse the response

 // create markers on the google map

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

 @Override public void onTaskCompleted(JSONObject result) {
 List<Article> notesList = new ArrayList();

 Log.d("ONTaskCompleted", "got the callback! boommmm");

 try {
 JSONArray talkbackJson = result.getJSONArray("talkback");

 for (int i = 0; i < talkbackJson.length(); i++) {
 JSONObject jsonObj = talkbackJson.getJSONObject(i);
 int id = (jsonObj.getInt("id"));
 String title = new String(jsonObj.getString("title"));
 String content = new String(jsonObj.getString("content"));
 Article Article = new Article(id, title, content);

 Log.v("Log", Article.getTitle());

 notesList.add(Article);
 }

 listAdapter.updateDataSet(notesList);
 listAdapter.notifyDataSetChanged();

 setListShown(true);

 } catch (Exception e) {
 e.printStackTrace();
 }

 }
 }
 */


