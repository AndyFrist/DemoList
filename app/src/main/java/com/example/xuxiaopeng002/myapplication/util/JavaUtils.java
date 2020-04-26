package com.example.xuxiaopeng002.myapplication.util;

import android.text.TextUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.regex.Pattern;

/**
 * @Date: 2020-04-26
 * @author: ex-xuxiaopeng002
 * @Description:
 */
public class JavaUtils {
    //三者取最大
    public static int compareMax(int a,int b,int c){
        int temp = 0;
        temp = a>b?a:b;
        int max = 0;
        max = temp>c?temp:c;
        return max;
    }

    //三者取最小
    public static int compareMin(int a,int b,int c){
        int temp = 0;
        temp = a<b?a:b;
        int min = 0;
        min = temp<c?temp:c;
        return min;
    }

    //四舍五入保留 num 位小数
    public static String formatDouble1(float f,int num){
        BigDecimal b = new BigDecimal(f);
        double f1 = b.setScale(num, BigDecimal.ROUND_HALF_UP).doubleValue();
        return f1 + "";
    }
    public static String tothousand(int num){
        float value = num / 1000f;
        return formatDouble1(value,1) +"千";
    }

    //数字三位隔开
    public static String formatTosepara(String data) {
        try {

            float value = Float.parseFloat(data);

            DecimalFormat df = new DecimalFormat("#,###");
            return df.format(value);
        } catch (Exception e) {
            return "0";
        }
    }
    public static int parseInt(String value) {
        if (TextUtils.isEmpty(value)) {
            return 0;
        }else{
            Pattern pattern = Pattern.compile("[0-9]*");
            if(pattern.matcher(value).matches()){
                return Integer.parseInt(value);
            }else {
                return 0;
            }
        }
    }

    public static float parseFloat(String value) {
        if (TextUtils.isEmpty(value)) {
            return 0;
        }else{
            Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
            if(pattern.matcher(value).matches()){
                return Float.parseFloat(value);
            }else {
                return 0;
            }
        }
    }

}
