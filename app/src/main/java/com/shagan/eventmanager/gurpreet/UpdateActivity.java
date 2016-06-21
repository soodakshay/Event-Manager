package com.shagan.eventmanager.gurpreet;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.shagan.eventmanager.Const;
import com.shagan.eventmanager.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class UpdateActivity extends ActionBarActivity implements View.OnClickListener, View.OnFocusChangeListener {
    private TextView tvTitle, tvDescription, tvTime, tvVenue;

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            switch (v.getId()) {


                case R.id.etTime:
                    myTimePicker.show();
                    datePicker.show();
                    break;
            }
        }
    }

    Button createBT, placePickerBT;
    EditText title, desc, time, Venue;
    TextView venueInfo;
    PlacePicker.IntentBuilder intentBuilder;
    private static final int PLACE_PICKER_REQUEST = 1;
    public LatLng gotLatLng;
    String placeName;
    String plName;
    String Addr;
    Double gotLat, gotLong;
    String LAT, LONG;
    DataBaseHelper myDB;
    public static final String LOG_TAG = "UpdateActivity";
    String minuteFixer;
    String GotTime = " ";
    String GotDate = " ";

    DatePickerDialog datePicker;
    TimePickerDialog myTimePicker;
    SimpleDateFormat dateFormatter;
    String TimeForDB;
    String DateForDB;
    int DayOfMonth;
    int MonthName;
    int id;
    static boolean flag = false;
    boolean flag_date = false, flag_venue = false;
    Cursor cursor;
    DataBaseHelper helper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create);
        Intent intent = getIntent();
        if (intent != null) {
            id = intent.getIntExtra("id", 0);
        }
        helper = new DataBaseHelper(UpdateActivity.this);
        SQLiteDatabase db = helper.getWritableDatabase();
        cursor = helper.getSpecificData(id);
        dateFormatter = new SimpleDateFormat("EEEE dd , MMM", Locale.US);


        showDate();
        showTime();
        casting();
    }

    public void casting() {
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvDescription = (TextView) findViewById(R.id.tvDescription);
        tvTime = (TextView) findViewById(R.id.tvTime);
        tvVenue = (TextView) findViewById(R.id.tvVenue);
        createBT = (Button) findViewById(R.id.createBT);
        Venue = (EditText) findViewById(R.id.etVenue);
        title = (EditText) findViewById(R.id.etTitle);
        desc = (EditText) findViewById(R.id.etDescription);
        time = (EditText) findViewById(R.id.etTime);
        placePickerBT = (Button) findViewById(R.id.placePickerBT);
        Venue.setTextColor(Color.BLACK);
        title.setTextColor(Color.BLACK);
        desc.setTextColor(Color.BLACK);
        time.setTextColor(Color.BLACK);

        tvTitle.setTextColor(Color.WHITE);
        tvDescription.setTextColor(Color.WHITE);
        tvTime.setTextColor(Color.WHITE);

        tvVenue.setTextColor(Color.WHITE);
        time.setInputType(InputType.TYPE_NULL);

        time.setInputType(InputType.TYPE_NULL);

        createBT.setText("Save");
        Venue.setOnFocusChangeListener(this);
        time.setOnFocusChangeListener(this);
        createBT.setOnClickListener(this);
        placePickerBT.setOnClickListener(this);
        Venue.setInputType(InputType.TYPE_NULL);
        if (cursor != null && cursor.moveToFirst()) {
            Venue.setText(cursor.getString(3));

            title.setText(cursor.getString(1));
            desc.setText(cursor.getString(2));
            time.setText(cursor.getString(7) + " at " + cursor.getString(8));
        }


        title.setHint("Title Goes Here");
        desc.setHint("Description Goes Here");
        time.setHint("Click to select Time & Date");

    }

    @Override
    public void onClick(View v) {

        switch (v.getId())

        {
            case R.id.placePickerBT: {


                try {
                    intentBuilder = new PlacePicker.IntentBuilder();
                    Intent intent = intentBuilder.build(UpdateActivity.this);
                    startActivityForResult(intent, PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    Toast.makeText(UpdateActivity.this, "Google Play Services is not available.",
                            Toast.LENGTH_LONG)
                            .show();
                }

                break;
            }
            case R.id.etVenue:
                try {
                    intentBuilder = new PlacePicker.IntentBuilder();
                    Intent intent = intentBuilder.build(UpdateActivity.this);
                    startActivityForResult(intent, PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    Toast.makeText(UpdateActivity.this, "Google Play Services is not available.",
                            Toast.LENGTH_LONG)
                            .show();
                }
                break;

            case R.id.etTime:
                myTimePicker.show();
                datePicker.show();
                break;
            case R.id.createBT:

                String TITLE = title.getText().toString();
                String DESC = desc.getText().toString();
                String eventTime = time.getText().toString();
                String PLACE = Venue.getText().toString();
                if (TITLE.equals(" ") || DESC.equals(" ") || PLACE.equals(" ")) {
                    Toast.makeText(this, "All fields are mandatory", Toast.LENGTH_LONG).show();
                } else {
                    myDB = new DataBaseHelper(this);

                    if (flag_date && flag_venue) {
                        myDB.updateDB(id, TITLE, DESC, PLACE, LAT, LONG, Addr, DateForDB, TimeForDB, DayOfMonth, MonthName);
                    } else if (flag_date && !flag_venue) {
                        myDB.updateDB(id, TITLE, DESC, cursor.getString(cursor.getColumnIndex(Const.Venue)), cursor.getString(cursor.getColumnIndex(Const.Latitude)), cursor.getString(cursor.getColumnIndex(Const.Longitude)), cursor.getString(cursor.getColumnIndex(Const.Address)), DateForDB, TimeForDB, DayOfMonth, MonthName);
                    } else if (!flag_date && flag_venue) {
                        myDB.updateDB(id, TITLE, DESC, PLACE, LAT, LONG, Addr, cursor.getString(cursor.getColumnIndex(Const.Date)), cursor.getString(cursor.getColumnIndex(Const.Time)), cursor.getInt(cursor.getColumnIndex(Const.Day_Of_Month)), cursor.getInt(cursor.getColumnIndex(Const.Month)));
                    } else {
                        myDB.updateDB(id, TITLE, DESC, cursor.getString(cursor.getColumnIndex(Const.Venue)), cursor.getString(cursor.getColumnIndex(Const.Latitude)), cursor.getString(cursor.getColumnIndex(Const.Longitude)), cursor.getString(cursor.getColumnIndex(Const.Address)), cursor.getString(cursor.getColumnIndex(Const.Date)), cursor.getString(cursor.getColumnIndex(Const.Time)), cursor.getInt(cursor.getColumnIndex(Const.Day_Of_Month)), cursor.getInt(cursor.getColumnIndex(Const.Month)));
                    }

                }
                finish();
                break;


        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                flag_venue = true;
                Place place = PlacePicker.getPlace(data, this);
                gotLatLng = place.getLatLng();
                plName = (String) place.getName();
                Addr = (String) place.getAddress();
                gotLat = gotLatLng.latitude;
                gotLong = gotLatLng.longitude;
                LAT = String.valueOf(gotLat);
                LONG = String.valueOf(gotLong);
                placeName = String.format("Place: %s", place.getName());
                Toast.makeText(this, placeName, Toast.LENGTH_LONG).show();
                Venue.setText(plName);

            }
        }
    }

    public void showDate() {
        Calendar cal = Calendar.getInstance();
        datePicker = new DatePickerDialog(this, new OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {


                Calendar newDate = Calendar.getInstance();

                newDate.set(year, monthOfYear, dayOfMonth);
                DayOfMonth = dayOfMonth;
                MonthName = monthOfYear;
                MonthName = MonthName + 1;
                monthOfYear = monthOfYear + 1;

                DateForDB = String.valueOf(dayOfMonth) + "/" + String.valueOf(monthOfYear) + "/" + String.valueOf(year);
                GotDate = dateFormatter.format(newDate.getTime()).toString();


            }
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_WEEK));
        flag = true;
    }

    public void showTime() {
        if (flag == true) {

            final Calendar calender = Calendar.getInstance();
            myTimePicker = new TimePickerDialog(this, new OnTimeSetListener() {

                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {


                    Calendar newTime = Calendar.getInstance();

                    calender.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    calender.set(Calendar.MINUTE, minute);

                    for (int i = 0; i <= 9; i++) {
                        if (minute == i) {
                            minuteFixer = "0" + String.valueOf(minute);
                            TimeForDB = String.valueOf(hourOfDay) + " : " + minuteFixer;
                        }

                    }
                    if (minute > 9) {
                        minuteFixer = String.valueOf(minute);
                    }
                    TimeForDB = String.valueOf(hourOfDay) + " : " + minuteFixer;
                    GotTime = GotDate + " At " + String.valueOf(hourOfDay) + ":" + minuteFixer;
                    time.setText(GotTime);
                    flag_date = true;

                }
            }, calender.get((Calendar.HOUR_OF_DAY)), calender.get(Calendar.MINUTE), true);
        }

    }
}
