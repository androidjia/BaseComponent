package com.jjs.zero.basecomponent.fragment;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;

import androidx.annotation.ColorInt;

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
//                viewBinding.iv1.setSelected(false);
//                viewBinding.iv1.setEnabled(false);

//                viewBinding.setIndex(2);
            }
        });

        viewBinding.iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("zero======","tv1:service");
                viewBinding.setIndex(0);
            }
        });

        viewBinding.iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewBinding.setIndex(1);
            }
        });

        viewBinding.iv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewBinding.setIndex(2);
            }
        });

//        StateListDrawable drawable = (StateListDrawable) viewBinding.iv1.getDrawable();

        SparseArray<int[]> sparseArray = new SparseArray<>();
        int [] img_1 = new int[]{R.drawable.kf1,R.drawable.kf2,R.drawable.kfbkd};
        int [] img_2 = new int[]{R.drawable.lc1,R.drawable.lc2,R.drawable.lcbkd};
        int [] img_3 = new int[]{R.drawable.kf1,R.drawable.kf2,R.drawable.kfbkd};
        sparseArray.append(0,img_1);
        sparseArray.append(1,img_2);
        sparseArray.append(2,img_1);

        viewBinding.setArray(sparseArray);
        viewBinding.setIndex(3);
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
