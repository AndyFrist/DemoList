package com.example.xuxiaopeng002.myapplication.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.example.ex_xuxiaopeng002.myapplication.R;


/**
 * @Author: ex-xuxiaopeng002
 * @CreateDate: 2019-06-27 15:54
 * @Description: 扇形刻度值
 */
public class DashboardView extends View {

    private int mRadius; // 扇形半径
    private int mStartAngle = 200; // 起始角度
    private int mSweepAngle = 140; // 绘制角度
    private int mMin = 0; // 最小值
    private int mMax = 9; // 刻度最大值

    private int mMaxValue = 7000; //经验值最大值
    private int mSection = 9; // 值域（mMax-mMin）等分份数
    private int mPortion = 3; // 一个mSection等分份数
    private String mHeaderText = "经验值"; // 表头
    private int mVelocity = mMin; // 实时速度
    private int mStrokeWidth; // 画笔宽度
    private int mLength1; // 长刻度的相对圆弧的长度
    private int mLength2; // 刻度读数顶部的相对圆弧的长度

    private int mPadding;
    private float mCenterX, mCenterY; // 圆心坐标
    private Paint mPaint;
    private RectF mRectFArc;
    private RectF mRectFInnerArc;
    private Rect mRectText;
    private String[] mTexts;
    private int[] mColors;

    private int currentProgress = 0;

    public DashboardView(Context context) {
        this(context, null);
    }

    public DashboardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DashboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mStrokeWidth = dp2px(1);
        mLength1 = dp2px(8) + mStrokeWidth;
        mLength2 = mLength1 + dp2px(4);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        mRectFArc = new RectF();
        mRectFInnerArc = new RectF();
        mRectText = new Rect();

        mTexts = new String[mSection + 1]; // 需要显示mSection + 1个刻度读数
        for (int i = 0; i < mTexts.length; i++) {
            int n = (mMax - mMin) / mSection;
            mTexts[i] = String.valueOf(mMin + i * n);
        }

        mColors = new int[]{ContextCompat.getColor(getContext(), R.color.color_green),
                ContextCompat.getColor(getContext(), R.color.color_yellow),
                ContextCompat.getColor(getContext(), R.color.color_yellow)};
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mPadding = Math.max(Math.max(getPaddingLeft(), getPaddingTop()), Math.max(getPaddingRight(), getPaddingBottom()));
        setPadding(mPadding, mPadding, mPadding, mPadding);

        int width = resolveSize(dp2px(260), widthMeasureSpec);
        mRadius = (width - mPadding * 2 - mStrokeWidth * 2) / 2;

        // 由起始角度确定的高度
        float[] point1 = getCoordinatePoint(mRadius, mStartAngle);
        // 由结束角度确定的高度
        float[] point2 = getCoordinatePoint(mRadius, mStartAngle + mSweepAngle);
        int height = (int) Math.max(point1[1] + mRadius + mStrokeWidth * 2, point2[1] + mRadius + mStrokeWidth * 2);
//        setMeasuredDimension(width, height + getPaddingTop() + getPaddingBottom());


        setMeasuredDimension(width, getMeasuredHeight());

        mCenterX = mCenterY = getMeasuredWidth() / 2f;
        mRectFArc.set(
                getPaddingLeft() + mStrokeWidth,
                getPaddingTop() + mStrokeWidth,
                getMeasuredWidth() - getPaddingRight() - mStrokeWidth,
                getMeasuredWidth() - getPaddingBottom() - mStrokeWidth
        );

        mPaint.setTextSize(sp2px(16));
        mPaint.getTextBounds("0", 0, "0".length(), mRectText);
        mRectFInnerArc.set(
                getPaddingLeft() + mLength2 + mRectText.height() - dp2px(10),
                getPaddingTop() + mLength2 + mRectText.height() - dp2px(10),
                getMeasuredWidth() - getPaddingRight() - mLength2 - mRectText.height() + dp2px(10),
                getMeasuredWidth() - getPaddingBottom() - mLength2 - mRectText.height() + dp2px(10)
        );

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(ContextCompat.getColor(getContext(), R.color.color_dark));

