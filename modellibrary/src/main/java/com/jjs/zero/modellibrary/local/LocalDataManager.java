package com.jjs.zero.modellibrary.local;

import android.content.Context;
import android.util.Log;

import com.jjs.zero.datalibrary.entity.User;
import com.jjs.zero.modellibrary.BaseManager;
import com.jjs.zero.modellibrary.Convert;
import com.jjs.zero.modellibrary.model.UserBean;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @Author: jiajunshuai
 * @CreateTime: 2020/11/17
 * @Details: <功能描述>
 */
public class LocalDataManager extends BaseManager {
    public LocalDataManager(Context mContext) {
        super(mContext);
    }

    public Observable<List<UserBean>> getUser() {
        return  mDataBases.userDao().getUsers()
                .map(users -> {return Convert.conVert(users); }).toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public Observable insertUser(UserBean userBean) {
      return  mDataBases.userDao().insertUsers(Convert.conVert(userBean)).toObservable();
//      .subscribe(new CompletableObserver() {
//            @Override
//            public void onSubscribe(@NonNull Disposable d) {
//                Log.i("zero=========","insertUsersonSubscribe");
//            }
//
//            @Override
//            public void onComplete() {
//                Log.i("zero=========","insertUsersonComplete");
//            }
//
//            @Override
//            public void onError(@NonNull Throwable e) {
//                Log.i("zero=========","insertUsersonError");
//            }
//        });
    }

    public void updateUser(UserBean... userBeans) {
        mDataBases.userDao().updateUsers(Convert.conVert(userBeans));
    }





}
