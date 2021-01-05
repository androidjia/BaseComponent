package com.jjs.zero.httplibrary.httpService;


import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.lang.reflect.Field;

/**
 * @Author: jiajunshuai
 * @CreateTime: 2020/11/24
 * @Details: <功能描述>
 */
public class ConfigUrl {

    private String url;
    private static ConfigUrl configUrl;
    private ConfigUrl(Context context) {
//        url = (String) getBuildConfigValue(context,"domain");
        getMetaData(context);
        Log.i("zero","红旗==============url:"+url);
    }

    public static ConfigUrl getInstance(Context context){
        if (configUrl == null) {
            synchronized (ConfigUrl.class) {
                if (configUrl == null) {
                    configUrl = new ConfigUrl(context);
                }
            }
        }
        return configUrl;
    }


    /**
     * 反射获取地址
     * @param fieldName
     * @return
     */
    private Object getBuildConfigValue(Context context,String fieldName) {
        try {
            Class<?> clazz = Class.forName(context.getPackageName() + ".BuildConfig");
            Field field = clazz.getField(fieldName);
            return field.get(null);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void getMetaData(Context context){
        if (context == null) return;
        ApplicationInfo applicationInfo = null;
        try {
            applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            url = applicationInfo.metaData.getString("BASE_URL","");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
