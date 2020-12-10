package com.jjs.zero.basecomponent.commonbind;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.util.Log;
import android.view.View;

import androidx.databinding.BindingAdapter;

import com.jjs.zero.basecomponent.R;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

/**
 * @Author: jiajunshuai
 * @CreateTime: 2020/11/17
 * @Details: <功能描述>
 */
public class CommonBind {

    @BindingAdapter(value = {"refreshing","moreLoading","hasMore"},requireAll = false)
    public static void bindSmartRefreshLayout(SmartRefreshLayout smartLayout,Boolean refreshing,Boolean moreLoading,Boolean hasMore) {

        Log.i("zero","bindSmartRefreshLayout：refreshing:"+refreshing+"     moreLoading:"+moreLoading+"  hasMore:"+hasMore);

        if (refreshing) smartLayout.finishRefresh();
        if (!hasMore) {
            smartLayout.finishLoadMoreWithNoMoreData();
            return;
        }
        if (moreLoading) smartLayout.finishLoadMore();
    }

    @BindingAdapter(value = {"autoRefresh"})
    public static void bindSmartRefreshLayout(SmartRefreshLayout smartLayout,boolean autoRefresh) {
        if (autoRefresh) smartLayout.autoRefresh();
    }

    @BindingAdapter(value = {"onRefreshListener","onLoadMoreListener"},requireAll = false)
    public static void bindSmartRefreshLayout(SmartRefreshLayout smartLayout, OnRefreshListener onRefreshListener, OnLoadMoreListener onLoadMoreListener) {
        smartLayout.setOnRefreshListener(onRefreshListener);
        smartLayout.setOnLoadMoreListener(onLoadMoreListener);
    }

    @BindingAdapter("bindRipple")
    public static void bindRipples(View view, Drawable drawableRes){
        Log.i("zero","bindRipples===========");
        if (drawableRes == null) return;
        int[][] status = new int[1][];
        status[0] = new int[]{android.R.attr.state_pressed};
        int[] colors = new int[]{R.color.colorPrimary};
        ColorStateList stateList = new ColorStateList(status,colors);
        RippleDrawable rippleDrawable = new RippleDrawable(stateList,drawableRes,null);
        view.setBackground(rippleDrawable);
    }


}
