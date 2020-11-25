package com.jjs.zero.modellibrary.manager;

import android.content.Context;

import androidx.annotation.NonNull;

import com.jjs.zero.httplibrary.api.RequestApi;
import com.jjs.zero.modellibrary.BaseManager;

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
}
