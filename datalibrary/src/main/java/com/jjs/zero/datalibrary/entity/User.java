package com.jjs.zero.datalibrary.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.UUID;

/**
 * @Author: jiajunshuai
 * @CreateTime: 2020/10/10
 * @Details: <功能描述>
 */
@Entity(tableName = "user")
public class User {
    @NonNull  // 主键不能为空
    @PrimaryKey
    @ColumnInfo(name = "user_id")//room 列注解
    private String id;

    @ColumnInfo(name = "user_name")
    private String mUserName;

    private String token;

    private String desc;
    /**
     * 构造方法
     * 设置为 @Ignore 将其忽视
     * 这样以来，这个注解方法就不会被传入 Room 中，做相应处理
     * @param mUserName
     */
    @Ignore
    public User(String mUserName) {
        this.id = UUID.randomUUID().toString();
        this.mUserName = mUserName;
    }

    /**
     * 我们发现与上个方法不同，该方法没有标记 @Ignore 标签
     * 所以编译时该方法会被传入 Room 中相应的注解处理器，做相应处理
     * 这里的处理应该是 add 新数据
     * @param id
     * @param userName
     */
    public User(String id, String userName) {
        this.id = id;
        this.mUserName = userName;
    }


    @NonNull
    public String getId() {
        return id;
    }

    public String getUserName() {
        return mUserName;
    }

    public String getDesc() {
        return desc;
    }

//    public void setId(@NonNull String id) {
//        this.id = id;
//    }

    public void setUserName(String mUserName) {
        this.mUserName = mUserName;
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
