package com.jjs.zero.basecomponent.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;

import com.jjs.zero.baseviewlibrary.BaseFragment;

import java.util.List;

/**
 * @Author: jiajunshuai
 * @CreateTime: 2020/11/18
 * @Details: <通过添加 FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
 *
 *  调用FragmentTransaction 的setMaxLifecycle
 * >
 */
public class AdapterLazFragment extends FragmentPagerAdapter {
    private List<BaseFragment> fragmentList;

    public AdapterLazFragment(@NonNull FragmentManager fragmentManager, List<BaseFragment> fragmentList) {
        super(fragmentManager,FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.fragmentList = fragmentList;
        FragmentTransaction transaction;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
