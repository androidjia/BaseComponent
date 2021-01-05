package com.jjs.zero.datalibrary;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.jjs.zero.datalibrary.dao.TabTestDao;
import com.jjs.zero.datalibrary.dao.UserDao;
import com.jjs.zero.datalibrary.entity.TabTest;
import com.jjs.zero.datalibrary.entity.User;

/**
 * @Author: jiajunshuai
 * @CreateTime: 2020/10/10
 * @Details: <功能描述>
 */

@Database(entities = {User.class,TabTest.class},version = 5, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class DataBases extends RoomDatabase {
    private static volatile DataBases INSTANCE;

    public abstract UserDao userDao();
    public abstract TabTestDao tabTestDao();

    public static DataBases getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (Database.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),DataBases.class,"baseDemo.db")
                            .allowMainThreadQueries()//允许主线程查询
                            .addMigrations(MIGRATION_1_2)//数据库升级 创建一个tab_test表
                            .addMigrations(MIGRATION_2_3)//数据库升级 tab_test表中添加字段
                            .addMigrations(MIGRATION_3_4)//数据库升级 tab_test表中添加字段
                            .addMigrations(MIGRATION_4_5)
                            // 如果没有匹配到Migration，则直接删除所有的表，重新创建表
//                            .fallbackToDestructiveMigration()
                            // 需要配合fallbackToDestructiveMigration方法使用，指定当前`version` 是 1或2，则直接删除所有的表，重新创建表
//                            .fallbackToDestructiveMigrationFrom(1, 2)
                            // 如果没有匹配到降级Migration，则删表重建
//                            .fallbackToDestructiveMigrationOnDowngrade()
                            .build();
                }
            }
        }
        return INSTANCE;
    }


    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            Log.i("zero=======","migration1-2");
            //注意创建的表和实体类中的类型要对应
            database.execSQL(
                    "CREATE TABLE IF NOT EXISTS `tab_test` (" +
                            "'uid' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                            "'user_name' TEXT, " +
                            "'age' INTEGER)");
        }
    };

    private static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
//            int version = database.getVersion();
            Log.i("zero=======","migration2-3");
            database.execSQL("ALTER TABLE `tab_test` ADD COLUMN `user_id` TEXT");
        }
    };

    private static final Migration MIGRATION_3_4 = new Migration(3,4) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            Log.i("zero=======","migration3_4");
            //使用Converters 将实体类中的Date类型转为Long 数据库中所有的表都会调用
            // (因为sql中没有Long 会使用Integer或者text类型)
            //text:格式为 "YYYY-MM-DD HH:MM:SS.SSS" 的日期。
            //Integer:从 1970-01-01 00:00:00 UTC 算起的秒数。
            database.execSQL("ALTER TABLE `tab_test` ADD COLUMN `create_time` INTEGER");
        }
    };
    private static final Migration MIGRATION_4_5 = new Migration(4,5) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            Log.i("zero=======","migration4_5");
        }
    };

}
