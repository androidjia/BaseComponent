package com.jjs.zero.basecomponent.utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Size;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

/**
 * @Author: jiajunshuai
 * @CreateTime: 2020/11/9
 * @Details: <功能描述>
 */
public class PermissionRequestUtils{

    private static volatile PermissionRequestUtils instance;
    private PermissionRequestUtils() {
    }

    public interface OnPermissionResultListener{
        void OnSuccessListener();
        void OnFailListener();
    }

    private OnPermissionResultListener onPermissionResultListener;

    public static PermissionRequestUtils getInstance() {
        if (instance == null) {
            synchronized (PermissionRequestUtils.class){
                if (instance == null) {
                    instance = new PermissionRequestUtils();
                }
            }
        }
        return instance;
    }

    private static final int REQUEST_CODE = 0X11;


    public Boolean checkPermission(@NonNull Activity activity,@Size(min = 1) @NonNull String... strings) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return false;
        for (String str:strings) {
            if (ContextCompat.checkSelfPermission(activity,str) != PackageManager.PERMISSION_GRANTED) {
                return true;
            }
        }
        return false;
    }

    public void requestPermission(@NonNull Activity activity, OnPermissionResultListener onPermissionResultListener, @Size(min = 1) @NonNull String... strings){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return;
        activity.requestPermissions(strings,REQUEST_CODE);
        this.onPermissionResultListener = onPermissionResultListener;
    }
    public void requestPermission(@NonNull Fragment activity, OnPermissionResultListener onPermissionResultListener, @Size(min = 1) @NonNull String... strings){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return;
        activity.requestPermissions(strings,REQUEST_CODE);
        this.onPermissionResultListener = onPermissionResultListener;
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //条件符合说明获取运行时权限成功
        if (requestCode != REQUEST_CODE) return;
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            onPermissionResultListener.OnSuccessListener();
        } else {
            onPermissionResultListener.OnFailListener();
        }
        onDestroy();
    }





    public void onDestroy(){
        if (instance != null) instance = null;
    }
}
