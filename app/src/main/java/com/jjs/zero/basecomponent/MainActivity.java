package com.jjs.zero.basecomponent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.jjs.zero.basecomponent.databinding.ActivityMainBinding;
import com.jjs.zero.basecomponent.utils.NotificationUtils;
import com.jjs.zero.datalibrary.DataManager;
import com.jjs.zero.datalibrary.entity.User;
import com.jjs.zero.servicelibrary.TestActivity;
import com.jjs.zero.viewlibrary.ViewActivity;


import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private int progress = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
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

        binding.tv.setOnClickListener(view -> {
//            NotificationUtils.getInstance(this).createNotification("这是第二个标题","gengduododsfdklasdjfsdjfklsdjfklsjf",
//                    NotificationUtils.NotificationStatus.LARGE,MainActivity2.class,2);



            //创建进度条通知
//            NotificationUtils.getInstance(this).createProgressNotification(builder,noId,100,progress);

            progress += 10;

            NotificationUtils.getInstance(this).createCustomNotification(this,"biaoti","benbds");
            Log.i("zero","点击了"+progress);
        });

        binding.tvService.setOnClickListener(view -> {
            startActivity(new Intent(this, TestActivity.class));
        });

        binding.tvView.setOnClickListener(view -> {
            startActivity(new Intent(this, ViewActivity.class));
        });

    }
}