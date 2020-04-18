package com.example.ex_xuxiaopeng002.myapplication.activity.reveal;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;

import com.example.ex_xuxiaopeng002.myapplication.R;
import com.example.ex_xuxiaopeng002.myapplication.util.AndroidUtils;

public class TwoActivity extends AppCompatActivity {
    private View rootTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
        rootTwo = findViewById(R.id.rootTwo);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                break;

            case MotionEvent.ACTION_UP:

                Animator anim = ViewAnimationUtils.createCircularReveal(rootTwo,(int)(event.getX()), (int)(event.getY()), AndroidUtils.getScreenHeight(this), 0);
                anim.setDuration(1000);
                anim.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        finish();
                    }
                });
                anim.start();

                break;
        }




        return super.onTouchEvent(event);
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0,0);
    }
}
