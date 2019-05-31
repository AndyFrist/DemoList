package com.example.ex_xuxiaopeng002.myapplication.activity;

import android.animation.ObjectAnimator;
import android.graphics.Path;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.ImageView;

import com.example.ex_xuxiaopeng002.myapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AnimatorActivity extends AppCompatActivity {

    @BindView(R.id.target)
    ImageView target;
    Path path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animator);
        ButterKnife.bind(this);
        path = new Path();
        path.moveTo(200,200);
        path.lineTo(800,200);
        path.lineTo(800, 1000);
        path.lineTo(200, 1000);
        path.close();
    }

    @OnClick(R.id.target)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.target:
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(target, "translationX", "translationY", path);
                objectAnimator.setDuration(3000);
                objectAnimator.setInterpolator(new AnticipateOvershootInterpolator());
                objectAnimator.start();
                break;
        }
    }
}
