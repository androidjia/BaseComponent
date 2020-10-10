package com.jjs.zero.datalibrary;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.jjs.zero.datalibrary.dao.UserDao;
import com.jjs.zero.datalibrary.entity.User;

/**
 * @Author: jiajunshuai
 * @CreateTime: 2020/10/10
 * @Details: <功能描述>
 */

@Database(entities = {User.class},version = 2, exportSchema = false)
public abstract class DataBases extends RoomDatabase {
    private static volatile DataBases INSTANCE;

    public abstract UserDao userDao();

    public static DataBases getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (Database.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),DataBases.class,"baseDemo.db")
                            .allowMainThreadQueries()//允许主线程查询
                            .addMigrations(MIGRATION_1_2)//数据库升级
                            .build();
                }
            }
        }
        return INSTANCE;
    }


    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
//            int version = database.getVersion();
            database.execSQL("ALTER TABLE `user` ADD COLUMN `desc` TEXT");
        }
    };
}
