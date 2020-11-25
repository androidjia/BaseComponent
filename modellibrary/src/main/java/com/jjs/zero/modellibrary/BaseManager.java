package com.jjs.zero.modellibrary;

import android.content.Context;

import com.jjs.zero.datalibrary.DataBases;
import com.jjs.zero.httplibrary.httpService.BaseHttpManager;
import com.jjs.zero.modellibrary.manager.LocalDataManager;

/**
 * @Author: jiajunshuai
 * @CreateTime: 2020/11/17
 * @Details: <功能描述>
 */
public class BaseManager {

    protected Context mContext;
    protected DataBases mDataBases;
    protected BaseHttpManager httpManager;
    public BaseManager(Context mContext) {
        this.mContext = mContext.getApplicationContext();
        mDataBases = DataBases.getInstance(this.mContext);
        httpManager = new BaseHttpManager(mContext, LocalDataManager.token,LocalDataManager.userId);
    }

}
