package com.example.easylife;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
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
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.util.Util;
import com.example.easylife.geofennceUI.GeoFencing;
import com.example.easylife.kotlinfiles.HttpProcess;
import com.example.easylife.kotlinfiles.RecyclerViewExample;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.installations.Utils;
import com.google.firebase.messaging.FirebaseMessaging;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import io.ghyeok.stickyswitch.widget.StickySwitch;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

public class MainHome extends AppCompatActivity {

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM hh:mm", Locale.US);
    StickySwitch stickySwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);

//        MediaType JSON = MediaType.get("application/json");
//        RequestBody body = RequestBody.create("{}",JSON);
//        Request request=new Request.Builder().url("url").post(body).build();

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.e("62", "MainHome -> onComplete: "+task);
                        return;
                    }

                    String token = task.getResult();
                    Log.e("68", "MainHome -> onComplete: "+token);

                });

        this.registerReceiver(this.mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        findViewById(R.id.api_call).setOnClickListener(view -> apiCall());
        findViewById(R.id.alarm).setOnClickListener(view -> startActivity(new Intent(this, Alarmclockreminder.class)));
        findViewById(R.id.collapsing).setOnClickListener(view -> startActivity(new Intent(this, MainActivity.class)));
        findViewById(R.id.notification).setOnClickListener(view -> MyAlarm.addNotification(this));
        findViewById(R.id.filecreation).setOnClickListener(view -> filecreation());
        findViewById(R.id.start_Button_ui).setOnClickListener(view ->  startActivity(new Intent(this, GeoFencing.class)));
        findViewById(R.id.netOnOff).setOnClickListener(view ->  startActivity(new Intent(this, InternetCheckActivity.class)));
        findViewById(R.id.anim).setOnClickListener(view -> { startActivity(new Intent(this, Animations.class));
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.os.action.POWER_SAVE_MODE_CHANGED");
        registerReceiver(powerSaverChangeReceiver, filter);
    });
        findViewById(R.id.gpsOnOff).setOnClickListener(view -> {
            IntentFilter filter = new IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION);
            filter.addAction(Intent.ACTION_PROVIDER_CHANGED);
            MainHome.this.registerReceiver(locationSwitchStateReceiver, filter);});

        findViewById(R.id.navigation).setOnClickListener(view -> {
            Intent intent = new Intent(MainHome.this, NavigationDrawer.class);
            startActivity(intent);
        });
        stickySwitch = findViewById(R.id.sticky_switch);
        stickySwitch.setLeftIcon(R.drawable.ic_coffie);
        stickySwitch.setRightIcon(R.drawable.ic_baseline_work_24);

        stickySwitch.setOnSelectedChangeListener((direction, text) -> {
            Log.e("70", "MainHome -> onSelectedChange: text "+text+"\n direction - "+direction);
            if(direction.equals(StickySwitch.Direction.RIGHT)) {
                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                } else {
                    v.vibrate(500);
                }                enterReveal();
            } else
            {
                exitReveal();
            }
        });


        BatteryManager powerManager = (BatteryManager) getSystemService(Context.BATTERY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            Log.e("113", "MainHome -> onCreate: charge counter "+powerManager.getLongProperty(BatteryManager.BATTERY_PROPERTY_CHARGE_COUNTER));
            Log.e("113", "MainHome -> onCreate: health over voltage  "+powerManager.getLongProperty(BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE));
            Log.e("113", "MainHome -> onCreate: plggedin "+powerManager.getLongProperty(BatteryManager.BATTERY_PLUGGED_AC));
            Log.e("113", "MainHome -> onCreate: health cold  "+powerManager.getLongProperty(BatteryManager.BATTERY_HEALTH_COLD));
            Log.e("113", "MainHome -> onCreate: health dead  "+powerManager.getLongProperty(BatteryManager.BATTERY_HEALTH_DEAD));
            Log.e("113", "MainHome -> onCreate: health Good  "+powerManager.getLongProperty(BatteryManager.BATTERY_HEALTH_GOOD));
            Log.e("113", "MainHome -> onCreate: health Good  "+powerManager.getLongProperty(BatteryManager.BATTERY_HEALTH_UNKNOWN));
            Log.e("113", "MainHome -> onCreate: level  "+powerManager.getLongProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY));
        }

