package com.mukesh.linkedin;

import com.example.linkedin.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import beans.NetworkUpdate;

public class MainActivity extends Activity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    ArrayList<NetworkUpdate> networkUpdates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        networkUpdates = (ArrayList<NetworkUpdate>)getIntent().getSerializableExtra("networkUpdates");
        setContentView(R.layout.activity_main);

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);
//
//        ImageView imageView = (ImageView) expListView.findViewById(R.id.imageView);
//        if (networkUpdates.get(0).getCompanyName().equals("Google")){
//            imageView.setImageResource(R.drawable.google);
//        }else if(networkUpdates.get(0).getCompanyName().equals("LinkedIn")){
//            imageView.setImageResource(R.drawable.logo);
//        }else if(networkUpdates.get(0).getCompanyName().equals("Oracle")){
//            imageView.setImageResource(R.drawable.oracle);
//        }else if(networkUpdates.get(0).getCompanyName().equals("Amazon")){
//            imageView.setImageResource(R.drawable.amazon);
//        }else if(networkUpdates.get(0).getCompanyName().equals("Baidu")){
//            imageView.setImageResource(R.drawable.baidu);
//        }else if(networkUpdates.get(0).getCompanyName().equals("Microsoft")){
//            imageView.setImageResource(R.drawable.microsoft);
//        }else if(networkUpdates.get(0).getCompanyName().equals("Twitter")){
//            imageView.setImageResource(R.drawable.twitter);
//        }else if(networkUpdates.get(0).getCompanyName().equals("Facebook")){
//            imageView.setImageResource(R.drawable.facebook);
//        }else {
//            imageView.setImageResource(R.drawable.logo);
//        }
        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);
    }

    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        for (int i = 0; i < networkUpdates.size(); i++){
            listDataHeader.add(networkUpdates.get(i).getTitle());
            List<String> newString = new ArrayList<String>();
            newString.add(networkUpdates.get(i).getDescription());
            listDataChild.put(listDataHeader.get(i), newString);
        }
        // Adding child data
//        listDataHeader.add("Top 250");
//        listDataHeader.add("Now Showing");
//        listDataHeader.add("Coming Soon..");

        // Adding child data
//        List<String> top250 = new ArrayList<String>();
//        top250.add("The Shawshank Redemptionxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
//
//        List<String> nowShowing = new ArrayList<String>();
//        nowShowing.add("The Conjuring");
//
//
//        List<String> comingSoon = new ArrayList<String>();
//        comingSoon.add("2 Guns");
//
//
//        listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
//        listDataChild.put(listDataHeader.get(1), nowShowing);
//        listDataChild.put(listDataHeader.get(2), comingSoon);
    }
    
}
