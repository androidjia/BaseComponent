package com.jjs.zero.basecomponent.fragment;

import android.util.Log;

import com.jjs.zero.basecomponent.bean.DataFragmentBean;
import com.jjs.zero.basecomponent.MainActivity2;
import com.jjs.zero.basecomponent.R;
import com.jjs.zero.basecomponent.databinding.FragmentDataBinding;
import com.jjs.zero.basecomponent.model.MainViewModel;
import com.jjs.zero.baseviewlibrary.BaseActivity;
import com.jjs.zero.baseviewlibrary.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: jiajunshuai
 * @CreateTime: 2020/11/18
 * @Details: <功能描述>
 */
public class FragmentData extends BaseFragment<FragmentDataBinding> {

    private MainViewModel mainViewModel;

    @Override
    protected int layoutResId() {
        return R.layout.fragment_data;
    }

    @Override
    protected void initData() {
        mainViewModel = createViewModel(MainViewModel.class);
        mainViewModel.init((BaseActivity) getActivity());
        viewBinding.setVm(mainViewModel);
        DataFragmentBean userBean = new DataFragmentBean("liveData和ViewModel以及DataBinding结合使用", MainActivity2.class);
        List<DataFragmentBean> list = new ArrayList<>();
        list.add(userBean);
        mainViewModel.setLiveData(list);



    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("zero======","onstart:data");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("zero======","onResume:data");
    }
}
