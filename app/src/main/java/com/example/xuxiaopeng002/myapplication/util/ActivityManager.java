package com.example.xuxiaopeng002.myapplication.util;

import android.app.Activity;

import java.util.Iterator;
import java.util.Stack;

/**
 * @Date: 2020-04-21
 * @author: ex-xuxiaopeng002
 * @Description:
 */
public class ActivityManager {
    private Stack<Activity> activityStack = new Stack<>();
    private static ActivityManager activityManager = new ActivityManager();
    private ActivityManager(){

    }


    public static ActivityManager getInstance() {
        return activityManager;
    }

    public final void add(Activity activity){
        activityStack.add(activity);
    }

    public final void finishActivity(Activity activity){
        activityStack.remove(activity);
    }

    public final Activity currentActivity() {
       return this.activityStack.lastElement();
    }

    public final void finishAllActivity(){
        Iterator var2 = this.activityStack.iterator();
        while(var2.hasNext()) {
            Activity activity = (Activity)var2.next();
            activity.finish();
        }
        this.activityStack.clear();
    }
}
