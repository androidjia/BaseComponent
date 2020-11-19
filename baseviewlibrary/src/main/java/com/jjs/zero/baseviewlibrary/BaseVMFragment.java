package com.jjs.zero.baseviewlibrary;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.jjs.zero.baseviewlibrary.commonmodel.CommonViewModelFactory;
import com.jjs.zero.baseviewlibrary.databinding.FragmentBaseBinding;


/**
 * @Author: jiajunshuai
 * @CreateTime: 2020-01-06
 * @Details: <功能描述>
 */
public abstract class BaseVMFragment<V extends ViewDataBinding,M extends ViewModel> extends Fragment {

    protected V viewBinding;
    protected M viewModel;

    private View emptyView;
    private LoadingFragment loadingView;
    protected abstract int layoutResId();

    protected Context mContext;
    private FragmentBaseBinding mRootViewBinding;

//    protected boolean mIsVisible = false;
    private boolean isFirstLoad = true;//是否第一次加载

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        mRootViewBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_base,container,false);
        viewBinding = DataBindingUtil.inflate(inflater,layoutResId(),null,false);
        viewBinding.setLifecycleOwner(this);//lifecycle结合databinding只有在前台才会更新数据
        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT);
        mRootViewBinding.container.addView(viewBinding.getRoot(),params);
        return mRootViewBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (isFirstLoad) {
            isFirstLoad = false;
            initData();
        }

    }

    //    //数据缓加载
//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if (getUserVisibleHint()){
//            mIsVisible = true;
//            onVisible();
//        }else {
//            mIsVisible = false;
//            onInVisible();
//        }
//    }

    protected <T extends View> T getView(@IdRes int id){
        return (T)getView().findViewById(id);
    }

    //加载数据
    protected abstract void initData();

    protected void onInVisible() {

    }

    protected void onVisible() {
        loadData();
    }

    /**
     * 显示时加载数据 生命周期会先执行 setUserVisibleHint 再执行onActivityCreated
     * 在 onActivityCreated 之后第一次显示加载数据，只加载一次
     */
    protected void loadData() {

    }


    /**
     * 显示加载中状态
     */
    protected void showLoading() {
        if (loadingView != null) {
            loadingView = null;
        }
        loadingView = new LoadingFragment();
        loadingView.show(getActivity().getSupportFragmentManager(),LoadingFragment.class.getName());
    }

    /**
     * 隐藏加载状态
     */
    protected void hideLoading() {
        if (loadingView != null) {
            loadingView.hide();
            loadingView = null;
        }
    }

    /**
     * 加载完成的状态
     */
    protected void showContentView() {
        if (emptyView != null) {
            emptyView.setVisibility(View.GONE);
        }
        if (viewBinding.getRoot().getVisibility() != View.VISIBLE) {
            viewBinding.getRoot().setVisibility(View.VISIBLE);
        }
    }

    /**
     * 加载失败点击重新加载的状态
     */
    protected void showEmpty() {
        if (emptyView == null) {
            ViewStub viewStub = getView(R.id.view_empty);
            emptyView = viewStub.inflate();
            // 点击加载失败布局
//            errorView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    showLoading();
//                    onErrorRefresh();
//                }
//            });
        } else {
            emptyView.setVisibility(View.VISIBLE);
        }
        if (viewBinding.getRoot().getVisibility() != View.GONE) {
            viewBinding.getRoot().setVisibility(View.GONE);
        }
    }

    protected void showEmpty(CharSequence text, @DrawableRes int image) {
        showEmpty();
        ImageView iv = emptyView.findViewById(R.id.iv_empty);
        iv.setImageResource(image);
        TextView tv = emptyView.findViewById(R.id.tv_empty);
        tv.setText(text);
    }

    //加载失败后点击
//    protected void onErrorRefresh() {
//
//    }

    protected void showToast(CharSequence msg){
        Toast.makeText(mContext,msg, Toast.LENGTH_SHORT).show();
    }

    protected void showToast(@StringRes int msg){
        Toast.makeText(mContext,msg, Toast.LENGTH_SHORT).show();
    }


    /**
     * 返回没有参数的viewModel
     * @param m
     * @param <M>
     * @return
     */
    protected <M extends ViewModel> M createViewModel(Class<M> m) {
        return (M) new ViewModelProvider(getActivity(),new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication())).get(m);
    }

    /**
     * 返回带参数的viewModel
     * @param t
     * @param <M>
     * @return
     */
    protected <M extends ViewModel> M createViewModel(ViewModel t) {
        return (M) new ViewModelProvider(getActivity(),new CommonViewModelFactory(t)).get(t.getClass());
    }


}
