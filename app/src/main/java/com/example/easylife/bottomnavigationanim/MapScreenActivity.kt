package com.example.easylife.bottomnavigationanim

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.*
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewAnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.easylife.R
import com.github.hariprasanths.bounceview.BounceView
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
    lateinit var breakButton: LinearLayout
    var play: Boolean = false
    private lateinit var mMap: GoogleMap
    var currentActiveStatus: Status = Status.FirstTime
    private val exitRevealTime: Long = 500
    private val enterRevealTime: Long = 500


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            this.supportActionBar?.hide()
        } catch (e: Exception) {
            Log.e("14", " ->  MapScreenActivity -> onCreate : " + e.message)
        }
        setContentView(R.layout.activity_map_screen)
        val mapFragment =
            (supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?)!!
        mapFragment.getMapAsync(this)


        stickySwitch = findViewById(R.id.sticky_switch)

        val myName = findViewById<TextView>(R.id.profile_name)
        myName.isSelected = true


        breakButtonClick()
        currentStatus(Status.FirstTime)
        officeModeWork()


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

    private fun officeModeWork() {
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
                        leftIcon?.setTint(Color.WHITE)
                        rightIcon?.setTint(Color.BLACK)
                        vibrate()
                        if (currentActiveStatus == Status.Break) {
                            playButtonWork()
                            Handler(Looper.getMainLooper()).postDelayed({
                                runOnUiThread {
                                    enterReveal(R.id.office_screen_layout, false)
                                }
                            }, 10)
                            exitReveal(R.id.break_screen_layout, true)
                        } else
                            enterReveal(R.id.office_screen_layout, false)
                        currentStatus(Status.Office)
                    } else {
                        currentStatus(Status.Tracking)
                        leftIcon?.setTint(Color.BLACK)
                        rightIcon?.setTint(Color.WHITE)
                        vibrate()
                        exitReveal(R.id.office_screen_layout, false)
                    }
                }
            }
    }

    fun vibrate(seconds: Long = 200) {
        val v =
            getSystemService(VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(
                VibrationEffect.createOneShot(
                    seconds,
                    VibrationEffect.DEFAULT_AMPLITUDE
                )
            )
        } else {
            v.vibrate(seconds)
        }

    }

    fun enterReveal(layout: Int, isBreak: Boolean) {
        val myView = findViewById<View>(layout)
        val cx: Int
        val cy: Int
        if (isBreak) {
            val breakLayout = findViewById<LinearLayout>(R.id.break_reveal_holder)
            cx = (breakLayout.x + breakLayout.measuredWidth / 2).toInt()
            cy = (breakLayout.y + breakLayout.measuredHeight / 2).toInt()
        } else {
            cx = (stickySwitch.x + stickySwitch.measuredWidth / 2).toInt()
            cy = (stickySwitch.y + stickySwitch.measuredHeight / 2).toInt()
        }
        val finalRadius = Math.max(myView.width, myView.height)
        val anim =
            ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0f, finalRadius.toFloat())
        anim.duration = enterRevealTime
        myView.visibility = View.VISIBLE
        findViewById<LinearLayout>(R.id.my_location).visibility = View.GONE
        anim.start()
    }

    fun exitReveal(layout: Int, isBreak: Boolean) {
        val myView = findViewById<View>(layout)
        val cx: Int
        val cy: Int
        if (isBreak) {
            val breakLayout = findViewById<LinearLayout>(R.id.break_reveal_holder)
            cx = (breakLayout.x + breakLayout.measuredWidth / 2).toInt()
            cy = (breakLayout.y + breakLayout.measuredHeight / 2).toInt()
        } else {
            cx = (stickySwitch.x + stickySwitch.measuredWidth / 2).toInt()
            cy = (stickySwitch.y + stickySwitch.measuredHeight / 2).toInt()
        }
        val initialRadius = Math.max(myView.width, myView.height)
        val anim =
            ViewAnimationUtils.createCircularReveal(myView, cx, cy, initialRadius.toFloat(), 0f)
        anim.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                myView.visibility = View.INVISIBLE
            }
        })
        anim.duration = exitRevealTime
        findViewById<LinearLayout>(R.id.my_location).visibility = View.VISIBLE
        anim.start()
    }

    private fun breakButtonClick() {
        breakButton = findViewById(R.id.break_tracking_card)
        if (!play)
            playButtonWork()
        else
            pauseButtonWork()


//        PushDownAnim
//            .setPushDownAnimTo( findViewById<LinearLayout>(R.id.break_tracking_card) )
//            .setScale(0.5f)
//            .setDurationPush(100)
//            .setDurationRelease(100)
//            .setOnClickListener { Log.e("210", " ->  MapScreenActivity -> onClick : "); }

        BounceView
            .addAnimTo(breakButton)
            .setScaleForPushInAnim(0.5f, 0.5f)
            .setScaleForPopOutAnim(1.0f, 1.0f)
            .setPushInAnimDuration(200)
            .setPopOutAnimDuration(200)

        breakButton.setOnClickListener {
            if (play) {
                playButtonWork()
                vibrate()
                exitReveal(R.id.break_screen_layout, true)
                currentStatus(Status.Tracking)
            } else {
                vibrate()
                pauseButtonWork()
                if (currentActiveStatus == Status.Office) {
                    stickySwitch.setDirection(StickySwitch.Direction.LEFT, true, false)
                    Handler(Looper.getMainLooper()).postDelayed({
                        runOnUiThread {
                            enterReveal(R.id.break_screen_layout, true)
                        }
                    }, 10)
                    exitReveal(R.id.office_screen_layout, false)
                } else
                    enterReveal(R.id.break_screen_layout, true)
                currentStatus(Status.Break)
            }
        }

    }

    private fun pauseButtonWork() {
        play = true
        val drawable: Drawable? = ContextCompat.getDrawable(this@MapScreenActivity, R.drawable.play)
        drawable?.setTint(resources.getColor(R.color.red))
        findViewById<ImageView>(R.id.break_tracking_image).setImageDrawable(drawable)

    }

    private fun playButtonWork() {
        play = false
        val drawable: Drawable? =
            ContextCompat.getDrawable(this@MapScreenActivity, R.drawable.pause)
        drawable?.setTint(resources.getColor(R.color.red))
        findViewById<ImageView>(R.id.break_tracking_image).setImageDrawable(drawable)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        Log.e("268", " ->  MapScreenActivity -> onMapReady : ")
        mMap = googleMap
        // Add a marker in Sydney and move the camera
        val sydney = LatLng(22.997536, 72.636645)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 16.0f))
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        return super.dispatchTouchEvent(ev)
    }

    private fun currentStatus(status: Status) {
        var currentStTv = "Make Your First Clock In"
        var color: Int = R.color.data_card_text_color

        when (status) {
            Status.Break -> {
                currentStTv = "On Break"
                color = R.color.on_break
            }
            Status.Tracking -> {
                currentStTv = "Tracking"
                color = R.color.tracking
            }
            Status.Office -> {
                currentStTv = "In Office"
                color = R.color.office_color
            }
            Status.FirstTime -> {
                currentStTv = "Make Your First Clock In"
                color = R.color.data_card_text_color
            }
            else -> {

            }
        }
        currentActiveStatus = status
        val currentStatusTextView = findViewById<TextView>(R.id.current_status)
        currentStatusTextView.text = currentStTv
        currentStatusTextView.isSelected = true
        currentStatusTextView.setTextColor(resources.getColor(color))
//        (findViewById<MaterialCardView>(R.id.current_status_card)).setCardBackgroundColor(
//            resources.getColor(
//                color
//            )
//        )
        val drawable = ContextCompat.getDrawable(this, R.drawable.dot)
        drawable?.setTint(resources.getColor(color))
        currentStatusTextView.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)
    }

    enum class Status {
        Break, Wifi, Office, PowerSaving, GPS, Internet, Tracking, FirstTime
    }

    fun myLocation(view: View?) {

    }
}