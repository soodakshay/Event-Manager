package com.shagan.eventmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.shagan.eventmanager.gurpreet.DataBaseHelper;


public class LocateActivity extends FragmentActivity {


    String Venue;
    String Lat;
    String Long;
    String Address;
    DataBaseHelper myDB;
    LatLng myLatLong;
    GoogleMap mMaps = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.browse);
        Intent intent = getIntent();
        if (intent != null) {
            Venue = intent.getStringExtra("venue");
            Lat = intent.getStringExtra("lat");
            Long = intent.getStringExtra("long");
            Address = intent.getStringExtra("Address");
            if (mMaps == null) {

                mMaps = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
            }

            GoogleMap finalMMaps = mMaps;
            myLatLong = new LatLng(Double.parseDouble(Lat), Double.parseDouble(Long));
            mMaps.addMarker(new MarkerOptions().position(myLatLong).title(Venue).snippet(Address)).showInfoWindow();
            CameraUpdate cU = CameraUpdateFactory.newLatLngZoom(myLatLong, 13);

            finalMMaps.animateCamera(cU);
        }


    }
}








