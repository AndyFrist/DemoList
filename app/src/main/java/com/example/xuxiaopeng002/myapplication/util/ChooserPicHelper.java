package com.example.xuxiaopeng002.myapplication.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.PathUtils;
import com.example.ex_xuxiaopeng002.myapplication.R;
import com.example.xuxiaopeng002.myapplication.util.glide.MyGlideEngine;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

//import com.blankj.utilcode.util.ToastUtils;

public class ChooserPicHelper {

    private volatile static ChooserPicHelper singleton = null;
    private static int TAKE_PHONE_REQUEST = 1001;
    private static int REQUEST_SELECT_IMAGES_CODE = 1002;
    public static ChooserPicHelper getInstance() {
        if (singleton == null) {
            synchronized (ChooserPicHelper.class) {
                if (singleton == null) {
                    singleton = new ChooserPicHelper();
                }
            }
        }
        return singleton;
    }

    public ChooserPicHelper setCaptureResultListener(ChooserPicHelper.CaptureResultListener listener) {
        ChooserPicHelper.listener = listener;
        return this;
    }
    public ChooserPicHelper setCaptureResultPathListener(ChooserPicHelper.CaptureResultPathListener listener) {
        ChooserPicHelper.Pathlistener = listener;
        return this;
    }
    private static ChooserPicHelper.CaptureResultListener listener;
    private static ChooserPicHelper.CaptureResultPathListener Pathlistener;
    public interface CaptureResultListener {
        void captureResult(String Result);
    }
    public interface CaptureResultPathListener {
        void captureResultPath(String... Result);
    }
    public void startChooserActivity(Activity activity, int maxSelect,int picSize,int type) {
        startChooserActivity(activity,maxSelect,picSize,type,true);
    }

    public void startChooserActivity(Activity activity, int maxSelect,int picSize,int type,boolean isBase64) {
        Intent intent = new Intent(activity, ChooserPicHelper.FileChooserActivity.class);
        intent.putExtra("maxSelect", maxSelect);
        intent.putExtra("isBase64", isBase64);
        intent.putExtra("picSize", picSize);
        intent.putExtra("type",type);
        activity.startActivity(intent);
    }

    public void startChooserResultPathActivity(Activity activity, int maxSelect,int picSize,int type) {
        startChooserActivity(activity,maxSelect,picSize,type,false);
    }


