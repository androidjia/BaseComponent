package com.jjs.zero.utilslibrary.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.jjs.zero.utilslibrary.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @Author: jiajunshuai
 * @CreateTime: 2020/10/12
 * @Details: <通知工具类>
 */
public class NotificationUtils {
    private static String CHANNEL_ID = "ch_id";

    private static NotificationUtils INSTANCE = null;
    private static NotificationChannel channel = null;
    private Context context;
    private NotificationManager notificationManager;
    @Retention(RetentionPolicy.SOURCE)
    public @interface NotificationStatus {
        int NORMAL = 0;
        int LARGE = 1;
    }


    private NotificationUtils(Context context) {
        this.context = context;
        this.notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,"channel_name", NotificationManager.IMPORTANCE_DEFAULT);
            //Android 8.0 这些功能设置在channel上 7.1以下设置在NotificationCompat.Builder
//            //设置提示音
//            channel.setSound();
//            //开启指示灯
//            channel.enableLights();
//            //开启震动
//            channel.enableVibration();
//            //设置锁屏展示
//            channel.setLockscreenVisibility();
            channel.setDescription("描述");
            //设置优先级
            channel.setImportance(NotificationManager.IMPORTANCE_HIGH);
            this.channel = channel;
            this.notificationManager.createNotificationChannel(channel);
        }
    }

    public static NotificationUtils getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (NotificationUtils.class) {
                if (INSTANCE == null) {
                    INSTANCE = new NotificationUtils(context);
                }
            }
        }
        return INSTANCE;
    }


    public void createNotification(String title, String content, Class<?> activityClass, int notifyId) {
        createNotification(title,content,NotificationStatus.NORMAL,activityClass,notifyId);
    }

    public void createNotification(String title, String content, @NotificationStatus int status,Class<?> activityClass,int notifyId) {
        PendingIntent pi = getPendingIntent(activityClass);
//        String replyLabel = "输入文本";
//        RemoteInput remoteInput = new RemoteInput.Builder("key_text_reply")
//                .setLabel(replyLabel)
//                .build();
//       NotificationCompat.Action action = new NotificationCompat.Action
//               .Builder(R.drawable.ic_zhifubao,"动作", pi)
//               .addRemoteInput(remoteInput)//添加输入的动作
//               .build();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(title)
                .setContentText(content)
//                .setOngoing(true)                                   //通知不会被清除掉，除非app killed
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pi)                               //跳转到activity 点击通知跳转
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(BitmapFactory.decodeResource(context.getResources(),R.drawable.bg_hr_top)))
                .setAutoCancel(true)                                //点击自动取消
                .setVibrate(new long[]{0,1000,1000,1000}) //设置振动， 需要添加权限  <uses-permission android:name="android.permission.VIBRATE"/>
                .setLights(Color.GREEN,1000,1000)     //设置前置LED灯进行闪烁， 第一个为颜色值  第二个为亮的时长  第三个为暗的时长
                .setDefaults(NotificationCompat.DEFAULT_ALL)        //使用默认效果， 会根据手机当前环境播放铃声， 是否振动

                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
//                .addAction(R.mipmap.ic_launcher_round,"动作",pi)
//                .addAction(action)
//                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_zhifubao))
//                .setStyle(new NotificationCompat.BigPictureStyle()
//                        .bigPicture(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_zhifubao))
//                        .bigLargeIcon(null))
                ;
        if (status == NotificationStatus.LARGE) {
            //添加更大的文本区域
            builder.setStyle(new NotificationCompat.BigTextStyle().bigText(content));
        }
//        NotificationManagerCompat compat = NotificationManagerCompat.from(context);
//        compat.notify(notifyId,builder.build());         //compat.notify 和 notificationManager.notify 等同
        notificationManager.notify(notifyId,builder.build());
//        notificationManager.cancel(1);//取消id为1的通知
    }

    /**
     * 创建线性通知进度条
     * @param builder
     * PendingIntent pendingIntent =  getPendingIntent(activityClass);
     * NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
     *                  .setContentTitle(title)
     *                     .setContentText(content)
     * //                    .setContentIntent(pendingIntent)
     *                     .setSmallIcon(R.mipmap.ic_launcher)
     *                     .setPriority(NotificationCompat.PRIORITY_LOW)
     *                     .setOnlyAlertOnce(true)                                             //通知只在第一次创建打断用户进行提升
     * //                    .setAutoCancel(true)
     *                     ;
     *                      int PROGRESS_MAX = 100;
     *             builder.setProgress(PROGRESS_MAX, progressCurrent, false); //第三个参数表示进度显示方式
     *              NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
     *             notificationManager.notify(notifyId, builder.build());
     *
     * @param notifyId
     * @param progressCurrent
     */
    public void createProgressNotification(NotificationCompat.Builder builder, int notifyId,@NonNull int progressMax, int progressCurrent) {
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
        if (progressCurrent >=100) {
            builder.setContentText("下载完成")
                    .setProgress(0,0,false)                     //移除进度条
                    .setTimeoutAfter(1000)                                                   //持续一段时间关闭通知
            ;
        } else {
            builder.setProgress(progressMax, progressCurrent, false); //第三个参数表示进度显示方式

        }
        managerCompat.notify(notifyId, builder.build());
    }

    /**
     * 获取通知builder
     * @param title
     * @param content
     * @param progressMax
     * @return
     */
    public NotificationCompat.Builder getProgressBuilder(String title, String content,@NonNull int progressMax) {
        //如果要跳转添加pendingIntent
//        PendingIntent pendingIntent =  getPendingIntent(null);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(content)
//                .setContentIntent(pendingIntent)                  //需要点击跳转添加
                .setSmallIcon(R.drawable.ic_launcher)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setOnlyAlertOnce(true)
                .setProgress(progressMax, 0, false); //第三个参数表示进度显示方式
        return builder;
    }



    public void createCustomNotification(AppCompatActivity activity,String title, String content) {
        /**自定义通知布局的可用高度取决于通知视图。通常情况下，收起后的视图布局的高度上限为 64 dp，展开后的视图布局的高度上限为 256 dp。
         *自定义通知有两种，一种是为内容区域创建自定义布局，另一种是创建完全自定义的通知布局。
         *自定义通知不支持ConstraintLayout布局
         *
         * 创建完全自定义的通知布局
         * 如果您不希望使用标准通知图标和标题装饰通知，请按照上述步骤使用 setCustomBigContentView()，但不要调用 setStyle()。
         * 要支持低于 Android 4.1（API 级别 16）的 Android 版本，您还应调用 setContent()，向其传递同一 RemoteViews 对象。
         */
        RemoteViews notiView = new RemoteViews(activity.getPackageName(),R.layout.notification_custom_normal);
        RemoteViews notiViewExpand = new RemoteViews(activity.getPackageName(),R.layout.notification_custom_expand);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
//                .setContentTitle("biaoti")
//                .setContentText("wenben")
                .setSmallIcon(R.drawable.ic_launcher)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .setCustomContentView(notiView)
                .setCustomBigContentView(notiViewExpand)
//                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setAutoCancel(true);

        //完全通知
        if (Build.VERSION.SDK_INT<= Build.VERSION_CODES.JELLY_BEAN) {
            builder.setContent(notiViewExpand);
        }

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(100, builder.build());
    }









    private PendingIntent getPendingIntent(Class<?> activityClass) {
        Intent intent = new Intent(context,activityClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return PendingIntent.getActivity(context,0,intent,0);
    }
}
