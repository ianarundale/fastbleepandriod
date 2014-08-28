package com.fastbleep.fastbleepnotes;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;

public class MainActivity extends Activity {
    // Declare Tab Variable
    ActionBar.Tab Tab1, Tab2, Tab3;
    Fragment fragmentTab1 = new NotesFragment();
    Fragment fragmentTab2 = new FavoritesFragment();
    Fragment fragmentTab3 = new MapFragment();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getActionBar();

        // Hide Actionbar Icon
        actionBar.setDisplayShowHomeEnabled(false);

        // Hide Actionbar Title
        actionBar.setDisplayShowTitleEnabled(false);

        // Create Actionbar Tabs
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Set Tab Icon and Titles
        Tab1 = actionBar.newTab().setText("Notes");//setIcon(R.drawable.tab1);
        Tab2 = actionBar.newTab().setText("Favorites");
        Tab3 = actionBar.newTab().setText("Maps");

        // Set Tab Listeners
        Tab1.setTabListener(new TabListener(fragmentTab1));
        Tab2.setTabListener(new TabListener(fragmentTab2));
        Tab3.setTabListener(new TabListener(fragmentTab3));

        // Add tabs to actionbar
        actionBar.addTab(Tab1);
        actionBar.addTab(Tab2);
        actionBar.addTab(Tab3);
    }

    public void loadNotes(View view) {
        Log.i("info", "load notes");
        Intent myIntent = new Intent(this, ViewArticleActivity.class);
        String value = new String("hello new activity");
        myIntent.putExtra("key", value); //Optional parameters
        this.startActivity(myIntent);
    }
}