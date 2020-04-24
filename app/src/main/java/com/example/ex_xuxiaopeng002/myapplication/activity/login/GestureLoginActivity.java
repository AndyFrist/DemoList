package com.example.ex_xuxiaopeng002.myapplication.activity.login;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.ex_xuxiaopeng002.myapplication.R;
import com.example.ex_xuxiaopeng002.myapplication.activity.BaseActivity;
import com.example.ex_xuxiaopeng002.myapplication.activity.MainActivitys;
import com.example.ex_xuxiaopeng002.myapplication.lock.GestureLockLayout;
import com.example.ex_xuxiaopeng002.myapplication.lock.JDLockView;
import com.example.ex_xuxiaopeng002.myapplication.util.SpUtil;

import butterknife.BindView;

public class GestureLoginActivity extends BaseActivity implements View.OnClickListener {
    GestureLockLayout mLockLayout;
    TextView tvOtherUser;
    TextView mTvUsername;

    private AlertDialog pwdErrorDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture_login);
        mLockLayout = findViewById(R.id.lock_pattern);
        tvOtherUser = findViewById(R.id.tv_other_user);
        mTvUsername = findViewById(R.id.m_tv_username);
        tvOtherUser.setOnClickListener(this);
        initView();
    }

    private void initView() {
        mLockLayout.setMode(GestureLockLayout.VERIFY_MODE);
        mLockLayout.setDotCount(3);
        int MAX_TRY_TIME = 5;
        mLockLayout.setTryTimes(MAX_TRY_TIME);
        mLockLayout.setLockView(new JDLockView(this));
        mLockLayout.setUnmatchedPathColor(getResources().getColor(R.color.color_red));
        String  Gesture = (String) SpUtil.mCommonSp().get("Gesture","");
        mLockLayout.setAnswer(Gesture);
        mLockLayout.setOnLockVerifyListener(new GestureLockLayout.OnLockVerifyListener() {
            @Override
            public void onGestureSelected(int id) {

            }

            @Override
            public void onGestureFinished(boolean isMatched) {
                resetGesture();
                if (isMatched) {
                    mLockLayout.resetGesture();
                    mLockLayout.setTouchable(false);
                    goMain();
                } else {
                    mTvUsername.setTextColor(Color.parseColor("#FD3D01"));
                    mTvUsername.setText("手势密码错误,请重试");
                }
            }

            @Override
            public void onGestureTryTimesBoundary() {
                mTvUsername.setTextColor(Color.parseColor("#fd3d01"));
                mTvUsername.setText("手势密码错误超过5次");
                if (pwdErrorDialog == null) {
                    pwdErrorDialog = new AlertDialog.Builder(GestureLoginActivity.this)
                            .setMessage("您已连续输错5次密码，请重新登录，登录成功后可重设手势密码。")
                            .setPositiveButton("退出", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    SpUtil.mCommonSp().put("Gesture","");
                                    finish();
                                }
                            })
                            .setCancelable(false)
                            .create();
                }
                pwdErrorDialog.show();
                if (pwdErrorDialog.getButton(DialogInterface.BUTTON_POSITIVE) != null) {
                    pwdErrorDialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextSize(15);
                }
                if (pwdErrorDialog.findViewById(android.R.id.message) != null) {
                    ((TextView) pwdErrorDialog.findViewById(android.R.id.message)).setTextSize(13);
                }
            }
        });
    }

    private void goMain() {
        startActivity(new Intent(this, MainActivitys.class));
        finish();
    }

    private void resetGesture() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mLockLayout.resetGesture();
            }
        }, 200);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_other_user:
                SpUtil.mCommonSp().put("Gesture","");
                startActivity(new Intent(this,SetGestureActivity.class));
                finish();
                break;
        }
    }
}
