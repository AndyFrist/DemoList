package com.example.xuxiaopeng002.myapplication.util;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by ex-tanhongchuan001 on 2018/8/9.
 */

public class FontCustom {

    private static String forgUrl="fonts/NumberFontHHB.ttf";
    private static Typeface tf;
    public static Typeface setFont(Context context){
        if (tf==null){
            tf = Typeface.createFromAsset(context.getAssets(),forgUrl);
        }
      return tf;
    }
}
