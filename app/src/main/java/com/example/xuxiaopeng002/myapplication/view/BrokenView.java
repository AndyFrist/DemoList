package com.example.xuxiaopeng002.myapplication.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.ex_xuxiaopeng002.myapplication.R;
import com.example.xuxiaopeng002.myapplication.bean.TimelyPremRecordBean;
import com.example.xuxiaopeng002.myapplication.util.TimeUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuxiaopeng002 on 2019/11/17.
 * 图表
 */
public class BrokenView extends View {
    private Context context;
    private float size1;
    private int mWidth;     //控件宽
    private int mHeight;    //控件高

    private double mGridWidth; //格子宽
    private int mGridHeight;//格子高

    private int rows = 4;//行数
    public static int columns = 24;//列数

    private int gridLeft;   //左边宽度
    private int gridTop;    //上边三角形高度
    private int gridRight;  //右边宽度
    private int gridBottom; //下边高度

    private Paint textPaint;//文本画笔
    private Paint linePaint;//线画笔hom
    private Paint tipPaint;//指示器

    private Paint brokenPaint0;//折线画笔
    private Paint brokenLinePaint0;//折线阴影画笔
    private Paint brokenPaint1;//折线画笔
    private Paint brokenLinePaint1;//折线阴影画笔
    private Paint brokenPaint2;//折线画笔
    private Paint brokenLinePaint2;//折线阴影画笔


    //画阴影
    private Path mPath0 = new Path();
    private Path mPath1 = new Path();
    private Path mPath2 = new Path();

    private Bitmap bitmap;
    private Bitmap bitmapPop;


    public BrokenView(Context context) {
        this(context, null);
    }

    public BrokenView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BrokenView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        size1 = context.getResources().getDimension(R.dimen.size_01);
        gridLeft = (int) (size1 * 25);
        gridTop = (int) (size1 * 15);
        gridRight = (int) (size1 * 5);
        gridBottom = (int) (size1 * 20);
        textPaint = new Paint();
        linePaint = new Paint();
        brokenPaint0 = new Paint();
        brokenPaint0.setColor(Color.parseColor("#ff61a8ff"));
        brokenPaint0.setAntiAlias(true);
        brokenPaint0.setStrokeWidth(size1);
        brokenLinePaint0 = new Paint();
        brokenLinePaint0.setColor(Color.parseColor("#6661a8ff"));

        brokenPaint1 = new Paint();
        brokenPaint1.setColor(Color.parseColor("#ffffa200"));
        brokenPaint1.setAntiAlias(true);
        brokenPaint1.setStrokeWidth(size1);
        brokenLinePaint1 = new Paint();
        brokenLinePaint1.setColor(Color.parseColor("#66ffe494"));

        brokenPaint2 = new Paint();
        brokenPaint2.setColor(Color.parseColor("#ffcf9dff"));
        brokenPaint2.setAntiAlias(true);
        brokenPaint2.setStrokeWidth(size1);
        brokenLinePaint2 = new Paint();
        brokenLinePaint2.setColor(Color.parseColor("#66cf9dff"));

        tipPaint = new Paint();
        textPaint.setAntiAlias(true);
        linePaint.setAntiAlias(true);
        tipPaint.setAntiAlias(true);
        linePaint.setColor(0xffeeeeef);
        tipPaint.setColor(0xffffa200);
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.tranalge);

        nameXList = new ArrayList<>();

        nameXList.clear();
        nameYList.clear();
        for (int i = 0; i < 24; i++) {
            if (i < 4) {
                nameYList.add("0");
            }
            nameXList.add(i + "点");
        }

        value0List = new ArrayList<>();
        value1List = new ArrayList<>();
        value2List = new ArrayList<>();
