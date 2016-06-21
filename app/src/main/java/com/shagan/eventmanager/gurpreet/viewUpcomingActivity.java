package com.shagan.eventmanager.gurpreet;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.shagan.eventmanager.Const;
import com.shagan.eventmanager.CustomListAdapter;
import com.shagan.eventmanager.DataClass;
import com.shagan.eventmanager.R;

import java.util.ArrayList;
import java.util.Calendar;


public class viewUpcomingActivity extends AppCompatActivity {
    int year, month, day;
    DataBaseHelper myDB;
    Cursor cursor;
    ListView viewAllLV;
    DataClass dataClass;
    ArrayList<DataClass> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("View");
        setContentView(R.layout.activity_view_all);
        getCurrentDate();

        viewAllLV = (ListView) findViewById(R.id.viewAllLV);

        myDB = new DataBaseHelper(this);
        cursor = myDB.getData();
        int count = cursor.getCount();

        if (count > 0) {
            for (int i = 0; i < count; i++) {
                if (cursor.moveToPosition(i)) {
                    dataClass = new DataClass();
                    dataClass.id = cursor.getInt(cursor.getColumnIndex(Const.ID));
                    dataClass.title = cursor.getString(cursor.getColumnIndex(Const.Title));
                    dataClass.description = cursor.getString(cursor.getColumnIndex(Const.Description));
                    dataClass.time = cursor.getString(cursor.getColumnIndex(Const.Time));
                    dataClass.venue = cursor.getString(cursor.getColumnIndex(Const.Venue)) + " " + cursor.getString(cursor.getColumnIndex(Const.Address));
                    dataClass.date = cursor.getString(cursor.getColumnIndex(Const.Date));
                    dataClass.lat = cursor.getString(cursor.getColumnIndex(Const.Latitude));
                    dataClass.longitude = cursor.getString(cursor.getColumnIndex(Const.Longitude));
                    dataClass.address = cursor.getString(cursor.getColumnIndex(Const.Address));
                    data.add(i, dataClass);

                }
            }
            viewAllLV.setAdapter(new CustomListAdapter(viewUpcomingActivity.this, data));
        } else {
            Toast.makeText(this, "No records found", Toast.LENGTH_SHORT).show();
        }


    }

    public void getCurrentDate() {
        Calendar now = Calendar.getInstance();
        year = now.get(Calendar.YEAR);
        month = now.get(Calendar.MONTH); // Note: zero based!
        day = now.get(Calendar.DAY_OF_MONTH);

    }


}
