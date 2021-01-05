package com.jjs.zero.utilslibrary.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @Author: jiajunshuai
 * @CreateTime: 2020/11/16
 * @Details: <功能描述>
 */
public class SPUtils {
    private static volatile SPUtils instance;
    private SharedPreferences sharedPreferences;

    private SPUtils(Context context){
        sharedPreferences = context.getSharedPreferences("baseComp",Context.MODE_PRIVATE);
    }

    private SPUtils(){}

    public static void init(Context context){
        instance = new SPUtils(context);
    }

    public static SPUtils getInstance() {
        if (instance == null) {
            synchronized (SPUtils.class) {
                if (instance == null) {
                    instance = new SPUtils();
                }
            }
        }
        return instance;
    }

    public void addTime(Long time) {
        sharedPreferences.edit().putLong("time",time).apply();
    }

    public void getTime(){
        sharedPreferences.getLong("time",0L);
    }

}
