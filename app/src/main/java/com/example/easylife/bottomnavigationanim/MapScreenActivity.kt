package com.example.easylife.bottomnavigationanim

import android.graphics.drawable.Drawable
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.easylife.R

class MapScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try
        {
            this.supportActionBar?.hide()
        }catch (e:Exception){
            Log.e("14", " ->  MapScreenActivity -> onCreate : "+e.message);
        }
        setContentView(R.layout.activity_map_screen)

        val imageView:ImageView = findViewById(R.id.profile)
        val drawable:Drawable = ContextCompat.getDrawable(this,R.drawable.person)!!

        Glide
            .with(this)
            .load(drawable)
            .circleCrop()
            .into(object :CustomTarget<Drawable>(){
                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {
                    imageView.setImageDrawable(drawable)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })


    }
}