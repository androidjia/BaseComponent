package com.jjs.zero.modellibrary;

import com.jjs.zero.datalibrary.entity.TabTest;
import com.jjs.zero.datalibrary.entity.User;
import com.jjs.zero.modellibrary.model.TableTestBean;
import com.jjs.zero.modellibrary.model.UserBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: jiajunshuai
 * @CreateTime: 2020/11/17
 * @Details: <功能描述>
 */
public class Convert {

    public static List<UserBean> conVert(List<User> userList) {
        List<UserBean> userBeans = new ArrayList<>();
        for (User user:userList) {
            UserBean b = new UserBean(user.getId(),user.getToken(),user.getUserName(),user.getDesc());
            userBeans.add(b);
        }
        return userBeans;
    }

    public static User[] conVert(UserBean... userBeans) {
        User[] arrayList = new User[userBeans.length];
        for (int i = 0; i < userBeans.length; i++) {
            User user = new User(userBeans[i].getId(),userBeans[i].getUserName());
            user.setDesc(userBeans[i].getDesc());
            user.setToken(userBeans[i].getToken());
            arrayList[i] = user;
        }
        return arrayList;
    }


    public static TabTest[] conVert(TableTestBean... tableTestBeans) {
        TabTest[] arrayList = new TabTest[tableTestBeans.length];
        for (int i = 0; i < tableTestBeans.length; i++) {
            TabTest tabTest = new TabTest(tableTestBeans[i].getUserName(),tableTestBeans[i].getUserId(),tableTestBeans[i].getAge(),tableTestBeans[i].getCreateTime());
            arrayList[i] = tabTest;
        }
        return arrayList;
    }


}
