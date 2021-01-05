package com.jjs.zero.modellibrary.manager;

import android.content.Context;

import androidx.annotation.NonNull;

import com.jjs.zero.httplibrary.api.RequestApi;
import com.jjs.zero.httplibrary.httpService.ResultMapper;
import com.jjs.zero.modellibrary.BaseManager;
import com.jjs.zero.modellibrary.model.UserBean;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

/**
 * @Author: jiajunshuai
 * @CreateTime: 2020/11/24
 * @Details: <功能描述>
 */
public class RequestManager extends BaseManager {
    public RequestManager(Context mContext) {
        super(mContext);
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
