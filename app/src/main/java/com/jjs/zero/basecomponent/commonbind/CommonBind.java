package com.jjs.zero.basecomponent.commonbind;

import android.util.Log;

import androidx.databinding.BindingAdapter;

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

}
