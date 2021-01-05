package com.jjs.zero.baseviewlibrary.commonmodel;

import androidx.annotation.CallSuper;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jjs.zero.baseviewlibrary.BaseActivity;

import java.util.List;

/**
 * @Author: jiajunshuai
 * @CreateTime: 2020/11/18
 * @Details: <功能描述>
 */
public abstract class BaseViewModel<D> extends ViewModel {
    protected MutableLiveData<Integer> page = new MutableLiveData<>();
    protected MutableLiveData<Boolean> refreshing = new MutableLiveData<>();
    protected MutableLiveData<Boolean> moreLoading = new MutableLiveData<>();
    protected MutableLiveData<Boolean> hasMore = new MutableLiveData<>();
    protected MutableLiveData<Boolean> autoRefresh = new MutableLiveData<>();

    protected MutableLiveData<List<D>> liveData = new MutableLiveData<>();

//    protected String tips;
    protected BaseActivity activity;

    public BaseViewModel(){
        refreshing.setValue(true);
        moreLoading.setValue(false);
        hasMore.setValue(true);
        autoRefresh.setValue(false);
    }

    public BaseViewModel(BaseActivity activity){
        this();
        this.activity = activity;
    }

    //需要这个的时候当不需要参数的时候可以使用这种
    public abstract void init(BaseActivity activity);

    @CallSuper
    public void onLoadMore() {
        page.setValue(page.getValue()==null?page.getValue():0 +1);
        refreshing.setValue(false);
        moreLoading.setValue(true);
    }

    @CallSuper
    public void onRefresh() {
        page.setValue(0);
        refreshing.setValue(true);
        moreLoading.setValue(false);
    }

    @CallSuper
    public void autoRefresh() {
        autoRefresh.setValue(true);
    }

    public MutableLiveData<List<D>> getLiveData() {
        return liveData;
    }

    public void setLiveData(MutableLiveData<List<D>> liveData) {
        this.liveData = liveData;
    }

    public MutableLiveData<Integer> getPage() {
        return page;
    }

    public void setPage(MutableLiveData<Integer> page) {
        this.page = page;
    }

    public MutableLiveData<Boolean> getHasMore() {
        return hasMore;
    }

    public void setHasMore(MutableLiveData<Boolean> hasMore) {
        this.hasMore = hasMore;
    }

    public MutableLiveData<Boolean> getRefreshing() {
        return refreshing;
    }

    public void setRefreshing(MutableLiveData<Boolean> refreshing) {
        this.refreshing = refreshing;
    }

    public MutableLiveData<Boolean> getMoreLoading() {
        return moreLoading;
    }

    public void setMoreLoading(MutableLiveData<Boolean> moreLoading) {
        this.moreLoading = moreLoading;
    }

    public MutableLiveData<Boolean> getAutoRefresh() {
        return autoRefresh;
    }

    public void setAutoRefresh(MutableLiveData<Boolean> autoRefresh) {
        this.autoRefresh = autoRefresh;
    }
    //重写父类的方法子类也需要调用时使用
    @CallSuper
    @Override
    protected void onCleared() {
        super.onCleared();

        if (activity != null && liveData != null) {
            if (liveData.hasObservers()) liveData.removeObservers(activity);
            liveData = null;
            activity = null;
        }
        if (page != null) page = null;
        if (refreshing != null) refreshing = null;
        if (moreLoading != null) moreLoading = null;
        if (hasMore != null) hasMore = null;
        if (autoRefresh != null) autoRefresh = null;

    }
}
