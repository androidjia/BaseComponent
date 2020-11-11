package com.jjs.zero.baseviewlibrary;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.jjs.zero.baseviewlibrary.databinding.ActivityBaseBinding;


/**
 * @Author: jiajunshuai
 * @CreateTime: 2019-06-18
 * @Details: <功能描述>
 */
public abstract class BaseActivity<V extends ViewDataBinding> extends AppCompatActivity {


    protected V viewBinding;
    private View errorView;
    private LoadingFragment loadingFragment;

    private ActivityBaseBinding mBaseBinding;
    protected Toolbar mToolbar;
    protected Context mContext;
    public abstract int layoutResId();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(layoutResId());
    }

    @Override
    public void setContentView(int layoutResID) {
        mBaseBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_base,null,false);
        viewBinding = DataBindingUtil.inflate(getLayoutInflater(),layoutResID,null,false);
//        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        RelativeLayout mContainer = mBaseBinding.getRoot().findViewById(R.id.container);
//        mContainer.addView(viewBinding.getRoot(),params);
//        RelativeLayout mContainer = ;
        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT,ConstraintLayout.LayoutParams.MATCH_PARENT);
        mBaseBinding.container.addView(viewBinding.getRoot(),params);
        getWindow().setContentView(mBaseBinding.getRoot());
        setToolBar();
        initData();
    }

    protected int setToolBarAndStatusBarColor() {
        return R.color.color_white;
    }

    /**
     * 获取父布局
     *
     *
     * @return
     */
    protected ConstraintLayout getParentView(){
        return mBaseBinding.container;
    }

    /**
     * 设置根布局距离父布局的位置
     * @param
     */
    protected void setRootViewMarginTop(int marginTop) {
        ConstraintLayout.LayoutParams param = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        param.topMargin = marginTop;
        mBaseBinding.container.setLayoutParams(param);
    }

    //加载数据
    protected abstract void initData();

    /**
     * 返回上一页
     */
    protected void onBack() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        } else {
            onBackPressed();
        }
    }

    protected TextView getTitleRight() {
        return mBaseBinding.tvRight;
    }

    private void setToolBar() {
        mToolbar = mBaseBinding.toolBar;
        mToolbar.setBackgroundColor(ContextCompat.getColor(mContext,setToolBarAndStatusBarColor()));
        setSupportActionBar(mToolbar);
        mBaseBinding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBack();
            }
        });


    }

    @Override
    public void setTitle(CharSequence title) {
        mBaseBinding.tvCenterTitle.setText(title);
    }

    public TextView getTitleView(){
        return mBaseBinding.tvCenterTitle;
    }

    public void showLoading(){
        if (loadingFragment != null) {
            loadingFragment = null;
        }
        loadingFragment = new LoadingFragment();
        loadingFragment.show(getSupportFragmentManager(),LoadingFragment.class.getName());
    }

    public void hideLoading() {
        if (loadingFragment != null) {
            loadingFragment.hide();
            loadingFragment.onDestroy();
            loadingFragment = null;
        }
    }

    protected void showContentView(){
        if (errorView!=null){
            if (errorView.getVisibility() != View.GONE) {
                errorView.setVisibility(View.GONE);
            }
        }

        if (viewBinding.getRoot().getVisibility() != View.VISIBLE){
            viewBinding.getRoot().setVisibility(View.VISIBLE);
        }
    }

    protected void showEmpty(){
        if (errorView==null){
            ViewStub viewStub = findViewById(R.id.view_empty);
            errorView = viewStub.inflate();
//            errorView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    showLoading();
//
//                }
//            });
        }else {
            if (errorView.getVisibility() != View.VISIBLE) {
                errorView.setVisibility(View.VISIBLE);
            }
        }

        if (viewBinding.getRoot().getVisibility()!= View.GONE){
            viewBinding.getRoot().setVisibility(View.GONE);
        }
    }


    protected void showEmpty(CharSequence text, @DrawableRes int image) {
        showEmpty();
        ImageView iv = errorView.findViewById(R.id.iv_empty);
        iv.setImageResource(image);
        TextView tv = errorView.findViewById(R.id.tv_empty);
        tv.setText(text);
    }

    protected void showEmpty(CharSequence text) {
        showEmpty();
        ImageView iv = errorView.findViewById(R.id.iv_empty);
        iv.setVisibility(View.GONE);
        TextView tv = errorView.findViewById(R.id.tv_empty);
        tv.setText(text);
    }
//
//    protected void showEmpty(@DrawableRes int image) {
//
//    }
    /**
     * 失败后点击刷新
     */
//    protected void onErrorRefresh() {
//
//    }


    protected void showToast(CharSequence msg){
        Toast.makeText(mContext,msg, Toast.LENGTH_SHORT).show();
    }

    protected void showToast(@StringRes int msg){
        Toast.makeText(mContext,msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewBinding.unbind();
        mBaseBinding.unbind();
    }
}
