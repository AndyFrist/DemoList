package com.example.xuxiaopeng002.myapplication.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.example.ex_xuxiaopeng002.myapplication.R;

/**
 * create by xuxiaopeng
 * on 2019/3/24
 * 描述：仿造iOS语音识别动画
 */
public class HistogramView extends View implements Runnable {
    public HistogramView(Context context) {
        this(context, null);
    }

    public HistogramView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HistogramView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);

    }

    //1dp
    private float dimen;
    //左右摆动动画 变量
    private double angle = 0;
    //虚线画笔
    private Paint paint1;
    //画笔
    private Paint paint;
    //中间虚线位置Y坐标值
    private float center_Y;


    //控件宽度
    private int width;
    //控件高度
    private int height_half;
    //中间大波浪高度
    private int max_Y;
    //中波浪高度
    private int mid_Y;
    //小波浪高度
    private int min_Y;
    //声音大小（0~1）
    private double voiceRate =0.3;

    private void init(Context context) {
        dimen = context.getResources().getDimension(R.dimen.size_01);
        center_Y = dimen * 83;
        paint = new Paint();
        paint.setColor(0xff666666);
        paint.setStrokeWidth(5);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);

        paint1 = new Paint();
        paint1.setColor(0xff666666);
        paint1.setStrokeWidth(5);
        paint1.setAntiAlias(true);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        voiceRate = 0.1;

        for (int i = 0; i < width; i++) {
            double random_height = Math.random();// 为了打乱长度
            if (i % ((int) (4 * dimen)) == 0) {
//                random_height = 0.2;
                if ((i > width / 7 && i < 2 * width / 7) || (i > 5 * width / 7 && i < 6 * width / 7)) {
                    //最两边的小波浪
                    double line1Y1 = voiceRate * random_height * min_Y * Math.sin((i + angle) * Math.PI / 180) + height_half;//加 height_half 是为了居中
                    double line1Y2 = 2 * height_half - line1Y1;
                    canvas.drawLine(i, (int) line1Y1, i, (int) line1Y2, paint1);
                } else if ((i > 2 * width / 7 && i < 3 * width / 7) || (i > 4 * width / 7 && i < 5 * width / 7)) {
                    //两边的中波浪
                    double line1Y1 = voiceRate * random_height * mid_Y * Math.sin((i - angle) * Math.PI / 180) + height_half;//加 height_half 是为了居中
                    double line1Y2 = 2 * height_half - line1Y1;
                    canvas.drawLine(i, (int) line1Y1, i, (int) line1Y2, paint1);

                } else if (i > 3 * width / 7 && i < 4 * width / 7) {
                    //中间大波浪
                    double line1Y1 = voiceRate * random_height * max_Y * Math.sin((i + angle) * Math.PI / 180) + height_half;//加 height_half 是为了居中
                    double line1Y2 = 2 * height_half - line1Y1;
                    canvas.drawLine(i, (int) line1Y1, i, (int) line1Y2, paint1);
                }
            }
        }
        //画中间的虚线
//        setLayerType(LAYER_TYPE_SOFTWARE, null);
        paint.setPathEffect(new DashPathEffect(new float[]{2 * dimen, 6 * dimen}, 4 * dimen));
        canvas.drawLine(0, center_Y, width / 7, center_Y, paint);
        canvas.drawLine(6 * width / 7, center_Y, width, center_Y, paint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = getWidth();
        height_half = getHeight() / 2;
        max_Y = getHeight() / 2;
        mid_Y = getHeight() / 4;
        min_Y = getHeight() / 8;
    }


    boolean flag = false;

    @Override
    public void run() {
        while (flag) {
            angle += 16;
            if (angle >= 360) {
                angle = 0;
            }
            try {
                Thread.sleep(160);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            postInvalidate();
        }
    }

    public boolean start() {
        if (flag) {
            return true;
        }
        flag = true;
        new Thread(this).start();
        return flag;
    }

    public void stop() {
        flag = false;
    }
    //设置音量大小
    public void setvoiceRate(double d) {
        this.voiceRate = d;
    }
}
