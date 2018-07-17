package com.example.app3.tool;

import android.util.Log;

import com.example.app4.util.StringUtil;

import org.json.JSONObject;

/**
 * Created by Administrator on 2017/9/22.
 */

public class JSONTool {
    public static final String SUCCESS = "40001";
    public static final String NoData = "30005";
    public static final String NoID = "30004";
    public static final String ReLogin = "30006";
    private static final String TAG = "JSONTool";

    public static String Code(String json) {
        String code = "";
        try {
            JSONObject jsonObject = new JSONObject(json);
            code = jsonObject.getString("code");
        } catch (Exception e) {
            Log.d(TAG, "Code: "+e);
        }

        return code;
    }

    public static String Tips(String json) {
        String tips = "";
        try {
            JSONObject jsonObject = new JSONObject(json);
            tips = jsonObject.getString("message");
        } catch (Exception e) {
            Log.d(TAG, "Code: "+e);
        }

        return tips;
    }

    /**
     * 处理返回数据不为json的方法
     */
    public static String jsonString(String s) {
        String backlogJsonStrTmp = s.replace("\\", "");
        s = backlogJsonStrTmp.substring(1, backlogJsonStrTmp.length() - 1);
        return s;
    }
}