//        for (int i = 0; i < nameXList.size(); i++) {
//            value0List.add((float) Math.random());
//            value1List.add((float) Math.random());
//            value2List.add((float) Math.random());
//        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画线
        drawLine(canvas);
        //画文字
        drawText(canvas);
        //画折线图
        drawBrokenLine2(canvas);
        drawBrokenLine1(canvas);
        drawBrokenLine0(canvas);
        //画指示线
        drawTip(canvas);
        Log.e("onDraw",  "****************************");
    }

    private float tipLineX;


    private void setTipLineX(int clickNum) {
        tipLineX = (float) (gridLeft + mGridWidth * clickNum);
    }

    private void drawTip(Canvas canvas) {
        if (!showTips) {
            return;
        }
        tipPaint.setStrokeWidth(1 * size1);
        canvas.drawLine(tipLineX, gridTop, tipLineX, mHeight - gridBottom, tipPaint);
        canvas.drawBitmap(bitmap, tipLineX - size1 * 5, gridTop / 2, tipPaint);
        //画气泡
        if (bitmapPop != null) {
            if (tipLineX < 90 * size1 + gridLeft) {
                canvas.drawBitmap(bitmapPop, tipLineX + 5 * size1, gridTop, tipPaint);
            } else {
                canvas.drawBitmap(bitmapPop, tipLineX - (90 + 5) * size1, gridTop, tipPaint);
            }
        }
    }

    private void drawBrokenLine0(Canvas canvas) {
        if (!show0) {
            return;
        }
        mPath0.reset();
        mPath0.moveTo(gridLeft, mHeight - gridBottom);
//        canvas.drawCircle(gridLeft, mHeight - gridBottom, 3, textPaint);

        for (int i = 0; i < value0List.size(); i++) {
            if (i > 0) {
                canvas.drawLine(
                        (int) (gridLeft + mGridWidth * (i - 1)),
                        gridTop + (1 - value0List.get(i - 1) * growth0) * (mHeight - gridTop - gridBottom),
                        (int) (gridLeft + mGridWidth * (i)),
                        gridTop + (1 - value0List.get(i) * growth0) * (mHeight - gridTop - gridBottom), brokenPaint0);

            }

            mPath0.lineTo((int) (gridLeft + mGridWidth * (i)), gridTop + (1 - value0List.get(i) * growth0) * (mHeight - gridTop - gridBottom));
//            canvas.drawCircle( (int)(gridLeft + mGridWidth * (i)), gridTop + (1 - value0List.get(i) * growth0) * (mHeight - gridTop - gridBottom), 3, textPaint);

            if (i == value0List.size() - 1) {
                mPath0.lineTo((int) (gridLeft + mGridWidth * (i)), mHeight - gridBottom);
//                canvas.drawCircle((int) (gridLeft + mGridWidth * (i)), mHeight - gridBottom, 3, textPaint);

                mPath0.close();
                canvas.drawPath(mPath0, brokenLinePaint0);

            }
        }

    }

    private void drawBrokenLine1(Canvas canvas) {
        if (!show1) {
            return;
        }
        mPath1.reset();
        mPath1.moveTo(gridLeft, mHeight - gridBottom);
//        canvas.drawCircle(gridLeft, mHeight - gridBottom, 3, textPaint);

        for (int i = 0; i < value1List.size(); i++) {
            if (i > 0) {
                canvas.drawLine(
                        (int) (gridLeft + mGridWidth * (i - 1)),
                        gridTop + (1 - value1List.get(i - 1) * growth1) * (mHeight - gridTop - gridBottom),
                        (int) (gridLeft + mGridWidth * (i)),
                        gridTop + (1 - value1List.get(i) * growth1) * (mHeight - gridTop - gridBottom), brokenPaint1);

            }

            mPath1.lineTo((int) (gridLeft + mGridWidth * (i)), gridTop + (1 - value1List.get(i) * growth1) * (mHeight - gridTop - gridBottom));
//            canvas.drawCircle((int) (gridLeft + mGridWidth * (i)), gridTop + (1 - value1List.get(i) * growth1) * (mHeight - gridTop - gridBottom), 3, textPaint);

            if (i == value1List.size() - 1) {
                mPath1.lineTo((int) (gridLeft + mGridWidth * (i)), mHeight - gridBottom);
//                canvas.drawCircle( (int)(gridLeft + mGridWidth * (i)), mHeight - gridBottom, 3, textPaint);

                mPath1.close();


                canvas.drawPath(mPath1, brokenLinePaint1);

            }
        }

    }

    private void drawBrokenLine2(Canvas canvas) {
        if (!show2) {
            return;
        }
        mPath2.reset();
        mPath2.moveTo(gridLeft, mHeight - gridBottom);
//        canvas.drawCircle(gridLeft, mHeight - gridBottom, 3, textPaint);

        for (int i = 0; i < value2List.size(); i++) {
            if (i > 0) {
                canvas.drawLine(
                        (int) (gridLeft + mGridWidth * (i - 1)),
                        gridTop + (1 - value2List.get(i - 1) * growth2) * (mHeight - gridTop - gridBottom),
                        (int) (gridLeft + mGridWidth * (i)),
                        gridTop + (1 - value2List.get(i) * growth2) * (mHeight - gridTop - gridBottom), brokenPaint2);

            }

            mPath2.lineTo((int) (gridLeft + mGridWidth * (i)), gridTop + (1 - value2List.get(i) * growth2) * (mHeight - gridTop - gridBottom));
//            canvas.drawCircle((int) (gridLeft + mGridWidth * (i)), gridTop + (1 - value2List.get(i) * growth2) * (mHeight - gridTop - gridBottom), 3, textPaint);

            if (i == value2List.size() - 1) {
                mPath2.lineTo((int) (gridLeft + mGridWidth * (i)), mHeight - gridBottom);
//                canvas.drawCircle( (int)(gridLeft + mGridWidth * (i)), mHeight - gridBottom, 3, textPaint);

                mPath2.close();


                canvas.drawPath(mPath2, brokenLinePaint2);

            }
        }

    }

    private List<String> nameXList = new ArrayList<>();
    private List<String> nameYList = new ArrayList<>();
    private List<Float> value0List = new ArrayList<>();
    private List<Float> value1List = new ArrayList<>();
    private List<Float> value2List = new ArrayList<>();


    private void drawText(Canvas canvas) {
        //画X轴文字
        textPaint.setColor(0xff666666);
        textPaint.setTextSize(8 * size1);
        // 文字baseline在y轴方向的位置
        float baseLineY = Math.abs(textPaint.ascent() + textPaint.descent()) / 2;
        for (int i = 0; i < nameXList.size(); i++) {
            if (i % 3 == 0) {
                String valueX = nameXList.get(i);
                if (i == 0) {
                    valueX = TimeUtil.getCurrentDay() + "日";
                }
                float textWidth1 = textPaint.measureText(valueX);
                canvas.drawText(valueX, (int) (gridLeft + mGridWidth * (i) - textWidth1 / 2), mHeight - baseLineY , textPaint);
            }
        }

        //画Y轴文字
        textPaint.setColor(0xff999999);
        textPaint.setTextSize(8 * size1);
        for (int i = 0; i < nameYList.size(); i++) {
            String nameY = nameYList.get(i);
            float textWidth5 = textPaint.measureText(nameY);
            canvas.drawText(nameY, gridLeft - textWidth5 - 5 * size1, gridTop + mGridHeight * i, textPaint);
        }
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

//        for (int j = 0; j < columns; j++) {
//            //画竖线
//            if (j == 0) {
//                linePaint.setStrokeWidth(1);
//            } else {
//                linePaint.setStrokeWidth(1);
//            }
//            canvas.drawLine( (int)(gridLeft + mGridWidth * j), gridTop,  (int)(gridLeft + mGridWidth * j), mHeight - gridBottom, linePaint);
//        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getWidth();
        mHeight = getHeight();

        mGridWidth = Double.valueOf(mWidth - gridLeft - gridRight) / (columns - 1);
        mGridHeight = (mHeight - gridTop - gridBottom) / (rows - 1);

//        LinearGradient s = new LinearGradient(0, 0, 0, mHeight, Color.parseColor("#33ff0000"), Color.parseColor("#aaFFFFFF"), Shader.TileMode.REPEAT);
//        brokenLinePaint0.setShader(s);
//
//        LinearGradient linearGradient1 = new LinearGradient(0, 0, 0, getHeight(), Color.parseColor("#330000ff"), Color.parseColor("#aaFFFFFF"), Shader.TileMode.CLAMP);
//        brokenLinePaint1.setShader(linearGradient1);
//
//        LinearGradient linearGradient2 = new LinearGradient(0, 0, 0, getHeight(), Color.parseColor("#33ff5da7"), Color.parseColor("#aaFFFFFF"), Shader.TileMode.CLAMP);
//        brokenLinePaint2.setShader(linearGradient2);
        setTipLineX(clickNum);
    }

    private int clickNum = 1; //默认选择第一个

