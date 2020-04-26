package com.example.xuxiaopeng002.myapplication.activity;

import android.animation.ObjectAnimator;
import android.graphics.Path;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;

import com.example.ex_xuxiaopeng002.myapplication.R;
import com.example.xuxiaopeng002.myapplication.view.ChartView;
import com.example.xuxiaopeng002.myapplication.view.GrowthTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AnimatorActivity extends BaseActivity {

    @BindView(R.id.chartview)
    ChartView chartview;
    @BindView(R.id.target)
    Button target;
    Path path;

    @BindView(R.id.text1)
    GrowthTextView text1;
    @BindView(R.id.text2)
    GrowthTextView text2;

    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<Float> value = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animator);
        ButterKnife.bind(this);
        path = new Path();
        path.moveTo(20, 20);
        path.lineTo(800, 20);
        path.lineTo(20, 800);
        path.lineTo(800, 800);
        path.close();


        for (int i = 0; i < 9; i++) {
            names.add("a" + i);
            value.add(1.0f / (i + 1));
        }


        chartview.setClickNum(3);
        chartview.setNameList(names);
        chartview.setValueList(value);
        chartview.setOnclickListener(new ChartView.OnBarClickListener() {
            @Override
            public void Onclick(int position) {
                Log.e("position", names.get(position - 1));
            }
        });

        text1.setText(1234 + "");
        text2.setText(12.34 + "%");
        text1.startAnimter();
        text2.startAnimter();

    }

    @OnClick({R.id.target})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.target:
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(target, "translationX", "translationY", path);
                objectAnimator.setDuration(3000);
                objectAnimator.setInterpolator(new LinearInterpolator());
                objectAnimator.setRepeatCount(ObjectAnimator.INFINITE);
                objectAnimator.setRepeatMode(ObjectAnimator.RESTART);
                objectAnimator.start();
                break;


        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("生命周期", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("生命周期", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("生命周期", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("生命周期", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("生命周期", "onDestroy");
    }
}
