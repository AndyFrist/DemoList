package com.example.ex_xuxiaopeng002.myapplication.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.ex_xuxiaopeng002.myapplication.R;
import com.example.ex_xuxiaopeng002.myapplication.view.CustomTextSwitcher;

public class TextSwitcherActivity extends BaseActivity {
    CustomTextSwitcher tvDownUpTextSwitcher;
    CustomTextSwitcher tvLeftRightTextSwitcher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_switcher);


        // 数据源
        String[] data = new String[]{
                "解放军于近日在东南沿海等海空域组织演习。",
                "北京市公布了生活垃圾分类治理三年行动计划。",
                "17日凌晨。",
                "中国辛鑫夺得世锦赛公开水域游泳的金牌。",
        };

        // 上下滚动
        tvDownUpTextSwitcher = findViewById(R.id.tvDownUpTextSwitcher);
        tvDownUpTextSwitcher.setInAnimation(R.anim.animation_down_up_in_animation)
                .setOutAnimation(R.anim.animation_down_up_out_animation)
                .bindData(data)
                .startSwitch(1000L);

        // 左右滚动
        tvLeftRightTextSwitcher = findViewById(R.id.tvLeftRightTextSwitcher);
        tvLeftRightTextSwitcher.setInAnimation(android.R.anim.slide_in_left)
                .setOutAnimation(android.R.anim.slide_out_right)
                .bindData(data)
                .startSwitch(1000L);

    }
}
