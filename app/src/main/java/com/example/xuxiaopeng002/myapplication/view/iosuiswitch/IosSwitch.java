package com.example.xuxiaopeng002.myapplication.view.iosuiswitch;

/**
 * @Date: 2020-05-08
 * @author: ex-xuxiaopeng002
 * @Description:
 */

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

/**
 * Created by caihan on 2017/2/10.
 * 仿iOS的UiSwitch控件
 */
public class IosSwitch extends View implements View.OnClickListener {

    private static final String TAG = "IosSwitch";

    private final int BORDER_WIDTH = 2;//边框宽度

    private int mBasePlaneColor = Color.parseColor("#e6e6e6");//底盘颜色,布局描边颜色
    private int mOpenSlotColor = Color.parseColor("#ff9113");//开启时手柄滑动槽的颜色
    private int mOffSlotColor = Color.parseColor("#e6e6e6");//关闭时手柄滑动槽的颜色

    private int mSlotColor;

    private RectF mRect = new RectF();

    //绘制参数
    private float mBackPlaneRadius;//底板的圆形半径
    private float mSpotRadius;//手柄半径

    private float spotStartX;//手柄的起始X位置,切换时平移改变它
    private float mSpotY;//手柄的起始X位置，不变
    private float mOffSpotX;//关闭时,手柄的水平位置

    private Paint mPaint;//画笔

    private boolean mIsToggleOn = false;//当前开关标记
    private boolean isTouchEvent = false;//是否由滑动事件消费掉
    private boolean isMoveing = false;//是否还在Touch相应中

    private OnToggleListener mOnToggleListener;//toggle事件监听

    private GestureUtils mGestureUtils;//手势工具类

    public interface OnToggleListener {
        void onSwitchChangeListener(boolean switchState);
    }

    public IosSwitch(Context context) {
        super(context);
        init(context);
    }

