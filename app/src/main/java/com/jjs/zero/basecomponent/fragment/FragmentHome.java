package com.jjs.zero.basecomponent.fragment;


import android.util.Log;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;

import com.jjs.zero.basecomponent.R;
import com.jjs.zero.basecomponent.databinding.FragmentHomeBinding;
import com.jjs.zero.basecomponent.model.MainViewModel;
import com.jjs.zero.baseviewlibrary.BaseFragment;

/**
 * @Author: jiajunshuai
 * @CreateTime: 2020/11/18
 * @Details: <功能描述>
 */
public class FragmentHome extends BaseFragment<FragmentHomeBinding> {

    private MainViewModel mainViewModel;

    @Override
    protected int layoutResId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initData() {

        mainViewModel = createViewModel(MainViewModel.class);
        mainViewModel.setText("fragment1");



        mainViewModel.getText().observe(this,ob -> {
            viewBinding.tv.setText(mainViewModel.getText().getValue());
            Log.i("zero","数据改变:"+mainViewModel.getText().getValue());
        });

        viewBinding.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainViewModel.setText("数据点击了");
            }
        });
    }
}
