package com.jjs.zero.datalibrary;

import android.content.Context;

import com.jjs.zero.datalibrary.dao.UserDao;

/**
 * @Author: jiajunshuai
 * @CreateTime: 2020/10/10
 * @Details: <功能描述>
 */
public class DataManager {

    public static UserDao userDao(Context context) {
        return DataBases.getInstance(context).userDao();
    }

}
