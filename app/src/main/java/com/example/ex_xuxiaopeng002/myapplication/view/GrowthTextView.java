package com.example.ex_xuxiaopeng002.myapplication.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;

import com.example.ex_xuxiaopeng002.myapplication.R;

/**
 * @Author: ex-xuxiaopeng002
 * @CreateDate: 2019-07-04 11:18
 * @Description: 自增长动画的TextView
 */
public class GrowthTextView extends NumberTextView {
    Context context;

    public GrowthTextView(Context context) {
        this(context, null);
    }

    public GrowthTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GrowthTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setBackgroundColor(getContext().getResources().getColor(R.color.color_green));
    }


    boolean numFlag = false;


    public void setText(String text, boolean isAnimater) {
        if (isAnimater) {
            startAnimter();
        } else {
            setText(text);
        }
    }


    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);

        if (TextUtils.isEmpty(text)) {
            setBackgroundColor(getContext().getResources().getColor(R.color.color_green));
        }else{
            setBackgroundColor(getContext().getResources().getColor(R.color.transparent));
        }
    }

    public void startAnimter() {

        try {
            String text = getText().toString();
            if (TextUtils.isEmpty(text)) {
                return;
            }

            if (text.endsWith("%")) {
                text = text.replace("%", "").trim();
                numFlag = true;
            }

            float end = Float.parseFloat(text);


            ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, end);
            valueAnimator.setDuration(10000);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float growth = (float) animation.getAnimatedValue();
                    if (numFlag) {
                        setText(growth + "%", BufferType.NORMAL);
                    } else {
                        setText(growth + "", BufferType.NORMAL);
                    }
                    invalidate();
                }
            });

            valueAnimator.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onPreDraw() {
        Log.e("生命周期", "onPreDraw");
        return super.onPreDraw();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.e("生命周期", "onFinishInflate");
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.e("生命周期", "onSizeChanged");
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        Log.e("生命周期", "onTextChanged");

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.e("生命周期", "onMeasure");
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.e("生命周期", "onLayout");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e("生命周期", "onDraw");
    }


}
