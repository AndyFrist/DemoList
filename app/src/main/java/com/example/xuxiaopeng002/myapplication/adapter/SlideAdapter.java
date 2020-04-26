package com.example.xuxiaopeng002.myapplication.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.xuxiaopeng002.myapplication.activity.MyRecycleViewActivity;
import com.example.xuxiaopeng002.myapplication.util.AndroidUtils;
import com.example.ex_xuxiaopeng002.myapplication.R;
import com.example.xuxiaopeng002.myapplication.view.LeftSlideView;

import java.util.List;

public class SlideAdapter extends BaseQuickAdapter<String, BaseViewHolder> implements LeftSlideView.IonSlidingButtonListener {
    private LeftSlideView mMenu = null;
    private IonSlidingViewClickListener mIDeleteBtnClickListener;
    private MyRecycleViewActivity activity;

    public SlideAdapter(List<String> data, IonSlidingViewClickListener ionSlidingViewClickListener, MyRecycleViewActivity activity) {

        super(R.layout.itme_todo, data);
        mIDeleteBtnClickListener = ionSlidingViewClickListener;
        this.activity = activity;
    }

    @Override
    protected void convert(final BaseViewHolder helper, String item) {
        helper.setText(R.id.todo, item);

        ((LeftSlideView)helper.getView(R.id.m_leftslideview)).setSlidingButtonListener(SlideAdapter.this);
        helper.getView(R.id.layout_content).getLayoutParams().width = AndroidUtils.getScreenWidth(activity);
        helper.getView(R.id.layout_content).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断是否有删除菜单打开
                if (menuIsOpen()) {
                    closeMenu();//关闭菜单
                } else {
                    int n = helper.getLayoutPosition();
                    mIDeleteBtnClickListener.onItemClick(v, n);
                }

            }
        });

        //左滑删除点击事件
        helper.getView(R.id.tv_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int n = helper.getLayoutPosition();
                mIDeleteBtnClickListener.onDeleteBtnCilck(view, n);
            }
        });
    }

    /**
     * 删除item
     *
     * @param position
     */
    public void removeData(int position) {
        remove(position);
        notifyItemRemoved(position);
    }


    @Override
    public void onMenuIsOpen(View view) {

        mMenu = (LeftSlideView) view;
    }

    @Override
    public void onDownOrMove(LeftSlideView leftSlideView) {
        if (menuIsOpen()) {
            if (mMenu != leftSlideView) {
                closeMenu();
            }
        }

    }


    /**
     * 关闭菜单
     */
    public void closeMenu() {
        mMenu.closeMenu();
        mMenu = null;

    }

    /**
     * 判断菜单是否打开
     *
     * @return
     */
    public Boolean menuIsOpen() {
        if (mMenu != null) {
            return true;
        }
        return false;
    }


    /**
     * 注册接口的方法：点击事件。在Mactivity.java实现这些方法。
     */
    public interface IonSlidingViewClickListener {
        void onItemClick(View view, int item);//点击item正文

        void onDeleteBtnCilck(View view, int item);//点击“删除”

    }

}
