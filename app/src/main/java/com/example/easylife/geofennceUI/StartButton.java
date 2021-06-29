package com.example.easylife.geofennceUI;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.easylife.R;

public class StartButton extends ConstraintLayout {

    ValueAnimator valueAnimator;
    ProgressBar progressBar;
    ImageButton start;
    boolean reverse = true;


    public StartButton(@NonNull Context context) {
        super(context);
        init(context);
    }

    public StartButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    private void init(Context context)
    {
        inflate(context, R.layout.start_button,this);
        CirclularProgressButton progressButton = findViewById(R.id.progressbutton);
        final Vibrator vibe = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

        progressButton.setCallback(new CirclularProgressButton.Callback() {
            @Override
            public void onComplete() {
                    progressButton.setReverse(!progressButton.isReverse());
//                vibe.vibrate(800);
                Log.e("43", "StartButton -> onComplete: "+progressButton.isReverse());
            }

            @Override
            public void onCancel() {

            }
        });
    }



    @Override
    public boolean performClick() {
        Log.e("70", "StartButton -> performClick: ");
        return super.performClick();
    }

}
