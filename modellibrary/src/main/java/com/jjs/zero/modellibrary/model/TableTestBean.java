package com.jjs.zero.modellibrary.model;

import java.util.Date;

/**
 * @Author: jiajunshuai
 * @CreateTime: 2020/11/27
 * @Details: <功能描述>
 */
public class TableTestBean {
    private int uid;
    private String userName;
    private String userId;
    private Integer age;
    private Date createTime;

    public TableTestBean(String userName, String userId, Integer age, Date createTime) {
        this.userName = userName;
        this.userId = userId;
        this.age = age;
        this.createTime = createTime;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
