package com.example.xuxiaopeng002.myapplication.activity.reveal;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;

import com.example.ex_xuxiaopeng002.myapplication.R;
import com.example.xuxiaopeng002.myapplication.activity.BaseActivity;
import com.example.xuxiaopeng002.myapplication.util.AndroidUtils;

public class OneActivity extends BaseActivity {
    private View rootOne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);
        rootOne = findViewById(R.id.rootOne);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                break;

            case MotionEvent.ACTION_UP:

                rootOne.setBackgroundColor(getResources().getColor(R.color.color_red));
                Animator anim = ViewAnimationUtils.createCircularReveal(rootOne, (int) (event.getX()), (int) (event.getY()), 0, AndroidUtils.getScreenHeight(this));
                anim.setDuration(1000);
                anim.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        startActivity(new Intent(OneActivity.this, TwoActivity.class));
                        rootOne.setBackgroundColor(getResources().getColor(R.color.color_blue));
                        overridePendingTransition(0,0);
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
