package com.example.easylife;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;

import java.util.List;

import kotlin.random.Random;

public class MyAlarm extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("MyAlarmBelal", "Alarm just fired");
        addNotification(context);
        new sqliteHelper(context).deletedata(intent.getLongExtra("time",0));
    }
        public  static void addNotification(Context context) {
            Uri sound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                    "://" + context.getPackageName() + "/" + R.raw.notif);

            RemoteViews remoteCollapsedViews = new RemoteViews(context.getPackageName(), R.layout.custom_normal);
            RemoteViews remoteExpandedViews = new RemoteViews(context.getPackageName(), R.layout.custom_expanded);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "252");
            builder.setSmallIcon(R.drawable.ic_baseline_check_24) //set icon for notification
                    .setAutoCancel(true) // makes auto cancel of notification
                    .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setCustomContentView(remoteCollapsedViews)
                    .setCustomBigContentView(remoteExpandedViews).setSound(null)
                    .setContentTitle("Reminder")
                    .setContentText("Don't forget to Clock-Out")
                    .setStyle(new NotificationCompat.BigTextStyle().bigText("Don't forget to Clock-Out"));;

            AudioAttributes attributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build();
            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel chan = new NotificationChannel("252","EasyLife", NotificationManager.IMPORTANCE_HIGH);
                chan.setSound(sound,attributes);
                chan.setLightColor(Color.BLUE);
                chan.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
                manager.createNotificationChannel(chan);
            }
            else {
                MediaPlayer mp = MediaPlayer.create(context, R.raw.notif);
                mp.start();
            }
            Log.e("59", "MyAlarm -> addNotification: ");
            manager.notify(0, builder.build());
            
        }
}