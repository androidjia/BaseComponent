package com.jjs.zero.basecomponent.utils;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;

import androidx.core.content.FileProvider;

import java.io.File;

import static android.content.Context.DOWNLOAD_SERVICE;

/**
 * @Author: jiajunshuai
 * @CreateTime: 2020/10/23
 * @Details: <apk下载安装>
 *   Android 7.0 需要fileProvider
 */
public class DownloadManagerUtil {

    public static final int REQUEST_CODE_UNKNOWN_APP = 0x11;

    private Activity mActivity;
    private DownloadManager mDownloadManager;
    private long mDownloadReference;
    private static DownloadManagerUtil downloadManagerUtil;
    public static DownloadManagerUtil getInstance(Activity mActivity) {

        if (downloadManagerUtil == null) {
            downloadManagerUtil = new DownloadManagerUtil(mActivity);
        }
        return downloadManagerUtil;
    }

    private DownloadManagerUtil(Activity activity) {
        mActivity = activity;
        mDownloadManager = (DownloadManager)mActivity.getSystemService(DOWNLOAD_SERVICE);
    }


    public void startDownload(String url, String version) {
        startDownload(url,"App-" + version + ".apk",AppUtils.getAppName(mActivity),"");
    }

    public void startDownload(String url, String destination, String displayName, String description) {
        Uri resource = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(resource);
        //下载的本地路径，表示设置下载地址为SD卡的Download文件夹，文件名为mobileqq_android.apk。
//        request.setDestinationInExternalPublicDir("Download", "ecgpatch-android.apk");
        request.setDestinationInExternalFilesDir(mActivity.getApplicationContext(), Environment.DIRECTORY_DOWNLOADS, destination);
        //start 一些非必要的设置
        request.setMimeType("application/vnd.android.package-archive");
        request.setDescription(description);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setVisibleInDownloadsUi(true);
        request.setTitle(displayName);
        //end 一些非必要的设置

        mDownloadReference = mDownloadManager.enqueue(request);
        mActivity.registerReceiver(this.mDownloadReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    private void installApk(String apkPath) {
        //andorid 8.0要判断权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            boolean hasInstallPermission = mActivity.getPackageManager().canRequestPackageInstalls();

            if (hasInstallPermission) {
                promptInstall(apkPath);
            } else {
                //跳转至“安装未知应用”权限界面，引导用户开启权限
                Uri selfPackageUri = Uri.parse("package:" + mActivity.getPackageName());
                Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, selfPackageUri);
                mActivity.startActivityForResult(intent, REQUEST_CODE_UNKNOWN_APP);
            }
        } else {
            promptInstall(apkPath);
        }
    }


    private void promptInstall(String data) {
        Intent promptInstall = new Intent(Intent.ACTION_VIEW);
        promptInstall.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri contentUri = FileProvider.getUriForFile(mActivity, mActivity.getPackageName()+".provider", new File(data));
            Log.d("TAG", "promptInstall: " + contentUri);
            promptInstall.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            promptInstall.setDataAndType(Uri.fromFile(new File(data)), "application/vnd.android.package-archive");
        }
        mActivity.startActivity(promptInstall);
//        android.os.Process.killProcess(android.os.Process.myPid());
    }

    private BroadcastReceiver mDownloadReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            mActivity.unregisterReceiver(this);

            long downloadCompletedId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0L);
            if (mDownloadReference == downloadCompletedId) {
                DownloadManager.Query query = new DownloadManager.Query();
                query.setFilterById(new long[]{mDownloadReference});
//                android.app.DownloadManager downloadManager = (android.app.DownloadManager) mActivity.getApplicationContext().getSystemService("download");
                Cursor c = mDownloadManager.query(query);
                if (c.moveToFirst()) {
                    int columnIndex = c.getColumnIndex("status");
                    if (8 == c.getInt(columnIndex)) {
                        int fileUriIdx = c.getColumnIndex("local_uri");
                        String fileUri = c.getString(fileUriIdx);
                        String fileName = null;
                        if (Build.VERSION.SDK_INT > 23) {
                            if (fileUri != null) {
                                fileName = Uri.parse(fileUri).getPath();
                            }
                        } else {
                            int fileNameIdx = c.getColumnIndex("local_filename");
                            fileName = c.getString(fileNameIdx);
                        }
                        downloadManagerUtil.installApk(fileName);
//                        downloadManagerUtil.promptInstall(fileName);
                    }
                }

            }
        }

    };

    public void onDestroy() {
        mActivity.unregisterReceiver(this.mDownloadReceiver);
        mDownloadManager = null;
        downloadManagerUtil = null;
    }
}
