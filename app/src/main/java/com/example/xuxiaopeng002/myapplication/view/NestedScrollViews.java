package com.example.xuxiaopeng002.myapplication.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @Author: ex-xuxiaopeng002
 * @CreateDate: 2019-12-26 16:56
 * @Description: java类作用描述
 */
public class NestedScrollViews extends NestedScrollView {

    public NestedScrollViews(@NonNull Context context) {
        super(context);
    }

    public NestedScrollViews(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public NestedScrollViews(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private float downX; // 按下的x坐标
    private float downY; // 按下的y坐标

    /**
     * 拦截判断
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = ev.getX();
                downY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:

                float xOffset = Math.abs(ev.getX() - downX);
                float yOffset = Math.abs(ev.getY() - downY);

                if(xOffset < yOffset && yOffset > 15){ // 超出一定距离时,才拦截
                    return true; // 拦截此次触摸事件, 界面的滚动
                }

                break;
            case MotionEvent.ACTION_UP:

                break;

            default:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }
}
