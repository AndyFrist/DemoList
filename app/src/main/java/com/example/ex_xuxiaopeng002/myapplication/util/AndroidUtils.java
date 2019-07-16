package com.example.ex_xuxiaopeng002.myapplication.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Environment;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @Author: ex-xuxiaopeng002
 * @CreateDate: 2019-05-31 10:20
 * @Description: java类作用描述
 */
public class AndroidUtils {
    public static String SD_PATH = Environment.getExternalStorageDirectory() + "/formats/";

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


    /**
     * 截取指定view
     * @param view
     * @return 文件路径
     */
    public static Bitmap shotView(View view){
        view.buildDrawingCache(false);
        Bitmap bitmap = view.getDrawingCache();

//                saveBitmap1(bitmap, "screenshots" + System.currentTimeMillis());
        return bitmap;
    }

    /**
     * 保存图片
     *
     * @param bm      保存的图片
     * @param picName 图片名称
     * @return
     */
    public static String saveBitmap1(Bitmap bm, String picName) {
        return saveBitmap1(bm, picName, SD_PATH);
    }
    static OutputStream out;

    public static String saveBitmap1(Bitmap bm, String picName, String path) {
        File f = null;
        try {
            if (!isDirExist(path)) {
                createSDDir(path);
            }
            f = new File(path, picName + ".JPEG");
            if (f.exists()) {
                return f.getAbsolutePath();
            }
            out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.JPEG, 50, out);
            out.flush();
            out.close();
            return f.getAbsolutePath();
        } catch (FileNotFoundException e) {
            if (f!=null){
                return f.getAbsolutePath();
            }else {
                return null;
            }
        } catch (IOException e) {
            if (f!=null){
                return f.getAbsolutePath();
            }else {
                return null;
            }
        }finally {
            try {
                if (out!=null){
                    out.flush();
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }



    /**
     * 判断文件是否存在
     *
     * @param fileDir 文件名
     * @return
     */
    public static boolean isDirExist(String fileDir) {
        File file = new File(fileDir);
        file.isFile();
        return file.exists();
    }
    /**
     * 创建路径
     *
     * @param dirName 路径
     * @return
     * @throws IOException
     */
    public static void createSDDir(String dirName) throws IOException {
        File dir = new File(dirName);
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            dir.mkdirs();
        } else {
            System.out.println("未发现内存卡");
            return;
        }
    }

}
