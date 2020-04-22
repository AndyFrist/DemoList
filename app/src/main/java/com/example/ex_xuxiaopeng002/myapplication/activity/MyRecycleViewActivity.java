package com.example.ex_xuxiaopeng002.myapplication.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.ex_xuxiaopeng002.myapplication.R;
import com.example.ex_xuxiaopeng002.myapplication.adapter.SlideAdapter;
import com.example.ex_xuxiaopeng002.myapplication.view.ReboundRecycleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyRecycleViewActivity extends BaseActivity implements SlideAdapter.IonSlidingViewClickListener {

    @BindView(R.id.m_recycler_view)
    ReboundRecycleView  listView;
    List<String> mlist;
    SlideAdapter listviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animator_listview);
        ButterKnife.bind(this);

        mlist = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            mlist.add("a" + i);
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listView.setLayoutManager(layoutManager);
        listviewAdapter = new SlideAdapter(mlist,this,this);
        listView.setAdapter(listviewAdapter);
    }

    @Override
    public void onItemClick(View view, int item) {
        Toast.makeText(this,"click",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDeleteBtnCilck(View view, int item) {
        Toast.makeText(this,"del",Toast.LENGTH_LONG).show();
        listviewAdapter.removeData(item);
    }
}
