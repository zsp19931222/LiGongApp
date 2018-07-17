package com.example.app4.util;

/**
 * Created by Administrator on 2018/7/17 0017.
 */

public class IsNullUtil {
    public static boolean isNotNull(String s) {
        return s != null && !s.equals("") && !s.equals("null") && !s.isEmpty();
    }
}
