package com.example.app4.util;

/**
 * Created by Administrator on 2018/3/28 0028.
 */

public class PlaceNamesUtil {
    /**
     * 取地名前两位作为显示，黑龙江和内蒙古特殊处理
     * */
    public static String dealPlaceNames(String name) {
        name = name.substring(0, 2);
        if (name.equals("黑龙")) {
            name = "黑龙江";
        } else if (name.equals("内蒙")) {
            name = "内蒙古";
        }
        return name;
    }
}