//    public void setClickNum(int clickNum) {
//        this.clickNum = clickNum;
//        setTipLineX(clickNum);
//        invalidate();
//    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {


        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                tipLineX = event.getX();
                if (tipLineX <= gridLeft) {
                    tipLineX = gridLeft;
                }
                if (tipLineX >= mWidth - gridRight) {
                    tipLineX = mWidth - gridRight;
                }
                Log.e("tipLineX ACTION_MOVE", tipLineX + "");
                PopView popView = new PopView(context);
                clickNum = (int) ((tipLineX - gridLeft + mGridWidth / 2) / mGridWidth);
                if (show0 || show1 || show2) {
                    showTips = true;
                    if (show0 && !show1 && !show2 && clickNum >= value0List.size()) {
                        showTips = false;
                    }
                } else {
                    showTips = false;
                }
                tipLineX = (float) (clickNum * mGridWidth) + gridLeft;
                popView.setValues(TimeUtil.getCurrentDay() + "日" + nameXList.get(clickNum > nameXList.size() - 1 ? nameXList.size() - 1 : clickNum)
                        , (show0 && clickNum < value0List.size()) ? datalist0.get(clickNum) : null
                        , (show1 && clickNum < value1List.size()) ? datalist1.get(clickNum) : null
                        , (show2 && clickNum < value2List.size()) ? datalist2.get(clickNum) : null);

                if (bitmapPop != null) {
                    bitmapPop.recycle();
                }
                bitmapPop = popView.convertViewToBitmap();
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                showTips = false;
                invalidate();
                break;
        }
        return true;
    }


    public void setNameXList(List<String> nameXList) {
        this.nameXList = nameXList;
    }

    public void setNameYList(List<String> nameYList) {
        this.nameYList = nameYList;

        //根据Y轴文字定 gridLeft
        gridLeft = 0;
        textPaint.setTextSize(8 * size1);
        for (int i = 0; i < nameYList.size(); i++) {
            String nameY = nameYList.get(i);
            float textWidth5 = textPaint.measureText(nameY);
            gridLeft = gridLeft >= (int)( textWidth5 + 5 * size1) ? gridLeft : (int)( textWidth5 + 5 * size1);

//            canvas.drawText(nameY, gridLeft - textWidth5 - 5 * size1, gridTop + mGridHeight * i, textPaint);
        }
        invalidate();
    }

    public void setValue0List(List<Float> value0List) {
        this.value0List = value0List;
    }

    public void setValue1List(List<Float> value1List) {
        this.value1List = value1List;
    }

    public void setValue2List(List<Float> value2List) {
        this.value2List = value2List;
    }

    private float growth0 = 1;
    private float growth1 = 1;
    private float growth2 = 1;

    private void startAnimter0(boolean anim) {
        if (anim) {
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
            valueAnimator.setDuration(1000);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    growth0 = (float) animation.getAnimatedValue();
                    invalidate();
                }
            });

            valueAnimator.start();
        } else {
            growth0 = 1;
            invalidate();
        }
    }

    private void startAnimter1(boolean anim) {
        if (anim) {
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
            valueAnimator.setDuration(1000);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    growth1 = (float) animation.getAnimatedValue();
                    invalidate();
                }
            });

            valueAnimator.start();
        } else {
            growth1 = 1;
            invalidate();
        }
    }

    private void startAnimter2(boolean anim) {
        if (anim) {
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
            valueAnimator.setDuration(1000);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    growth2 = (float) animation.getAnimatedValue();
                    invalidate();
                }
            });

            valueAnimator.start();
        } else {
            growth2 = 1;
            invalidate();
        }
    }

    private boolean show0 = true;
    private boolean show1 = false;
    private boolean show2 = false;
    private boolean showTips = false;

    public void showBorken0() {
        show0 = true;
        startAnimter0(true);
    }

    public void hideBorken0() {
        show0 = false;
        invalidate();
    }

    public void showBorken1() {
        show1 = true;
        startAnimter1(true);
    }

    public void hideBorken1() {
        show1 = false;
        invalidate();
    }

    public void showBorken2() {
        show2 = true;
        startAnimter2(true);
    }

    public void hideBorken2() {
        show2 = false;
        invalidate();
    }
    private float downX; // 按下的x坐标
    private float downY; // 按下的y坐标


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = ev.getX();
                downY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:

                float xOffset = Math.abs(ev.getX() - downX);
                float yOffset = Math.abs(ev.getY() - downY);

                if(xOffset > yOffset && xOffset > 15){ // 超出一定距离时,才拦截
                    getParent().requestDisallowInterceptTouchEvent(true);
                }else {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }

                break;
            case MotionEvent.ACTION_UP:

                break;

            default:
                break;
        }

        return super.dispatchTouchEvent(ev);
    }

    public void hideTips(){
        if (showTips) {
            showTips = false;
            invalidate();
        }
    }

    List<String> datalist0 = new ArrayList<>();
    List<String> datalist1 = new ArrayList<>();
    List<String> datalist2 = new ArrayList<>();
    TimelyPremRecordBean bean;

    public void setData(TimelyPremRecordBean bean) {
        this.bean = bean;
        datalist0.clear();
        datalist1.clear();
        datalist2.clear();

        if (bean != null && bean.getToday() != null && bean.getToday().getData() != null) {
            datalist0.addAll(bean.getToday().getData());
        }

        if (bean != null && bean.getYesterday() != null && bean.getYesterday().getData() != null) {
            datalist1.addAll(bean.getYesterday().getData());
        }

        if (bean != null && bean.getSevenDayAvg() != null && bean.getSevenDayAvg().getData() != null ) {
            datalist2.addAll(bean.getSevenDayAvg().getData());
        }

    }
}
