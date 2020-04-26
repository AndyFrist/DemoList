package com.example.xuxiaopeng002.myapplication.view;

import android.content.Context;
import android.util.AttributeSet;

import com.example.xuxiaopeng002.myapplication.util.FontCustom;

/**
 * Created by ex-tanhongchuan001 on 2018/8/8.
 */

public class NumberTextView extends android.support.v7.widget.AppCompatTextView {
    public NumberTextView(Context context) {
        super(context);
        setTypeface(context);
    }

    public NumberTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(context);
    }

    public NumberTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTypeface(context);
    }

    private void setTypeface(Context context) {
        setPadding(0, 4, 0, 0);
        setTypeface(FontCustom.setFont(context));
    }
}
