package com.example.app4.presenter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.app3.tool.HintTool;
import com.example.app4.activity.ADActivity;
import com.example.app4.activity.MainActivity;
import com.example.app4.api.GetAppUrl;
import com.example.app4.api.OkHttpUtil;
import com.example.app4.entity.UserMessageEntity;
import com.example.app4.tool.UserMessageTool;
import com.example.app4.tool.Tool;
import com.example.app4.util.ActivityUtil;
import com.example.app4.util.DeviceUtils;
import com.example.app4.util.GetSchoolListUtil;
import com.example.app4.util.JsonObjectUtil;
import com.example.app4.util.MapUtil;
import com.example.app4.util.StringUtil;
import com.example.app4.util.SystemUtil;
import com.example.jpushdemo.ApnsStart;
import com.example.smartclass.eventbus.MessageEvent;
import com.example.smartclass.util.TagUtil;

import org.androidpn.push.Constants;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import yh.app.tool.MD5;
import yh.app.tool.SqliteHelper;
import 云华.智慧校园.工具.RSAApi;
import 云华.智慧校园.工具.RSAHelper;

/**
 * Created by Administrator on 2018/4/11 0011.
 * 登录相关处理
 */

public class LoginUtil {
    /**
     * 储存学校名称，手机号，密码等信息
     */
    private static final String TAG = "LoginUtil";

    public static void saveSchoolName_Phone_PassWord(String schoolName, String phone, String passWord, String schoolId) {
        new SqliteHelper().rawQuery("delete from password4");
        new SqliteHelper().execSQL("insert or replace into password4(password,phone,schoolname,schoolId) values(?,?,?,?)", passWord, phone, schoolName, schoolId);
        Log.d(TAG, "password: " + UserMessageTool.getPassWord());
    }

    /**
     * 储存用户基本信息并跳转到主页面
     */
    public static void saveUserMessage(UserMessageEntity userMessageEntity, Context context) throws JSONException {
        StringBuilder userinfo = new StringBuilder();
        for (int i = 0; i < userMessageEntity.getContent().getUserinfo().size(); i++) {
            userinfo.append(new RSAHelper().decode(userMessageEntity.getContent().getUserinfo().get(i)));
        }
        int usertype = userMessageEntity.getContent().getUsertype();
        JSONObject object = new JSONObject(userinfo.toString());
        String userid = object.getString("userid");
        String tsid = object.getString("tsid");

        new SqliteHelper().rawQuery("delete from userinfo4");
        String zydm;
        if (usertype == 1) {
            zydm = JsonObjectUtil.deal(object, "zymc");
        } else {
            zydm = JsonObjectUtil.deal(object, "bmmc");
        }
        new SqliteHelper().execSQL(
                "insert or replace into userinfo4(wx,lxdh,xb,rxnf,txdz,userid,zydm,xydm,sfzh,username,bjdm,sr,usertype,tsid) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                JsonObjectUtil.deal(object, "wx"),
                JsonObjectUtil.deal(object, "lxdh"),
                JsonObjectUtil.deal(object, "xb"),
                JsonObjectUtil.deal(object, "rxnf"),
                JsonObjectUtil.deal(object, "txdz"),
                userid,
                zydm,
                JsonObjectUtil.deal(object, "xymc"),
                JsonObjectUtil.deal(object, "sfzh"),
                JsonObjectUtil.deal(object, "username"),
                JsonObjectUtil.deal(object, "bjdm"),
                JsonObjectUtil.deal(object, "sr"),
                usertype + "",
                JsonObjectUtil.deal(object, "tsid")
        );

        Log.d(TAG, "tsid: " + tsid);
        new SqliteHelper().rawQuery("delete from usertype");
        new SqliteHelper().execSQL("insert or replace into usertype(userid,usertype) values(?,?)",
                userid,
                usertype
        );

        Constants.number = userid;
        Constants.jpushID = tsid;
        String password = new SqliteHelper().rawQuery("select * from password4").get(0).get("password");
        password = new RSAHelper().decode(password);
        Constants.code = MD5.MD5(password);

        new StartPresenter(context).getMyselfList();

        registerJpush(context);
    }

