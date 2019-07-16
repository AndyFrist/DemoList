package com.example.ex_xuxiaopeng002.myapplication.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Toast;

import com.example.ex_xuxiaopeng002.myapplication.R;

/**
 * @Author: ex-xuxiaopeng002
 * @CreateDate: 2019-06-06 16:09
 * @Description: java类作用描述
 */
public class LotteryView extends View {
    private Context context;
    private int radius;
    private Paint paint;
    private RectF rectF;
    private RectF mRectFText;
    private int tadiusText;
    private int textPading = dp2px(34);
    private int subView = 8; // 扇形个数
    private double degree;//角度
    private String[] mTexts;
    private Rect mRectText;

    private float centerX;
    private float centerY;

    private int lotteryBtnRadius = dp2px(56);


    public LotteryView(Context context) {
        this(context, null);
    }

    public LotteryView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LotteryView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        paint = new Paint();
        paint.setAntiAlias(true);
        mRectText = new Rect();
//        mTexts = new String[]{"谢谢参与", "可乐", "小米耳机", "小米9", "华为mate20", "华为P30", "iPhoneXs",  "现金十万", "极好", "950"};
//        mTexts = new String[]{"谢谢参与", "谢谢参与", "谢谢参与", "谢谢参与", "谢谢参与", "谢谢参与", "谢谢参与",  "谢谢参与", "谢谢参与", "谢谢参与"};
        mTexts = new String[]{"350", "较差", "550", "中等", "600", "良好", "650", "优秀", "700", "极好", "950"};

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < subView; i++) {

            // 画转盘
            if (i % 2 == 0) {
                paint.setColor(context.getResources().getColor(R.color.colorAccent));
            } else {
                paint.setColor(context.getResources().getColor(R.color.color_orange));
            }
            if (i == 0) {
                paint.setColor(context.getResources().getColor(R.color.color_blue));
            }
            canvas.drawArc(rectF, (float) (degree * i) + animaterValue, (float) degree, true, paint);

            //画抽奖 奖项
            Path path = new Path();
            paint.getTextBounds(mTexts[i], 0, mTexts[i].length(), mRectText);
            // 粗略把文字的宽度视为圆心角2*θ对应的弧长，利用弧长公式得到θ，下面用于修正角度
            float θ = (float) (180 * mRectText.width() / 2.0 / Math.PI /(tadiusText + mRectFText.height()/2));

            path.addArc(mRectFText, (float) (degree * i + degree / 2 - θ + animaterValue), (float) degree);

            paint.setTextSize(sp2px(15));
            paint.setColor(Color.WHITE);
            if (i == 0) {
                paint.setColor(context.getResources().getColor(R.color.color_red));
            }
            canvas.drawTextOnPath(mTexts[i], path, 0, 0, paint);


            //画抽奖按钮
            paint.setColor(0xffaacc00);
            canvas.drawCircle(centerX, centerY, lotteryBtnRadius, paint);
            //画抽奖按钮文案
            paint.setColor(Color.WHITE);
            paint.setTextSize(sp2px(23));
            paint.getTextBounds("抽奖", 0, "抽奖".length(), mRectText);
            canvas.drawText("抽奖", centerX - mRectText.width() / 2, centerY + mRectText.height() / 2, paint);


        }
    }

    boolean isclick = false;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                float x = event.getX();
                float y = event.getY();
                float offSetX = Math.abs(x - centerX);
                float offSetY = Math.abs(y - centerY);


                double sqrt = Math.sqrt(offSetX * offSetX + offSetY * offSetY);
                if (sqrt < lotteryBtnRadius) {
                    isclick = true;
                } else {
                    isclick = false;
                }

                break;
            case MotionEvent.ACTION_UP:


                float x1 = event.getX();
                float y1 = event.getY();
                float offSetX1 = Math.abs(x1 - centerX);
                float offSetY1 = Math.abs(y1 - centerY);


                double sqrt1 = Math.sqrt(offSetX1 * offSetX1 + offSetY1 * offSetY1);
                if (sqrt1 < lotteryBtnRadius && isclick) {
                    isclick = true;

                    Toast.makeText(context, "抽奖啦", Toast.LENGTH_LONG).show();
                    startAnimation(7);
                } else {
                    isclick = false;
                }

                isclick = false;
                break;


        }
        return true;


    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        radius = getWidth() / 2;
        centerX = getWidth() / 2;
        centerY = getWidth() / 2;

        rectF = new RectF(0, 0, getWidth(), getWidth());
        mRectFText = new RectF(textPading, textPading, getWidth() - textPading, getWidth() - textPading);
        tadiusText = (getWidth() - textPading) / 2;
        degree = 360 / subView;

    }

    private int animaterValue = 0;

    public void startAnimation(int value) {

        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 360 * value);
        valueAnimator.setDuration(6000);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                animaterValue = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.start();

    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                Resources.getSystem().getDisplayMetrics());
    }

    private int sp2px(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
                Resources.getSystem().getDisplayMetrics());
    }

}
