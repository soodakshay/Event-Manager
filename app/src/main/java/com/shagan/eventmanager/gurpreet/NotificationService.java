package com.shagan.eventmanager.gurpreet;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Calendar;


public class NotificationService extends Service {
    DataBaseHelper helper;
    String Title;
    String Venue;
    String Date;
    String Time;
    String Day_Of_Month;
    String Month;
    Calendar calendar;
    PendingIntent alarmReciever;
    long then;
    long now;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        preferences = getSharedPreferences("notification" , MODE_PRIVATE);
        editor = preferences.edit();
        Log.e("Service", "InService");
        if (intent != null) {
            then = intent.getLongExtra("then", 0);
            now = intent.getLongExtra("now", 0);
            Title = intent.getStringExtra("title");
            Venue = intent.getStringExtra("venue");
            Date = intent.getStringExtra("date");
            Time = intent.getStringExtra("time");
        }


        long timeToWait = then - now;
        Log.e("Time", "" + timeToWait);
        if(preferences.getBoolean("notification_check" , false)) {
            editor.putBoolean("notification_check" , false).commit();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent1 = new Intent(getApplicationContext(), NotificationReciever.class);
                    intent1.putExtra("title", Title);
                    intent1.putExtra("venue", Venue);
                    intent1.putExtra("time", Time);
                    intent1.putExtra("date", Date);
                    sendBroadcast(intent1);
                }
            }, 30000);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
