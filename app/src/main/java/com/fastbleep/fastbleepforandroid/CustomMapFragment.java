package com.fastbleep.fastbleepforandroid;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.plus.PlusOneButton;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * A fragment with a Google +1 button.
 * Activities that contain this fragment must implement the
 * {@link CustomMapFragment} interface
 * to handle interaction events.
 * Use the {@link CustomMapFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class CustomMapFragment extends MapFragment implements IOnTaskCompleted {

    double lat;
    double lng;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getNearbyPlaces();
    }

    public void getNearbyPlaces() {

        // Get the current device location
        LocationManager locationManager;
        locationManager = (LocationManager) this.getActivity().getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);   //default

        String provider;

        provider = locationManager.getBestProvider(criteria, false);

        // the last known location of this provider
        Location location = locationManager.getLastKnownLocation(provider);


        if(location != null){
            lat = location.getLatitude();
            lng = location.getLongitude();
        } else {
            // default to London
            lat = 51.5033630;
            lng = -0.1276250;
        }

        // append the current location to the google maps URL
        String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?rankby=distance&types=hospital&sensor=true&key=<key>&location=";


        url = url + String.valueOf(lat) + "," + String.valueOf(lng);

        Log.d("log", url);

        new ApiCall(this).execute(url);
    }

    @Override
    public void onTaskCompleted(JSONObject jsonResponse) {

        Log.d("log", "on task completed");

        try {
            JSONArray results = jsonResponse.getJSONArray("results");

            getMap().moveCamera( CameraUpdateFactory.newLatLng(new LatLng(lat, lng)) );
            getMap().animateCamera(CameraUpdateFactory.zoomTo(12));



            for (int i = 0; i < results.length(); i++) {
                JSONObject destination = results.getJSONObject(i);

                String name = new String(destination.getString("name"));
                String vicinity = new String(destination.getString("vicinity"));

                JSONObject location = destination.getJSONObject("geometry").getJSONObject("location");

                Double lat = new Double(location.getDouble("lat"));
                Double lng = new Double(location.getDouble("lng"));

                Log.d("log ", name + " " + lat + " " + lng + " " + vicinity);


                getMap().getUiSettings().setZoomControlsEnabled(false);
                getMap().addMarker(new MarkerOptions().position(new LatLng(lat, lng)).title(name));
            }

        } catch (Exception e) {
            Log.d("log", e.getMessage());
        }
    }

}
