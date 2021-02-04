package com.example.easylife;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;


public class GpsonofReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"Location Service ",Toast.LENGTH_SHORT).show();
        Log.e("GPSON", "onProviderEnabled: yessssssssssssssssssssssssssssssssssssssssssssssssssss wokrrrrrrrrrrrrrrrrrrrrrrrrrringnn" );
//        Log.e("Gps on off", "onReceive: "+intent );
    }
}