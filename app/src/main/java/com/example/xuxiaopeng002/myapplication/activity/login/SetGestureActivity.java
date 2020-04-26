package com.example.xuxiaopeng002.myapplication.activity.login;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.ex_xuxiaopeng002.myapplication.R;
import com.example.xuxiaopeng002.myapplication.activity.BaseActivity;
import com.example.xuxiaopeng002.myapplication.activity.MainActivitys;
import com.example.xuxiaopeng002.myapplication.lock.GestureLockDisplayView;
import com.example.xuxiaopeng002.myapplication.lock.GestureLockLayout;
import com.example.xuxiaopeng002.myapplication.lock.JDLockView;
import com.example.xuxiaopeng002.myapplication.util.SpUtil;
import com.example.xuxiaopeng002.myapplication.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

public class SetGestureActivity extends BaseActivity implements View.OnClickListener {
    TextView titles;
    GestureLockLayout mGestureLockLayout;
    TextView mTvReset;
    GestureLockDisplayView mLockDisplayView;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_gesture);
        handler = new Handler();
        titles = findViewById(R.id.titles);
        mGestureLockLayout = findViewById(R.id.lock_pattern);
        mTvReset = findViewById(R.id.m_tv_reset);
        mLockDisplayView = findViewById(R.id.gesture_display);
        mTvReset.setOnClickListener(this);
        initLockView();
    }

    private void resetHintText() {
        titles.setText("至少覆盖4个点");
        titles.setTextColor(getResources().getColor(R.color.color333333));
    }

    private void initLockView() {
        resetHintText();
        mLockDisplayView.setDotCount(3);
        mGestureLockLayout.setDotCount(3);
        mGestureLockLayout.setMinCount(4);
        mGestureLockLayout.setMatchedPathColor(getResources().getColor(R.color.colorPrimary));
        mGestureLockLayout.setUnmatchedPathColor(getResources().getColor(R.color.color_red));
        mGestureLockLayout.setLockView(new JDLockView(this));
        mGestureLockLayout.setMode(GestureLockLayout.RESET_MODE);

        mGestureLockLayout.setOnLockResetListener(new GestureLockLayout.OnLockResetListener() {
            @Override
            public void onConnectCountUnmatched(int connectCount, int minCount) {
                titles.setText("至少覆盖"+minCount+"个点");
            }

            @Override
            public void onFirstPasswordFinished(List<Integer> answerList) {
                mTvReset.setVisibility(View.VISIBLE);
                titles.setText("请确认你的手势密码");
                mLockDisplayView.setAnswer(answerList);
                resetGesture();
            }

            @Override
            public void onSetPasswordFinished(boolean isMatched, final List<Integer> answerList) {
                if (isMatched) {
                    ToastUtils.show("手势密码设置成功");
                    SpUtil.mCommonSp().put("Gesture",answerList.toString());
                    mGestureLockLayout.resetGesture();
                    mGestureLockLayout.setTouchable(false);
                    goMain();


                } else {
                    titles.setTextColor(getResources().getColor(R.color.color_red));
                    titles.setText("与上次绘制不一致，请重试");
                }
            }
        });
    }

    private void resetGesture() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mGestureLockLayout.resetGesture();

            }
        }, 200);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.m_tv_reset:
                resetHintText();
                mTvReset.setVisibility(View.GONE);
                mGestureLockLayout.resetGesture();
                mGestureLockLayout.setMode(GestureLockLayout.RESET_MODE);
                mLockDisplayView.setAnswer(new ArrayList<Integer>(1));
                break;
        }
    }
    private void goMain() {
        startActivity(new Intent(this, MainActivitys.class));
        finish();
    }
}
