package com.example.xuxiaopeng002.myapplication.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ex_xuxiaopeng002.myapplication.R;
import com.example.xuxiaopeng002.myapplication.util.JavaUtils;

/**
 * @Author: ex-xuxiaopeng002
 * @CreateDate: 2019-11-18 15:02
 * @Description: java类作用描述
 */
public class PopView extends FrameLayout {
    private float dimension;
    private TextView yjMonth;
    private TextView today;
    private TextView yesterday;
    private TextView avg7;
    private LinearLayout llToday;
    private LinearLayout llYesterday;
    private LinearLayout ll7avg;

    public PopView(@NonNull Context context) {
        this(context, null);
    }

    public PopView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PopView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View view = View.inflate(context, R.layout.view_yjpop_layout, this);
        yjMonth = view.findViewById(R.id.yj_month);
        today = view.findViewById(R.id.today);
        yesterday = view.findViewById(R.id.yesterday);
        avg7 = view.findViewById(R.id.avg7);
        llToday = view.findViewById(R.id.ll_today);
        llYesterday = view.findViewById(R.id.ll_yesterday);
        ll7avg = view.findViewById(R.id.ll_7avg);

        dimension = context.getResources().getDimension(R.dimen.size_01);
        markerHeight = (int) (dimension * 70);
        markerWidth = (int) (dimension * (120 + 10));
    }

    public PopView setValues(String month, String today_data, String yesterday_data, String avg_data) {
        yjMonth.setText(month);

        if (!TextUtils.isEmpty(today_data)) {
            llToday.setVisibility(VISIBLE);
            today.setText(JavaUtils.formatTosepara(today_data));
        }else {
            llToday.setVisibility(GONE);
        }

        if (!TextUtils.isEmpty(yesterday_data)) {
            llYesterday.setVisibility(VISIBLE);
            yesterday.setText(JavaUtils.formatTosepara(yesterday_data));
        }else {
            llYesterday.setVisibility(GONE);
        }

        if (!TextUtils.isEmpty(avg_data)) {
            ll7avg.setVisibility(VISIBLE);
            avg7.setText(JavaUtils.formatTosepara(avg_data));
        }else {
            ll7avg.setVisibility(GONE);
        }

        return this;
    }

//    //设置气泡箭头方向
//    public PopView setOrientation(boolean left) {
//        if (left) {
//            arrow_left.setVisibility(GONE);
//            arrow_right.setVisibility(VISIBLE);
//        } else {
//            arrow_left.setVisibility(VISIBLE);
//            arrow_right.setVisibility(GONE);
//        }
//        return this;
//    }


    private int markerWidth;
    private int markerHeight;

    /**
     * @return view的界面展示图像
     */
    public Bitmap convertViewToBitmap() {
        destroyDrawingCache();
        measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        layout(0, 0, getMeasuredWidth(), getMeasuredHeight());
        clearFocus();
        setPressed(false);
        buildDrawingCache();
        Bitmap bitmap = getDrawingCache();
        return resizeImage(bitmap, markerWidth, markerHeight);
    }

    /**
     * 使用Bitmap加Matrix来缩放  比Bitmap.option的缩放效果好
     * Bitmap.option只能缩放整数倍
     * Matrix可以任意缩放
     *
     * @param bitmap
     * @param w
     * @param h
     * @return
     */
    public static Bitmap resizeImage(Bitmap bitmap, int w, int h) {
        Bitmap BitmapOrg = bitmap;
        int width = BitmapOrg.getWidth();
        int height = BitmapOrg.getHeight();
        int newWidth = w;
        int newHeight = h;

        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        Matrix matrix = new Matrix();
//        matrix.postScale(scaleWidth, scaleHeight);
        matrix.postScale(1, 1);
        // if you want to rotate the Bitmap
        // matrix.postRotate(45);
        Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width, height, matrix, true);
        return resizedBitmap;
    }
}
