package com.jjs.zero.viewlibrary;

/**
 * @Author: jiajunshuai
 * @CreateTime: 2020/11/13
 * @Details: <功能描述>
 */
public class ViewBaseBean {
    private String title;
    private String detalis;
    private Class<?> intentClass;

    public ViewBaseBean() {
    }

    public ViewBaseBean(String title, String detalis, Class<?> intentClass) {
        this.title = title;
        this.detalis = detalis;
        this.intentClass = intentClass;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetalis() {
        return detalis;
    }

    public void setDetalis(String detalis) {
        this.detalis = detalis;
    }

    public Class<?> getIntentClass() {
        return intentClass;
    }

    public void setIntentClass(Class<?> intentClass) {
        this.intentClass = intentClass;
    }
}
