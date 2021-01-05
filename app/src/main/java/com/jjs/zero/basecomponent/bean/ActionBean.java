package com.jjs.zero.basecomponent.bean;

import android.view.View;

import com.jjs.zero.basecomponent.interfaces.Action;

/**
 * @Author: jiajunshuai
 * @CreateTime: 2020/12/18
 * @Details: <功能描述>
 */
public class ActionBean {
    Action<View,Object> action;

    public ActionBean(Action<View, Object> action) {
        this.action = action;
    }
}
