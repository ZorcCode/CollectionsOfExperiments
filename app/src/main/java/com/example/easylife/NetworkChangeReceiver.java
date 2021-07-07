package com.example.easylife;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.util.Log;

import com.google.android.datatransport.cct.internal.LogEvent;

import static android.util.Log.e;

public class NetworkChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
//        int status = NetworkUtils.getConnectivityStatusString(context);

        if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction()))
        {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            e("25", "NetworkChangeReceiver -> onReceive  ->  : "+activeNetwork);
        if (null != activeNetwork) {
            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                e("25", "NetworkChangeReceiver -> onReceive  ->  : wifi");
            if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                e("28", "NetworkChangeReceiver -> onReceive  ->  : Network");
        }
        else
        {
            e("35", "NetworkChangeReceiver -> onReceive  ->  : No Internet");
        }

//            if (status == NetworkUtils.NETWORK_STATUS_NOT_CONNECTED) {
//                InternetCheckActivity.setNetworkToText("Off",false);
//                e("33", "NetworkChangeReceiver -> onReceive  ->  : "+status);
//            } else {
//                e("36", "NetworkChangeReceiver -> onReceive  ->  : "+status);
//                InternetCheckActivity.setNetworkToText("On",true);
//            }
        }
    }
}