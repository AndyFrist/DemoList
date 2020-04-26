package com.example.xuxiaopeng002.myapplication.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ex_xuxiaopeng002.myapplication.R;
import com.example.xuxiaopeng002.myapplication.util.ActivityManager;
import com.example.xuxiaopeng002.myapplication.util.AndroidUtils;


/**
 * @Date: 2020-04-15
 * @author: ex-xuxiaopeng002
 * @Description: 支持适配刘海屏的 通用title
 */
public class CommonTitleView extends LinearLayout {
    private Context context;
    private View titleStatus;
    private RelativeLayout titleRootView;

    private LinearLayout titleLeftLL;
    private ImageView titleBackIv;
    private TextView titleBackTv;

    private TextView titleContentTV;

    private LinearLayout titleRightLL;
    private ImageView titleRightIv;
    private TextView titleRightTv;

    public CommonTitleView(Context context) {
        this(context, null);
    }

    public CommonTitleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonTitleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private int backColor, statusColor, titleColor, rightColor, contentColor;
    private String backText, contentText, rightText;
    private float backSize, contentSize, rightSize;
    private int backImgId, rightImgId;

    private void init(Context context, @Nullable AttributeSet attrs) {
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.common_titleview_layout, this, true);
        titleStatus = findViewById(R.id.titleStatus);
        titleRootView = findViewById(R.id.titleRootView);
        titleLeftLL = findViewById(R.id.titleLeftLL);
        titleBackIv = findViewById(R.id.titleBackIv);
        titleBackTv = findViewById(R.id.titleBackTv);
        titleContentTV = findViewById(R.id.titleContentTV);
        titleRightLL = findViewById(R.id.titleRightLL);
        titleRightIv = findViewById(R.id.titleRightIv);
        titleRightTv = findViewById(R.id.titleRightTv);

