package com.example.easylife;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.example.easylife.geofennceUI.GeoFencing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class MainHome extends AppCompatActivity {

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM hh:mm", Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);
        findViewById(R.id.alarm).setOnClickListener(view -> startActivity(new Intent(this, Alarmclockreminder.class)));
        findViewById(R.id.collapsing).setOnClickListener(view -> startActivity(new Intent(this, MainActivity.class)));
        findViewById(R.id.notification).setOnClickListener(view -> MyAlarm.addNotification(this));
        findViewById(R.id.filecreation).setOnClickListener(view -> filecreation());
        findViewById(R.id.start_Button_ui).setOnClickListener(view ->  startActivity(new Intent(this, GeoFencing.class)));
        findViewById(R.id.navigation).setOnClickListener(view -> {
            Intent intent = new Intent(MainHome.this, NavigationDrawer.class);
            startActivity(intent);
        });


        LocationManager locationManager;
        try {
            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, new LocationListener() {
                @Override
                public void onLocationChanged(@NonNull Location location) {

                }
                @Override
                public void onProviderEnabled(@NonNull String provider) {
                    Log.e("Location", "onProviderEnabled: "+provider );
                    GpsStatus status ;
                }

                @Override
                public void onProviderDisabled(@NonNull String provider) {
                    Log.e("Location", "onProviderDisabled: "+provider );
                }
            });

            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

                Log.e("About GPS", "GPS is Enabled in your devide");
            } else {
                //showAlert
                Log.e("About GPS", "GPS is Disabled in your devide");
            }
        }
        catch (Exception e) {
            Log.e("", "onCreate: " + e.getMessage());
        }
        sqliteHelper sqlitehelper = new sqliteHelper(this);
        Log.e("MainHome", "onCreate: "+sqlitehelper.getdata() );
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_BOOT_COMPLETED)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.RECEIVE_BOOT_COMPLETED},1);
        }
        File folder = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/Easy Life/");
        if (!folder.exists())
        {
            folder.mkdir();
        }

        ComponentName receiver = new ComponentName(this,bootCompletedReceiver.class);
        PackageManager pm = this.getPackageManager();
        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);

        File file = new File(folder,"bootcompleted.txt");
        try {
            BufferedReader reader=new BufferedReader(new FileReader(file));
            String s;
            while ((s=reader.readLine())!=null){
                Log.e("TAG", "onCreate: line "+s );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void filecreation()
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
                Toast.makeText(this,"File Created",Toast.LENGTH_SHORT).show();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        else Toast.makeText(this,"File Already Created",Toast.LENGTH_SHORT).show();
        try {
            FileWriter fileWriter = new FileWriter(file,true);
            fileWriter.write("\n Hello this is when,The boot is Completed nad Time is - "+dateFormat.format(System.currentTimeMillis()));
            Toast.makeText(this,"File Written",Toast.LENGTH_SHORT).show();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//
//    public  void  registerBroadcast(Activity activity)
//    {
//        IntentFilter filter = new IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION);
//        filter.addAction(Intent.ACTION_PROVIDER_CHANGED);
//        activity.registerReceiver(locationSwitchStateReceiver, filter);
//        Toast.makeText(activity,"broadcastdded",Toast.LENGTH_SHORT);
//    }
//
//    private BroadcastReceiver locationSwitchStateReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//
//            if (LocationManager.PROVIDERS_CHANGED_ACTION.equals(intent.getAction())) {
//
//                LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
//                boolean isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
//                boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
//
//                if (isGpsEnabled || isNetworkEnabled) {
//                    Toast.makeText(context,"ON",Toast.LENGTH_SHORT);
//                    //location is enabled
//                } else {
//                    //location is disabled
//                    Toast.makeText(context,"Off",Toast.LENGTH_SHORT);
//                }
//            }
//        }
//    };

}