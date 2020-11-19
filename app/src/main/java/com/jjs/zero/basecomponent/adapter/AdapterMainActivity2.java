package com.jjs.zero.basecomponent.adapter;

import com.jjs.zero.basecomponent.R;
import com.jjs.zero.basecomponent.databinding.AdapterMain2ItemBinding;
import com.jjs.zero.baseviewlibrary.BaseRecycleAdapter;
import com.jjs.zero.modellibrary.model.UserBean;

import java.util.List;

/**
 * @Author: jiajunshuai
 * @CreateTime: 2020/11/17
 * @Details: <功能描述>
 */
public class AdapterMainActivity2 extends BaseRecycleAdapter<UserBean, AdapterMain2ItemBinding> {
    public AdapterMainActivity2(List<UserBean> data) {
        super(data);
    }

    @Override
    public int layoutResId() {
        return R.layout.adapter_main2_item;
    }

    @Override
    public void onBindViewHolders(AdapterMain2ItemBinding binding, int position) {
        binding.setUser(data.get(position));
    }
}
