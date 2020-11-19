package com.jjs.zero.modellibrary;

import android.content.Context;

import com.jjs.zero.datalibrary.DataBases;

/**
 * @Author: jiajunshuai
 * @CreateTime: 2020/11/17
 * @Details: <功能描述>
 */
public class BaseManager {

    protected Context mContext;
    protected DataBases mDataBases;
    //api工具类

    public BaseManager(Context mContext) {
        this.mContext = mContext.getApplicationContext();
        mDataBases = DataBases.getInstance(this.mContext);

    }

}
