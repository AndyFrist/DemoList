package com.example.xuxiaopeng002.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.andyfrist.fitstatusbarlibrary.StatusBarUtil;
import com.example.ex_xuxiaopeng002.myapplication.R;
import com.example.xuxiaopeng002.myapplication.util.ActivityManager;

/**
 * Created by xuxiaopeng
 * on 2020/4/18.
 * Description：
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //所有的activity都设置沉浸式的
        StatusBarUtil.translucentStatusBar(this);
        //设置字体颜色
        StatusBarUtil.systemStatusBarTextColor(this, false);
        ActivityManager.getInstance().add(this);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_no_anim);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode, Bundle options) {
        super.startActivityForResult(intent, requestCode, options);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_no_anim);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.slide_pop_out_right);
    }

    public void startActivityY(Intent intent) {
        startActivity(intent);
        overridePendingTransition(R.anim.activity_y_come, R.anim.slide_no_anim);
    }

    public void startActivityX(Intent intent) {
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_no_anim);

    }

    public void finishWithX() {
        overridePendingTransition(0, R.anim.slide_pop_out_right);
    }

    public void finishWithY() {
        overridePendingTransition(0, R.anim.activity_y_out);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.getInstance().finishActivity(this);
    }
}
