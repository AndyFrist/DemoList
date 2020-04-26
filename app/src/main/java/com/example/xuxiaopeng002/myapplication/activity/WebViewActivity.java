package com.example.xuxiaopeng002.myapplication.activity;

import android.os.Bundle;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.ex_xuxiaopeng002.myapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WebViewActivity extends BaseActivity {


    //    @BindView(R.id.relativeLayout)
//    RelativeLayout relativeLayout;
    @BindView(R.id.click)
    Button click;
    @BindView(R.id.root_view)
    LinearLayout rootView;
    @BindView(R.id.webView)
    WebView webView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);

//        String webview = getIntent().getExtras().getString("webview");
//
//        click.setText(webview + "world!");

//        webView = new WebView(this);
//        rootView.addView(webView);
        //允许webview加载JS代码
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        webView.getSettings().setAllowFileAccessFromFileURLs(true);
        webView.getSettings().setAllowFileAccess(true);


//        webView.loadUrl("file:///android_asset/webview.html");


        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {


                return super.shouldOverrideUrlLoading(view, request);
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);

            }
        });

        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String s, String s1, String s2, String s3, long l) {

            }
        });

        //允许浏览器调试
        webView.setWebContentsDebuggingEnabled(true);
        webView.loadUrl("https://www.baidu.com/");


    }

    @OnClick(R.id.click)
    public void onClick() {
        String url = "http://beta.juzhennet.com/dtkj10/file/upload/2016/11/07/1478680611.pdf";

        webView.loadUrl("file:///android_asset/pdf/index.html?" + url);
//        String url = "/storage/emulated/0/formats/share/a.jpg";
//        //Android调用js代码传值给H5
//        webView.loadUrl("javascript:if(window.getImgUrl){window.getImgUrl('" + url + "')}");

//        Intent intent = new Intent();
//        intent.putExtra("result", "result");
//        setResult(1, intent);

//        shotView(rootView);
//        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.stopLoading(); //停止加载
        ((ViewGroup) webView.getParent()).removeView(webView); //把webview从视图中移除
        webView.removeAllViews(); //移除webview上子view
        webView.clearCache(true); //清除缓存
        webView.clearHistory(); //清除历史
        webView.destroy(); //销毁webview自身
        webView = null;

//        android.os.Process.killProcess(android.os.Process.myPid()); //杀死WebView所在的进程
    }





}
