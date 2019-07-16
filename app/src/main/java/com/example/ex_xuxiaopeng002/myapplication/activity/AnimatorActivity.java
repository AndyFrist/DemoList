package com.example.ex_xuxiaopeng002.myapplication.activity;

import android.animation.ObjectAnimator;
import android.graphics.Path;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ex_xuxiaopeng002.myapplication.R;
import com.example.ex_xuxiaopeng002.myapplication.view.ChartView;
import com.example.ex_xuxiaopeng002.myapplication.view.GrowthTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AnimatorActivity extends AppCompatActivity {

    @BindView(R.id.chartview)
    ChartView chartview;
    @BindView(R.id.target)
    ImageView target;
    Path path;

    @BindView(R.id.text1)
    TextView text1;
    @BindView(R.id.text2)
    TextView text2;

    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<Float> value = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animator);
        ButterKnife.bind(this);
        path = new Path();
        path.moveTo(200, 200);
        path.lineTo(400, 200);
        path.lineTo(400, 600);
        path.lineTo(200, 600);
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

    }

    @OnClick({R.id.target, R.id.text1, R.id.text2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.target:
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(target, "translationX", "translationY", path);
                objectAnimator.setDuration(3000);
                objectAnimator.setInterpolator(new AnticipateOvershootInterpolator());
                objectAnimator.start();
                break;

            case R.id.text1:
                text1.setText(12.34 + "%");
                break;
            case R.id.text2:

//                text1.setText(10000 + "", TextView.BufferType.NORMAL);
                text2.setText(10000.34 + "%", TextView.BufferType.NORMAL);


                break;
        }
    }


}
