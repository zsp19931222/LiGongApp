package com.example.app4.presenter;

import android.app.Activity;
import android.content.Context;

import com.example.app3.tool.HintTool;
import com.example.app4.api.GetAppUrl;
import com.example.app4.api.OkHttpUtil;
import com.example.app4.tool.GetSchoolNumTool;
import com.example.app4.tool.UserMessageTool;
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
 * 验证数字化校园
 */

public class SchoolIdentificationPresenter {
    private Context context;
    private Map<String, String> parameterMap;

    public SchoolIdentificationPresenter(Context context) {
        this.context = context;
        parameterMap = MapUtil.getMap(parameterMap);
    }

    /**
     * 账号密码验证
     */
    public void verifyIdentification(String userid,String password) {
        parameterMap.clear();
        try {
            JSONObject mob = new JSONObject();
            mob.put("userid", StringUtil.takeOutSpacing(userid));
            mob.put("mob", UserMessageTool.getPhone());
            mob.put("password", StringUtil.takeOutSpacing(password));
            mob.put("xxbh", GetSchoolNumTool.getSchoolNum());
            String b = URLEncoder.encode(RSAApi.getEncryptSecurity(mob.toString()), "utf-8");
            parameterMap.put("b", b);
            OkHttpUtil.OkHttpPost(GetAppUrl.UIA.otherRegister.getUrl(), parameterMap, TagUtil.verifyIdentificationTag, (Activity) context);
        } catch (Exception e) {
            EventBus.getDefault().post(new MessageEvent(HintTool.REQUESTFAIL, HintTool.REQUESTFAIL));
            e.printStackTrace();
        }
    }
}
