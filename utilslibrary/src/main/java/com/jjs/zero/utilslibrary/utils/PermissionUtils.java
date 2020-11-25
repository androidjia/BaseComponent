package com.jjs.zero.utilslibrary.utils;

import android.Manifest;
import android.os.Build;

/**
 * @Author: jiajunshuai
 * @CreateTime: 2020/11/6
 * @Details: <功能描述>
 */
public class PermissionUtils {

    public static String[] ABS_CALENDAR;
    public static String[] ABS_CAMERA;
    public static String[] ABS_CONTACTS;
    public static String[] ABS_LOCATION;
    public static String[] ABS_MICROPHONE;
    public static String[] ABS_PHONE;
    public static String[] ABS_SENSORS;
    public static String[] ABS_SMS;
    public static String[] ABS_STORAGE;

    static {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            ABS_CALENDAR = new String[] {};
            ABS_CAMERA = new String[] {};
            ABS_CONTACTS = new String[] {};
            ABS_LOCATION = new String[] {};
            ABS_MICROPHONE = new String[] {};
            ABS_PHONE = new String[] {};
            ABS_SENSORS = new String[] {};
            ABS_SMS = new String[] {};
            ABS_STORAGE = new String[] {};
        } else {
            //日历权限
            ABS_CALENDAR = new String[] {
                    Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR
            };
            //摄像头权限
            ABS_CAMERA = new String[] {
                    Manifest.permission.CAMERA
            };
            //读取手机联系账户
            ABS_CONTACTS = new String[] {
                    Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS,
                    Manifest.permission.GET_ACCOUNTS
            };
            //定位权限

            if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.Q) {
                ABS_LOCATION = new String[] {
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
                        ,Manifest.permission.ACCESS_BACKGROUND_LOCATION
                };
            } else {
                ABS_LOCATION = new String[] {
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
                };
            }

            //麦克风权限
            ABS_MICROPHONE = new String[] {
                    Manifest.permission.RECORD_AUDIO
            };
            //Phone权限
            ABS_PHONE = new String[] {
                    Manifest.permission.READ_PHONE_STATE, Manifest.permission.CALL_PHONE,
                    Manifest.permission.READ_CALL_LOG, Manifest.permission.WRITE_CALL_LOG,
                    Manifest.permission.USE_SIP, Manifest.permission.PROCESS_OUTGOING_CALLS
            };
            //传感器
            ABS_SENSORS = new String[] {
                    Manifest.permission.BODY_SENSORS
            };
            //短信
            ABS_SMS = new String[] {
                    Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS,
                    Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_WAP_PUSH,
                    Manifest.permission.RECEIVE_MMS
            };
            //读写权限
            ABS_STORAGE = new String[] {
                    Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
            };
        }
    }

}
