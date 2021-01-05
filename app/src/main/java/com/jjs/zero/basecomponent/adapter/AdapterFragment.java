package com.jjs.zero.basecomponent.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.jjs.zero.baseviewlibrary.BaseFragment;

import java.util.List;

/**
 * @Author: jiajunshuai
 * @CreateTime: 2020/11/18
 * @Details: <功能描述>
 */
public class AdapterFragment extends FragmentStateAdapter {
    private List<BaseFragment> fragmentList;

    public AdapterFragment(@NonNull FragmentActivity fragmentActivity,List<BaseFragment> fragmentList) {
        super(fragmentActivity);
        this.fragmentList = fragmentList;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getItemCount() {
        return fragmentList.size();
    }

}
