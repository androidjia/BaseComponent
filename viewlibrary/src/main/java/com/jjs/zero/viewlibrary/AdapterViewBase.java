package com.jjs.zero.viewlibrary;


import com.jjs.zero.baseviewlibrary.BaseRecycleAdapter;
import com.jjs.zero.viewlibrary.databinding.AdapterViewBaseItemBinding;

import java.util.List;

/**
 * @Author: jiajunshuai
 * @CreateTime: 2020/11/13
 * @Details: <功能描述>
 */
public class AdapterViewBase extends BaseRecycleAdapter<ViewBaseBean,AdapterViewBaseItemBinding> {
    protected AdapterViewBase(List data) {
        super(data);
    }

    @Override
    public int layoutResId() {
        return R.layout.adapter_view_base_item;
    }

    @Override
    public void onBindViewHolders(AdapterViewBaseItemBinding binding, int position) {
        binding.setText(data.get(position).getTitle());
    }

}
