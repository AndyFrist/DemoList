package com.example.ex_xuxiaopeng002.myapplication.util;

/**
 * @Author: ex-xuxiaopeng002
 * @CreateDate: 2019-06-04 10:44
 * @Description: java类作用描述
 */

import android.content.Context;

public class TestManager {

    private static TestManager manager;
    private Context context;

    private TestManager(Context context) {
        this.context = context;
    }

    /**
     * 如果传入的context是activity,service的上下文，会导致内存泄漏
     * 原因是我们的manger是一个static的静态对象，这个对象的生命周期和整个app的生命周期一样长
     * 当activity销毁的时候，我们的这个manger仍然持有者这个activity的context，就会导致activity对象无法被释放回收，就导致了内存泄漏
     */
    public static TestManager getInstance(Context context) {
        if (manager == null) {
            manager = new TestManager(context);
        }
        return manager;
    }

    //正确写法
    public static TestManager getInstanceSafe(Context context) {
        if (manager == null) {
            manager = new TestManager(context.getApplicationContext());
        }
        return manager;
    }

}
