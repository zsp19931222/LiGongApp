package com.example.app4.presenter;

import android.app.Activity;
import android.content.Context;

import com.example.app3.tool.HintTool;
import com.example.app4.api.GetAppUrl;
import com.example.app4.api.OkHttpUtil;
import com.example.app4.tool.Tool;
import com.example.app4.util.DeviceUtils;
import com.example.app4.util.MapUtil;
import com.example.app4.util.StringUtil;
import com.example.smartclass.eventbus.MessageEvent;
import com.example.smartclass.util.TagUtil;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.Map;

import 云华.智慧校园.工具.RSAApi;

/**
 * Created by Administrator on 2018/6/4 0004.
 */

public class BindingPhoneOtherPresenter {
    private Context context;
    private Map<String, String> parameterMap;


    public BindingPhoneOtherPresenter(Context context) {
        this.context = context;
        parameterMap = MapUtil.getMap(parameterMap);
    }

    /**
     * 获取验证码
     * */
    public void getVerificationCode(String phone, String login_id) {
        parameterMap.clear();
        try {
            JSONObject mob = new JSONObject();
            mob.put("mob", StringUtil.takeOutSpacing(phone));
            mob.put("random", Tool.stochasticString());
            mob.put("login_id", StringUtil.takeOutSpacing(login_id));

            String b = URLEncoder.encode(RSAApi.getEncryptSecurity(mob.toString()), "utf-8");
            parameterMap.put("b", b);
            OkHttpUtil.OkHttpPost(GetAppUrl.UIA.getVerificationCodeOther.getUrl(), parameterMap, TagUtil.GetVerificationCodeTag,(Activity) context);
        } catch (Exception e) {
            EventBus.getDefault().post(new MessageEvent(HintTool.REQUESTFAIL, HintTool.REQUESTFAIL));
            e.printStackTrace();
        }
    }

    /**
     * 验证验证码
     * */
    public void verifyVerificationCode(String mob,String yzm,String login_id){
        parameterMap.clear();
        parameterMap.put("mob", StringUtil.takeOutSpacing(mob));
        parameterMap.put("yzm", StringUtil.takeOutSpacing(yzm));
        parameterMap.put("login_id", StringUtil.takeOutSpacing(login_id));
        OkHttpUtil.OkHttpPost(GetAppUrl.UIA.verifyVerificationCodeOther.getUrl(), parameterMap, TagUtil.VerifyVerificationCodeTag,(Activity) context);
    }
}
