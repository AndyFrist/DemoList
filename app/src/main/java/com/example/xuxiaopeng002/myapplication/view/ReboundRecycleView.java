package com.example.xuxiaopeng002.myapplication.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.DecelerateInterpolator;

import com.example.ex_xuxiaopeng002.myapplication.R;

/**
 * @Author: ex-xuxiaopeng002
 * @CreateDate: 2019-07-02 10:56
 * @Description: 回弹效果的recycleView
 */
public class ReboundRecycleView extends RecyclerView {

    private int oritation;

    public int getOritation() {
        return oritation;
    }

    /**
     * 只能设置0和1
     * <br/><br/>
     * 0表示RecylerView是竖直排布
     * <br/><br/>
     * 1表示RecylerView是水平排布
     * <br/><br/>
     * 否则默认为0---竖直排布
     */
    public void setOritation(int oritation) {
        if (oritation != 0 && oritation != 1) {
            oritation = 0;
        }
        this.oritation = oritation;
    }

    public ReboundRecycleView(Context context) {
        this(context, null);
    }

    public ReboundRecycleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ReboundRecycleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        paddingTop = getPaddingTop();
        paddingBottom = getPaddingBottom();
        paddingLeft = getPaddingLeft();
        paddingRight = getPaddingRight();

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BounceRecyclerView, defStyle, 0);
        //enum中的value只能为int类型
        oritation = typedArray.getInt(R.styleable.BounceRecyclerView_oritation, 0);
        typedArray.recycle();
    }


    private float downTouch;
    //因为Item设置了点击监听导致RecyclerView收不到ACTION_DOWN的触摸事件
    private boolean firstTouch = true;
    private static final int MOVE_VERTIFY = 1;
    //可以延伸到屏幕的四分之一
    private static final int DEFAULT_DEVIDE = 1;

    //recyclerView_thumbnail的padding
    private int paddingTop;
    private int paddingBottom;
    private int paddingLeft;
    private int paddingRight;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
//                downY = event.getY();
////                    Log.d("zbv","downY="+downY);
                break;
            case MotionEvent.ACTION_MOVE:
                //这样写是因为无法监听到down事件所以第一次move事件的坐标作为down

                if (firstTouch) {
                    //消除第一次downX和moveX不一致
                    if (oritation == 0) {
                        downTouch = event.getY();
                    } else if (oritation == 1) {
                        downTouch = event.getX();
                    }
                    firstTouch = false;
                    return false;
                }
                float moveTouch = 0;
                if (oritation == 0) {
                    moveTouch = event.getY();
                    if (!canScrollVertically(-1)) {
                        if ((moveTouch - downTouch) >= MOVE_VERTIFY) {
                            int deltY = (int) (moveTouch - downTouch) / DEFAULT_DEVIDE;
                            setPadding(getPaddingLeft(), getPaddingTop() + deltY, getPaddingRight(),
                                    getPaddingBottom());
                        } else if ((moveTouch - downTouch) <= -MOVE_VERTIFY) {
                            setPadding(getPaddingLeft(), paddingTop, getPaddingRight(), paddingBottom);
                        }
                    } else if (!canScrollVertically(1)) {
                        if ((downTouch - moveTouch) >= MOVE_VERTIFY) {
                            int deltY = (int) (downTouch - moveTouch) / DEFAULT_DEVIDE;
                            setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight(),
                                    getPaddingBottom() + deltY);
                        } else if ((downTouch - moveTouch) <= -MOVE_VERTIFY) {
                            setPadding(getPaddingLeft(), paddingTop, getPaddingRight(), paddingBottom);
                        }
                    } else {
                        setPadding(getPaddingLeft(), paddingTop, getPaddingRight(), paddingBottom);
                    }
                } else if (oritation == 1) {
                    moveTouch = event.getX();
                    if (!canScrollHorizontally(-1)) {
                        if ((moveTouch - downTouch) >= MOVE_VERTIFY) {
                            int deltY = (int) (moveTouch - downTouch) / DEFAULT_DEVIDE;
                            setPadding(getPaddingLeft() + deltY, getPaddingTop(), getPaddingRight(),
                                    getPaddingBottom());
                        } else if ((moveTouch - downTouch) <= -MOVE_VERTIFY) {
                            setPadding(getPaddingLeft(), paddingTop, getPaddingRight(), paddingBottom);
                        }
                    } else if (!canScrollHorizontally(1)) {
                        if ((downTouch - moveTouch) >= MOVE_VERTIFY) {
                            int deltY = (int) (downTouch - moveTouch) / DEFAULT_DEVIDE;
                            setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight() + deltY,
                                    getPaddingBottom());
                        } else if ((downTouch - moveTouch) <= -MOVE_VERTIFY) {
                            setPadding(getPaddingLeft(), paddingTop, getPaddingRight(), paddingBottom);
                        }
                    } else {
                        setPadding(getPaddingLeft(), paddingTop, getPaddingRight(), paddingBottom);
                    }
                }

                //防止在既不是顶部也不是底部时的闪烁
                downTouch = moveTouch;
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (oritation == 0) {
                    startVertical();
//                    setPadding(getPaddingLeft(), paddingTop, getPaddingRight(), paddingBottom);
                } else if (oritation == 1) {
                    setPadding(paddingLeft, getPaddingTop(), paddingRight, getPaddingBottom());
                }
                firstTouch = true;
                break;
        }
        return super.onTouchEvent(event);


    }

    private void startVertical() {

        ValueAnimator valueAnimator = ValueAnimator.ofInt(getPaddingTop(), paddingTop);
        if (getPaddingTop() > 0) {
            valueAnimator = ValueAnimator.ofInt(getPaddingTop(), paddingTop);
        }

        if (getPaddingBottom() > 0) {
            valueAnimator = ValueAnimator.ofInt(getPaddingBottom(), paddingBottom);
        }

        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int offset = 0;

                if (getPaddingTop() > 0) {
                    offset = (int) animation.getAnimatedValue();
                    setPadding(0, offset, 0, getPaddingBottom());
                }

                if (getPaddingBottom() > 0) {
                    offset = (int) animation.getAnimatedValue();
                    setPadding(0, getPaddingTop(), 0, offset);
                }

                Log.e("offset", "offset=" + offset);
                invalidate();
            }
        });
        valueAnimator.start();
    }


}