    public IosSwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        setOnClickListener(this);
        setEnabled(true);
        mGestureUtils = new GestureUtils();
    }

    @Override
    public void onClick(View v) {
        onClickToggle();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mGestureUtils.actionDown(event);
                isTouchEvent = false;
                isMoveing = false;
                break;
            case MotionEvent.ACTION_MOVE:
                mGestureUtils.actionMove(event);
                if (mGestureUtils.getGesture(GestureUtils.Gesture.PullLeft)) {
                    //左滑,关闭
                    isTouchEvent = true;
                    touchToggle(false);
                    return true;
                } else if (mGestureUtils.getGesture(GestureUtils.Gesture.PullRight)) {
                    //右滑,开启
                    isTouchEvent = true;
                    touchToggle(true);
                    return true;
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
            case MotionEvent.ACTION_UP:
                isMoveing = false;
                if (isTouchEvent) {
                    //不会触发Onclick事件了
                    return true;
                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int hMode = MeasureSpec.getMode(heightMeasureSpec);
        int wSize = MeasureSpec.getSize(widthMeasureSpec);
        int hSize = MeasureSpec.getSize(heightMeasureSpec);
        int resultWidth = wSize;
        int resultHeight = hSize;
        Resources r = Resources.getSystem();
        //lp = wrapcontent时 指定默认值
        if (wMode == MeasureSpec.AT_MOST) {
            resultWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, r.getDisplayMetrics());
        }
        if (hMode == MeasureSpec.AT_MOST) {
            resultHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, r.getDisplayMetrics());
        }
        setMeasuredDimension(resultWidth, resultHeight);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        mBackPlaneRadius = Math.min(getWidth(), getHeight()) * 0.5f;
        mSpotRadius = mBackPlaneRadius - BORDER_WIDTH;
        spotStartX = 0;
        mSpotY = 0;
        mOffSpotX = getMeasuredWidth() - mBackPlaneRadius * 2;
        mSlotColor = mOffSlotColor;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //画底板
        mRect.set(0, 0, getMeasuredWidth(), getMeasuredHeight());
        mPaint.setColor(mBasePlaneColor);
        canvas.drawRoundRect(mRect, mBackPlaneRadius, mBackPlaneRadius, mPaint);

        //画手柄的槽
        mRect.set(BORDER_WIDTH,
                BORDER_WIDTH,
                getMeasuredWidth() - BORDER_WIDTH,
                getMeasuredHeight() - BORDER_WIDTH);

        mPaint.setColor(mSlotColor);
        canvas.drawRoundRect(mRect, mSpotRadius, mSpotRadius, mPaint);

        //手柄包括包括两部分,深色底板和白板,这样做的目的是使圆盘具有边框
        //手柄的底盘
        mRect.set(spotStartX,
                mSpotY,
                spotStartX + mBackPlaneRadius * 2,
                mSpotY + mBackPlaneRadius * 2);

        mPaint.setColor(mBasePlaneColor);
        canvas.drawRoundRect(mRect, mBackPlaneRadius, mBackPlaneRadius, mPaint);

        //手柄的圆板
        mRect.set(spotStartX + BORDER_WIDTH,
                mSpotY + BORDER_WIDTH,
                mSpotRadius * 2 + spotStartX + BORDER_WIDTH,
                mSpotRadius * 2 + mSpotY + BORDER_WIDTH);

        mPaint.setColor(Color.WHITE);
        canvas.drawRoundRect(mRect, mSpotRadius, mSpotRadius, mPaint);
    }

    public float getSpotStartX() {
        return spotStartX;
    }

    public void setSpotStartX(float spotStartX) {
        this.spotStartX = spotStartX;
    }

    /**
     * 计算切换时的手柄槽的颜色
     *
     * @param fraction   动画播放进度
     * @param startColor 起始颜色
     * @param endColor   终止颜色
     */
    public void calculateColor(float fraction, int startColor, int endColor) {
        final int fb = Color.blue(startColor);
        final int fr = Color.red(startColor);
        final int fg = Color.green(startColor);

        final int tb = Color.blue(endColor);
        final int tr = Color.red(endColor);
        final int tg = Color.green(endColor);

        //RGB三通道线性渐变
        int sr = (int) (fr + fraction * (tr - fr));
        int sg = (int) (fg + fraction * (tg - fg));
        int sb = (int) (fb + fraction * (tb - fb));
        //范围限定
        sb = clamp(sb, 0, 255);
        sr = clamp(sr, 0, 255);
        sg = clamp(sg, 0, 255);

        mSlotColor = Color.rgb(sr, sg, sb);
    }

    private int clamp(int value, int low, int high) {
        return Math.min(Math.max(value, low), high);
    }

    /**
     * 关闭开关
     */
    public void toggleOn() {
        //手柄槽颜色渐变和手柄滑动通过属性动画来实现
        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "spotStartX", 0, mOffSpotX);
        animator.setDuration(300);
        animator.start();
        animator.setInterpolator(new DecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction = animation.getAnimatedFraction();
                calculateColor(fraction, mOffSlotColor, mOpenSlotColor);
                invalidate();
            }
        });
    }

    /**
     * 打开开关
     */
    public void toggleOff() {
        //手柄槽颜色渐变和手柄滑动通过属性动画来实现
        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "spotStartX", mOffSpotX, 0);
        animator.setDuration(300);
        animator.start();
        animator.setInterpolator(new DecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction = animation.getAnimatedFraction();
                calculateColor(fraction, mOpenSlotColor, mOffSlotColor);
                invalidate();
            }
        });
    }

    public boolean getSwitchState() {
        return mIsToggleOn;
    }

    /**
     * Touch事件触发
     * mIsToggleOn是当前状态,当mIsToggleOn != open时做出相应
     *
     * @param open 是否打开
     */
    private void touchToggle(boolean open) {
        if (!isMoveing) {
            isMoveing = true;
            if (mIsToggleOn != open) {
                if (mIsToggleOn) {
                    toggleOff();
                } else {
                    toggleOn();
                }
                mIsToggleOn = !mIsToggleOn;
                if (mOnToggleListener != null) {
                    mOnToggleListener.onSwitchChangeListener(mIsToggleOn);
                }
            }
        }
    }

    /**
     * Onclick事件触发
     */
    private void onClickToggle() {
        if (mIsToggleOn) {
            toggleOff();
        } else {
            toggleOn();
        }
        mIsToggleOn = !mIsToggleOn;
        if (mOnToggleListener != null) {
            mOnToggleListener.onSwitchChangeListener(mIsToggleOn);
        }
    }

    public void setOnToggleListener(OnToggleListener listener) {
        mOnToggleListener = listener;
    }

    /**
     * 界面上设置开关初始状态
     * @param open
     */
    public void setChecked(final boolean open) {
        this.postDelayed(new Runnable() {
            @Override
            public void run() {
                touchToggle(open);
            }
        }, 300);
    }
}