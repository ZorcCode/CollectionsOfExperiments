package com.example.easylife;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.bumptech.glide.signature.ObjectKey;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;


public class FirebaseNotificaton extends FirebaseMessagingService{
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Map<String, String> data=remoteMessage.getData();
//        Log.e("27 data",data.toString());
//        Log.e("28", "FirebaseNotificaton -> onMessageReceived: "+remoteMessage.getNotification().getTitle());
        if(data.containsKey("title") && data.containsKey("body") && data.containsKey("screen")) {
            showNotification(data.get("title"), data.get("body"), data.get("screen"),data.get("icon"));
        }else if(data.containsKey("signal")){
            processSignal(data.get("signal"));
        }
        else if(data.containsKey("image") && data.containsKey("link"))
        {
            addNotification(this,data.get("image"),data.get("link"),data.get("title"),data.get("description"));
        }
    }
    private void processSignal(String signal) {
    }

    private void showNotification(String title, String body,String activity,String icon){
        String NOTIFICATION_CHANNEL_ID = "emplitrack_channel";
        String channelName = "Emplitrack Notifications";
        NotificationManager manager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID,channelName, NotificationManager.IMPORTANCE_HIGH);
            chan.setLightColor(Color.BLUE);
            chan.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            manager.createNotificationChannel(chan);
        }
        Intent intent;
        PendingIntent pendingIntent;

//		int res = getResources().getIdentifier(icon,"drawable",getPackageName());
        int res = R.drawable.only_icon_logo_white_scaled;

        int notificationId=5555;
        try {
            switch (activity){
                case "features.AccountDetailsActivity":notificationId=5557;
                    break;
                case "features.expense.ExpenseListActivity":notificationId=5558;
                    break;
                case "features.leave.LeaveListActivity":notificationId=5559;
                    break;
                case "features.leave.TeamLeaveActivity":notificationId=5560;
                    break;
                case "features.expense.TeamExpenseActivity":notificationId=5561;
                    break;
                case "tracking.MapsActivity":notificationId=5562;
                    break;
                case "features.SupportListActivity":notificationId=5563;
                    break;
                case "features.AttendanceActivity":notificationId=5564;
                    break;
                case "":
            }

            activity = getPackageName()+"."+activity;
            intent = new Intent(this,Class.forName(activity));
            if (activity.equals(getPackageName()+".MainActivity"))
                pendingIntent=PendingIntent.getActivity(this,10,intent,PendingIntent.FLAG_UPDATE_CURRENT);
            else
                pendingIntent=PendingIntent.getActivities(this,10,new Intent[]{new Intent(this,MainActivity.class),intent},PendingIntent.FLAG_UPDATE_CURRENT);
        } catch (ClassNotFoundException e) {
            Log.e("exception",e.getMessage());
            return;
        }
        NotificationCompat.Builder notificationBuilder=new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        notificationBuilder
                .setSmallIcon(res)
                .setContentTitle(title)
                .setContentText(body)
                .setContentIntent(pendingIntent)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(body));
        manager.notify(notificationId,notificationBuilder.build());
    }
    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Log.d("new Token",s);
    }


    public void addNotification(Context context, String image, String link, String title, String description) {
        RemoteViews remoteCollapsedViews = new RemoteViews(context.getPackageName(), R.layout.custom_normal);
        remoteCollapsedViews.setTextViewText(R.id.title_notification,title);
        remoteCollapsedViews.setTextViewText(R.id.description_notification,description);
        RemoteViews remoteExpandedViews = new RemoteViews(context.getPackageName(), R.layout.custom_expanded);
        remoteExpandedViews.setTextViewText(R.id.title_notification,title);
        remoteExpandedViews.setTextViewText(R.id.description_notification,description);
        Intent in = new Intent(Intent.ACTION_VIEW);
        in.setData(Uri.parse(link));
        PendingIntent intent = PendingIntent.getActivity(this,10,in,PendingIntent.FLAG_UPDATE_CURRENT);
        Glide.with(this)
                .asBitmap()
                .load(Uri.parse(image))
                .placeholder(R.drawable.ic_coffie)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        remoteExpandedViews.setImageViewBitmap(R.id.icon_notification,resource);
                        remoteCollapsedViews.setImageViewBitmap(R.id.icon_notification,resource);
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "1000");
                        builder.setSmallIcon(R.drawable.ic_baseline_check_24) //set icon for notification
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                .setContentIntent(intent)
                                .setAutoCancel(true) // makes auto cancel of notification
                                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                .setCustomContentView(remoteCollapsedViews)
                                .setCustomBigContentView(remoteExpandedViews);
                        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            NotificationChannel chan = new NotificationChannel("1000","channelName", NotificationManager.IMPORTANCE_HIGH);
                            chan.setLightColor(Color.BLUE);
                            chan.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
                            manager.createNotificationChannel(chan);
                        }
                        manager.notify(0, builder.build());
                    }
                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });


    }



}