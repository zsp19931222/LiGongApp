package com.example.app4.presenter;

import android.app.Activity;
import android.content.Context;

import com.example.app4.api.GetAppUrl;
import com.example.app4.api.OkHttpUtil;
import com.example.app4.tool.Tool;
import com.example.app4.tool.UserMessageTool;
import com.example.app4.util.DeviceUtils;
import com.example.app4.util.MapUtil;
import com.example.app4.util.StringUtil;
import com.example.smartclass.util.TagUtil;

import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import yh.app.tool.MD5;
import yh.app.tool.SqliteHelper;
import 云华.智慧校园.工具.RSAApi;

/**
 * Created by Administrator on 2018/7/14 0014.
 */

public class NewUserLoginPresenter {
    private Context context;

    public NewUserLoginPresenter(Context context) {
        this.context = context;
    }

    public void newUserVerifyVerificationCode(String yzm,String mob) {
        Map<String, String> parameter = new HashMap<>();
        parameter.put("yzm", StringUtil.takeOutSpacing(yzm));
        parameter.put("mob", StringUtil.takeOutSpacing(mob));
        OkHttpUtil.OkHttpPost(GetAppUrl.UIA.newUserVerifyVerificationCode.getUrl(), parameter, TagUtil.newUserVerifyVerificationCodeTag, (Activity) context);
    }

    /**
     *
     * */
    public void getCode(String phone) {
        try {
            Map<String, String> map = new HashMap<>();
            JSONObject mob = new JSONObject();
            mob.put("mob", StringUtil.takeOutSpacing(phone));
            mob.put("password", StringUtil.takeOutSpacing(""));
            mob.put("random", Tool.stochasticString());
            mob.put("sn", DeviceUtils.getUniqueId(context));
            mob.put("xxbh", StringUtil.takeOutSpacing(UserMessageTool.getXXBH()));
            String b = URLEncoder.encode(RSAApi.getEncryptSecurity(mob.toString()), "utf-8");
            map.put("b", b);
            OkHttpUtil.OkHttpPost(GetAppUrl.UIA.getNewUserVerificationCode.getUrl(), map, TagUtil.getNewUserVerificationCodeTag, (Activity) context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void schoolIdentification(String sfz,String userid){
        try {
            Map<String, String> map = new HashMap<>();
            JSONObject mob = new JSONObject();
            mob.put("sfz", StringUtil.takeOutSpacing(sfz));
            mob.put("userid", StringUtil.takeOutSpacing(userid));
            mob.put("random", Tool.stochasticString());
            mob.put("sn", DeviceUtils.getUniqueId(context));
            mob.put("xxbh", StringUtil.takeOutSpacing(UserMessageTool.getXXBH()));
            String b = URLEncoder.encode(RSAApi.getEncryptSecurity(mob.toString()), "utf-8");
            map.put("b", b);
            OkHttpUtil.OkHttpPost(GetAppUrl.UIA.newUserUserInfo.getUrl(), map, TagUtil.newUserUserInfoTag, (Activity) context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void newUserSetPassword(String np,String userid ,String phone){
        try {
            Map<String, String> map = new HashMap<>();
            JSONObject mob = new JSONObject();
            mob.put("mob", StringUtil.takeOutSpacing(phone));
            mob.put("userid", StringUtil.takeOutSpacing(userid));
            mob.put("np", MD5.MD5(StringUtil.takeOutSpacing(np)));
            mob.put("sn", DeviceUtils.getUniqueId(context));
            mob.put("xxbh", StringUtil.takeOutSpacing(UserMessageTool.getXXBH()));
            String b = URLEncoder.encode(RSAApi.getEncryptSecurity(mob.toString()), "utf-8");
            map.put("b", b);
            OkHttpUtil.OkHttpPost(GetAppUrl.UIA.newUserSetPassword.getUrl(), map, TagUtil.newUserSetPasswordTag, (Activity) context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
