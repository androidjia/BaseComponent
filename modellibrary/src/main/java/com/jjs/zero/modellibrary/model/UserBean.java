package com.jjs.zero.modellibrary.model;

/**
 * @Author: jiajunshuai
 * @CreateTime: 2020/11/17
 * @Details: <功能描述>
 */
public class UserBean {

    private String id;
    private String mUserName;
    private String desc;
    private String token;

    public UserBean(String id,String token,String mUserName, String desc) {
        this.id = id;
        this.token = token;
        this.mUserName = mUserName;
        this.desc = desc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
