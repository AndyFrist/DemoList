package com.example.xuxiaopeng002.myapplication.activity;

import android.os.Bundle;
import android.view.View;

import com.example.ex_xuxiaopeng002.myapplication.R;

public class ShareElementActivity extends BaseActivity {

    static  Demo sDemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_element);

        findViewById(R.id.rootViewShare).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        if (sDemo == null) {
            sDemo = new Demo();
        }
    }



    private static class  Demo{


    }

}
