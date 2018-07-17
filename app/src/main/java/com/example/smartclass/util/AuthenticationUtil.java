package com.example.smartclass.util;

import yh.app.tool.SqliteHelper;

/**
 * Created by Administrator on 2018/1/18 0018.
 * <p>
 * 身份认证（学生、老师）
 */

public class AuthenticationUtil {
    public static final String Teacher = "教师";
    public static final String Student = "学生";

    public static String getIdentity() {
        try {
            if ("2".equals(new SqliteHelper().rawQuery("select * from usertype").get(0).get("usertype"))) {//代表教师
                return Teacher;
            } else if ("1".equals(new SqliteHelper().rawQuery("select * from usertype").get(0).get("usertype"))) {//代表学生
                return Student;
            }
        }catch (Exception ignored){

        }

        return Teacher;
    }
}
