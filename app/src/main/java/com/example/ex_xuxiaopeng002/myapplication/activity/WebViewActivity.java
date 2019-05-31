package com.example.ex_xuxiaopeng002.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ex_xuxiaopeng002.myapplication.R;
import com.example.ex_xuxiaopeng002.myapplication.activity.HistogramActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WebViewActivity extends AppCompatActivity {


    @BindView(R.id.click)
    TextView click;
    @BindView(R.id.root_view)
    LinearLayout rootView;

    private WebView webView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        webView = new WebView(this);
        rootView.addView(webView);
        webView.loadUrl("file:///android_asset/index.html");
    }

    @OnClick(R.id.click)
    public void onClick() {
        String url = "/storage/emulated/0/formats/share/a.jpg";
        //Android调用js代码传值给H5
        webView.loadUrl("javascript:if(window.getImgUrl){window.getImgUrl('" + url + "')}");


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        rootView.removeAllViews();
        webView.clearCache(true);
        webView.destroy();
    }


}
