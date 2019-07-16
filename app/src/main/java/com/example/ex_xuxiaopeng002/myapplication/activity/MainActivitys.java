package com.example.ex_xuxiaopeng002.myapplication.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.Pair;
import android.view.View;
import android.widget.Button;

import com.example.ex_xuxiaopeng002.myapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.support.v7.app.AppCompatDelegate.setDefaultNightMode;

public class MainActivitys extends AppCompatActivity {

    @BindView(R.id.webview)
    Button webview;
    @BindView(R.id.sensor)
    Button sensor;
    @BindView(R.id.iosvoiceview)
    Button iosvoiceview;
    @BindView(R.id.animator)
    Button animator;
    @BindView(R.id.element)
    View element;
    @BindView(R.id.para)
    View para;
    @BindView(R.id.listviewanimator)
    Button listviewanimator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activitys);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.webview, R.id.sensor, R.id.iosvoiceview, R.id.animator, R.id.para, R.id.listviewanimator, R.id.switcher_day_night})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.webview:
                Intent intent = new Intent(this, WebViewActivity.class);
                intent.putExtra("webview", "hello!");
                startActivityForResult(intent,1);
                break;
            case R.id.sensor:
                startActivity(new Intent(this, SensorManagerActivity.class));
                break;
            case R.id.iosvoiceview:
                startActivity(new Intent(this, HistogramActivity.class));
                break;
            case R.id.animator:
                startActivity(new Intent(this, AnimatorActivity.class));
                break;
            case R.id.para:
                Pair<View, String> logoPair = Pair.create(element, element.getTransitionName());
                Pair<View, String> rootPair = Pair.create(para, para.getTransitionName());
                startActivity(new Intent(this, ShareElementActivity.class), ActivityOptions.makeSceneTransitionAnimation(this, logoPair, rootPair).toBundle());
                break;
            case R.id.listviewanimator:
                startActivity(new Intent(this, MyRecycleViewActivity.class));
                break;
            case R.id.switcher_day_night:

                setNightMode();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {
            String result = data.getExtras().getString("result");
            webview.setText(""+result);
        }
    }

    private void setNightMode() {
        //  获取当前模式
        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        //  切换模式
        setDefaultNightMode(currentNightMode == Configuration.UI_MODE_NIGHT_NO ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
        //  重启Activity
        recreate();
    }


}
