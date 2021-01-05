package com.jjs.zero.basecomponent.adapter;

import com.jjs.zero.basecomponent.bean.DataFragmentBean;
import com.jjs.zero.basecomponent.R;
import com.jjs.zero.basecomponent.databinding.AdapterFragmentDataItemBinding;
import com.jjs.zero.baseviewlibrary.BaseRecycleAdapter;

import java.util.List;

/**
 * @Author: jiajunshuai
 * @CreateTime: 2020/11/17
 * @Details: <功能描述>
 */
public class AdapterFragmentData extends BaseRecycleAdapter<DataFragmentBean, AdapterFragmentDataItemBinding> {
    public AdapterFragmentData(List<DataFragmentBean> data) {
        super(data);
    }

    @Override
    public int layoutResId() {
        return R.layout.adapter_fragment_data_item;
    }

    @Override
    public void onBindViewHolders(AdapterFragmentDataItemBinding binding, int position) {
        binding.setBean(data.get(position));
    }

}
