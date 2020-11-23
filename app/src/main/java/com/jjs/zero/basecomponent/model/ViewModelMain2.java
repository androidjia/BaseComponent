package com.jjs.zero.basecomponent.model;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.Observer;

import com.jjs.zero.basecomponent.adapter.AdapterMainActivity2;
import com.jjs.zero.baseviewlibrary.BaseActivity;
import com.jjs.zero.baseviewlibrary.OnItemClickListener;
import com.jjs.zero.baseviewlibrary.commonmodel.BaseViewModel;
import com.jjs.zero.modellibrary.local.LocalDataManager;
import com.jjs.zero.modellibrary.model.UserBean;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @Author: jiajunshuai
 * @CreateTime: 2020/11/17
 * @Details: <ViewModel 和liveData和MutableLiveData使用>
 */
public class ViewModelMain2 extends BaseViewModel<UserBean> {

    private AdapterMainActivity2 adapter;
    private List<UserBean> userList;
    private DemoLiveData demoLiveData = new DemoLiveData();

    private CompositeDisposable disposable;


    public ViewModelMain2(BaseActivity activity){
        super(activity);
        Log.i("zero","ViewModelMain2=============");
    }
    public ViewModelMain2(){
        super();
    }

    @Override
    public void init(BaseActivity activity) {
        this.activity = activity;
        init();
    }


    private void init() {
        disposable = new CompositeDisposable();
        userList = new ArrayList<>();
        adapter = new AdapterMainActivity2(userList);
        adapter.addOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClickListener(View view, int position) {
//                new LocalDataManager(mContext).updateUser(userBeans.toArray(new UserBean[userBeans.size()]));
//                activity.finish();

                demoLiveData.setTag("改变了tag");

            }
        });
        liveData.observe(activity, new Observer<List<UserBean>>() {
            @Override
            public void onChanged(List<UserBean> userBeans) {
                Log.i("zero","listLiveData更新："+userBeans.size());
                setUserList(userBeans);
            }
        });

        demoLiveData.observe(activity,change -> {
            Toast.makeText(activity,"改变了tag",Toast.LENGTH_SHORT).show();
        });

        getUsers(activity);
    }

    public DemoLiveData getDemoLiveData() {
        return demoLiveData;
    }

    public void setUserList(List<UserBean> list) {
        Log.i("zero","userListSize:"+userList.size());
        userList.addAll(list);
        Log.i("zero","userListAfterSize:"+userList.size());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh(){
        super.onRefresh();
        userList.clear();
        getUsers(activity);
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        getUsers(activity);
    }

    private void getUsers(Context mContext){

        new LocalDataManager(mContext).getUser().subscribe(new io.reactivex.Observer<List<UserBean>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                disposable.add(d);
            }

            @Override
            public void onNext(@NonNull List<UserBean> userBeans) {
                Log.i("zero","获取数据结果："+userBeans.size());
                liveData.setValue(userBeans);
//                listLiveData.postValue(userBeans);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    public AdapterMainActivity2 getAdapter(){
        return adapter;
    }

    /**
     * 由于屏幕旋转导致的Activity重建，该方法不会被调用
     * activity销毁时会自动调用onCleared()方法
     */
    @Override
    protected void onCleared() {
        super.onCleared();
        Log.i("zero","调用了onCleared");
        if (adapter != null) adapter = null;
        if (disposable != null) disposable.dispose();
    }
}
