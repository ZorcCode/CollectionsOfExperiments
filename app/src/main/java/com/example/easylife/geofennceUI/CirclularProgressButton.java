package com.example.easylife.geofennceUI;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.easylife.R;

public class CirclularProgressButton extends View implements View.OnTouchListener {
    private String successText;
    float progress = 0;
    float scale = 0.2f;
    float textsize ;
    Callback callback;
    float progressBarWidth;
    float shadowPadding;
    int progressColor;
    int buttonColor;
    int shadowColor;
    int progressBackgroundColor;
    String text;
    boolean success = false;
    private boolean reverse = false;
    int duration ;
     Vibrator vibe;

    public CirclularProgressButton(Context context) {
        super(context);
        commonConstruct(context);
    }
    public CirclularProgressButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs,R.styleable.CirclularProgressButton);
        progressBarWidth = array.getDimension(R.styleable.CirclularProgressButton_progressBarWidth,50);
        shadowPadding = array.getDimension(R.styleable.CirclularProgressButton_shadowPadding,50);
        textsize = array.getDimension(R.styleable.CirclularProgressButton_android_textSize,15);
        progressColor = array.getInt(R.styleable.CirclularProgressButton_progressColor,R.color.progressColor);
        progressBackgroundColor = array.getInt(R.styleable.CirclularProgressButton_progressBackgroundColor,R.color.progressBackground);
        buttonColor = array.getInt(R.styleable.CirclularProgressButton_buttonColor,R.color.mainButton);
        shadowColor = array.getInt(R.styleable.CirclularProgressButton_shadowColor,R.color.shadowColor);
        duration = array.getInt(R.styleable.CirclularProgressButton_android_duration,1000);
        text = array.getString(R.styleable.CirclularProgressButton_mainText);
        successText = array.getString(R.styleable.CirclularProgressButton_successText);
        array.recycle();
        commonConstruct(context);
    }
    public void setCallback(Callback callback){
        this.callback=callback;
    }
    Paint paint;
    BlurMaskFilter blurMaskFilter;
    private void commonConstruct(Context context){
    vibe = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if(text==null)   text = "Clock In";
        paint = new Paint();
        blurMaskFilter = new BlurMaskFilter(40, BlurMaskFilter.Blur.NORMAL);
        setOnTouchListener(this);
    }
    ValueAnimator animator;
    private void animateProgress(){
        if(reverse)
            animator = ValueAnimator.ofFloat(progress,0);
        else
            animator = ValueAnimator.ofFloat(progress,360);
        animator.addUpdateListener(animation -> {
            float value = (float) animation.getAnimatedValue();
            progress = value;
//            vibe.vibrate(1/1);
            setScaleX(1+value*scale/360);
            setScaleY(1+value*scale/360);
            invalidate();
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) { }
            @Override
            public void onAnimationEnd(Animator animation) {
                Log.e("86", "----------------------------------------CirclularProgressButton -> onAnimationEnd: "+progress);
                if ((!reverse && progress==360) || (reverse && progress==0)) {
                    if (callback!=null)
                    callback.onComplete();
//                    if(reverse) progress = 0;
                    success = !success;
                }
            }
            @Override
            public void onAnimationCancel(Animator animation) {
                ValueAnimator valueAnimator;
                if (reverse)
                    valueAnimator = ValueAnimator.ofFloat(progress,360);
                else
                    valueAnimator = ValueAnimator.ofFloat(progress,0);
                valueAnimator.addUpdateListener(animation1 -> {
                    float value = (float) animation1.getAnimatedValue();
                    progress = value;
                    setScaleX(1+value*scale/360);
                    setScaleY(1+value*scale/360);
                    Log.e("86", "CirclularProgressButton -> progress: "+progress);
                    invalidate();
                });
                valueAnimator.start();
                invalidate();
                if (callback!=null)
                    callback.onCancel();
            }
            @Override
            public void onAnimationRepeat(Animator animation) {}
        });
        animator.setDuration(duration);
        animator.start();
    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
//        if(!success)
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                animateProgress();
                break;
            case MotionEvent.ACTION_UP:
                animator.cancel();
                break;
        }
        return true;
    }
    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        int width = getWidth();
        int height = getHeight();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(progressBarWidth);

        paint.setMaskFilter(blurMaskFilter);
        paint.setColor(shadowColor);
        canvas.drawCircle(width/2.0f,height/2.0f,width/2.0f-progressBarWidth-shadowPadding,paint);

        paint.setMaskFilter(null);
        paint.setColor(progressBackgroundColor);
        canvas.drawArc(progressBarWidth+shadowPadding,progressBarWidth+shadowPadding,width-progressBarWidth-shadowPadding,height-progressBarWidth-shadowPadding,progress,360f,false,paint);

        paint.setColor(progressColor);
        canvas.drawArc(progressBarWidth+shadowPadding,progressBarWidth+shadowPadding,width-progressBarWidth-shadowPadding,height-progressBarWidth-shadowPadding,-90,progress,false,paint);

//        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
//        canvas.drawCircle(width/2.0f,height/2.0f,width/2.0f-progressBarWidth*2,paint);

        paint.setColor(buttonColor);
        canvas.drawCircle(width/2.0f,height/2.0f,width/2.0f-progressBarWidth-shadowPadding,paint);

        String currentText = success ? successText : text;
//        Log.e("153", "CirclularProgressButton -> onDraw: "+currentText);
        paint.setColor(Color.WHITE);
        paint.setTextSize(textsize);
        float textwidth = paint.measureText(currentText);
        canvas.drawText(currentText, width/2f-(textwidth/2), height/2f+textsize/4f, paint);
        super.onDraw(canvas);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
    interface Callback{
        void onComplete();
        void onCancel();
    }

    public void setReverse(boolean reverse) {
        if(reverse) {
            setScaleX(1 + scale);
            setScaleY(1 + scale);
        }
        else
        {
            setScaleX(1);
            setScaleY(1);
        }
        this.reverse = reverse;
    }

    public boolean isReverse()
    {
        return  reverse;
    }
}
