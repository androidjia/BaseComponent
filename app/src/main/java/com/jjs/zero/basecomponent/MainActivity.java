package com.jjs.zero.basecomponent;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.graphics.Color;
import android.util.Log;

import com.jjs.zero.basecomponent.databinding.ActivityMainBinding;
import com.jjs.zero.basecomponent.utils.NotificationUtils;
import com.jjs.zero.basecomponent.utils.PermissionRequestUtils;
import com.jjs.zero.basecomponent.utils.PermissionUtils;
import com.jjs.zero.basecomponent.utils.StatusBarUtils;
import com.jjs.zero.baseviewlibrary.BaseActivity;
import com.jjs.zero.datalibrary.DataManager;
import com.jjs.zero.datalibrary.entity.User;
import com.jjs.zero.servicelibrary.TestActivity;
import com.jjs.zero.viewlibrary.ViewActivity;


import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity<ActivityMainBinding> {
    private int progress = 0;

    @Override
    public int layoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
//        DataBases.getInstance(this).userDao().
//                insertUsers(new User("123456","张三2"))
//                .subscribe(new CompletableObserver() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        Log.i("zero=========","insertUsersonSubscribe");
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.i("zero=========","insertUsersonComplete");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.i("zero=========","insertUsersonError");
//                    }
//                });

        DataManager.userDao(this).getUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<User>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i("zero=========","Subscription");
                    }

                    @Override
                    public void onSuccess(List<User> users) {
                        Log.i("zero=========","onSuccess:"+users.size());
                        for (User s:users
                        ) {
                            s.setDesc("我是"+s.getUserName());
                            Log.i("zero=========","user:"+s.getId()+" "+s.getUserName() +" "+ s.getDesc());
                        }
//                        DataBases.getInstance(MainActivity.this).userDao().updateUsers((User) Arrays.asList(users));
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("zero=========","onError:");
                    }
                });

//        NotificationUtils.getInstance(this).createNotification("这是标题","jisdfjlksjfklsdjflksjdflksjfklsjfdlksdjflksjflksdjlksdfd士大夫撒旦法发达的第三方士大夫fsdfsdfsdff",
//                NotificationUtils.NotificationStatus.LARGE,MainActivity2.class,1);


//        int noId = 100;
//        NotificationCompat.Builder builder = NotificationUtils.getInstance(this).getProgressBuilder("下载标题","下载内容",100);

        viewBinding.tv.setOnClickListener(view -> {
//            NotificationUtils.getInstance(this).createNotification("这是第二个标题","gengduododsfdklasdjfsdjfklsdjfklsjf",
//                    NotificationUtils.NotificationStatus.LARGE,MainActivity2.class,2);



            //创建进度条通知
//            NotificationUtils.getInstance(this).createProgressNotification(builder,noId,100,progress);
            progress += 10;

            NotificationUtils.getInstance(this).createCustomNotification(this,"biaoti","benbds");
            Log.i("zero","点击了"+progress);
        });

        viewBinding.tvService.setOnClickListener(view -> {
            startActivity(new Intent(this, TestActivity.class));
        });

        viewBinding.tvView.setOnClickListener(view -> {
            startActivity(new Intent(this, ViewActivity.class));
        });


        if (PermissionRequestUtils.getInstance().checkPermission(this, PermissionUtils.ABS_CAMERA)) {
            PermissionRequestUtils.getInstance().requestPermission(this, new PermissionRequestUtils.OnPermissionResultListener() {
                @Override
                public void OnSuccessListener() {
                }

                @Override
                public void OnFailListener() {
                }
            },PermissionUtils.ABS_CAMERA);
        }

//          状态栏设置
//        StatusBarUtils.setStatusBarColorDark(this,true);
//        StatusBarUtils.setStatusBarColor(this, Color.BLUE);
//        setTitle(StatusBarUtils.statusBarHight(this)+"高度");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionRequestUtils.getInstance().onRequestPermissionsResult(requestCode,permissions,grantResults);
    }
}