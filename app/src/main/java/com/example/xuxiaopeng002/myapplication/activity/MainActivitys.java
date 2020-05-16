package com.example.xuxiaopeng002.myapplication.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDelegate;
import android.util.Pair;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

    @OnClick({R.id.webview, R.id.sensor, R.id.iosvoiceview, R.id.animator, R.id.para, R.id.listviewanimator, R.id.switcher_day_night, R.id.reveal, R.id.dragRecycleView, R.id.textSwitcher,R.id.stick,R.id.aboutHome,R.id.safeCenter})
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
            case R.id.safeCenter:
                startActivity(new Intent(this, SafeCenterActivity.class));
                break;
            default:
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
        ToastMessage("黑白模式切换", "切换成功");
    }

    /**
     * 将Toast封装在一个方法中，在其它地方使用时直接输入要弹出的内容即可
     */
    private void ToastMessage(String titles, String messages) {
        //LayoutInflater的作用：对于一个没有被载入或者想要动态载入的界面，都需要LayoutInflater.inflate()来载入，LayoutInflater是用来找res/layout/下的xml布局文件，并且实例化
        LayoutInflater inflater = getLayoutInflater();//调用Activity的getLayoutInflater()
        View view = inflater.inflate(R.layout.toast_style, null); //加載layout下的布局
        ImageView iv = view.findViewById(R.id.tvImageToast);
        iv.setImageResource(R.mipmap.ic_category_0);//显示的图片
        TextView title = view.findViewById(R.id.tvTitleToast);
        title.setText(titles); //toast的标题
        TextView text = view.findViewById(R.id.tvTextToast);
        text.setText(messages); //toast内容
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER, 12, 20);//setGravity用来设置Toast显示的位置，相当于xml中的android:gravity或android:layout_gravity
        toast.setDuration(Toast.LENGTH_SHORT);//setDuration方法：设置持续时间，以毫秒为单位。该方法是设置补间动画时间长度的主要方法
        toast.setView(view); //添加视图文件
        toast.show();
    }
}
