package com.example.app4.util;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2018/5/4 0004.
 */

public class JsonObjectUtil {
    public static String deal(JSONObject object, String key) {
        String s;
        try {
            s = object.getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
            s = "";
        }
        return s;
    }
}
