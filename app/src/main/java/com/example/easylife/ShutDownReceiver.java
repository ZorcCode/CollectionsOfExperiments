package com.example.easylife;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class ShutDownReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        file(context);

    }




    private  void  file(Context context)
    {
        Log.e("28", "ShutDownReceiver -> file: ");
        SimpleDateFormat dateFormat=new SimpleDateFormat("dd MMM hh:mm", Locale.US);
        File folder ;
        folder = new File(context.getExternalFilesDir("Easy Life"),"");
        if (!folder.exists())
        {
            folder.mkdir();
        }
        File file = new File(folder,"shutdownreceiver.txt");
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