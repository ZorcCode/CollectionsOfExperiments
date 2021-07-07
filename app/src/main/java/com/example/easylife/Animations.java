package com.example.easylife;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.PathInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.easylife.bottomnavigationanim.BottomNavigation;
import com.race604.drawable.wave.WaveDrawable;
import com.victor.loading.book.BookLoading;

public class Animations extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animations);

//        BookLoading loading = findViewById(R.id.bookloading);
//        loading.start();
//        Drawable mWaveDrawable = new WaveDrawable(ContextCompat.getDrawable(this,R.drawable.only_icon_logo_white_scaled));
//        ((ImageView)findViewById(R.id.animImage)).setImageDrawable(mWaveDrawable);
//        LinearLayout root = findViewById(R.id.root);


//        int rotationAngle = 0;
//        ObjectAnimator anim = ObjectAnimator.ofFloat(findViewById(R.id.imageanim), "rotation",rotationAngle, rotationAngle + 180);
//        anim.setDuration(500);
//        anim.start();
//        rotationAngle += 180;
//        rotationAngle = rotationAngle%360;

//        PathInterpolator pathInterpolator = null;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Path path = new Path();
//            path.arcTo(0f, 0f, 1f, 1f, 270f, -180f, true);
//           pathInterpolator = new PathInterpolator(path);
//        }
//        ObjectAnimator animation = ObjectAnimator.ofFloat(findViewById(R.id.imageanim), "translationX", 100f);
//        animation.setInterpolator(pathInterpolator);
//        animation.start();


//        slideUp(findViewById(R.id.imageanim),findViewById(R.id.imageanim));
//        slideDown(findViewById(R.id.imageanim),findViewById(R.id.imageanim));
    }
//    public void slideUp(final View view, final View llDomestic){
//
//        ObjectAnimator animation = ObjectAnimator.ofFloat(view, "translationY",0f);
//        animation.setDuration(100);
//        llDomestic.setVisibility(View.GONE);
//        animation.start();
//
//    }
// slide the view from below itself to the current position
//    public void slideDown(View view,View llDomestic){
//        llDomestic.setVisibility(View.VISIBLE);
//        ObjectAnimator animation = ObjectAnimator.ofFloat(view, "translationY",   0f);
//        animation.setDuration(100);
//        animation.start();
//    }

    public void bottomNav(View view)
    {
        Intent in = new Intent(Animations.this, BottomNavigation.class);
        startActivity(in);
    }

}