    /**
     * 储存用户基本信息并跳转到广告页面
     */
    public static void saveUserMessage2ADActivity(UserMessageEntity userMessageEntity, Context context, String url) throws JSONException {
        StringBuilder userinfo = new StringBuilder();
        for (int i = 0; i < userMessageEntity.getContent().getUserinfo().size(); i++) {
            userinfo.append(new RSAHelper().decode(userMessageEntity.getContent().getUserinfo().get(i)));
        }
        int usertype = userMessageEntity.getContent().getUsertype();
        JSONObject object = new JSONObject(userinfo.toString());
        String userid = object.getString("userid");
        String tsid = object.getString("tsid");

        new SqliteHelper().rawQuery("delete from userinfo4");
        String zydm;
        if (usertype == 1) {
            zydm = JsonObjectUtil.deal(object, "zymc");
        } else {
            zydm = JsonObjectUtil.deal(object, "bmmc");
        }
        new SqliteHelper().execSQL(
                "insert or replace into userinfo4(wx,lxdh,xb,rxnf,txdz,userid,zydm,xydm,sfzh,username,bjdm,sr,usertype,tsid) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                JsonObjectUtil.deal(object, "wx"),
                JsonObjectUtil.deal(object, "lxdh"),
                JsonObjectUtil.deal(object, "xb"),
                JsonObjectUtil.deal(object, "rxnf"),
                JsonObjectUtil.deal(object, "txdz"),
                userid,
                zydm,
                JsonObjectUtil.deal(object, "xymc"),
                JsonObjectUtil.deal(object, "sfzh"),
                JsonObjectUtil.deal(object, "username"),
                JsonObjectUtil.deal(object, "bjdm"),
                JsonObjectUtil.deal(object, "sr"),
                usertype + "",
                JsonObjectUtil.deal(object, "tsid")
        );
        Log.d(TAG, "tsid: " + tsid);
        new SqliteHelper().rawQuery("delete from usertype");
        new SqliteHelper().execSQL("insert or replace into usertype(userid,usertype) values(?,?)",
                userid,
                usertype
        );

        Constants.number = userid;
        Constants.jpushID = tsid;
        String password = new SqliteHelper().rawQuery("select * from password4").get(0).get("password");
        password = new RSAHelper().decode(password);
        Constants.code = MD5.MD5(password);

        new StartPresenter(context).getMyselfList();

        registerJpush(context);
    }

