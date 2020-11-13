package com.example.xuxiaopeng002.myapplication.activity;

import android.annotation.SuppressLint;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.ex_xuxiaopeng002.myapplication.R;
import com.example.xuxiaopeng002.myapplication.adapter.TempAlbumAdapter;
import com.example.xuxiaopeng002.myapplication.util.ChooserPicHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 拍照插入列表，选择图片插入列表
 * SD卡里获取相片展示列表
 */

public class AlbumActivity extends AppCompatActivity {

    private RecyclerView tempRV;
    private RecyclerView sdRV;
    private Button clickbtn,clickbtn2;
    private TempAlbumAdapter tempAbumAdapter;
    private ArrayList<String> tempList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        tempRV = findViewById(R.id.tempRV);
        sdRV = findViewById(R.id.sdRV);
        clickbtn = findViewById(R.id.clickbtn);
        clickbtn2 = findViewById(R.id.clickbtn2);

        tempList.add("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1951006571,2765556974&fm=26&gp=0.jpg");
        tempList.add("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1951006571,2765556974&fm=26&gp=0.jpg");
        tempList.add("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1951006571,2765556974&fm=26&gp=0.jpg");
        tempList.add("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1951006571,2765556974&fm=26&gp=0.jpg");
        tempList.add("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1951006571,2765556974&fm=26&gp=0.jpg");
        tempList.add("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1951006571,2765556974&fm=26&gp=0.jpg");
        tempList.add("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1951006571,2765556974&fm=26&gp=0.jpg");
        tempList.add("add");

        tempAbumAdapter = new TempAlbumAdapter(tempList);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
        tempRV.setLayoutManager(layoutManager);
        tempRV.setAdapter(tempAbumAdapter);


        clickbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooserPicHelper.getInstance().setCaptureResultPathListener(new ChooserPicHelper.CaptureResultPathListener() {
                    @Override
                    public void captureResultPath(String... Result) {
                        tempList.addAll(Arrays.asList(Result));
                        tempAbumAdapter.notifyDataSetChanged();
                    }
                }).startChooserResultPathActivity(AlbumActivity.this, 4, 200, 0);
            }
        });

        clickbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooserPicHelper.getInstance().setCaptureResultListener(new ChooserPicHelper.CaptureResultListener() {
                    @Override
                    public void captureResult(String Result) {
                        clickbtn2.append(Result);
                    }
                }).startChooserActivity(AlbumActivity.this, 1, 200, 0);
            }
        });

        tempList.addAll( getImagePathFromSD());
        tempAbumAdapter.notifyDataSetChanged();

    }


    /**
     * 从sd卡获取图片资源
     * @return
     */
    private List<String> getImagePathFromSD() {
        // 图片列表
        List<String> imagePathList = new ArrayList<String>();
        // 得到sd卡内image文件夹的路径   File.separator(/)
        String filePath = Environment.getExternalStorageDirectory().toString() + File.separator
                + "DCIM/Camera";
        // 得到该路径文件夹下所有的文件
        File fileAll = new File(filePath);
        File[] files = fileAll.listFiles();
        // 将所有的文件存入ArrayList中,并过滤所有图片格式的文件
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            if (checkIsImageFile(file.getPath())) {
                imagePathList.add(file.getPath());
            }
        }
        // 返回得到的图片列表
        return imagePathList;
    }

    /**
     * 检查扩展名，得到图片格式的文件
     * @param fName  文件名
     * @return
     */
    @SuppressLint("DefaultLocale")
    private boolean checkIsImageFile(String fName) {
        boolean isImageFile = false;
        // 获取扩展名
        String FileEnd = fName.substring(fName.lastIndexOf(".") + 1,
                fName.length()).toLowerCase();
        if (FileEnd.equals("jpg") || FileEnd.equals("png") || FileEnd.equals("gif")
                || FileEnd.equals("jpeg")|| FileEnd.equals("bmp") ) {
            isImageFile = true;
        } else {
            isImageFile = false;
        }
        return isImageFile;
    }
}
