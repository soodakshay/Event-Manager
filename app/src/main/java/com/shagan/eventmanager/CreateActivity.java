package com.shagan.eventmanager;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
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
import com.shagan.eventmanager.gurpreet.DataBaseHelper;
import com.shagan.eventmanager.gurpreet.NotificationService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class CreateActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {
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
    public static final String LOG_TAG = "CreateActivity";
    String minuteFixer;
    String GotTime = " ";
    String GotDate = " ";
    TextView tvTitle, tvDescription, tvTime, tvVenue;
    DatePickerDialog datePicker;
    TimePickerDialog myTimePicker;
    SimpleDateFormat dateFormatter;
    String TimeForDB;
    String DateForDB;
    int DayOfMonth;
    int MonthName;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    static boolean flag = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create);
        dateFormatter = new SimpleDateFormat("EEEE dd , MMM", Locale.US);
        preferences= getSharedPreferences("notification" , MODE_PRIVATE);


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


        Venue.setOnFocusChangeListener(this);
        time.setOnFocusChangeListener(this);
        createBT.setOnClickListener(this);
        placePickerBT.setOnClickListener(this);
        Venue.setInputType(InputType.TYPE_NULL);
        Venue.setText(" ");
        title.setText(" ");
        desc.setText(" ");
        time.setText(" ");

    }

    @Override
    public void onClick(View v) {

        switch (v.getId())

        {
            case R.id.placePickerBT: {
                try {
                    intentBuilder = new PlacePicker.IntentBuilder();
                    Intent intent = intentBuilder.build(CreateActivity.this);
                    startActivityForResult(intent, PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    Toast.makeText(CreateActivity.this, "Google Play Services is not available.",
                            Toast.LENGTH_LONG)
                            .show();
                }

                break;
            }
            case R.id.etVenue:
                try {
                    intentBuilder = new PlacePicker.IntentBuilder();
                    Intent intent = intentBuilder.build(CreateActivity.this);
                    startActivityForResult(intent, PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    Toast.makeText(CreateActivity.this, "Google Play Services is not available.",
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
                if (TITLE.equals(" ") || DESC.equals(" ") || GotTime.equals(" ")) {
                    Toast.makeText(this, "All fields are mandatory", Toast.LENGTH_LONG).show();
                } else {
                    if(PLACE.isEmpty() || PLACE == " " || PLACE.equals(" "))
                    {
                        PLACE = "Not Set";
                        LAT = "0";
                        LONG = "0";
                        Addr = "Not Set";

                    }
                    myDB = new DataBaseHelper(this);


                    int flag = myDB.InsertValues(TITLE, DESC, PLACE, LAT, LONG, Addr, DateForDB, TimeForDB, DayOfMonth, MonthName);


                    if (flag == 1) {

                        Log.e(LOG_TAG, TITLE);
                        Log.e(LOG_TAG, DESC);
                        Log.e(LOG_TAG, PLACE);
                        Log.e(LOG_TAG, LAT);
                        Log.e(LOG_TAG, LONG);
                        Log.e(LOG_TAG, Addr);
                        Log.e(LOG_TAG, DateForDB);
                        Log.e(LOG_TAG, TimeForDB);
                        Log.e(LOG_TAG, String.valueOf(DayOfMonth));
                        Log.e(LOG_TAG, String.valueOf(MonthName));

                        Toast.makeText(CreateActivity.this, "Reminder created sucessfully!", Toast.LENGTH_LONG).show();
                        Calendar calendar;
                        Log.e("SERVICE", TITLE + " " + Venue + " " + " " + DateForDB + " " + TimeForDB);



                        /*AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                        Intent notificationIntent = new Intent("android.media.action.DISPLAY_NOTIFICATION");
                        notificationIntent.addCategory("android.intent.category.DEFAULT");
                        notificationIntent.putExtra("title", TITLE);
                        notificationIntent.putExtra("venue", PLACE);
                        notificationIntent.putExtra("date", DateForDB);
                        notificationIntent.putExtra("time", TimeForDB);

                        PendingIntent broadcast = PendingIntent.getBroadcast(this, 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                        calendar = Calendar.getInstance();
                        calendar.set(Calendar.YEAR, 2016);
                        calendar.set(Calendar.MONTH, 04);
                        calendar.set(Calendar.DAY_OF_MONTH, 0);
                        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(TimeForDB.split(":")[0].trim()));
                        calendar.set(Calendar.MINUTE, Integer.parseInt(TimeForDB.split(":")[1].trim()));
                        calendar.set(Calendar.SECOND, 0);
                        Log.e(("TIME"), ""+calendar.getTimeInMillis());
                        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), broadcast);*/


                        calendar = Calendar.getInstance();
                        calendar.set(Calendar.YEAR, 2016);
                        calendar.set(Calendar.MONTH, MonthName);
                        calendar.set(Calendar.DAY_OF_MONTH, DayOfMonth);
                        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(TimeForDB.split(":")[0].trim()));
                        calendar.set(Calendar.MINUTE, Integer.parseInt(TimeForDB.split(":")[1].trim()));
                        calendar.set(Calendar.SECOND, 0);

                        long then = calendar.getTimeInMillis();

                        Calendar nowCal = Calendar.getInstance();
                        long now = nowCal.getTimeInMillis();


                        Intent intent = new Intent(this, NotificationService.class);
                        intent.putExtra("then" , then);
                        intent.putExtra("now" , now);
                        intent.putExtra("title" , TITLE);
                        intent.putExtra("venue" , placeName);
                        intent.putExtra("time" , TimeForDB);
                        intent.putExtra("date" , DateForDB);
                        startService(intent);
                        editor = preferences.edit();
                        editor.putBoolean("notification_check" , true).commit();
                        finish();
                    } else {
                        Toast.makeText(CreateActivity.this, "Something went wrong.", Toast.LENGTH_LONG).show();
                    }
                }
                break;


        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                gotLatLng = place.getLatLng();
                plName = (String) place.getName();
                Addr = (String) place.getAddress();
                gotLat = gotLatLng.latitude;
                gotLong = gotLatLng.longitude;
                LAT = String.valueOf(gotLat);
                LONG = String.valueOf(gotLong);
                placeName = String.format("Place: %s", place.getName());
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
                            TimeForDB = String.valueOf(hourOfDay) + ":" + minuteFixer;
                        }

                    }
                    if (minute > 9) {
                        minuteFixer = String.valueOf(minute);
                    }
                    TimeForDB = String.valueOf(hourOfDay) + " : " + minuteFixer;
                    GotTime = GotDate + " At " + String.valueOf(hourOfDay) + ":" + minuteFixer;
                    time.setText(GotTime);

                }
            }, calender.get((Calendar.HOUR_OF_DAY)), calender.get(Calendar.MINUTE), true);
        }

    }
}
