package com.jjs.zero.datalibrary.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.jjs.zero.datalibrary.entity.User;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * @Author: jiajunshuai
 * @CreateTime: 2020/10/10
 * @Details: <功能描述>
 */
@Dao
public interface UserDao {

    /**
     * RxJava的 Maybe, Single 和 Flowable 对象来执行异步查询
     * Maybe
     * 可能发生以下事情:
     * 当数据库中没有user，查询没有返回行时，Maybe调用complete。
     * 当数据库中有一个user时，Maybe将触发onSuccess并调用complete。
     * 如果在Maybe的complete调用之后user被更新，什么也不会发生。
     *
     * Single
     * 可能发生以下事情:
     * 当数据库中没有user，查询没有返回行时，Single触发onError（EmptyResultSetException.class）。
     * 当数据库中有一个user时， Single触发onSuccess。
     * 如果在Single.onComplete调用之后user被更新，什么也不会发生。
     *
     * Flowable
     * 可能发生以下事情：
     * 当数据库中没有user，查询没有返回行时，Flowable不会发射，也不会触发onNext, 或者 onError。
     * 当数据库中有一个user时，Flowable会触发onNext。
     * 每当user更新之后，Flowable将自动发射，这样你就可以根据最新的数据来更新UI。
     * 译者注：这里的getUserById返回的是Flowable<User>，如果查询没有结果的话，什么信息也收不到，
     * 这在实际开发中是很蛋疼的。但是如果我们把返回类型改成Flowable<List<User>>的话，
     * 如果查询没有结果是可以得到一个空list的
     *  在androidx中 flowable 使用有问题
     * @return
     */

    @Query("SELECT * FROM  user LIMIT 1")
    Single<List<User>> getUser();

    @Query("SELECT * FROM  user")
    Single<List<User>> getUsers();

    //根据字段查询
    @Query("SELECT * FROM User WHERE username= :name")
    Single<User> getUserByName(String name);


    /**
     *      Completable 可以看作是 RxJava 的 Runnale 接口
     *      但他只能调用 onComplete 和 onError 方法，不能进行 map、flatMap 等操作
     *      想知道结果就使用Complitable 不想就用void
     * @param users
     * @return
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertUsers(User... users);
//    void insertUsers(User... users);


    @Delete
    void deleteUsers(User... users);

    @Query("DELETE FROM user")
    void deleteAllUsers();


    @Update
    void updateUsers(User... users);


}
