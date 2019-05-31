package com.example.ex_xuxiaopeng002.myapplication.util;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

/**
 * @Author: ex-xuxiaopeng002
 * @CreateDate: 2019-05-31 10:20
 * @Description: java类作用描述
 */
public class AndroidUtils {

    public static void showInput(EditText ai_et, Activity activity){
        ai_et.setFocusable(true);
        ai_et.setFocusableInTouchMode(true);
        ai_et.requestFocus();
        ai_et.setGravity(Gravity.NO_GRAVITY);
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(ai_et, InputMethodManager.SHOW_FORCED);// 显示输入法
    }

    public static void canNext(EditText editText){
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //可以根据需求获取下一个焦点还是上一个
                View nextView = v.focusSearch(View.FOCUS_DOWN);
                if (nextView != null) {
                    nextView.requestFocus(View.FOCUS_DOWN);
                }
                //这里一定要返回true
                return true;
            }
        });

    }
    public static int getScreenWidth(Context ctx) {
        WindowManager mWindowManager = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
        return mWindowManager.getDefaultDisplay().getWidth();
    }
}
