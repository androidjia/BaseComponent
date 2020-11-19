package com.jjs.zero.basecomponent.fragment;

import android.util.Log;

import com.jjs.zero.basecomponent.R;
import com.jjs.zero.basecomponent.databinding.FragmentServiceBinding;
import com.jjs.zero.baseviewlibrary.BaseFragment;

/**
 * @Author: jiajunshuai
 * @CreateTime: 2020/11/18
 * @Details: <功能描述>
 */
public class FragmentService extends BaseFragment<FragmentServiceBinding> {
    @Override
    protected int layoutResId() {
        return R.layout.fragment_service;
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("zero======","onstart:service");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("zero======","onResume:service");
    }
}
