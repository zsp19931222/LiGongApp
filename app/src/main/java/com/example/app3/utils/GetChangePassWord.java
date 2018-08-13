package com.example.app3.utils;

import com.example.app4.util.DefaultUtil;

/**
 * Created by Administrator on 2018/5/26 0026.
 * 获取修改密码网址
 */

public class GetChangePassWord {
    /**
     * 获取忘记密码地址
     */
    public static String getForgetPasswordUrl() {
        String url;
        switch (DefaultUtil.getDefaultSchool()) {
            case "重庆工业职业技术学院":
                url = "http://authserver.cqipc.edu.cn/authserver/getBackPasswordMainPage.do";
                break;
            default:
                url = "";
                break;
        }
        return url;
    }

    /**
     * 获取修改密码地址
     */
    public static String getChangePasswordUrl() {
        String url;
        switch (DefaultUtil.getDefaultSchool()) {
            case "重庆工业职业技术学院":
                url = "http://authserver.cqipc.edu.cn/authserver/login?";
                break;
            default:
                url = "";
                break;
        }
        return url;
    }
}