        int top = AndroidUtils.getStatusBarHeight(context);
        LayoutParams layoutParams = (LayoutParams) titleStatus.getLayoutParams();
        layoutParams.height = top;
        titleStatus.setLayoutParams(layoutParams);


        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CommonTitleView);

        statusColor = array.getColor(R.styleable.CommonTitleView_status_color, context.getResources().getColor(R.color.colorffffff));
        titleColor = array.getColor(R.styleable.CommonTitleView_title_color, context.getResources().getColor(R.color.colorffffff));
        backColor = array.getColor(R.styleable.CommonTitleView_back_color, context.getResources().getColor(R.color.color333333));
        rightColor = array.getColor(R.styleable.CommonTitleView_right_color, context.getResources().getColor(R.color.color333333));
        contentColor = array.getColor(R.styleable.CommonTitleView_title_content_color, context.getResources().getColor(R.color.color333333));

        backText = array.getString(R.styleable.CommonTitleView_back_Text);
        contentText = array.getString(R.styleable.CommonTitleView_title_content_Text);
        rightText = array.getString(R.styleable.CommonTitleView_right_Text);

        backSize = array.getDimension(R.styleable.CommonTitleView_back_size, AndroidUtils.dp2px(context, 14));
        contentSize = array.getDimension(R.styleable.CommonTitleView_title_content_size, AndroidUtils.dp2px(context, 17));
        rightSize = array.getDimension(R.styleable.CommonTitleView_right_size, AndroidUtils.dp2px(context, 14));

        backImgId = array.getResourceId(R.styleable.CommonTitleView_back_img, -1);
        rightImgId = array.getResourceId(R.styleable.CommonTitleView_right_img, -1);

        array.recycle();


        setTitleStatusColor(statusColor);
        setTitleRootViewColor(titleColor);

        setTitleBackIv(backImgId);

        setTitleBackTvText(backText);
        setTitleBackTvColor(backColor);
        settitleBackTvSize(backSize);

        setText(contentText);
        setTitleContentTVColor(contentColor);
        setTitleContentTVSize(contentSize);

        setTitleRightIv(rightImgId);

        setTitleRightTvText(rightText);
        setTitleRightTvColor(rightColor);
        setTitleRightTvSize(rightSize);

        titleBackIv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Activity activity = ActivityManager.getInstance().currentActivity();
                activity.finish();
            }
        });
    }

    /**
     * 设置状态栏颜色
     *
     * @param color
     */
    public void setTitleStatusColor(int color) {
        titleStatus.setBackgroundColor(color);
    }

    /**
     * 设置title颜色
     *
     * @param color
     */
    public void setTitleRootViewColor(int color) {
        titleRootView.setBackgroundColor(color);
    }

    /**
     * 设置返回按钮icon
     *
     * @param drawable
     */
    public void setTitleBackIv(int drawable) {
        if (drawable != -1) {
            titleBackIv.setVisibility(View.VISIBLE);
            titleBackIv.setImageResource(drawable);
        }
    }

    /**
     * 设置返回文案
     *
     * @param text
     */
    public void setTitleBackTvText(String text) {
        if (!TextUtils.isEmpty(text)) {
            titleBackTv.setVisibility(VISIBLE);
            titleBackTv.setText(text);
        }
    }

    /**
     * 设置返回文案颜色
     *
     * @param color
     */
    public void setTitleBackTvColor(int color) {
        titleBackTv.setTextColor(color);
    }

    /**
     * 设置返回文案大小
     *
     * @param backSize
     */
    public void settitleBackTvSize(float backSize) {
        titleBackTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, backSize);
    }

    /**
     * 设置title文案
     *
     * @param text
     */
    public void setText(String text) {
        if (!TextUtils.isEmpty(text)) {
            titleContentTV.setVisibility(VISIBLE);
            titleContentTV.setText(text);
        }
    }

    /**
     * 设置title文案颜色
     *
     * @param color
     */
    public void setTitleContentTVColor(int color) {
        titleContentTV.setTextColor(color);
    }

    /**
     * 设置title文案大小
     *
     * @param contentSize
     */
    public void setTitleContentTVSize(float contentSize) {
        titleContentTV.setTextSize(TypedValue.COMPLEX_UNIT_PX, contentSize);
    }

    /**
     * 设置返回按钮icon
     *
     * @param drawable
     */
    public void setTitleRightIv(int drawable) {
        if (drawable != -1) {
            titleRightIv.setVisibility(View.VISIBLE);
            titleRightIv.setImageResource(drawable);
        }
    }

    /**
     * 设置title文案
     *
     * @param text
     */
    public void setTitleRightTvText(String text) {
        if (!TextUtils.isEmpty(text)) {
            titleRightTv.setVisibility(VISIBLE);
            titleRightTv.setText(text);
        }
    }

    /**
     * 设置title文案颜色
     *
     * @param color
     */
    public void setTitleRightTvColor(int color) {
        titleRightTv.setTextColor(color);
    }

    /**
     * 设置title文案大小
     *
     * @param contentSize
     */
    public void setTitleRightTvSize(float contentSize) {
        titleRightTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, contentSize);
    }

    /**
     * 返回按钮的点击事件
     *
     * @param listener
     */
    public void setTitleBackIvClickListener(OnClickListener listener) {
        titleBackIv.setOnClickListener(listener);
    }
    /**
     * 返回按钮的点击事件
     *
     * @param listener
     */
    public void setTitleBackTvClickListener(OnClickListener listener) {
        titleBackTv.setOnClickListener(listener);
    }



    /**
     * 右边按钮的的点击事件
     *
     * @param listener
     */
    public void setTitleRightTvClickListener(OnClickListener listener) {
        titleRightTv.setOnClickListener(listener);
    }

    /**
     * 右边按钮的的点击事件
     *
     * @param listener
     */
    public void setTitleRightIvClickListener(OnClickListener listener) {
        titleRightIv.setOnClickListener(listener);
    }

    public void showTitle(int show) {
        titleRootView.setVisibility(show);
    }

    /**
     * 支持右边自定义，可以把定义好样式和点击事件的view，添加到titleLeftLL
     *
     * @param views
     */
    public void addViewAttitleLeftLL(View... views) {
        titleLeftLL.removeAllViews();
        for (View view : views) {
            titleLeftLL.addView(view);
        }
    }

    /**
     * 支持右边自定义，可以把定义好样式和点击事件的view，添加到titleRightLL
     *
     * @param views
     */
    public void addViewAttitleRightLL(View... views) {
        titleLeftLL.removeAllViews();
        for (View view : views) {
            titleRightLL.addView(view);
        }
    }

}
