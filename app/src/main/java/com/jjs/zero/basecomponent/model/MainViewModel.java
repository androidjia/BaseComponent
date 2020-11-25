package com.jjs.zero.basecomponent.model;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.jjs.zero.basecomponent.R;
import com.jjs.zero.basecomponent.bean.DataFragmentBean;
import com.jjs.zero.basecomponent.adapter.AdapterFragmentData;
import com.jjs.zero.baseviewlibrary.BaseActivity;
import com.jjs.zero.baseviewlibrary.commonmodel.BaseViewModel;
import com.jjs.zero.modellibrary.manager.LocalDataManager;
import com.jjs.zero.modellibrary.manager.RequestManager;
import com.jjs.zero.modellibrary.model.UserBean;
import com.jjs.zero.utilslibrary.utils.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * @Author: jiajunshuai
 * @CreateTime: 2020/11/18
 * @Details: <功能描述>
 */
public class MainViewModel extends BaseViewModel<DataFragmentBean> {

    private MutableLiveData<String> text = new MutableLiveData<>();

    private AdapterFragmentData adapter;
    private List<DataFragmentBean> data = new ArrayList<>();

    public MainViewModel(){
        super();
    }

    public MainViewModel(BaseActivity activity) {
        super(activity);
    }

    public void init(BaseActivity activity){
        adapter = new AdapterFragmentData(data);
        adapter.addOnItemClickListener((view,position)->{
            switch (position){
                case 1:
                    LocalDataManager.getInstance().insertUser(new UserBean("2","5sd4fd1sd4f545sd4f53","张三","你好a")).subscribe(new Observer() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onNext(@NonNull Object o) {
                            Log.i("zero==========","onNext");
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            Log.i("zero==========","onError");
                        }

                        @Override
                        public void onComplete() {
                            Log.i("zero==========","onComplete");
                        }
                    });
                    break;
                case 2:
                    activity.showLoading();
                    new RequestManager(activity).downloadFile("http://172.16.1.15:8088/group1/M00/0B/63/rBABD15XMp2AR_Z8AANnoeI1CwA345.pdf").subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<okhttp3.ResponseBody>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onNext(@NonNull okhttp3.ResponseBody body) {
                            activity.hideLoading();
                            long fileLength = body.contentLength();//文件大小
                            File file = FileUtils.downloadFile(activity,body.byteStream(),".pdf",fileLength);
                            if (file != null)Log.i("=====zero=====",file.getAbsolutePath());
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            activity.hideLoading();
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
                    break;
                case 3:
//                    activity.showLoading();
                    Bitmap bitmap = BitmapFactory.decodeResource(activity.getResources(), R.drawable.ic_empty);
                    FileUtils.saveImage(activity,bitmap);
//                    activity.hideLoading();
                    break;
                default:
                    activity.startActivity(new Intent(activity,data.get(position).getAction()));
                    break;
            }
        });

        liveData.observe(activity,observe ->{
            data.addAll(observe);
            adapter.notifyDataSetChanged();
        });
    }


    public MutableLiveData<String> getText() {
        return text;
    }

    public void setText(String text) {
        this.text.setValue(text);
    }

    public void setLiveData(List<DataFragmentBean> list){
        liveData.setValue(list);
    }


    public AdapterFragmentData getAdapter(){
        return adapter;
    }

}