    public static class FileChooserActivity extends AppCompatActivity implements View.OnClickListener {
        private TextView mTakePhoto,mChooseLocal,mCancel;
        private AppCompatActivity mContext = this;
        private  int maxSelect = 1;
        private  int picSize = 0;
        protected CompositeDisposable compositeDisposable;
        File outputImage = null;
        Uri photoUri = null;
        private int type;
        private Boolean isBase64 = true;
        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            BarUtils.setStatusBarColor(this, Color.TRANSPARENT);
            Intent intent = getIntent();
            if (intent!=null){
                maxSelect = intent.getIntExtra("maxSelect",1);
                picSize = intent.getIntExtra("picSize",0);
                type = intent.getIntExtra("type",0);
                isBase64 = intent.getBooleanExtra("isBase64",true);
            }
            compositeDisposable = new CompositeDisposable();
            if (type==1){
                takePhone();
            }else if (type==2){
                zhihu();
            }else {
                setContentView(R.layout.choise_pic_layout);
                mTakePhoto = findViewById(R.id.m_tv_take_photo);
                mChooseLocal = findViewById(R.id.m_tv_choose_local);
                mCancel = findViewById(R.id.m_tv_cancel);
                mTakePhoto.setOnClickListener(this);
                mChooseLocal.setOnClickListener(this);
                mCancel.setOnClickListener(this);
            }


        }

        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.m_tv_take_photo) {
                takePhone();
            } else if (id == R.id.m_tv_choose_local) {
                zhihu();
            } else if (id == R.id.m_tv_cancel) {
                finish();
            }
        }

        @SuppressLint("CheckResult")
        private void takePhone() {
            RxPermissions permissions = new RxPermissions(this);
            permissions.setLogging(true);
            permissions.request(
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            ).subscribe(new Consumer<Boolean>() {
                @Override
                public void accept(Boolean aBoolean) throws Exception {
                    if (aBoolean) {
                        haveRightToTakePhone();
                    } else {
                        ToastUtils.show("暂无权限");
                    }
                }
            });
        }




        private void haveRightToTakePhone() {
            outputImage =new  File(PathUtils.getExternalStoragePath(),   System.currentTimeMillis()+".JPEG");
            FileUtils.createFileByDeleteOldFile(outputImage);
            //判断当前Android版本
            if (Build.VERSION.SDK_INT >= 24) {
                photoUri = FileProvider.getUriForFile(
                        this, AppUtils.getAppPackageName() + ".fileprovider",
                        outputImage
            );
            } else {
                photoUri = Uri.fromFile(outputImage);
            }

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            startActivityForResult(intent, TAKE_PHONE_REQUEST);

        }
        @SuppressLint("CheckResult")
        private void zhihu() {

              RxPermissions permissions = new RxPermissions(this);
              permissions.setLogging(true);
              permissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Consumer<Boolean>() {
                @Override
                public void accept(Boolean aBoolean){
                    if (aBoolean){
                        //已授权
                        Matisse.from(mContext)
                                .choose(MimeType.allOf())
                                .countable(false)
                                .maxSelectable(maxSelect)
                                .theme(R.style.Matisse_Dracula)
                                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                                .thumbnailScale(0.85f)
                                .imageEngine(new MyGlideEngine())
                                .forResult(REQUEST_SELECT_IMAGES_CODE);
                    }else {
                        ToastUtils.show("暂无权限");
                    }
                }
            });

        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode ==TAKE_PHONE_REQUEST){
                if (photoUri != null) {
                    try {
                        String path = UriTool.getFilePathByUri(mContext,photoUri);
                         if (FileUtils.getFileLength(path)<1){
                             FileUtils.delete(path);
                             finish();
                             return;
                         }
                            if (picSize<1){
                                if (isBase64){
                                    Bitmap b = null;
                                    b = BitmapFactory.decodeStream(new FileInputStream(path));
                                    if (b!=null){
                                        listener.captureResult(ImageUtil.bitmapToBase64(b));
                                    }
                                }else {
                                    Pathlistener.captureResultPath(path);
                                }
                            }else {
                                Luban.with(this)
                                        .load(path)                                   // 传人要压缩的图片列表
                                        .ignoreBy(picSize)// 忽略不压缩图片的大小
                                        .setCompressListener(new OnCompressListener() { //设置回调
                                            @Override
                                            public void onStart() {
                                            }
                                            @Override
                                            public void onSuccess(File file) {
                                                if (isBase64){
                                                    //  压缩成功后调用，返回压缩后的图片文件
                                                    Bitmap b = null;
                                                    try {
                                                        b = BitmapFactory.decodeStream(new FileInputStream(file));
                                                    } catch (FileNotFoundException e) {
                                                        e.printStackTrace();
                                                    }
                                                    if (b!=null){
                                                        String base64Img = ImageUtil.bitmapToBase64(b);
                                                        listener.captureResult(base64Img);
                                                    }
                                                }else {
                                                    Pathlistener.captureResultPath(file.getAbsolutePath());
                                                }
                                            }
                                            @Override
                                            public void onError(Throwable e) {
                                                ToastUtils.show("图片压缩出现问题了" + e.getMessage());
                                                //  当压缩过程出现问题时调用
                                            }
                                        }).launch();    //启动压缩
                            }


                    } catch (Exception e ) {
                      e.printStackTrace();
                    }
                }
            }else if (requestCode==REQUEST_SELECT_IMAGES_CODE){
                if (data != null) {
                    final ArrayList<Uri> arrayList = new ArrayList<>(Matisse.obtainResult(data));
                    if (arrayList == null || arrayList.size() == 0) {
                        ToastUtils.show("获取图片失败");
                    }else {
                        final ArrayList<String> arrayList1 = new ArrayList();
                        for (Uri uri : arrayList) {
                            Log.e("Log","uri = " + uri);
                            arrayList1.add(UriTool.getFilePathByUri(mContext, uri));
                        }
                            Disposable disposable = Flowable.just(arrayList1)
                                    .onBackpressureDrop()
                                    .observeOn(Schedulers.io())
                                    .map(new Function<ArrayList, Object>() {
                                        @Override
                                        public Object apply(ArrayList list) {
                                            // 同步方法直接返回压缩后的文件
                                            JSONArray jsonArray = new JSONArray();
                                            JSONArray jsonNameArray = new JSONArray();
                                            ArrayList<String> pathlist = new ArrayList<>();
                                            if (picSize<1){
                                                if (isBase64){
                                                    for (String path:arrayList1){
                                                        Bitmap b = null;
                                                        try {
                                                            b = BitmapFactory.decodeStream(new FileInputStream(path));
                                                            String base64Img = ImageUtil.bitmapToBase64(b);
                                                            jsonArray.put(base64Img);
                                                            jsonNameArray.put(FileUtils.getFileName(path));
                                                        } catch (FileNotFoundException e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                }else {
                                                    pathlist.addAll(arrayList1);
                                                }
                                            }else {
                                                List<File> listFile = null;
                                                try {
                                                    listFile = Luban.with(FileChooserActivity.this).load(arrayList1).ignoreBy(picSize).get();
                                                    for (int i = 0; i < listFile.size(); i++) {
                                                        if (isBase64){
                                                            Bitmap b = null;
                                                            try {
                                                                b = BitmapFactory.decodeStream(new FileInputStream(listFile.get(i)));
                                                            } catch (FileNotFoundException e) {
                                                                e.printStackTrace();
                                                            }
                                                            String base64Img = ImageUtil.bitmapToBase64(b);
                                                            jsonArray.put(base64Img);
                                                            jsonNameArray.put(FileUtils.getFileName(arrayList1.get(i)));
                                                        }else {
                                                            pathlist.add(listFile.get(i).getAbsolutePath());
                                                        }
                                                    }
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                    Log.e("Log","IOException = "+e.getMessage());
                                                }
                                            }
                                            if (isBase64){
                                                JSONObject jo1 = new JSONObject();
                                                try {
                                                    jo1.put("imageArr", jsonArray);
                                                    jo1.put("imageNameArr", jsonNameArray);
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                                return jo1;
                                            }else {
                                                return pathlist;
                                            }
                                        }
                                    })
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Consumer<Object>() {
                                        @Override
                                        public void accept(Object jsonObject) throws Exception {
                                            if(listener!=null){
                                                listener.captureResult(jsonObject.toString());
                                            }
                                            if (Pathlistener!=null){
                                                ArrayList<String> paths = (ArrayList<String>) jsonObject;
                                                String[] desc = new String[paths.size()];
                                                paths.toArray(desc);
                                                Pathlistener.captureResultPath(desc);
                                            }
                                        }
                                    });

                            compositeDisposable.add(disposable);
                    }
                }
            }
            finish();

        }
    }
}
