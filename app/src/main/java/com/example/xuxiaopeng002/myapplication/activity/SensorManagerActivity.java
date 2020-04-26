package com.example.xuxiaopeng002.myapplication.activity;

import android.animation.ObjectAnimator;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.ex_xuxiaopeng002.myapplication.R;
import com.example.xuxiaopeng002.myapplication.view.ArcProgressView;
import com.example.xuxiaopeng002.myapplication.view.DashboardView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SensorManagerActivity extends BaseActivity implements SensorEventListener {
    private static final String TAG = "SensorManagerActivity";
    @BindView(R.id.m_radioButton_wk)
    RadioButton mRadioButtonWk;
    @BindView(R.id.m_radioButton)
    RadioButton mRadioButton;
    @BindView(R.id.m_radioButton_back)
    RadioButton mRadioButtonBack;
    @BindView(R.id.m_radiogroup)
    RadioGroup mRadiogroup;
    @BindView(R.id.dashboard_view_4)
    DashboardView mDashboardView;
    private boolean isAnimFinished = true;

    private ArcProgressView hello;
    private View view;
    private float size1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_manager);
        ButterKnife.bind(this);
        hello = findViewById(R.id.hello);
        hello.setValues(100, (float) 95.50, "中心");

        view = findViewById(R.id.view);
        size1 = getResources().getDimension(R.dimen.size_01);
    }

    SensorManager mSensorManager;

    @Override
    protected void onStart() {
        super.onStart();
        //获取 SensorManager 负责管理传感器
        mSensorManager = ((SensorManager) getSystemService(SENSOR_SERVICE));
        if (mSensorManager != null) {
            //获取加速度传感器
            Sensor mAccelerometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            if (mAccelerometerSensor != null) {
                mSensorManager.registerListener(this, mAccelerometerSensor, SensorManager.SENSOR_DELAY_UI);
            }
        }
    }

    @Override
    protected void onPause() {
        // 务必要在pause中注销 mSensorManager
        // 否则会造成界面退出后摇一摇依旧生效的bug
        if (mSensorManager != null) {
            mSensorManager.unregisterListener(this);
        }
        super.onPause();
    }

    ///////////////////////////////////////////////////////////////////////////
// SensorEventListener回调方法
///////////////////////////////////////////////////////////////////////////
    @Override
    public void onSensorChanged(SensorEvent event) {
        int type = event.sensor.getType();
        //获取三个方向值
        float[] values = event.values;
        float x = values[0];
        float y = values[1];
        float z = values[2];
        Log.d(TAG, "x = " + x + "y = " + y + "z = " + z);

        if ((Math.abs(x) > 17 || Math.abs(y) > 17 || Math.abs(z) > 17)) {
            Log.d(TAG, "震动了" + event.accuracy);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @OnClick({R.id.m_radioButton_wk, R.id.m_radioButton, R.id.m_radioButton_back, R.id.m_radiogroup, R.id.dashboard_view_4})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.m_radioButton_wk:
                aa(0);
                break;
            case R.id.m_radioButton:
                aa(120);
                break;
            case R.id.m_radioButton_back:
                aa(240);
                break;
            case R.id.m_radiogroup:
                break;
            case R.id.dashboard_view_4:

                mDashboardView.setVelocity(7000,9);


                break;
        }
    }

    private void aa(int size) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationX", size * size1);//setTranslationY(Float 200)
        animator.setDuration(500);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.start();
    }


}
