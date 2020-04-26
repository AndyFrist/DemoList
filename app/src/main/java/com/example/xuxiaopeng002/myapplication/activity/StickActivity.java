package com.example.xuxiaopeng002.myapplication.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ex_xuxiaopeng002.myapplication.R;
import com.example.xuxiaopeng002.myapplication.view.MyScrollView;

import java.util.ArrayList;

public class StickActivity extends AppCompatActivity {

    private MyScrollView scrollView;
    private TextView tv_content;//内容1

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stick);
        scrollView = (MyScrollView) findViewById(R.id.scrollView);

        tabTop = (TabLayout) findViewById(R.id.tabTop);
        tab = (TabLayout) findViewById(R.id.tab);
        pager = (ViewPager) findViewById(R.id.pager);

        tv_content = (TextView) findViewById(R.id.tv_content);

        //scrollview滚动监听
        scrollView.setScrollViewListener(new MyScrollView.ScrollViewListener(){
            @Override
            public void onScrollChanged(MyScrollView scrollView, int l, int t, int oldl, int oldt)
            {
                //t:scrollview滚动的高度
                //tv_content.getHeight()内容1的高度
                if (t > tv_content.getHeight())
                {
                    tabTop.setVisibility(View.VISIBLE);
                } else
                {
                    tabTop.setVisibility(View.GONE);
                }
            }
        });

        init();
    }
    private TabLayout tabTop;
    private TabLayout tab;
    private ViewPager pager;
    private Myadapter myadapter;
    private ArrayList<String> list = new ArrayList<>();

    private void init() {
        for (int i = 0; i < 40; i++) {
            list.add("第" + i + "页");
        }
        myadapter = new Myadapter();
        pager.setAdapter(myadapter);
        tab.setTabsFromPagerAdapter(myadapter);
        tab.setupWithViewPager(pager);

        tabTop.setTabsFromPagerAdapter(myadapter);
        tabTop.setupWithViewPager(pager);
    }

    class Myadapter extends PagerAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;//官方推荐写法
        }

        private View view;

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            view = View.inflate(StickActivity.this, R.layout.item_tablayout, null);
            TextView viewById = (TextView) view.findViewById(R.id.tab_tv);
            viewById.setText(list.get(position));
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //删除页卡
            container.removeView((View) object);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return list.get(position);//页卡标题
        }
    }


}