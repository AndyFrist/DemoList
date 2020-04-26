package com.example.xuxiaopeng002.myapplication.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.example.ex_xuxiaopeng002.myapplication.R;


/**
 * 公告新闻切换
 *
 */
public class CustomTextSwitcher extends TextSwitcher implements ViewSwitcher.ViewFactory {

    private Context mContext;
    private String[] mData;
    private final long DEFAULT_TIME_SWITCH_INTERVAL = 1000;//1s
    private long mTimeInterval = DEFAULT_TIME_SWITCH_INTERVAL;
    private int mCurrentIndex = 0;

    public CustomTextSwitcher(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        setFactory(this);
    }

    public CustomTextSwitcher setInAnimation(int animationResId){
        Animation animation = AnimationUtils.loadAnimation(this.mContext, animationResId);
        setInAnimation(animation);
        return this;
    }

    public CustomTextSwitcher setOutAnimation(int animationResId){
        Animation animation = AnimationUtils.loadAnimation(this.mContext, animationResId);
        setOutAnimation(animation);
        return this;
    }

    /**
     * 通知/公告数据绑定
     * @param data
     * @return
     */
    public CustomTextSwitcher bindData(String[] data){
        this.mData = data;
        return this;
    }

    public void startSwitch(long timeInterval){
        this.mTimeInterval = timeInterval;
        if (mData != null && mData.length != 0) {
            mSwitchHandler.sendEmptyMessage(0);
        }else{
            throw new RuntimeException("data is empty");
        }
    }

    /**
     * 工厂类中创建TextView供给TextSwitcher使用
     * @return
     */
    @Override
    public View makeView() {
        TextView view = new TextView(this.mContext);

        Drawable drawable = getResources().getDrawable(R.mipmap.next);
        /// 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        //设置text 左边图片
        view.setCompoundDrawables(null, null, drawable, null);

        return view;
    }

    @SuppressLint("HandlerLeak")
    private Handler mSwitchHandler = new Handler(){
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            int index = mCurrentIndex % mData.length;
            mCurrentIndex++;
            setText(mData[index]);
            sendEmptyMessageDelayed(0, mTimeInterval);
        }
    };

}
