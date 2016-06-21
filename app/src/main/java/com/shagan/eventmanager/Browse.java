package com.shagan.eventmanager;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.shagan.eventmanager.gurpreet.DataBaseHelper;

import java.util.ArrayList;


public class Browse extends AppCompatActivity {


    String Venue;
    String Lat;
    String Long;
    String Address;
    DataBaseHelper myDB;
    LatLng myLatLong;
    ArrayList<String> LAT, LONG, VENUE, ADDR , title;

    static final String LOG_TAG = "CHECk";
    GoogleMap mMaps = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.browse);
        LAT = new ArrayList<String>();
        ADDR = new ArrayList<String>();
        LONG = new ArrayList<String>();
        VENUE = new ArrayList<String>();
title = new ArrayList<>();

        myDB = new DataBaseHelper(this);

        int flag = myDB.checkBrowse();
        if (flag == 1) {
            Toast.makeText(this, "Could'nt found any event. Blank Map is shown", Toast.LENGTH_LONG).show();

        } else {


            if (mMaps == null) {

                mMaps = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();


            } else {

            }

            Cursor AllInOne = myDB.getData();
           /* for (AllInOne.moveToFirst(); !AllInOne.isAfterLast(); AllInOne.moveToNext()) {*/
            for(int i = 0 ; i < AllInOne.getCount() ; i++){
                AllInOne.moveToPosition(i);
                title .add(AllInOne.getString(AllInOne.getColumnIndex(Const.Title)));
                Venue = AllInOne.getString(AllInOne.getColumnIndex(Const.Venue));
                Lat = AllInOne.getString(AllInOne.getColumnIndex(Const.Latitude));
                Long = AllInOne.getString(AllInOne.getColumnIndex(Const.Longitude));
                Address = AllInOne.getString(AllInOne.getColumnIndex(Const.Address));
                LAT.add(Lat);
                LONG.add(Long);
                VENUE.add(Venue);
                ADDR.add(Address);
                GoogleMap finalMMaps = mMaps;
                if(LAT.get(i).equals("0") || LONG.get(i).equals("0"))
                {
                    Toast.makeText(this , "Location Not Set For " + title.get(i), Toast.LENGTH_SHORT).show();
                }else {
                    myLatLong = new LatLng(Double.parseDouble(Lat), Double.parseDouble(Long));
                    mMaps.addMarker(new MarkerOptions().position(myLatLong).title(Venue).snippet(Address)).showInfoWindow();
                    CameraUpdate cU = CameraUpdateFactory.newLatLngZoom(myLatLong, 13);
                    finalMMaps.animateCamera(cU);
                }




            }
        }
    }
}








