package com.example.xuxiaopeng002.myapplication.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Date: 2020-04-26
 * @author: ex-xuxiaopeng002
 * @Description:
 */
public class TimeUtil {

    /**
     * 获取现在的年份
     *
     * @return
     */
    public static int getCurrentYear() {
        Calendar now = Calendar.getInstance();
        return now.get(Calendar.YEAR);
    }

    /**
     * 获取现在的月份
     *
     * @return
     */
    public static int getCurrentMonth() {
        Calendar now = Calendar.getInstance();
        return now.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取现在的天份
     *
     * @return
     */
    public static int getCurrentDay() {
        Calendar now = Calendar.getInstance();
        return now.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取某年某月有多少天
     *
     * @param year
     * @param month
     * @return
     */
    public static int getDayOfMonth(int year, int month) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, 0); //输入类型为int类型
        return c.get(Calendar.DAY_OF_MONTH);
    }


    /**
     * 字符串类型时间戳转 日期yyyy-MM-dd HH:mm
     *
     * @param longg
     * @return
     */
    public static String string2date(String longg) {
        try {
            Long timeStamp = Long.parseLong(longg);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");//这个是你要转成后的时间的格式
            longg = sdf.format(new Date(timeStamp));   // 时间戳转换成时间
        } catch (Exception e) {
            e.printStackTrace();
        }
        return longg;
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static String getCurrentTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  //设置日期格式
        return df.format(new Date());       // new Date()为获取当前系统时间

    }


    /**
     * 获取现在时间
     */
    public static String getNowyearMonth() {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM");//MMddHHmmss
        return dateFormat.format(now);
    }

    /**
     * 字符转日期
     */
    public static Date toDate(String dateStr) {
        Date date = null;
        SimpleDateFormat formater = new SimpleDateFormat();
        formater.applyPattern("yyyy-MM-dd");
        try {
            date = formater.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    /**
     * 获取n个月后的月份数
     */
    public static int getnextmonth(Date date, int n) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");// 定义日期显示格式
        calendar.add(Calendar.MONTH, n);// 获取上个月月份
        return calendar.getTime().getMonth() + 1;
    }

    /**
     * 获取上个月当前时间
     *
     * @return
     */
    public static String getlastTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();

        System.out.println("当前时间是：" + dateFormat.format(date));

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date); // 设置为当前时间
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1); // 设置为上一个月
        date = calendar.getTime();

        System.out.println("上一个月的时间： " + dateFormat.format(date));

        return dateFormat.format(date);
    }
}
