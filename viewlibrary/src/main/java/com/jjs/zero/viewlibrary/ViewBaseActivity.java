package com.jjs.zero.viewlibrary;

import android.content.Intent;
import android.view.View;

import com.jjs.zero.baseviewlibrary.BaseActivity;
import com.jjs.zero.baseviewlibrary.OnItemClickListener;
import com.jjs.zero.viewlibrary.databinding.ActivityViewBaseBinding;

import java.util.ArrayList;
import java.util.List;

public class ViewBaseActivity extends BaseActivity<ActivityViewBaseBinding> {

    private AdapterViewBase adapterViewBase;
    private List<ViewBaseBean> data;
    @Override
    public int layoutResId() {
        return R.layout.activity_view_base;
    }

    @Override
    protected void initData() {
        setTitle("view的使用");
        data = new ArrayList<>();

        ViewBaseBean baseBean = new ViewBaseBean("view的使用demo","描述",ViewActivity.class);
        data.add(baseBean);
        ViewBaseBean baseBean1 = new ViewBaseBean("计时器","描述",View2Activity.class);
        data.add(baseBean1);

        adapterViewBase = new AdapterViewBase(data);
        viewBinding.setAdapter(adapterViewBase);
        adapterViewBase.addOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClickListener(View view, int position) {
                startActivity(new Intent(mContext,data.get(position).getIntentClass()));
            }
        });

    }
}