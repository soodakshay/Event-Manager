package com.shagan.eventmanager.gurpreet;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import com.shagan.eventmanager.R;

public class NotificationReciever extends WakefulBroadcastReceiver {
    NotificationManager manager;
    DataBaseHelper helper;
    String Title;
    String Venue;
    String Date;
    String Time;
    String Day_Of_Month;
    String Month;

    @Override
    public void onReceive(Context context, Intent intent) {


        Intent notificationIntent = new Intent(context, viewUpcomingActivity.class);
        if (intent != null) {
            Title = intent.getStringExtra("title");
            Venue = intent.getStringExtra("venue");
            Date = intent.getStringExtra("date");
            Time = intent.getStringExtra("time");
        }
        Log.e("Broadcast", Title + " " + Venue + " " + " " + Date);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(viewUpcomingActivity.class);
        stackBuilder.addNextIntent(notificationIntent);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        String NotificationTitle;

        if (Venue == null) {
            NotificationTitle = Title;
        } else {
            NotificationTitle = Title + " At " + Venue;
        }
        Notification notification = builder.setContentTitle(NotificationTitle)
                .setContentText(Time + " On " + Date)
                .setTicker("New Message Alert!")
                .setSmallIcon(R.mipmap.icon).setVibrate(new long[]{1000, 1000, 1000, 1000, 1000}).setLights(Color.RED, 3000, 3000).setSound(uri).setContentIntent(pendingIntent).build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);

    }
}
