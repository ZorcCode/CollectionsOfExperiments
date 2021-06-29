package com.example.easylife;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;


public class GpsonofReceiver extends BroadcastReceiver implements LocationListener
{

@Override
public void onReceive(Context context, Intent intent)
        {
        if (intent.getAction().matches("android.location.PROVIDERS_CHANGED"))
        {
            Log.e("23", "GpsonofReceiver -> onReceive: Providr changed");
        }
        }

    @Override
    public void onLocationChanged(@NonNull Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.e("35", "GpsonofReceiver -> onStatusChanged: status "+status);
        Log.e("36", "GpsonofReceiver -> onStatusChanged: provider "+provider);
        Log.e("37", "GpsonofReceiver -> onStatusChanged: extras "+extras.toString());
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        Log.e("41", "GpsonofReceiver -> onProviderEnabled: "+provider);
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        Log.e("46", "GpsonofReceiver -> onProviderDisabled: "+provider);

    }
}