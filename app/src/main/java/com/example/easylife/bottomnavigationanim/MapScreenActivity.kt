package com.example.easylife.bottomnavigationanim

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewAnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.example.easylife.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import io.ghyeok.stickyswitch.widget.StickySwitch
import io.ghyeok.stickyswitch.widget.StickySwitch.OnSelectedChangeListener
import java.util.*


class MapScreenActivity : AppCompatActivity(), OnMapReadyCallback {

    lateinit var stickySwitch: StickySwitch
    lateinit var breakButton: ImageView
    lateinit var breakLayout: LinearLayout
    var play: Boolean = false
    private lateinit var mMap: GoogleMap


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            this.supportActionBar?.hide()
        } catch (e: Exception) {
            Log.e("14", " ->  MapScreenActivity -> onCreate : " + e.message)
        }
        setContentView(R.layout.activity_map_screen)
        val mapFragment = (supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?)!!
        mapFragment.getMapAsync(this)


        breakLayout = findViewById(R.id.break_layout)

        val myName = findViewById<TextView>(R.id.profile_name)
        myName.isSelected = true


        breakButtonClick()

        stickySwitch = findViewById(R.id.sticky_switch)
        val leftIcon = ContextCompat.getDrawable(this, R.drawable.ic_coffie)
        val rightIcon = ContextCompat.getDrawable(this, R.drawable.ic_baseline_work_24)

        stickySwitch.leftIcon = leftIcon
        stickySwitch.rightIcon = rightIcon

        stickySwitch.onSelectedChangeListener =
            object : OnSelectedChangeListener {
                override fun onSelectedChange(direction: StickySwitch.Direction, text: String) {
                    Log.e(
                        "70",
                        "MainHome -> onSelectedChange: text $text\n direction - $direction"
                    )
                    if (direction == StickySwitch.Direction.RIGHT) {
//                        val v =
//                            getSystemService(VIBRATOR_SERVICE) as Vibrator
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                            v.vibrate(
//                                VibrationEffect.createOneShot(
//                                    500,
//                                    VibrationEffect.DEFAULT_AMPLITUDE
//                                )
//                            )
//                        } else {
//                            v.vibrate(500)
//                        }
//                            leftIcon?.setColorFilter(Color.BLACK, PorterDuff.Mode.MULTIPLY)
                        leftIcon?.setTint(Color.WHITE)
                        rightIcon?.setTint(Color.BLACK)
                        enterReveal(R.id.office_screen_layout, false)
                    } else {
                        leftIcon?.setTint(Color.BLACK)
                        rightIcon?.setTint(Color.WHITE)
                        exitReveal(R.id.office_screen_layout, false)
                    }
                }
            }

//        val imageView:ImageView = findViewById(R.id.profile)
//        val drawable:Drawable = ContextCompat.getDrawable(this,R.drawable.person)!!

//        Glide
//            .with(this)
//            .load(drawable)
//            .circleCrop()
//            .into(object :CustomTarget<Drawable>(){
//                override fun onResourceReady(
//                    resource: Drawable,
//                    transition: Transition<in Drawable>?
//                ) {
//                    imageView.setImageDrawable(drawable)
//                }
//
//                override fun onLoadCleared(placeholder: Drawable?) {
//                }
//            })

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


    fun enterReveal(layout: Int, isBreak: Boolean) {
        // previously invisible view
        val myView = findViewById<View>(layout)
        //        Log.e("208", "MainHome -> onCreate: x -> "+);
        //        Log.e("209", "MainHome -> enterReveal: y -> "+);

        // get the center for the clipping circle
        val cx: Int
        val cy: Int

        if (isBreak) {
            // get the center for the clipping circle
            cx = (breakLayout.x + breakLayout.measuredWidth / 2).toInt()
            cy = (breakLayout.y + breakLayout.measuredHeight / 2).toInt()
        } else {
            cx = (stickySwitch.x + stickySwitch.measuredWidth / 2).toInt()
            cy = (stickySwitch.y + stickySwitch.measuredHeight / 2).toInt()
        }
        // get the final radius for the clipping circle
        val finalRadius = Math.max(myView.width, myView.height)

        // create the animator for this view (the start radius is zero)
        val anim =
            ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0f, finalRadius.toFloat())

        // make the view visible and start the animation
        anim.duration = 1000
        myView.visibility = View.VISIBLE
        anim.start()
    }

    fun exitReveal(layout: Int, isBreak: Boolean) {
        // previously visible view
        val myView = findViewById<View>(layout)
        val cx: Int
        val cy: Int

        if (isBreak) {
            // get the center for the clipping circle
            cx = (breakLayout.x + breakLayout.measuredWidth / 2).toInt()
            cy = (breakLayout.y + breakLayout.measuredHeight / 2).toInt()
        } else {
            cx = (stickySwitch.x + stickySwitch.measuredWidth / 2).toInt()
            cy = (stickySwitch.y + stickySwitch.measuredHeight / 2).toInt()
        }

        // get the final radius for the clipping circle
        val initialRadius = Math.max(myView.width, myView.height)

        // create the animation (the final radius is zero)
        val anim =
            ViewAnimationUtils.createCircularReveal(myView, cx, cy, initialRadius.toFloat(), 0f)

        // make the view invisible when the animation is done
        anim.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                myView.visibility = View.INVISIBLE
            }
        })

        // start the animation
        anim.start()
    }

    private fun breakButtonClick() {
        breakButton = findViewById(R.id.break_tracking_image)
        if (!play)
            playButtonWork()
        else
            pauseButtonWork()

        breakButton.setOnClickListener {
            if (play) {
                playButtonWork()
                exitReveal(R.id.break_screen_layout, true)
            }

            else {
                pauseButtonWork()
                enterReveal(R.id.break_screen_layout, true)
            }
        }
    }

    private fun pauseButtonWork() {
        play = true
        (findViewById<CardView>(R.id.break_tracking_card)).setCardBackgroundColor(
            resources.getColor(
                R.color.white
            )
        )

        val drawable: Drawable? = ContextCompat.getDrawable(this@MapScreenActivity, R.drawable.play)
        drawable?.setTint(resources.getColor(R.color.break_over_color))
        breakButton.setImageDrawable(drawable)

    }

    private fun playButtonWork() {
        play = false
        (findViewById<CardView>(R.id.break_tracking_card)).setCardBackgroundColor(
            resources.getColor(
                R.color.white
            )
        )

        val drawable: Drawable? = ContextCompat.getDrawable(this@MapScreenActivity, R.drawable.pause)
        drawable?.setTint(resources.getColor(R.color.break_color))
        breakButton.setImageDrawable(drawable)

    }
    
    override fun onMapReady(googleMap: GoogleMap) {
        Log.e("268", " ->  MapScreenActivity -> onMapReady : ");
        mMap = googleMap
        // Add a marker in Sydney and move the camera
        val sydney = LatLng(22.997536, 72.636645)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney,16.0f))
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        return super.dispatchTouchEvent(ev)
    }
}