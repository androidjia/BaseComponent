package com.jjs.zero.datalibrary.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

/**
 * @Author: jiajunshuai
 * @CreateTime: 2020/11/27
 * @Details: <功能描述>
 */
@Entity(tableName = "tab_test")
public class TabTest {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int uid;

    @ColumnInfo(name = "user_name")
    private String userName;

    @ColumnInfo(name = "user_id")
    private String userId;

    private Integer age;

    @ColumnInfo(name = "create_time")
    private Date createTime;

    @Ignore
    public TabTest(int uid, String userName,int age){
        this.uid = uid;
        this.userName = userName;
        this.age = age;
    }

    public TabTest(String userName, String userId, Integer age, Date createTime) {
        this.userName = userName;
        this.userId = userId;
        this.age = age;
        this.createTime = createTime;
    }

    @NonNull
    public int getUid() {
        return uid;
    }

    public void setUid(@NonNull int uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
