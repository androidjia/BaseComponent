package com.jjs.zero.httplibrary.httpService;

import com.jjs.zero.httplibrary.error.CommonException;
import com.jjs.zero.httplibrary.error.ErrorInfo;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * @Author: jiajunshuai
 * @CreateTime: 2020/11/24
 * @Details: <功能描述>
 */
public class ResultMapper<T> implements Function<BaseResult<T>, Observable<T>> {
    @Override
    public Observable<T> apply(@NonNull BaseResult<T> tBaseResult) throws Exception {
        if (!tBaseResult.isSuccess()) {
            throw  new CommonException(ErrorInfo.Bz.getErrorMessage(tBaseResult.getErrorCode(),tBaseResult.getErrorMsg()));
        }
        if (tBaseResult == null) return Observable.empty();
        return Observable.just(tBaseResult.getResult());
    }
}
