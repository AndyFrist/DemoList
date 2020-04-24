package com.example.ex_xuxiaopeng002.myapplication.util;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;


public class ToastUtils {
	private static Handler handler = new Handler(Looper.getMainLooper());
	public static void show(final Activity activity, final String str,
							final int time) {
		if ("main".equalsIgnoreCase(Thread.currentThread().getName())) {

			Toast.makeText(activity, str, time).show();
		} else {
			activity.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(activity, str, time).show();

				}
			});

		}
	}

	public static void show(final Activity activity, final String str) {
		if ("main".equalsIgnoreCase(Thread.currentThread().getName())) {
			Toast.makeText(activity, str, Toast.LENGTH_SHORT).show();
		} else {
			activity.runOnUiThread(new Runnable() {

				@Override
				public void run() {
					Toast.makeText(activity, str, Toast.LENGTH_SHORT).show();
				}
			});
		}
	}

	public static void show(final String content){
		if ("main".equalsIgnoreCase(Thread.currentThread().getName())) {
			Toast.makeText(ActivityManager.getInstance().currentActivity(), content, Toast.LENGTH_SHORT).show();
		} else {
			handler.post(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(ActivityManager.getInstance().currentActivity(), content, Toast.LENGTH_SHORT).show();
				}
			});
		}
	}

}
