package com.jjs.zero.datalibrary.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.jjs.zero.datalibrary.entity.TabTest;

import java.util.List;

/**
 * @Author: jiajunshuai
 * @CreateTime: 2020/11/27
 * @Details: <功能描述>
 */
@Dao
public interface TabTestDao {

    @Query("SELECT * FROM tab_test")
    List<TabTest> getTabTest();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUsers(TabTest... tabTests);
}
