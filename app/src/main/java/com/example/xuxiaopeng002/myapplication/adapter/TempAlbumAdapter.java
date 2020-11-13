package com.example.xuxiaopeng002.myapplication.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.ex_xuxiaopeng002.myapplication.R;
import com.example.xuxiaopeng002.myapplication.util.glide.GlideUtils;

import java.util.List;

public class TempAlbumAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public TempAlbumAdapter(@Nullable List<String> data) {
        super(R.layout.item_abum, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

        if (item.startsWith("add")) {
        } else {
            ImageView imageView = helper.getView(R.id.image);
            GlideUtils.getInstance().glideLoad(item, imageView, R.drawable.default_bg);
        }

    }
}
