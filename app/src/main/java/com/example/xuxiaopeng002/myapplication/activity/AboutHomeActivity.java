package com.example.xuxiaopeng002.myapplication.activity;

import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.ex_xuxiaopeng002.myapplication.R;
import com.example.xuxiaopeng002.myapplication.app.MyApp;
import com.example.xuxiaopeng002.myapplication.bean.TimelyPremRecordBean;
import com.example.xuxiaopeng002.myapplication.util.AndroidUtils;
import com.example.xuxiaopeng002.myapplication.view.TimelyPremRecordView;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AboutHomeActivity extends BaseActivity implements AppBarLayout.OnOffsetChangedListener{
    AppBarLayout ablBar;
    View includeToolBar0;
    View includeToolBar1;
    Toolbar toolBar;
    TimelyPremRecordView timelyPremRecordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_home);
        includeToolBar0 = findViewById(R.id.include_toolbar_0);
        includeToolBar1 = findViewById(R.id.include_toolbar_1);
        toolBar = findViewById(R.id.toolbar);
        timelyPremRecordView = findViewById(R.id.timelyPremRecordView);
        ablBar = findViewById(R.id.abl_bar);
        ablBar.addOnOffsetChangedListener(this);
        int statusBarHeight = AndroidUtils.getStatusBarHeight(MyApp.INSTANCE);
        CollapsingToolbarLayout.LayoutParams layoutParams = (CollapsingToolbarLayout.LayoutParams) toolBar.getLayoutParams();
        layoutParams.height = statusBarHeight + AndroidUtils.dp2px(this,50);
        toolBar.setLayoutParams(layoutParams);
        toolBar.setPadding(0, statusBarHeight, 0, 0);

        Gson gson = new Gson();
        TimelyPremRecordBean timelyPremRecordBean = gson.fromJson(getCityJson(), TimelyPremRecordBean.class);
        timelyPremRecordView.setData(timelyPremRecordBean);

    }




    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        int absVerticalOffset = Math.abs(verticalOffset);//AppBarLayout竖直方向偏移距离px
        int totalScrollRange = appBarLayout.getTotalScrollRange();//AppBarLayout总的距离px
        if (absVerticalOffset <= totalScrollRange / 2) {

            includeToolBar0.setVisibility(View.VISIBLE);
            includeToolBar1.setVisibility(View.GONE);
        } else {

            includeToolBar0.setVisibility(View.GONE);
            includeToolBar1.setVisibility(View.VISIBLE);
        }
        int mMaskColor = getResources().getColor(R.color.white);
        int argb = Color.argb(absVerticalOffset * 255 / totalScrollRange, Color.red(mMaskColor), Color.green(mMaskColor), Color.blue(mMaskColor));
        toolBar.setBackgroundColor(argb);
    }


    private String getCityJson() {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = this.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(assetManager.open("timelyPremRecord.json")));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

}
