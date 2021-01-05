package com.jjs.zero.datalibrary;

import androidx.room.TypeConverter;

import java.util.Date;

/**
 * @Author: jiajunshuai
 * @CreateTime: 2020/11/27
 * @Details: <功能描述>
 */
public class Converters {
    @TypeConverter
    public static Date fromLongToDate(Long time){
        return time == null?null:new Date(time);
    }

    @TypeConverter
    public static Long fromDateToLong(Date time){
        return time == null?null:time.getTime();
    }
}
