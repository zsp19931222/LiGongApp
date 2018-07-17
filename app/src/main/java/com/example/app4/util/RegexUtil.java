package com.example.app4.util;

import java.util.regex.Pattern;

/**
 * Created by Administrator on 2018/7/16 0016.
 */

public class RegexUtil {
    /**
     * 验证密码（输入八位及八位以上的密码包含数字和字母）
     */
    public static boolean regex(String password) {
        String pattern = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[\\x21-\\x7e])([a-zA-Z0-9\\x21-\\x7e]{8,20})$";
        return Pattern.matches(pattern, password);
    }
}
