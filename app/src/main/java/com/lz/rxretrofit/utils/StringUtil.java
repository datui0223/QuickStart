package com.lz.rxretrofit.utils;

public class StringUtil {
    public static boolean isValue(Object object){
        return object!=null&&!"".equals(object.toString());
    }
}
