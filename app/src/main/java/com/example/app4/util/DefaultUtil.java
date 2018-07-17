package com.example.app4.util;

/**
 * Created by Administrator on 2018/3/28 0028.
 * <p>
 * 默认类
 */

public class DefaultUtil {
    private static String DefaultArea = "重庆";//默认定位地点
    private static String DefaultSchool = "";//默认学校

    public static String getDefaultSchool() {
        return DefaultSchool;
    }

    public static void setDefaultSchool(String defaultSchool) {
        DefaultSchool = defaultSchool;
    }

    public static String getDefaultArea() {
        return DefaultArea;
    }


    public static void setDefaultArea(String defaultArea) {
        DefaultArea = defaultArea;
    }

}
