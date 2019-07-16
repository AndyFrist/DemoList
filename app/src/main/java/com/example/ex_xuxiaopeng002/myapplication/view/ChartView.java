package com.example.ex_xuxiaopeng002.myapplication.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.ex_xuxiaopeng002.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuxiaopeng002 on 2019/5/20.
 * 图表
 */
public class ChartView extends View {
    private Context context;
    private float size1;
    private int mWidth;     //控件宽
    private int mHeight;    //控件高

    private int mGridWidth; //格子宽
    private int mGridHeight;//格子高

    private int rows = 6;//行数
    private int columns = 6;//列数

    private int gridLeft;   //百分数文本宽度
    private int gridTop;    //上边三角形高度
    private int gridRight;
    private int gridBottom;

    private Paint textPaint;//文本画笔
    private Paint linePaint;//线画笔
    private Paint chartPaint;//柱状画笔
    private Paint tipPaint;//指示器

    private int chartWidth;//柱状宽度

    private Bitmap bitmap;

    public ChartView(Context context) {
        this(context, null);
    }

    public ChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        size1 = context.getResources().getDimension(R.dimen.size_01);
        gridLeft = (int) (size1 * 25);
        gridTop = (int) (size1 * 15);
        gridRight = (int) (size1 * 5);
        gridBottom = (int) (size1 * 15);

        chartWidth = (int) (size1 * 6);

