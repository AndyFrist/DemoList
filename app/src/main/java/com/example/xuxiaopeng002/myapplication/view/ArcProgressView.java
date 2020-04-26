package com.example.xuxiaopeng002.myapplication.view;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.example.ex_xuxiaopeng002.myapplication.R;

/**
 * Created by xuxiaopeng002 on 2019/4/17.
 * 渐变圆弧
 */
public class ArcProgressView extends View {
    Context context;
    //背景圆弧Paint
    private Paint mArcBackpaint;
    //根据数据显示的圆弧Paint
    private Paint mArcPaint;
    //文字描述的paint
    private Paint mTextPaint;
    //圆弧开始的角度
    private float startAngle = -90;
    //圆弧结束的角度
    //圆弧背景的开始和结束间的夹角大小
    private float mAngle = 360;
    //当前进度夹角大小
    private float mIncludedAngle = 0;
    //圆弧的画笔的宽度
    private float mStrokeWith = 10;
    //中心的文字描述
    private String mDes = "";
    //动画效果的数据及最大/小值
    private float mAnimatorValue;
    //中心点的XY坐标
    private float centerX, centerY;

    //大文字大小
    float textsize1;
    // 小文字大小
    float textsize2;
    // 大文字颜色
    int textcolor1;
    // 小文字颜色
    int textcolor2;
    // 进度条颜色
    int arccolorfront;
    // 进度条背景色
    int arccolorback;

    public ArcProgressView(Context context) {
        this(context, null);
    }

