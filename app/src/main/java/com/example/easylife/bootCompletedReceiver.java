package com.example.easylife;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Map;

public class bootCompletedReceiver extends BroadcastReceiver {

    SimpleDateFormat dateFormat=new SimpleDateFormat("dd MMM hh:mm", Locale.US);

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"Boot Completed",Toast.LENGTH_SHORT);
        file(context);
        setAlarm(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setAlarm(Context context) {
        sqliteHelper sqlitehelper = new sqliteHelper(context);
        Map<Long,String> map = sqlitehelper.getdata();
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if(map!=null)
        {
            for(Map.Entry<Long, String> s:map.entrySet())
            {
                Intent i = new Intent(context, MyAlarm.class);
                i.putExtra("time", s.getKey());
                i.setAction(s.getValue());
                PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, PendingIntent.FLAG_CANCEL_CURRENT);
                am.setRepeating(AlarmManager.RTC_WAKEUP, s.getKey(), AlarmManager.INTERVAL_DAY, pi);
                Toast.makeText(context,"Alarm Setted For Easy Life"+dateFormat.format(s.getKey()),Toast.LENGTH_SHORT).show();
                Log.e("50", "bootCompletedReceiver -> setAlarm: this is Easy life man.........!");
            }
        }
    }


    private  void  file(Context context)
    {
        File folder = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/Easy Life/");
        if (!folder.exists())
        {
            folder.mkdir();
        }
        File file = new File(folder,"bootcompleted.txt");
        if(!file.exists())
        {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileWriter fileWriter = new FileWriter(file,true);
            fileWriter.write("\n Hello this is when,The boot is Completed nad Time is - "+dateFormat.format(System.currentTimeMillis()));
            Toast.makeText(context,"File Written",Toast.LENGTH_SHORT).show();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}