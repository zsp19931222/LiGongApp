package com.example.app4.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.example.app3.tool.HintTool;
import com.example.app4.activity.BindingPhoneOtherActivity;
import com.example.app4.api.GetAppUrl;
import com.example.app4.api.OkHttpUtil;
import com.example.app4.entity.OtherLoginEntity;
import com.example.app4.tool.GetSchoolNumTool;
import com.example.app4.util.DefaultUtil;
import com.example.app4.util.MapUtil;
import com.example.app4.util.StringUtil;
import com.example.jpushdemo.ExampleApplication;
import com.example.smartclass.eventbus.MessageEvent;
import com.example.smartclass.util.TagUtil;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import yh.app.tool.SqliteHelper;
import yh.app.utils.GsonImpl;
import 云华.智慧校园.工具.RSAApi;

/**
 * Created by Administrator on 2018/6/1 0001.
 * 其他登录方式处理
 */

public class BindingOtherPresenter {
    private Map<String, String> parameterMap;
    private Context context;

    public BindingOtherPresenter(Context context) {
        this.context = context;
        parameterMap = MapUtil.getMap(parameterMap);
    }

    /**
     * 登录
     */
    public void login(String userid, String password) {
        parameterMap.clear();
        try {
            JSONObject mob = new JSONObject();
            mob.put("userid", StringUtil.takeOutSpacing(userid));
            mob.put("password", StringUtil.takeOutSpacing(password));
            mob.put("xxbh", GetSchoolNumTool.getSchoolNum());
            mob.put("type", "1");
            String b = URLEncoder.encode(RSAApi.getEncryptSecurity(mob.toString()), "utf-8");
            parameterMap.put("b", b);
            OkHttpUtil.OkHttpPost(GetAppUrl.UIA.verifyIdentification.getUrl(), parameterMap, TagUtil.otherRegisterTag, (Activity) context);
        } catch (Exception e) {
            EventBus.getDefault().post(new MessageEvent(HintTool.REQUESTFAIL, HintTool.REQUESTFAIL));
            e.printStackTrace();
        }
    }

    /**
     * 跳转
     */
    public void intent2Where(String result) {
        OtherLoginEntity loginEntity = GsonImpl.get().toObject(result, OtherLoginEntity.class);
        Intent intent;
        if (loginEntity.getContent().getState().equals("0")) {//未绑定
            LoginUtil.saveSchoolName_Phone_PassWord(DefaultUtil.getDefaultSchool(), "", loginEntity.getContent().getPassword(), GetSchoolNumTool.getSchoolNum());
            intent = new Intent(context, BindingPhoneOtherActivity.class);
            intent.putExtra("login_id", loginEntity.getContent().getLogin_id());
            context.startActivity(intent);
        } else {//已绑定
            LoginUtil.saveSchoolName_Phone_PassWord(DefaultUtil.getDefaultSchool(), loginEntity.getContent().getMob(), loginEntity.getContent().getPassword(), GetSchoolNumTool.getSchoolNum());
            LoginUtil.getUserMessage((Activity) context,new HashMap<String, String>());
        }
    }

}
