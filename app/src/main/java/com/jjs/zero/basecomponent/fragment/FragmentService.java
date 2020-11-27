package com.jjs.zero.basecomponent.fragment;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.util.Log;
import android.view.View;

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
        viewBinding.tv.setPercentage(60);
        viewBinding.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("zero======","tv:service");
                viewBinding.tv1.setSelected(false);
                viewBinding.tv1.setEnabled(false);
            }
        });

        viewBinding.tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("zero======","tv1:service");
                viewBinding.tv1.setSelected(true);
            }
        });

        StateListDrawable drawable = (StateListDrawable) viewBinding.tv1.getDrawable();
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
