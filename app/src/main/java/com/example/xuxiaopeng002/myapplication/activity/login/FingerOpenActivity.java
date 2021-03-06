package com.example.xuxiaopeng002.myapplication.activity.login;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ex_xuxiaopeng002.myapplication.R;
import com.example.xuxiaopeng002.myapplication.activity.BaseActivity;
import com.example.xuxiaopeng002.myapplication.activity.MainActivitys;
import com.example.xuxiaopeng002.myapplication.util.ConstantsKey;
import com.example.xuxiaopeng002.myapplication.util.SpUtil;
import com.example.xuxiaopeng002.myapplication.util.ToastUtils;
import com.example.xuxiaopeng002.myapplication.util.finger.AonFingerChangeCallback;
import com.example.xuxiaopeng002.myapplication.util.finger.FingerManager;
import com.example.xuxiaopeng002.myapplication.util.finger.SharePreferenceUtil;
import com.example.xuxiaopeng002.myapplication.util.finger.SimpleFingerCheckCallback;

public class FingerOpenActivity extends BaseActivity implements View.OnClickListener {

    private TextView fingerSkipTV;
    private ImageView mIv;
    private CheckBox fingerRemindCk;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finger_open);
        fingerSkipTV = findViewById(R.id.fingerSkipTV);
        mIv = findViewById(R.id.mIv);
        fingerRemindCk = findViewById(R.id.fingerRemindCk);
        mIv.setOnClickListener(this);
        fingerSkipTV.setOnClickListener(this);

    }

    private void initFinger(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            switch (FingerManager.checkSupport(FingerOpenActivity.this)) {
                case DEVICE_UNSUPPORTED:
                    ToastUtils.show("您的设备不支持指纹");
                    break;
                case SUPPORT_WITHOUT_DATA:
                    ToastUtils.show("请在系统录入指纹后再验证");
                    break;
                case SUPPORT:
                    FingerManager.build().setApplication(getApplication())
                            .setTitle("指纹验证")
                            .setDes("请按下指纹")
                            .setNegativeText("取消")
                            .setFingerCheckCallback(new SimpleFingerCheckCallback() {
                                @Override
                                public void onSucceed() {
                                    ToastUtils.show("验证成功");
                                    SpUtil.mCommonSp().put(ConstantsKey.finger_open,true);
                                    goMain();
                                }

                                @Override
                                public void onError(String error) {
                                    ToastUtils.show("验证失败");
                                }

                                @Override
                                public void onCancel() {
                                    ToastUtils.show("您取消了识别");
                                }
                            })
                            .setFingerChangeCallback(new AonFingerChangeCallback() {
                                @Override
                                protected void onFingerDataChange() {
                                    ToastUtils.show("指纹数据发生了变化");

                                    FingerManager.updateFingerData(FingerOpenActivity.this);
                                    SharePreferenceUtil.saveData(FingerOpenActivity.this, SharePreferenceUtil.KEY_IS_FINGER_CHANGE, "");
                                }
                            })
                            .create()
                            .startListener(FingerOpenActivity.this);

                    break;
                default:
                    break;
            }
        }

    }
    private void goMain() {
        startActivity(new Intent(this, MainActivitys.class));
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fingerSkipTV:
                if (fingerRemindCk.isChecked()) {
                    SpUtil.mCommonSp().put(ConstantsKey.finger_remind_never,true);
                }
                startActivity(new Intent(this,SetGestureActivity.class));
                break;
            case R.id.mIv:
                initFinger();
                break;
            default:
                break;
        }
    }
}
