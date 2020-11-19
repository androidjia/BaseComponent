package com.jjs.zero.basecomponent.model;

import android.content.Intent;

import androidx.lifecycle.MutableLiveData;

import com.jjs.zero.basecomponent.bean.DataFragmentBean;
import com.jjs.zero.basecomponent.adapter.AdapterFragmentData;
import com.jjs.zero.baseviewlibrary.BaseActivity;
import com.jjs.zero.baseviewlibrary.commonmodel.BaseViewModel;

import java.util.ArrayList;
import java.util.List;


/**
 * @Author: jiajunshuai
 * @CreateTime: 2020/11/18
 * @Details: <功能描述>
 */
public class MainViewModel extends BaseViewModel<DataFragmentBean> {

    private MutableLiveData<String> text = new MutableLiveData<>();

    private AdapterFragmentData adapter;
    private List<DataFragmentBean> data = new ArrayList<>();

    public MainViewModel(){
        super();
    }

    public MainViewModel(BaseActivity activity) {
        super(activity,activity.getClass().getName());
    }

    public void init(BaseActivity activity){
        adapter = new AdapterFragmentData(data);
        adapter.addOnItemClickListener((view,position)->{
            activity.startActivity(new Intent(activity,data.get(position).getAction()));
        });

        liveData.observe(activity,observe ->{
            data.addAll(observe);
            adapter.notifyDataSetChanged();
        });
    }


    public MutableLiveData<String> getText() {
        return text;
    }

    public void setText(String text) {
        this.text.setValue(text);
    }

    public void setLiveData(List<DataFragmentBean> list){
        liveData.setValue(list);
    }


    public AdapterFragmentData getAdapter(){
        return adapter;
    }

}
