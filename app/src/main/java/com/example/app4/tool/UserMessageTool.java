package com.example.app4.tool;

import android.util.Log;

import java.net.URLEncoder;

import yh.app.tool.SqliteHelper;
import 云华.智慧校园.工具.RSAApi;
import 云华.智慧校园.工具.RSAHelper;

/**
 * Created by Administrator on 2018/5/5 0005.
 * 获取用户信息
 */

public class UserMessageTool {

    /**
     * 获取用户密码(加密)
     * */
    public static String getPassWord() {
        String s;
        try {
            s = URLEncoder.encode(RSAApi.getEncryptSecurity(new RSAHelper().decode(new SqliteHelper().rawQuery("select * from password4 ").get(0).get("password"))), "utf-8");
            Log.d("zsp", "getPassWord: " + new RSAHelper().decode(new SqliteHelper().rawQuery("select * from password4 ").get(0).get("password")));
        } catch (Exception e) {
            s = "";
        }
        return s;
    }
    /**
     * 获取用户密码(未加密)
     * */
    public static String getPassWordUnencrypted() {
        String s;
        try {
            s = new RSAHelper().decode(new SqliteHelper().rawQuery("select * from password4 ").get(0).get("password"));
            Log.d("zsp", "getPassWord: " + new RSAHelper().decode(new SqliteHelper().rawQuery("select * from password4 ").get(0).get("password")));
        } catch (Exception e) {
            s = "";
        }
        return s;
    }



    /**
     * 获取用户身份
     * */
    public static String getPhone() {
        String s;
        try {
            s = new SqliteHelper().rawQuery("select * from password4 ").get(0).get("phone");
            Log.d("zsp", "phone: " + s);
        } catch (Exception e) {
            s = "";
        }
        return s;
    }

    /**
     * 获取用户身份
     * */
    public static String getType(){
        String s;
        try {
            s = new SqliteHelper().rawQuery("select * from usertype ").get(0).get("usertype");
            Log.d("zsp", "usertype: " + s);
        } catch (Exception e) {
            s = "";
        }
        return s;
    }
    /**
     * 获取用户 userID
     * */
    public static String getUserId(){
        String s;
        try {
            s = new SqliteHelper().rawQuery("select * from usertype ").get(0).get("userid");
            Log.d("zsp", "userid: " + s);
        } catch (Exception e) {
            s = "";
        }
        return s;
    }

    /**
     * 获取学校编号
     * */
    public static String getXXBH(){
        String s;
        try {
            s = new SqliteHelper().rawQuery("select * from password4 ").get(0).get("schoolId");
            Log.d("zsp", "userid: " + s);
        } catch (Exception e) {
            s = GetSchoolNumTool.getSchoolNum();

        }
        return s;
    }
}