    public ArcProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.acrprogress);

        mStrokeWith = array.getDimension(R.styleable.acrprogress_arcstrokewith, 15);

        textsize1 = array.getDimension(R.styleable.acrprogress_textsize1, 55);
        textsize2 = array.getDimension(R.styleable.acrprogress_textsize2, 18);
        textcolor1 = array.getColor(R.styleable.acrprogress_textcolor1, 0xff333333);
        textcolor2 = array.getColor(R.styleable.acrprogress_textcolor2, 0xff333333);
        arccolorfront = array.getColor(R.styleable.acrprogress_arccolorfront, -1);
        arccolorback = array.getColor(R.styleable.acrprogress_arccolorback, 0xff333333);
        array.recycle();

        setArcBackColor(arccolorback);
        init();

    }

    private void init() {

        mArcBackpaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mArcBackpaint.setColor(0xFFF5F5F5);
        mArcBackpaint.setAntiAlias(true);
        //设置透明度（数值为0-255）
        mArcBackpaint.setAlpha(255);
        //设置画笔类型
        mArcBackpaint.setStyle(Paint.Style.STROKE);
        mArcBackpaint.setStrokeWidth(mStrokeWith);

        //圆弧的paint
        mArcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        //抗锯齿
        mArcPaint.setAntiAlias(true);
//        mArcPaint.setColor(Color.parseColor("#666666"));

//        Shader s = new SweepGradient(0, 0, mColors, null);
        s = new LinearGradient(0, 0, 0, getWidth(), 0xff00e7ff, 0xff36ff7e, Shader.TileMode.REPEAT);
        mArcPaint.setShader(s);
        //设置透明度（数值为0-255）
        mArcPaint.setAlpha(100);
        //设置画笔的画出的形状
        mArcPaint.setStrokeJoin(Paint.Join.ROUND);
        mArcPaint.setStrokeCap(Paint.Cap.ROUND);
        //设置画笔类型
        mArcPaint.setStyle(Paint.Style.STROKE);
        mArcPaint.setStrokeWidth(mStrokeWith);

        //中心文字的paint
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(Color.parseColor("#333333"));
        //设置文本的对齐方式
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        //mTextPaint.setTextSize(getResources().getDimensionPixelSize(R.dimen.dp_12));
        mTextPaint.setTextSize(dp2px(55));
    }

    Shader s;

    /**
     * 绘制文本
     *
     * @param canvas
     */
    private void drawText(Canvas canvas) {
        Rect mRect = new Rect();
        String mValue = String.valueOf(mAnimatorValue);
        //绘制中心的数值
        mTextPaint.getTextBounds(mValue, 0, mValue.length(), mRect);
        mTextPaint.setColor(textcolor2);
        mTextPaint.setTextSize(textsize2);
        if (TextUtils.isEmpty(mDes)) {
            canvas.drawText(String.format("%.2f", mAnimatorValue), centerX, centerY + mRect.height() / 2, mTextPaint);
        } else {
            canvas.drawText(String.format("%.2f", mAnimatorValue), centerX, centerY - mRect.height() / 2, mTextPaint);
        }

        //绘制中心文字描述
        mTextPaint.setColor(textcolor1);
        mTextPaint.setTextSize(textsize1);
        mTextPaint.getTextBounds(mDes, 0, mDes.length(), mRect);
        canvas.drawText(mDes, centerX, centerY + 2 * mRect.height() + dp2px(10), mTextPaint);

    }

    /**
     * 绘制当前的圆弧
     *
     * @param canvas
     */
    private void drawArc(Canvas canvas) {
        s = new LinearGradient(0, 0, 0, getWidth(), 0xff00e7ff, 0xff36ff7e, Shader.TileMode.REPEAT);
        mArcPaint.setShader(s);

        //绘制圆弧背景
        RectF mRectF = new RectF(mStrokeWith + dp2px(1), mStrokeWith + dp2px(1), getWidth() - mStrokeWith - dp2px(1), getHeight() - mStrokeWith - dp2px(1));
        canvas.drawArc(mRectF, startAngle, mAngle, false, mArcBackpaint);
        //绘制当前数值对应的圆弧
        mArcPaint.setColor(Color.parseColor("#FF4A40"));
        //根据当前数据绘制对应的圆弧
        canvas.drawArc(mRectF, startAngle, mIncludedAngle, false, mArcPaint);
    }

    /**
     * 为绘制弧度及数据设置动画
     *
     * @param startAngle   开始的弧度
     * @param currentAngle 需要绘制的弧度
     * @param currentValue 需要绘制的数据
     * @param time         动画执行的时长
     */
    private void setAnimation(float startAngle, float currentAngle, float currentValue, int time) {
        //绘制当前数据对应的圆弧的动画效果
        ValueAnimator progressAnimator = ValueAnimator.ofFloat(startAngle, currentAngle);
        progressAnimator.setDuration(time);
        progressAnimator.setTarget(mIncludedAngle);
        progressAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mIncludedAngle = (float) animation.getAnimatedValue();
                //重新绘制，不然不会出现效果
                postInvalidate();
            }
        });
        //开始执行动画
        progressAnimator.start();

        //中心数据的动画效果
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(mAnimatorValue, currentValue);
        valueAnimator.setDuration(time);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                mAnimatorValue = (float) valueAnimator.getAnimatedValue();
                postInvalidate();
            }
        });
        valueAnimator.start();
    }

    /**
     * 设置数据
     *
     * @param minValue     最小值
     * @param maxValue     最大值
     * @param currentValue 当前绘制的值
     * @param des          描述信息
     */
    public void setValues(int maxValue, float currentValue, String des) {
        mDes = des;
        //完全覆盖
        if (currentValue > maxValue) {
            currentValue = maxValue;
        }
        //计算弧度比重
        float scale = currentValue / maxValue;
        //计算弧度
        float currentAngle = scale * mAngle;
        //开始执行动画
        setAnimation(0, currentAngle, currentValue, 2500);
    }

    public float dp2px(float dp) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return dp * metrics.density;
    }


    /**
     * 大文字大小
     *
     * @param size
     */
    public void setTextSize1(float size) {
        textsize1 = size;
    }

    /**
     * 小文字大小
     *
     * @param size
     */
    public void setTextSize2(float size) {
        textsize2 = size;
    }

    /**
     * 大文字颜色
     *
     * @param colorId
     */
    public void setTextColor1(int colorId) {
        textcolor1 = colorId;
    }

    /**
     * 小文字颜色
     *
     * @param colorId
     */
    public void setTextColor2(int colorId) {
        textcolor2 = colorId;
    }

    /**
     * 进度条背景色
     *
     * @param colorId
     */
    public void setArcBackColor(int colorId) {
        if (mArcBackpaint != null) {
            mArcBackpaint.setColor(context.getResources().getColor(colorId));
        }
    }

    /**
     * 进度条颜色 当传小于零是彩色
     *
     * @param colorId
     */
    public void setArcFrontColor(int colorId) {
        if (mArcPaint != null) {
            if (0 < colorId) {
                mArcPaint.setColor(context.getResources().getColor(colorId));
            } else {
                mArcPaint.setColor(context.getResources().getColor(colorId));
            }
        }
    }

    /**
     * 进度条的宽度
     *
     * @param width
     */
    public void setStrokeWith(int width) {
        if (mTextPaint != null && mArcBackpaint != null) {
            mArcBackpaint.setStrokeWidth(width);
            mArcPaint.setStrokeWidth(width);
        }
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = getWidth() / 2;
        centerY = getHeight() / 2;
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制弧度
        drawArc(canvas);
        //绘制文本
        drawText(canvas);
    }
}
