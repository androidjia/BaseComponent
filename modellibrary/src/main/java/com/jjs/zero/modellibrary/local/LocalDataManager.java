package com.jjs.zero.modellibrary.local;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.jjs.zero.datalibrary.entity.User;
import com.jjs.zero.httplibrary.api.RequestApi;
import com.jjs.zero.httplibrary.dto.Order;
import com.jjs.zero.httplibrary.httpService.ResultMapper;
import com.jjs.zero.modellibrary.BaseManager;
import com.jjs.zero.modellibrary.Convert;
import com.jjs.zero.modellibrary.model.UserBean;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * @Author: jiajunshuai
 * @CreateTime: 2020/11/17
 * @Details: <功能描述>
 */
public class LocalDataManager extends BaseManager {

    public static String token;
    public static String userId;
    private static LocalDataManager localDataManager;
    private LocalDataManager(Context mContext) {
        super(mContext);
    }

    public static LocalDataManager getInstance(){
        return localDataManager;
    }


    public static void init(Context context) {
        localDataManager =new LocalDataManager(context);
        User user = localDataManager.mDataBases.userDao().getUserInfo();
        if ( user!= null) {
            userId = user.getId();
            token = user.getToken();
            localDataManager.httpManager.setUserCode(userId);
            localDataManager.httpManager.setToken(token);
        }
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


    public Observable<ResponseBody> downloadFile(@NonNull String url){
        return httpManager.getInterface(RequestApi.class).downloadFile(url);
    }

    public Observable<UserBean> getActive(Long time){
        return httpManager.getInterface(RequestApi.class).getExperiencesActive(time).flatMap(new ResultMapper<>())
                .map(order -> {
                    return new UserBean("1","sdfsdfsdsffsdfsdf","张三","你好");
                });
    }



}
