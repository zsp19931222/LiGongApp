package com.example.app4.util;

/**
 * Created by Administrator on 2018/4/14 0014.
 */

public class StringUtil {

    /**
     * 去掉字符串空格
     */
    public static String takeOutSpacing(String s) {
        try {
            s = s.replace(" ", "");
        } catch (Exception e) {
            // TODO: 2018/5/2 0002  
        }
        return s;
    }

}
