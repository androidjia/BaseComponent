package com.jjs.zero.utilslibrary.utils;

import androidx.annotation.NonNull;

import java.util.Arrays;

/**
 * @Author: jiajunshuai
 * @CreateTime: 2020/12/11
 * @Details: <功能描述>
 */
public class ArrayUtils {

    /**
     * 判断数组是否为空
     * @param array
     * @param <T>
     * @return
     */
    public static <T> boolean isNull(T[] array) {
        if (array != null && array.length>0) return false;
        return true;
    }

    /**
     * 合并数组
     * @param first
     * @param second
     * @param <T>
     * @return
     */
    public static <T> T[] concat(@NonNull T[] first,@NonNull T[] second) {
        T[] result = Arrays.copyOf(first,first.length+second.length);
        System.arraycopy(second,0,result,first.length,second.length);

//        String[] result = new String[first.length+second.length];
//        System.arraycopy(first,0,result,0,first.length);
//        System.arraycopy(second,0,result,first.length,second.length);
        return result;
    }


    public static <T> T[] concat(@NonNull T[] first,@NonNull T[]... seconds) {
        int totalLength = first.length;
        for(T[] array:seconds) {
            totalLength += array.length;
        }
        T[] result = Arrays.copyOf(first,totalLength);
        int offset = first.length;
        for (T[] array:seconds) {
            System.arraycopy(array,0,result,offset,array.length);
            offset += array.length;
        }
        return result;
    }


}