    /**
     * 上传手机信息
     */
    static void uploadingPhoneMessage(Activity activity) {
        try {
            JSONObject mob = new JSONObject();
            mob.put("company", SystemUtil.getDeviceBrand());
            mob.put("model", SystemUtil.getSystemModel());
            mob.put("version", SystemUtil.getSystemVersion());
            mob.put("userid", "123");
            Map<String, String> map = new HashMap<>();
            map.put("b", mob.toString());
            OkHttpUtil.OkHttpPost(GetAppUrl.UIA.uploadingPhoneMessage.getUrl(), map, "uploadingPhoneMessage", activity);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取验证码
     */
    public static void getVerificationCode(Context context, Map<String, String> getVerificationCodeMap, String phoneNum, String passWord, String schoolId) {
        getVerificationCodeMap = MapUtil.getMap(getVerificationCodeMap);
        try {
            JSONObject mob = new JSONObject();
            mob.put("mob", StringUtil.takeOutSpacing(phoneNum));
            mob.put("password", StringUtil.takeOutSpacing(passWord));
            mob.put("random", Tool.stochasticString());
            mob.put("sn", DeviceUtils.getUniqueId(context));

            mob.put("xxbh", StringUtil.takeOutSpacing(schoolId));

            String b = URLEncoder.encode(RSAApi.getEncryptSecurity(mob.toString()), "utf-8");
            getVerificationCodeMap.put("b", b);
            OkHttpUtil.OkHttpPost(GetAppUrl.UIA.getVerificationCode.getUrl(), getVerificationCodeMap, TagUtil.GetVerificationCodeTag, (Activity) context);
        } catch (Exception e) {
            EventBus.getDefault().post(new MessageEvent(HintTool.REQUESTFAIL, HintTool.REQUESTFAIL));
            e.printStackTrace();
        }
    }

    /**
     * 获取密码
     */
    public static void getPassWord(Activity activity, Map<String, String> getPassWordMap, String phoneNum) {
        getPassWordMap = MapUtil.getMap(getPassWordMap);
        getPassWordMap.put("mob", StringUtil.takeOutSpacing(phoneNum));
        OkHttpUtil.OkHttpPost(GetAppUrl.UIA.getPassWord.getUrl(), getPassWordMap, TagUtil.GetPassWordTag, activity);
    }

    /**
     * 验证验证码
     */
    public static void verifyVerificationCode(Activity activity, Map<String, String> verifyVerificationCodeMap, String yzm, String mob) {
        verifyVerificationCodeMap = MapUtil.getMap(verifyVerificationCodeMap);
        verifyVerificationCodeMap.put("mob", StringUtil.takeOutSpacing(mob));
        verifyVerificationCodeMap.put("yzm", StringUtil.takeOutSpacing(yzm));
        OkHttpUtil.OkHttpPost(GetAppUrl.UIA.verifyVerificationCode.getUrl(), verifyVerificationCodeMap, TagUtil.VerifyVerificationCodeTag, activity);
    }

    /**
     * 获取用户基本信息
     */
    public static void getUserMessage(Activity activity, Map<String, String> getUserMessageMap) {
        try {
            getUserMessageMap = MapUtil.getMap(getUserMessageMap);
            String phone = new SqliteHelper().rawQuery("select * from password4 ").get(0).get("phone");
            getUserMessageMap.put("mob", StringUtil.takeOutSpacing(phone));
            getUserMessageMap.put("password", StringUtil.takeOutSpacing(UserMessageTool.getPassWord()));
            OkHttpUtil.OkHttpPost(GetAppUrl.UIA.getUserMessage.getUrl(), getUserMessageMap, TagUtil.GetUserMessageTag, activity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 验证学/工号
     */
    public static void verifyWorkNum(Activity activity, Map<String, String> verifyIDMap, String mob, String stuid, String universityName) {
        try {
            verifyIDMap = MapUtil.getMap(verifyIDMap);
            verifyIDMap.put("mob", StringUtil.takeOutSpacing(mob));
            verifyIDMap.put("stuid", StringUtil.takeOutSpacing(stuid));
            List<Map<String, String>> maps = new ArrayList<>();
            maps = GetSchoolListUtil.getSchoolData("select * from schools where xxmc='" + universityName + "'", maps);
            verifyIDMap.put("xxbh", maps.get(0).get("xxbh"));
            OkHttpUtil.OkHttpPost(GetAppUrl.UIA.verifyID.getUrl(), verifyIDMap, TagUtil.VerifyIDTag, activity);
        } catch (Exception ignored) {

        }
    }

    /**
     * 验证身份证
     */

    public static void verifyID(Activity activity, Map<String, String> verifyIDCardMap, String mob, String sfzh, String universityName) {
        verifyIDCardMap = MapUtil.getMap(verifyIDCardMap);

        verifyIDCardMap.put("mob", StringUtil.takeOutSpacing(mob));
        verifyIDCardMap.put("sfzh", StringUtil.takeOutSpacing(sfzh));
        List<Map<String, String>> maps = new ArrayList<>();
        maps = GetSchoolListUtil.getSchoolData("select * from schools where xxmc='" + universityName + "'", maps);
        verifyIDCardMap.put("xxbh", maps.get(0).get("xxbh"));
        OkHttpUtil.OkHttpPost(GetAppUrl.UIA.verifyIDCard.getUrl(), verifyIDCardMap, TagUtil.VerifyIDCardTag, activity);
    }


    /**
     * 跳转到主页面
     */
    public static void intentToMain(Context context) {
            Activity activity = (Activity) context;
            Intent intent = new Intent(activity, MainActivity.class);
            activity.startActivity(intent);
            EventBus.getDefault().post(new MessageEvent(TagUtil.ChangePhoneBindingSuccess, ""));
            activity.finish();
    }

    /**
     * 跳转到广告页面
     */
    public static void intentToADActivity(Context context, String url) {
        Activity activity = (Activity) context;
        Intent intent = new Intent(activity, ADActivity.class);
        intent.putExtra("imageUrl", url);
        activity.startActivity(intent);
        EventBus.getDefault().post(new MessageEvent(TagUtil.ChangePhoneBindingSuccess, ""));
        activity.finish();
    }

    /**
     * 注册jpush
     */
    @SuppressLint("StaticFieldLeak")
    private static ApnsStart apnsStart;

    private static void registerJpush(Context context) {
        apnsStart = new ApnsStart(context);
        apnsStart.start();
    }

    /**
     * 取消注册jpush
     */
    public static void unregisterJpush() {
        apnsStart.unregisterMessageReceiver();
    }
}
