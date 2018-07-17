package com.example.app4.presenter;

import android.app.Activity;
import android.content.Context;

import com.example.app3.tool.HintTool;
import com.example.app4.api.GetAppUrl;
import com.example.app4.api.OkHttpUtil;
import com.example.app4.tool.Tool;
import com.example.app4.tool.UserMessageTool;
import com.example.app4.util.DeviceUtils;
import com.example.app4.util.StringUtil;
import com.example.smartclass.eventbus.MessageEvent;
import com.example.smartclass.util.TagUtil;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import yh.app.tool.MD5;
import 云华.智慧校园.工具.RSAApi;

/**
 * Created by Administrator on 2018/7/14 0014.
 * 忘记密码流程网络请求
 */

public class ForgetPasswordPresenter {
    private Context context;
    private Map<String, String> parameter;

    public ForgetPasswordPresenter(Context context) {
        this.context = context;
        parameter = new HashMap<>();
    }

    /**
     * 获取验证码
     */
    public void forgetPasswordGetCode(String phone) {
        parameter.clear();
        try {
            JSONObject mob = new JSONObject();
            mob.put("mob", StringUtil.takeOutSpacing(phone));
            mob.put("password", StringUtil.takeOutSpacing(""));
            mob.put("random", Tool.stochasticString());
            mob.put("sn", DeviceUtils.getUniqueId(context));
            mob.put("xxbh", StringUtil.takeOutSpacing(UserMessageTool.getXXBH()));
            String b = URLEncoder.encode(RSAApi.getEncryptSecurity(mob.toString()), "utf-8");
            parameter.put("b", b);
            OkHttpUtil.OkHttpPost(GetAppUrl.UIA.forgetPasswordGetCode.getUrl(), parameter, TagUtil.forgetPasswordGetCodeTag,(Activity) context);
        } catch (Exception e) {
            EventBus.getDefault().post(new MessageEvent(HintTool.REQUESTFAIL, HintTool.REQUESTFAIL));
            e.printStackTrace();
        }
    }

    /**
     * 验证验证码
     */
    public void verificationCode(String yzm, String mob) {
        parameter.clear();
        parameter.put("yzm", StringUtil.takeOutSpacing(yzm));
        parameter.put("mob", StringUtil.takeOutSpacing(mob));
        OkHttpUtil.OkHttpPost(GetAppUrl.UIA.forgetPasswordVerificationCode.getUrl(), parameter, TagUtil.forgetPasswordVerificationCodeTag, (Activity) context);
    }

    /**
     * 设置新密码
     */
    public void setting(String np, String phone) {
        try {
            parameter.clear();
            JSONObject object = new JSONObject();
            object.put("np", MD5.MD5(StringUtil.takeOutSpacing(np)));
            object.put("mob", StringUtil.takeOutSpacing(phone));
            String b = URLEncoder.encode(RSAApi.getEncryptSecurity(object.toString()), "utf-8");
            parameter.put("b", b);
            OkHttpUtil.OkHttpPost(GetAppUrl.UIA.forgetPasswordSetting.getUrl(), parameter, TagUtil.forgetPasswordSettingTag, (Activity) context);

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
