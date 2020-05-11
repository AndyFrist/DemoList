package com.example.xuxiaopeng002.myapplication.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.example.ex_xuxiaopeng002.myapplication.R;
import com.example.xuxiaopeng002.myapplication.activity.login.SetGestureActivity;
import com.example.xuxiaopeng002.myapplication.util.ConstantsKey;
import com.example.xuxiaopeng002.myapplication.util.SpUtil;
import com.example.xuxiaopeng002.myapplication.util.ToastUtils;
import com.example.xuxiaopeng002.myapplication.util.finger.AonFingerChangeCallback;
import com.example.xuxiaopeng002.myapplication.util.finger.FingerManager;
import com.example.xuxiaopeng002.myapplication.util.finger.SharePreferenceUtil;
import com.example.xuxiaopeng002.myapplication.util.finger.SimpleFingerCheckCallback;
import com.example.xuxiaopeng002.myapplication.view.iosuiswitch.IosSwitch;

public class SafeCenterActivity extends BaseActivity {

    private CheckBox fingerLoginCk;
    private CheckBox gestureLoginCk;
    private IosSwitch iosSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safe_center);
        fingerLoginCk = findViewById(R.id.fingerLoginCk);
        gestureLoginCk = findViewById(R.id.gestureLoginCk);
        iosSwitch = findViewById(R.id.iosSwitch);
        iosSwitch.setOnToggleListener(new IosSwitch.OnToggleListener() {
            @Override
            public void onSwitchChangeListener(boolean switchState) {
                ToastUtils.show(switchState ? "开" :"关");
            }
        });
        String  Gesture = (String) SpUtil.mCommonSp().get("Gesture","");
        if (TextUtils.isEmpty(Gesture)){
            gestureLoginCk.setChecked(false);
        }else {
            gestureLoginCk.setChecked(true);
        }
        gestureLoginCk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    startActivity(new Intent(SafeCenterActivity.this, SetGestureActivity.class));
                }else{
                    SpUtil.mCommonSp().put("Gesture","");
                }
            }
        });

        boolean  finger = (boolean) SpUtil.mCommonSp().get(ConstantsKey.finger_open,false);
        if (finger){
            fingerLoginCk.setChecked(true);
        }else {
            fingerLoginCk.setChecked(false);
        }

        fingerLoginCk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    initFinger();
                }else{
                    SpUtil.mCommonSp().put(ConstantsKey.finger_open,false);
                }
            }
        });
    }

    private void initFinger(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            switch (FingerManager.checkSupport(SafeCenterActivity.this)) {
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

                                    FingerManager.updateFingerData(SafeCenterActivity.this);
                                    SharePreferenceUtil.saveData(SafeCenterActivity.this, SharePreferenceUtil.KEY_IS_FINGER_CHANGE, "");
                                }
                            })
                            .create()
                            .startListener(SafeCenterActivity.this);

                    break;
                default:
                    break;
            }
        }

    }
}
