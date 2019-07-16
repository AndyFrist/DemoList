package com.example.ex_xuxiaopeng002.myapplication.app;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * @Author: ex-xuxiaopeng002
 * @CreateDate: 2019-06-04 10:40
 * @Description: java类作用描述
 */
public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        // Normal app init code...
    }
}
