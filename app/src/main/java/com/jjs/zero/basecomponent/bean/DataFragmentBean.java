package com.jjs.zero.basecomponent.bean;

/**
 * @Author: jiajunshuai
 * @CreateTime: 2020/11/19
 * @Details: <功能描述>
 */
public class DataFragmentBean {

    private String title;
    private Class<?> action;

    public DataFragmentBean(String title, Class<?> action) {
        this.title = title;
        this.action = action;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Class<?> getAction() {
        return action;
    }

    public void setAction(Class<?> action) {
        this.action = action;
    }
}