//        LocationManager locationManager;
//        try {
//            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                return;
//            }
//            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, new LocationListener() {
//                @Override
//                public void onLocationChanged(@NonNull Location location) {
//
//                }
//                @Override
//                public void onProviderEnabled(@NonNull String provider) {
//                    Log.e("Location", "onProviderEnabled: "+provider );
//                    GpsStatus status ;
//                }
//
//                @Override
//                public void onProviderDisabled(@NonNull String provider) {
//                    Log.e("Location", "onProviderDisabled: "+provider );
//                }
//            });
//
//            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//                Log.e("About GPS", "GPS is Enabled in your devide");
//            } else {
//                //showAlert
//                Log.e("About GPS", "GPS is Disabled in your devide");
//            }
//        }
//        catch (Exception e) {
//            Log.e("", "onCreate: " + e.getMessage());
//        }
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
//
//
//        RecyclerView recyclerView = findViewById(R.id.recycler);
//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(@NonNull @NotNull RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//            }
//
//            @Override
//            public void onScrolled(@NonNull @NotNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//            }
//        });
//
    }

    private void apiCall() {
        com.example.easylife.kotlinfiles.HttpProcess process = new com.example.easylife.kotlinfiles.HttpProcess("http://192.168.138.76:3000/xyz");
        process.post(String.format("{clock_out:%s}",823734982), new HttpProcess.Callback() {
            @Override
            public void onError(@Nullable IOException exception) {
                Log.e("228", "MainHome -> onError: "+exception.getMessage());
            }

            @Override
            public void onResponse(@Nullable String response) throws JSONException {
                Log.e("233", "MainHome -> onResponse: "+response);
            }
        });
    }

    public void filecreation()
    {
        File folder ;
        folder = new File(getExternalFilesDir("Easy Life"),"");
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

//    @Override
//    protected void onPause() {
//        this.unregisterReceiver(locationSwitchStateReceiver);
//        super.onPause();
//    }

    private BroadcastReceiver locationSwitchStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (LocationManager.PROVIDERS_CHANGED_ACTION.equals(intent.getAction())) {
                LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
                boolean isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
                if (isGpsEnabled || isNetworkEnabled) {
                    Log.e("241", "MainHome -> onReceive: gps enabled");
                    //location is enabled
                } else {
                    Log.e("244", "MainHome -> onReceive: gps disabled");
                    //location is disabled
                }
            }
        }
    };



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



    void enterReveal() {
        // previously invisible view
        final View myView = findViewById(R.id.root_layout);
//        Log.e("208", "MainHome -> onCreate: x -> "+);
//        Log.e("209", "MainHome -> enterReveal: y -> "+);

        // get the center for the clipping circle
        int cx = (int) (stickySwitch.getX()+(stickySwitch.getMeasuredWidth() / 2));
        int cy = (int) (stickySwitch.getY()+(stickySwitch.getMeasuredHeight() / 2));

        // get the final radius for the clipping circle
        int finalRadius = Math.max(myView.getWidth(), myView.getHeight());

        // create the animator for this view (the start radius is zero)
        Animator anim =
                ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0, finalRadius);

        // make the view visible and start the animation
        anim.setDuration(1000);
        myView.setVisibility(View.VISIBLE);
        anim.start();
    }
    void exitReveal() {
        // previously visible view
        final View myView = findViewById(R.id.root_layout);

        // get the center for the clipping circle
        int cx = (int) (stickySwitch.getX()+(stickySwitch.getMeasuredWidth() / 2));
        int cy = (int) (stickySwitch.getY()+(stickySwitch.getMeasuredHeight() / 2));

        // get the final radius for the clipping circle
        int initialRadius = Math.max(myView.getWidth(), myView.getHeight());

        // create the animation (the final radius is zero)
        Animator anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, initialRadius, 0);

        // make the view invisible when the animation is done
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                myView.setVisibility(View.INVISIBLE);
            }
        });

        // start the animation
        anim.start();
    }

    private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context ctxt, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            float batteryPct = level * 100 / (float)scale;
            ((TextView) findViewById(R.id.battery)).setText(batteryPct + "%");
        }
    };

    BroadcastReceiver powerSaverChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            if (pm.isPowerSaveMode()) {
                Log.e("346", "MainHome -> onReceive: Power Mode on");
            } else {
                Log.e("346", "MainHome -> onReceive: Power Mode off");
            }
        }
    };



}