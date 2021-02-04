package com.example.easylife;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.icu.text.RelativeDateTimeFormatter;
import android.os.Build;
import android.os.IBinder;
import android.service.controls.Control;
import android.service.controls.ControlsProviderService;
import android.service.controls.DeviceTypes;
import android.service.controls.actions.ControlAction;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Flow;
import java.util.function.Consumer;

@RequiresApi(api = Build.VERSION_CODES.R)
public class Powermenufn extends ControlsProviderService {

    int types;

    public Powermenufn() {
        types = DeviceTypes.TYPE_AC_UNIT;
    }

    @NonNull
    @Override
    public Flow.Publisher<Control> createPublisherForAllAvailable() {
        //        Context context = getBaseContext();
//        Intent i = new Intent();
//        PendingIntent pi = PendingIntent.getActivity(context, 1, i, PendingIntent.FLAG_UPDATE_CURRENT);
//        List controls = new ArrayList<>();
//        Control control = new Control.StatelessBuilder(MY-UNIQUE-DEVICE-ID, pi)
//                // Required: The name of the control
//                .setTitle(MY-CONTROL-TITLE)
//                // Required: Usually the room where the control is located
//                .setSubtitle(MY-CONTROL-SUBTITLE)
//                // Optional: Structure where the control is located, an example would be a house
//                .setStructure(MY-CONTROL-STRUCTURE)
//                // Required: Type of device, i.e., thermostat, light, switch
//                .setDeviceType(DeviceTypes.DEVICE-TYPE) // For example, DeviceTypes.TYPE_THERMOSTAT
//                .build();
//        controls.add(control);
//        // Create more controls here if needed and add it to the ArrayList
//
//        // Uses the RxJava 2 library
//        return FlowAdapters.toFlowPublisher(Flowable.fromIterable(controls));
        return null;
     }

    @NonNull
    @Override
    public Flow.Publisher<Control> createPublisherFor(@NonNull List<String> list) {
        return null;
    }

    @Override
    public void performControlAction(@NonNull String s, @NonNull ControlAction controlAction, @NonNull Consumer<Integer> consumer) {

    }

//    @Override
//    public Flow.Publisher createPublisherForAllAvailable() {
//        Context context = getBaseContext();
//        Intent i = new Intent();
//        PendingIntent pi = PendingIntent.getActivity(context, 1, i, PendingIntent.FLAG_UPDATE_CURRENT);
//        List controls = new ArrayList<>();
//        Control control = new Control.StatelessBuilder(MY-UNIQUE-DEVICE-ID, pi)
//                // Required: The name of the control
//                .setTitle(MY-CONTROL-TITLE)
//                // Required: Usually the room where the control is located
//                .setSubtitle(MY-CONTROL-SUBTITLE)
//                // Optional: Structure where the control is located, an example would be a house
//                .setStructure(MY-CONTROL-STRUCTURE)
//                // Required: Type of device, i.e., thermostat, light, switch
//                .setDeviceType(DeviceTypes.DEVICE-TYPE) // For example, DeviceTypes.TYPE_THERMOSTAT
//                .build();
//        controls.add(control);
//        // Create more controls here if needed and add it to the ArrayList
//
//        // Uses the RxJava 2 library
//        return FlowAdapters.toFlowPublisher(Flowable.fromIterable(controls));
//    }
//


}