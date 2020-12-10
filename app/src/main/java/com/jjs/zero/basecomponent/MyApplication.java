package com.jjs.zero.basecomponent;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.jjs.zero.modellibrary.manager.LocalDataManager;
import com.scwang.smart.refresh.footer.BallPulseFooter;
import com.scwang.smart.refresh.header.MaterialHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.constant.SpinnerStyle;
import com.tencent.bugly.crashreport.CrashReport;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


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

        initBugly(getApplicationContext());
    }


    /**
     * 初始化Bugly
     * @param context
     */
    private void initBugly(Context context){
        // 获取当前包名
        String packageName = getApplicationContext().getPackageName();
        // 获取当前进程名
        String processName = getProcessName(android.os.Process.myPid());
        // 设置是否为上报进程
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
        CrashReport.initCrashReport(getApplicationContext(), "e7c1c61602", true,strategy);//release版本改为false
//        manifest中配置后可以使用下面的方法
//        CrashReport.initCrashReport(getApplicationContext(),strategy);
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }
}
