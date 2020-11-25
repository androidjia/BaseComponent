package com.jjs.zero.basecomponent;

import android.app.Application;

import com.jjs.zero.modellibrary.manager.LocalDataManager;
import com.scwang.smart.refresh.footer.BallPulseFooter;
import com.scwang.smart.refresh.header.MaterialHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.constant.SpinnerStyle;


/**
 * @Author: jiajunshuai
 * @CreateTime: 2020/10/10
 * @Details: <功能描述>
 */
public class MyApplication extends Application {

    static {
        SmartRefreshLayout.setDefaultRefreshHeaderCreator((context,layout) -> { return new MaterialHeader(context);});
        SmartRefreshLayout.setDefaultRefreshFooterCreator((context,layout) -> { return new BallPulseFooter(context).setSpinnerStyle(SpinnerStyle.FixedBehind);});
    }
    @Override
    public void onCreate() {
        super.onCreate();

        String ur = BuildConfig.domain;
        LocalDataManager.init(this);

    }
}
