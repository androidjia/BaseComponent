package com.jjs.zero.utilslibrary.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

/**
 * @Author: jiajunshuai
 * @CreateTime: 2020/10/22
 * @Details: <功能描述控制滑动和切换动画>
 */
public class ViewPagerNoScroll extends ViewPager {

    private boolean isScroll;
    private boolean isAnimator = true;
    public ViewPagerNoScroll(Context context) {
        super(context);
    }

    public ViewPagerNoScroll(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isScroll) return super.onInterceptTouchEvent(ev);
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isScroll)return super.onTouchEvent(ev);
        return true;
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, isAnimator);
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item,isAnimator);
    }

    public void setScroll(boolean isScroll) {
        this.isScroll = isScroll;
    }

    public void setAnimator(boolean animator) {
        isAnimator = animator;
    }
}
