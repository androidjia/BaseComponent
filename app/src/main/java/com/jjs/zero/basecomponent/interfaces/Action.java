package com.jjs.zero.basecomponent.interfaces;

/**
 * @Author: jiajunshuai
 * @CreateTime: 2020/12/18
 * @Details: <功能描述>
 */
public interface Action<T,D> {
    D excute(T var);
}
