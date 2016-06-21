package com.shagan.eventmanager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.shagan.eventmanager.gurpreet.DataBaseHelper;
import com.shagan.eventmanager.gurpreet.viewUpcomingActivity;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton create, browse, seeAll, exitBT;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        casting();
    }

    public void casting() {
        create = (ImageButton) findViewById(R.id.createBT);
        browse = (ImageButton) findViewById(R.id.locateBT);
        seeAll = (ImageButton) findViewById(R.id.viewBT);
        exitBT = (ImageButton) findViewById(R.id.exitBT);
        exitBT.setOnClickListener(this);
        create.setOnClickListener(this);
        browse.setOnClickListener(this);
        seeAll.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.createBT:
                Intent openCreateActivity = new Intent(MainActivity.this, CreateActivity.class);
                startActivity(openCreateActivity);

                break;
            case R.id.locateBT:
                DataBaseHelper helper = new DataBaseHelper(this);
                SQLiteDatabase db = helper.getWritableDatabase();
                Cursor count = helper.getData();
                if (count.getCount() > 0) {
                    Intent openBrowse = new Intent(MainActivity.this, Browse.class);
                    startActivity(openBrowse);
                } else {
                    Toast.makeText(this, "Couldn't found any record", Toast.LENGTH_SHORT).show();
                }


                break;
            case R.id.viewBT:

                Intent openMyEvents = new Intent(MainActivity.this, viewUpcomingActivity.class);
                startActivity(openMyEvents);
                break;

            case R.id.exitBT:

                finish();

        }
    }
}
