package com.example.easylife;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class MyAlarm extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("MyAlarmBelal", "Alarm just fired");
        addNotification(context);
        new sqliteHelper(context).deletedata(intent.getLongExtra("time",0));
    }
        public  static void addNotification(Context context) {
            Bitmap myBitmap = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.ic_baseline_check_24);
            NotificationCompat.Builder builder =
                    new NotificationCompat.Builder(context,"1000")
                            .setSmallIcon(R.drawable.ic_baseline_check_24) //set icon for notification
                            .setContentTitle("Notifications Example") //set title of notification
                            .setContentText("This is a notification on a particular time")//this is notification message
                            .setAutoCancel(true) // makes auto cancel of notification
                            .setStyle(new NotificationCompat.BigTextStyle()
                                    .bigText("Hello There this is alarm axample"))
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel chan = new NotificationChannel("1000","channelName", NotificationManager.IMPORTANCE_HIGH);
                chan.setLightColor(Color.BLUE);
                chan.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
                manager.createNotificationChannel(chan);
            }
            manager.notify(0, builder.build());
        }
}