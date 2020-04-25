package com.example.ex_xuxiaopeng002.myapplication.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class MyScrollView extends ScrollView
{
    private ScrollViewListener scrollViewListener = null;

    public interface ScrollViewListener
    {
        void onScrollChanged(MyScrollView scrollView, int l, int t, int oldl, int oldt);
    }

    public void setScrollViewListener(ScrollViewListener scrollViewListener)
    {
        this.scrollViewListener = scrollViewListener;
    }

    public MyScrollView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    //此方法受保护的
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt)
    {
        super.onScrollChanged(l, t, oldl, oldt);
        if (scrollViewListener != null)
        {
            scrollViewListener.onScrollChanged(this, l, t, oldl, oldt);
        }
    }
}