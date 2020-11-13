package com.example.xuxiaopeng002.myapplication.util.glide;


import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.ex_xuxiaopeng002.myapplication.R;
import com.example.xuxiaopeng002.myapplication.app.MyApp;


import java.io.File;


public class GlideUtils {

    private String TAG = GlideUtils.class.getSimpleName();

    /**
     * 借助内部类 实现线程安全的单例模式
     * 属于懒汉式单例，因为Java机制规定，内部类SingletonHolder只有在getInstance()
     * 方法第一次调用的时候才会被加载（实现了lazy），而且其加载过程是线程安全的。
     * 内部类加载的时候实例化一次instance。
     */
    private GlideUtils() {
    }

    private static class GlideLoadUtilsHolder {
        private final static GlideUtils INSTANCE = new GlideUtils();
    }

    public static GlideUtils getInstance() {
        return GlideLoadUtilsHolder.INSTANCE;
    }

    /**
     * Glide 加载 简单判空封装 防止异步加载数据时调用Glide 抛出异常
     *
     * @param context
     * @param url       加载图片的url地址  String
     * @param imageView 加载图片的ImageView 控件
     */
    public void glideLoad(Context context, String url, ImageView imageView) {
        if (context instanceof Activity && ((Activity) context).isDestroyed()) {
            return;
        }
        if (context != null) {
            Glide.with(context).load(url).into(imageView);
        } else {
            Log.i(TAG, "Picture loading failed,context is null");
        }
    }

    public void glideLoadAsGif(Context context, String url, ImageView imageView) {
        if (context instanceof Activity && ((Activity) context).isDestroyed()) {
            return;
        }
        if (context != null) {
            Glide.with(context).asGif().load(url).into(imageView);
        } else {
            Log.i(TAG, "Picture loading failed,context is null");
        }
    }

    public void glideLoad(Context context, String url, ImageView imageView, RequestOptions options) {
        if (context instanceof Activity && ((Activity) context).isDestroyed()) {
            return;
        }
        if (context != null) {
            Glide.with(context).load(url).apply(options).into(imageView);
        } else {
            Log.i(TAG, "Picture loading failed,context is null");
        }
    }

    public void glideLoad(Context context, int res, ImageView imageView, RequestOptions options) {
        if (context instanceof Activity && ((Activity) context).isDestroyed()) {
            return;
        }
        if (context != null) {
            Glide.with(MyApp.INSTANCE).load(res).apply(options).into(imageView);
        } else {
            Log.i(TAG, "Picture loading failed,context is null");
        }
    }

    public void glideLoadRound(String url, ImageView imageView, int r) {
        Glide.with(MyApp.INSTANCE)
                .load(url)
                .transform(new GlideRoundTransform(MyApp.INSTANCE, r))
                .error(R.drawable.default_bg)
                .into(imageView);

    }
    public void glideLoadRound(String url, ImageView imageView, int r,int defultimage) {
        Glide.with(MyApp.INSTANCE)
                .load(url)
                .transform(new GlideRoundTransform(MyApp.INSTANCE, r))
                .placeholder(defultimage)
                .error(defultimage)
                .into(imageView);

    }

    public void glideLoad(String url, ImageView imageView) {
        Glide.with(MyApp.INSTANCE).load(url).into(imageView);
    }
    public void glideLoad(String url, ImageView imageView,int defultimage) {
        Glide.with(MyApp.INSTANCE).load(url).placeholder(defultimage).error(defultimage).into(imageView);
    }

    /**
     * Glide 加载 简单判空封装 防止异步加载数据时调用Glide 抛出异常
     *
     * @param context
     * @param bytes           加载图片的byte[]
     * @param imageView     加载图片的ImageView 控件
     * @param placeholder   图片展示默认本地图片 id
     * @param error_image   图片展示错误的本地图片 id
     *
     */
    public void glideLoad(Context context, byte[] bytes, ImageView imageView,int placeholder,int error_image) {
        if (context != null) {
            Glide.with(context).load(bytes).placeholder(placeholder).error(error_image).into(imageView);
        }
    }
    public void glideLoad(Activity activity, File file, ImageView imageView) {
        if (!activity.isDestroyed()) {
            Glide.with(activity).load(file).into(imageView);
        }
    }


    //Glide加载图片为圆形
    public  void loadCircleImageView(Context context, String url, ImageView iv, boolean isShowFrame, int color) {
        if (context != null) {
            if (context instanceof Activity) {
                if (!((Activity) context).isDestroyed()) {
                    if (isShowFrame) {
                        loadCircleBorder(context, url, iv, color);
                    } else {
                        loadCircle(context, url, iv);
                    }
                }
            } else {
                if (isShowFrame) {
                    loadCircleBorder(context, url, iv, color);
                } else {
                    loadCircle(context, url, iv);
                }
            }
        }
    }

    private static void loadCircle(Context context, String url, ImageView iv) {
        Glide.with(context).load(url).transform(new GlideRoundTransform(context)).
                diskCacheStrategy(DiskCacheStrategy.RESOURCE).into(iv);
    }

    private static void loadCircleBorder(Context context, String url, ImageView iv, int color) {
        Glide.with(context).load(url).
                diskCacheStrategy(DiskCacheStrategy.RESOURCE).
                transform(new GlideCircleTransformWithBorder(context, 1, color)).
                into(iv);
    }



}