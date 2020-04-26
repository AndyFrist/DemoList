package com.example.xuxiaopeng002.myapplication.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDelegate;
import android.util.Pair;
import android.view.View;
import android.widget.Button;

import com.example.ex_xuxiaopeng002.myapplication.R;
import com.example.xuxiaopeng002.myapplication.activity.reveal.OneActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.support.v7.app.AppCompatDelegate.setDefaultNightMode;

public class MainActivitys extends BaseActivity {

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

    @BindView(R.id.reveal)
    Button reveal;
    @BindView(R.id.textSwitcher)
    Button textSwitcher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activitys);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.webview, R.id.sensor, R.id.iosvoiceview, R.id.animator, R.id.para, R.id.listviewanimator, R.id.switcher_day_night, R.id.reveal, R.id.dragRecycleView, R.id.textSwitcher,R.id.stick,R.id.aboutHome})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.webview:
                Intent intent = new Intent(this, WebViewActivity.class);
                intent.putExtra("webview", "hello!");
                startActivityForResult(intent, 1, null);
                break;
            case R.id.sensor:
                startActivityY(new Intent(this, SensorManagerActivity.class));
                break;
            case R.id.iosvoiceview:
                startActivityX(new Intent(this, HistogramActivity.class));
                break;
            case R.id.animator:
                startActivityY(new Intent(this, AnimatorActivity.class));
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
            case R.id.dragRecycleView:
                startActivity(new Intent(this, DragRecyclerViewActivity.class));
                break;
            case R.id.reveal:
                startActivity(new Intent(this, OneActivity.class));
                break;
            case R.id.textSwitcher:
                startActivity(new Intent(this, TextSwitcherActivity.class));
                break;
            case R.id.aboutHome:
                startActivity(new Intent(this, AboutHomeActivity.class));
                break;
            case R.id.stick:
                startActivity(new Intent(this, StickActivity.class));
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {
            String result = data.getExtras().getString("result");
            webview.setText("" + result);
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
