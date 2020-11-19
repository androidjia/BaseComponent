package com.jjs.zero.basecomponent.model;

import androidx.lifecycle.LiveData;

/**
 * @Author: jiajunshuai
 * @CreateTime: 2020/11/19
 * @Details: <使用liveData>
 */
public class DemoLiveData extends LiveData<DemoLiveData> {

    private String tag;
    private String des;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
        postValue(this);
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
        setValue(this);//只能在主线程中运行
    }
}
