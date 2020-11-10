package com.jjs.zero.basecomponent.utils;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.ColorInt;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @Author: jiajunshuai
 * @CreateTime: 2020/11/10
 * @Details: <状态栏>
 */
public class StatusBarUtils {

    /**
     * 设置是否灰色状态栏图标和文字
     * @param activity
     * @param isDark
     */
    public static void setStatusBarColorDark(Activity activity,boolean isDark){
        /**
         * View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN 是从API 16开始启用，
         * 实现效果：视图延伸至状态栏区域，状态栏悬浮于视图之上
         * View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR 是从 API 23开始启用，
         * 实现效果：设置状态栏图标和状态栏文字颜色为深色，为适应状态栏背景为浅色调，该Flag只有在使用了FLAG_DRWS_SYSTEM_BAR_BACKGROUNDS，
         * 并且没有使用FLAG_TRANSLUCENT_STATUS时才有效，即只有在透明状态栏时才有效。
         */

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (isDark) {
                //实现状态栏图标和文字颜色为暗色
                activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            }
            //小米和魅族适配状态栏颜色
            miuiSetStatusBarLightMode(activity.getWindow(),isDark);
            flymeSetStatusBarLightMode(activity.getWindow(),isDark);
        } else {
            activity.getWindow().getDecorView().findViewById(android.R.id.content).setPadding(0, 0, 0, statusBarHight(activity));
        }
    }

    /**
     * 小米手机
     * @param window
     * @param dark
     */
    private static void miuiSetStatusBarLightMode(Window window, boolean dark) {
        if (window != null) {
            Class clazz = window.getClass();
            try {
                int darkModeFlag = 0;
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if (dark) {
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag);//状态栏透明且黑色字体
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
                }
            } catch (Exception e) {

            }
        }
    }

    /**
     * 魅族手机
     * @param window
     * @param dark
     * @return
     */
    public static void flymeSetStatusBarLightMode(Window window, boolean dark) {
        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
            } catch (Exception e) {

            }
        }
    }



    public static void setStatusBarColor(Activity activity,@ColorInt int color) {
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);//需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        activity.getWindow().setStatusBarColor(color);
        // 去掉系统状态栏下的windowContentOverlay
//        View v = activity.getWindow().findViewById(android.R.id.content);
//        if (v != null) {
//            v.setForeground(null);
//        }
    }

    /**
     * 获取状态栏高度
     * @return
     */
    public static int statusBarHight(Activity activity) {
        Resources resources = activity.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }


}