        textPaint = new Paint();
        linePaint = new Paint();
        chartPaint = new Paint();
        tipPaint = new Paint();
        textPaint.setAntiAlias(true);
        linePaint.setAntiAlias(true);
        chartPaint.setAntiAlias(true);
        tipPaint.setAntiAlias(true);
        linePaint.setColor(context.getResources().getColor(R.color.colorAccent));
        tipPaint.setColor(0xff6d9dff);

        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.tranalge);


        nameList = new ArrayList<>();
        nameList.add("集团");
        nameList.add("单店");
        nameList.add("二网卖场");
        nameList.add("其他");

        valueList = new ArrayList<>();
        valueList.add((float) 0.9);
        valueList.add((float) 0.1);
        valueList.add((float) 0.7);
        valueList.add((float) 0.3);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //画线
        drawLine(canvas);
        //画文字
        drawText(canvas);
        //画柱状图
        drawChart(canvas);
        //画指示线
        drawTip(canvas);

    }

    private float tipLineX;

    private float getTipLineX() {
        return gridLeft + mGridWidth * clickNum;
    }

    private void setTipLineX(int clickNum) {
        tipLineX = gridLeft + mGridWidth * clickNum;
    }

    private void drawTip(Canvas canvas) {
        tipPaint.setStrokeWidth(1 * size1);


        canvas.drawLine(tipLineX, gridTop, tipLineX, mHeight - gridBottom, tipPaint);

        canvas.drawBitmap(bitmap, tipLineX - size1 * 5, gridTop / 2, tipPaint);
    }

    private void drawChart(Canvas canvas) {

        for (int i = 0; i < nameList.size(); i++) {
            canvas.drawRect(gridLeft + mGridWidth * (i + 1) - chartWidth / 2, gridTop + (1 - valueList.get(i) * growth) * (mHeight - gridTop - gridBottom), gridLeft + mGridWidth * (i + 1) + chartWidth / 2, mHeight - gridBottom, chartPaint);
        }


    }

    private List<String> nameList;
    private List<Float> valueList;

    public void setNameList(List<String> nameList) {
        this.nameList = nameList;
    }

    public void setValueList(List<Float> valueList) {
        this.valueList = valueList;
    }

    private void drawText(Canvas canvas) {
        textPaint.setColor(0xff666666);
        textPaint.setTextSize(10 * size1);
        // 文字baseline在y轴方向的位置
        float baseLineY = Math.abs(textPaint.ascent() + textPaint.descent()) / 2;

        if (nameList.size() > 0) {
            // 文字宽
            float textWidth1 = textPaint.measureText(nameList.get(0));
            canvas.drawText(nameList.get(0), gridLeft + mGridWidth * 1 - textWidth1 / 2, mHeight - baseLineY, textPaint);
        }
        if (nameList.size() > 1) {
            float textWidth2 = textPaint.measureText(nameList.get(1));
            canvas.drawText(nameList.get(1), gridLeft + mGridWidth * 2 - textWidth2 / 2, mHeight - baseLineY, textPaint);
        }
        if (nameList.size() > 2) {
            float textWidth3 = textPaint.measureText(nameList.get(2));
            canvas.drawText(nameList.get(2), gridLeft + mGridWidth * 3 - textWidth3 / 2, mHeight - baseLineY, textPaint);
        }
        if (nameList.size() > 3) {
            float textWidth4 = textPaint.measureText(nameList.get(3));
            canvas.drawText(nameList.get(3), gridLeft + mGridWidth * 4 - textWidth4 / 2, mHeight - baseLineY, textPaint);
        }

        textPaint.setColor(0xff999999);
        textPaint.setTextSize(8 * size1);
        float textWidth5 = textPaint.measureText("100%");
        canvas.drawText("100%", gridLeft - textWidth5 - 5 * size1, gridTop + mGridHeight * 0, textPaint);
        float textWidth6 = textPaint.measureText("80%");
        canvas.drawText("80%", gridLeft - textWidth6 - 5 * size1, gridTop + mGridHeight * 1, textPaint);
        float textWidth7 = textPaint.measureText("60%");
        canvas.drawText("60%", gridLeft - textWidth7 - 5 * size1, gridTop + mGridHeight * 2, textPaint);
        float textWidth8 = textPaint.measureText("40%");
        canvas.drawText("40%", gridLeft - textWidth8 - 5 * size1, gridTop + mGridHeight * 3, textPaint);
        float textWidth9 = textPaint.measureText("20%");
        canvas.drawText("20%", gridLeft - textWidth9 - 5 * size1, gridTop + mGridHeight * 4, textPaint);

    }

    private void drawLine(Canvas canvas) {

        for (int i = 0; i < rows; i++) {
            //画横线
            if (i == rows - 1) {
                linePaint.setStrokeWidth(1);
            } else {
                linePaint.setStrokeWidth(1);
            }
            canvas.drawLine(gridLeft, gridTop + mGridHeight * i, mWidth - gridRight, gridTop + mGridHeight * i, linePaint);
        }

        for (int j = 0; j < columns; j++) {
            //画竖线
            if (j == 0) {
                linePaint.setStrokeWidth(1);
            } else {
                linePaint.setStrokeWidth(1);
            }
            canvas.drawLine(gridLeft + mGridWidth * j, gridTop, gridLeft + mGridWidth * j, mHeight - gridBottom, linePaint);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getWidth();
        mHeight = getHeight();

        mGridWidth = (mWidth - gridLeft - gridRight) / (columns - 1);
        mGridHeight = (mHeight - gridTop - gridBottom) / (rows - 1);

        LinearGradient s = new LinearGradient(0, 0, 0, mHeight, 0xff87F4FF, 0xff84C3F1, Shader.TileMode.REPEAT);
        chartPaint.setShader(s);
        setTipLineX(clickNum);
        startAnimter();
    }


    private int clickNum = 1; //默认选择第一个


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                Log.e("ACTION_DOWN", event.getX() + "=" + event.getRawX());

                int x = (int) event.getX() - gridLeft;

                float start = getTipLineX();

                if (x % mGridWidth < mGridWidth / 2) {
                    clickNum = x / mGridWidth;
                } else {
                    clickNum = x / mGridWidth + 1;
                }

                if (clickNum <= 1) {
                    clickNum = 1;
                }

                if (clickNum >= nameList.size()) {
                    clickNum = nameList.size();
                }


                startAnimter(start, getTipLineX());
                if (listener != null) {
                    listener.Onclick(clickNum);
                }
                break;

        }
        return super.onTouchEvent(event);
    }


    public interface OnBarClickListener {
        void Onclick(int position);
    }

    OnBarClickListener listener;

    public void setOnclickListener(OnBarClickListener listener) {
        this.listener = listener;
    }

    public void setClickNum(int clickNum) {
        this.clickNum = clickNum;
        setTipLineX(clickNum);
        invalidate();
    }

    private void startAnimter(float start, float end) {

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(start, end);
        valueAnimator.setDuration(1000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                tipLineX = (float) animation.getAnimatedValue();
                invalidate();
            }
        });

        valueAnimator.start();
    }


    private float growth = 1;
    private void startAnimter() {

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(1000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                growth = (float) animation.getAnimatedValue();
                invalidate();
            }
        });

        valueAnimator.start();
    }


}
