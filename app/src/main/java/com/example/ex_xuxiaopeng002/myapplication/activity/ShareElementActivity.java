package com.example.ex_xuxiaopeng002.myapplication.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ex_xuxiaopeng002.myapplication.R;
import com.example.ex_xuxiaopeng002.myapplication.util.TestManager;

public class ShareElementActivity extends AppCompatActivity {

    static  Demo sDemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_element);
//        TestManager manager = TestManager.getInstance(this);
        if (sDemo == null) {
            sDemo = new Demo();
        }
    }


    private static class  Demo{


    }

}
