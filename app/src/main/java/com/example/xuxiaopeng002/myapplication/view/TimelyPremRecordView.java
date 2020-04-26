package com.example.xuxiaopeng002.myapplication.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ex_xuxiaopeng002.myapplication.R;
import com.example.xuxiaopeng002.myapplication.bean.TimelyPremRecordBean;
import com.example.xuxiaopeng002.myapplication.util.JavaUtils;

import java.util.ArrayList;

public class TimelyPremRecordView extends LinearLayout {
    private View view;
    private Context context;
    private BrokenView brokenView;
    private CheckBox today;
    private CheckBox yesterday;
    private CheckBox sevenAvg;
    private TextView  timely_premerecord_name, area_textview;

    private ArrayList<String> nameX = new ArrayList<>();
    private ArrayList<String> nameY = new ArrayList<>();
    private ArrayList<Float> value0 = new ArrayList<>();
    private ArrayList<Float> value1 = new ArrayList<>();
    private ArrayList<Float> value2 = new ArrayList<>();


    public TimelyPremRecordView(Context context) {
        this(context, null);
    }

    public TimelyPremRecordView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @SuppressLint("ClickableViewAccessibility")
    public TimelyPremRecordView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.timely_premrecord, this, true);
        brokenView = view.findViewById(R.id.brokenView);
        today = view.findViewById(R.id.today);
        yesterday = view.findViewById(R.id.yesterday);
        sevenAvg = view.findViewById(R.id.jun);
        timely_premerecord_name = view.findViewById(R.id.timely_premerecord_name);
        area_textview = view.findViewById(R.id.area_textview);
        initCheckBox();
        Display defaultDisplay = ((Activity) context).getWindowManager().getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getSize(point);
    }

    TimelyPremRecordBean bean;
    int minValue;
    int maxValue;
    int range;

    public void setData(TimelyPremRecordBean bean) {
        this.bean = bean;
        resetRange();
    }



    public void setName(String name) {
        if (!TextUtils.isEmpty(name)) {
            if (timely_premerecord_name != null) {
                timely_premerecord_name.setText(name);
            }
        }
    }

    private void resetRange() {
        if (bean == null || bean.getToday() == null || bean.getYesterday() == null || bean.getSevenDayAvg() == null) {
            return;
        }
        if (today.isChecked() && yesterday.isChecked() && sevenAvg.isChecked()) {
            minValue = JavaUtils.compareMin(JavaUtils.parseInt(bean.getToday().getMin()), JavaUtils.parseInt(bean.getYesterday().getMin()), JavaUtils.parseInt(bean.getSevenDayAvg().getMin()));
            maxValue = JavaUtils.compareMax(JavaUtils.parseInt(bean.getToday().getMax()), JavaUtils.parseInt(bean.getYesterday().getMax()), JavaUtils.parseInt(bean.getSevenDayAvg().getMax()));
        } else if (today.isChecked() && yesterday.isChecked() && !sevenAvg.isChecked()) {
            minValue = JavaUtils.parseInt(bean.getToday().getMin()) > JavaUtils.parseInt(bean.getYesterday().getMin()) ? JavaUtils.parseInt(bean.getYesterday().getMin()) : JavaUtils.parseInt(bean.getToday().getMin());
            maxValue = JavaUtils.parseInt(bean.getToday().getMax()) < JavaUtils.parseInt(bean.getYesterday().getMax()) ? JavaUtils.parseInt(bean.getYesterday().getMax()) : JavaUtils.parseInt(bean.getToday().getMax());
        } else if (yesterday.isChecked() && sevenAvg.isChecked() && !today.isChecked()) {
            minValue = JavaUtils.parseInt(bean.getSevenDayAvg().getMin()) > JavaUtils.parseInt(bean.getYesterday().getMin()) ? JavaUtils.parseInt(bean.getYesterday().getMin()) : JavaUtils.parseInt(bean.getSevenDayAvg().getMin());
            maxValue = JavaUtils.parseInt(bean.getSevenDayAvg().getMax()) < JavaUtils.parseInt(bean.getYesterday().getMax()) ? JavaUtils.parseInt(bean.getYesterday().getMax()) : JavaUtils.parseInt(bean.getSevenDayAvg().getMax());
        } else if (today.isChecked() && sevenAvg.isChecked() && !yesterday.isChecked()) {
            minValue = JavaUtils.parseInt(bean.getToday().getMin()) > JavaUtils.parseInt(bean.getSevenDayAvg().getMin()) ? JavaUtils.parseInt(bean.getSevenDayAvg().getMin()) : JavaUtils.parseInt(bean.getToday().getMin());
            maxValue = JavaUtils.parseInt(bean.getToday().getMax()) < JavaUtils.parseInt(bean.getSevenDayAvg().getMax()) ? JavaUtils.parseInt(bean.getSevenDayAvg().getMax()) : JavaUtils.parseInt(bean.getToday().getMax());
        } else if (today.isChecked() && !yesterday.isChecked() && !sevenAvg.isChecked()) {
            minValue = JavaUtils.parseInt(bean.getToday().getMin());
            maxValue = JavaUtils.parseInt(bean.getToday().getMax());
        } else if (!today.isChecked() && yesterday.isChecked() && !sevenAvg.isChecked()) {
            minValue = JavaUtils.parseInt(bean.getYesterday().getMin());
            maxValue = JavaUtils.parseInt(bean.getYesterday().getMax());
        } else if (!today.isChecked() && !yesterday.isChecked() && sevenAvg.isChecked()) {
            minValue = JavaUtils.parseInt(bean.getSevenDayAvg().getMin());
            maxValue = JavaUtils.parseInt(bean.getSevenDayAvg().getMax());
        }
        range = maxValue - minValue;
        initBrokenLine();

        //显示地区
        if (!TextUtils.isEmpty(bean.getDeptDesc())) {
            area_textview.setText(bean.getDeptDesc());
        }
    }

    private void initBrokenLine() {
        nameY.clear();
        nameX.clear();
        value0.clear();
        value1.clear();
        value2.clear();

        for (int i = 3; i >= 0; i--) {
            int value = minValue + range * i / 3;
            if (value < 1000) {
                nameY.add(value + "");
            } else {
                nameY.add(JavaUtils.tothousand(value));
            }
        }

        for (int i = 0; i < BrokenView.columns; i++) {
            nameX.add(i + "点");
        }

        if (bean != null && bean.getToday() != null && bean.getToday().getData() != null) {
            for (int i = 0; i < bean.getToday().getData().size(); i++) {
                String data = bean.getToday().getData().get(i);
                value0.add((JavaUtils.parseFloat(data) - minValue) / range);
            }
        }

        if (bean != null && bean.getYesterday() != null && bean.getYesterday().getData() != null) {
            for (String data : bean.getYesterday().getData()) {
                value1.add((JavaUtils.parseFloat(data) - minValue) / range);
            }
        }

        if (bean != null && bean.getSevenDayAvg() != null && bean.getSevenDayAvg().getData() != null) {
            for (String data : bean.getSevenDayAvg().getData()) {
                value2.add((JavaUtils.parseFloat(data) - minValue) / range);
            }
        }

        brokenView.setNameYList(nameY);
        brokenView.setNameXList(nameX);
        brokenView.setValue0List(value0);
        brokenView.setValue1List(value1);
        brokenView.setValue2List(value2);
        brokenView.setData(bean);
    }

    private void initCheckBox() {
        today.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    brokenView.showBorken0();
                } else {
                    brokenView.hideBorken0();
                }
                resetRange();
            }
        });
        yesterday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    brokenView.showBorken1();
                } else {
                    brokenView.hideBorken1();
                }
                resetRange();
            }
        });
        sevenAvg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    brokenView.showBorken2();
                } else {
                    brokenView.hideBorken2();
                }
                resetRange();
            }
        });

    }
}
