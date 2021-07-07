package com.example.easylife;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import static android.util.Log.e;

public class InternetCheckActivity extends AppCompatActivity {

    static TextView textView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internet_check);

        textView = findViewById(R.id.textView2);
//        IntentFilter intentFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
//        registerReceiver(new NetworkChangeReceiver(), intentFilter);
//
    }

    public  void  checkInternet(View view)
    {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(networkCallback);
        } else {
            NetworkRequest request = new NetworkRequest.Builder()
                    .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET).build();
            connectivityManager.registerNetworkCallback(request, networkCallback);
        }
    }

    ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback() {
        @Override
        public void onAvailable(Network network) {
            // network available
            e("45", "InternetCheckActivity -> onAvailable  ->  : "+network);
        }

        @Override
        public void onLost(Network network) {
            e("52", "InternetCheckActivity -> onLost  ->  : "+network);
        }
    };

    public static void setNetworkToText(String text,boolean color)
    {

        textView.setText(text);
        textView.setBackgroundColor(color?Color.GREEN:Color.RED);
    }


    
}