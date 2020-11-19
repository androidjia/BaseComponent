package com.jjs.zero.baseviewlibrary.commonmodel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;


/**
 * @Author: jiajunshuai
 * @CreateTime: 2020/11/18
 * @Details: <ViewModel初始化有参数使用>
 *     无参数使用 new ViewModelProvider.AndroidViewModelFactory(getApplication())
 */
public class CommonViewModelFactory implements ViewModelProvider.Factory {

    private ViewModel viewModel;
    public CommonViewModelFactory(ViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @androidx.annotation.NonNull
    @Override
    public <T extends ViewModel> T create(@androidx.annotation.NonNull Class<T> modelClass) {
        return (T)viewModel;
    }
}