        //背景圆弧
        mPaint.setStrokeCap(Paint.Cap.SQUARE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(dp2px(7));
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setColor(0xff191e34);
        canvas.drawArc(mRectFInnerArc, mStartAngle, mSweepAngle, false, mPaint);

        //比例圆弧
        mPaint.setShader(generateSweepGradient());
        float endAngle = (float) (mVelocity * mSweepAngle / mMaxValue);
        canvas.drawArc(mRectFInnerArc, mStartAngle, endAngle, false, mPaint);


        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setShader(null);

        //画表头
        if (!TextUtils.isEmpty(mHeaderText)) {
            mPaint.setTextSize(sp2px(14));
            mPaint.setTextAlign(Paint.Align.CENTER);
            mPaint.getTextBounds(mHeaderText, 0, mHeaderText.length(), mRectText);
            mPaint.setColor(0xffffffff);
            canvas.drawText(mHeaderText, mCenterX, mCenterY - mRectText.height() * 3, mPaint);

            mPaint.setTextSize(sp2px(30));
            mPaint.setColor(0xffff8f5b);
            canvas.drawText(mVelocity + "", mCenterX, mCenterY - mRectText.height() * 5, mPaint);
        }


        //画实时度数值
        mPaint.setColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        mPaint.setStrokeWidth(dp2px(2));

        int num = mSection * 3;
        for (int i = 0; i <= num; i++) {
            double cosx = Math.cos(Math.toRadians(mStartAngle - 180 + mSweepAngle * i / num));
            double sinx = Math.sin(Math.toRadians(mStartAngle - 180 + mSweepAngle * i / num));
            int x = (int) (mPadding + mStrokeWidth + mRadius * (1 - cosx));
            int y = (int) (mPadding + mStrokeWidth + mRadius * (1 - sinx));

            mPaint.setColor(0xffff854e);
            if (i % 3 == 0 && i / 3 != 0 && i / 3 != 9) {

                if (i / 3 == currentProgress) {
                    mPaint.setStyle(Paint.Style.STROKE);
                    mPaint.setStrokeWidth(dp2px(1));
                    canvas.drawCircle(x, y, dp2px(9), mPaint);
                    mPaint.setStyle(Paint.Style.FILL);
                    canvas.drawCircle(x, y, dp2px(7), mPaint);
                }

                mPaint.setTextSize(sp2px(10));
                mPaint.setColor(ContextCompat.getColor(getContext(), R.color.white));
                Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
                float bottom = fontMetrics.descent;
                float top = fontMetrics.ascent;
                canvas.drawText(i / 3 + "", x, y + (bottom - top) / 4, mPaint);
            } else {
                mPaint.setStyle(Paint.Style.FILL);
                mPaint.setColor(ContextCompat.getColor(getContext(), R.color.white));
                canvas.drawCircle(x, y, 2, mPaint);
            }
        }


        mPaint.setTextSize(sp2px(12));
        double cos = Math.cos(Math.toRadians(mStartAngle - 180 + mSweepAngle));
        double sin = Math.sin(Math.toRadians(mStartAngle - 180 + mSweepAngle));
        float x0 = (float) (mPadding + mStrokeWidth + mRadius * (1 - cos)) - dp2px(10);
        float y0 = (float) (mPadding + mStrokeWidth + mRadius * (1 - sin)) + dp2px(25);
        canvas.drawText("V9", x0, y0, mPaint);


        double cos1 = Math.cos(Math.toRadians(mStartAngle - 180));
        double sin1 = Math.sin(Math.toRadians(mStartAngle - 180));
        float x1 = (float) (mPadding + mStrokeWidth + mRadius * (1 - cos1)) + dp2px(10);
        float y1 = (float) (mPadding + mStrokeWidth + mRadius * (1 - sin1)) + dp2px(25);
        mPaint.setColor(0xffff8f5b);
        canvas.drawText("V0", x1, y1, mPaint);


//        SweepGradient sweepGradient;
//        sweepGradient = new SweepGradient(mCenterX, mCenterY - mRectText.height() * 3, mColors, new float[]{0, 140 / 360f, mSweepAngle / 360f});
//
//        mPaint.setShader(sweepGradient);
//        mPaint.setStyle(Paint.Style.STROKE);
//        canvas.drawArc(mCenterX, mCenterY - mRectText.height() * 3, 56, mPaint);

    }


    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                Resources.getSystem().getDisplayMetrics());
    }

    private int sp2px(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
                Resources.getSystem().getDisplayMetrics());
    }

    public float[] getCoordinatePoint(int radius, float angle) {
        float[] point = new float[2];

        double arcAngle = Math.toRadians(angle); //将角度转换为弧度
        if (angle < 90) {
            point[0] = (float) (mCenterX + Math.cos(arcAngle) * radius);
            point[1] = (float) (mCenterY + Math.sin(arcAngle) * radius);
        } else if (angle == 90) {
            point[0] = mCenterX;
            point[1] = mCenterY + radius;
        } else if (angle > 90 && angle < 180) {
            arcAngle = Math.PI * (180 - angle) / 180.0;
            point[0] = (float) (mCenterX - Math.cos(arcAngle) * radius);
            point[1] = (float) (mCenterY + Math.sin(arcAngle) * radius);
        } else if (angle == 180) {
            point[0] = mCenterX - radius;
            point[1] = mCenterY;
        } else if (angle > 180 && angle < 270) {
            arcAngle = Math.PI * (angle - 180) / 180.0;
            point[0] = (float) (mCenterX - Math.cos(arcAngle) * radius);
            point[1] = (float) (mCenterY - Math.sin(arcAngle) * radius);
        } else if (angle == 270) {
            point[0] = mCenterX;
            point[1] = mCenterY - radius;
        } else {
            arcAngle = Math.PI * (360 - angle) / 180.0;
            point[0] = (float) (mCenterX + Math.cos(arcAngle) * radius);
            point[1] = (float) (mCenterY - Math.sin(arcAngle) * radius);
        }

        return point;
    }

    private SweepGradient generateSweepGradient() {
        SweepGradient sweepGradient = new SweepGradient(mCenterX, mCenterY,
                mColors,
                new float[]{0, 140 / 360f, mSweepAngle / 360f}
        );

        Matrix matrix = new Matrix();
        matrix.setRotate(mStartAngle - 3, mCenterX, mCenterY);
        sweepGradient.setLocalMatrix(matrix);

        return sweepGradient;
    }

    public int getVelocity() {
        return mVelocity;
    }

    public void setVelocity(int velocity, int index) {

        ValueAnimator currentProgressanimator = ValueAnimator.ofInt(0, index);
        currentProgressanimator.setInterpolator(new DecelerateInterpolator());
        currentProgressanimator.setDuration(1500);

        currentProgressanimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                currentProgress = value;
                postInvalidate();
                Log.e("value = ", "" + value);
            }
        });
        currentProgressanimator.start();


        ValueAnimator animator = ValueAnimator.ofInt(0, velocity);
        animator.setDuration(1500);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                mVelocity = value;
                postInvalidate();
                Log.e("value = ", "" + value);
            }
        });
        animator.start();


        Log.e("mVelocity", "" + mVelocity);
    }
}
