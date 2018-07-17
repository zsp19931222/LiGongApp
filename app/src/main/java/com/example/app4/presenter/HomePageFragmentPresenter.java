package com.example.app4.presenter;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.example.app3.activity.BrowserActivity;
import com.example.app3.adapter.dragrecyclear.bean.DragBean;
import com.example.app3.childview.HomeApplyView;
import com.example.app3.tool.NetImageLoadHolder;
import com.example.app3.tool.Tool;
import com.example.app4.api.GetAppUrl;
import com.example.app4.api.OkHttpUtil;
import com.example.app4.bean.HomePageRecBean;
import com.example.app4.entity.ApplicationClassifyEntity;
import com.example.app4.entity.ApplicationEntity;
import com.example.app4.entity.HomePageWidgetEntity;
import com.example.app4.tool.GetSchoolNumTool;
import com.example.app4.tool.UserMessageTool;
import com.example.app4.util.FunctionIntentUtil;
import com.example.app4.util.IsNull;
import com.example.app4.util.IsNullUtil;
import com.example.app4.util.MapUtil;
import com.example.app4.util.StringUtil;
import com.example.jpushdemo.ExampleApplication;
import com.example.smartclass.eventbus.MessageEvent;
import com.example.smartclass.util.TagUtil;
import com.google.gson.Gson;
import com.tbruyelle.rxpermissions.RxPermissions;

import yh.app.appstart.lg.R;

import com.zxing.activity.CaptureActivity;

import org.androidpn.push.Constants;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import rx.Observer;
import yh.app.model.DAModel;
import yh.app.tool.MD5;
import yh.app.tool.SqliteHelper;
import yh.app.utils.GsonImpl;
import yh.app.utils.VolleyInterface;
import yh.app.utils.VolleyRequest;
import 云华.智慧校园.工具.CodeManage;
import 云华.智慧校园.工具.JsonReaderHelper;
import 云华.智慧校园.工具.RSAHelper;
import 云华.智慧校园.工具.RandomTools;
import 云华.智慧校园.工具._链接地址导航;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Administrator on 2018/4/16 0016.
 * <p>
 * 首页方法
 */

public class HomePageFragmentPresenter {

    private Context context;
    private Map<String, String> map;

    public HomePageFragmentPresenter(Context context) {
        this.context = context;
        map = MapUtil.getMap(map);
    }

    /**
     * 获取首页控件
     */
    public void showWidget() {
        map.clear();
        map.put("type", new SqliteHelper().rawQuery("select usertype from userinfo4").get(0).get("usertype"));
        map.put("user_id", UserMessageTool.getUserId());
        map.put("password", StringUtil.takeOutSpacing(UserMessageTool.getPassWord()));
        OkHttpUtil.OkHttpPost(GetAppUrl.Show.widgetHomePage.getUrl(), map, TagUtil.ShowWidgetTag, (Activity) context);
    }

    /**
     * 获取应用列表
     */
    public void getApplicationList() {
        map.clear();
        map.put("type", new SqliteHelper().rawQuery("select usertype from userinfo4").get(0).get("usertype"));
        map.put("userid", UserMessageTool.getUserId());
        map.put("password", StringUtil.takeOutSpacing(UserMessageTool.getPassWord()));
        map.put("xxbh", GetSchoolNumTool.getSchoolNum());
        OkHttpUtil.OkHttpPost(GetAppUrl.Show.getApplicationList.getUrl(), map, TagUtil.getApplicationListTag, (Activity) context);
    }

    /**
     * 储存首页控件
     */
    public void saveWidget(String json) {
        new SqliteHelper().rawQuery("delete from homepage_widget_json");
        new SqliteHelper().execSQL("insert or replace into homepage_widget_json(json) values(?)", json);
    }

    /**
     * 储存首页应用
     */
    public void saveApplication(String json) {

        new SqliteHelper().rawQuery("delete from homepage_application_json");
        new SqliteHelper().execSQL("insert or replace into homepage_application_json(json) values(?)", json);

        ApplicationEntity applicationEntity = GsonImpl.get().toObject(json, ApplicationEntity.class);
        new SqliteHelper().rawQuery("delete from compile_data");
        for (int i = 0; i < applicationEntity.getContent().size(); i++) {
            if (!"com.example.app4.activity.MoreActivity".equals(applicationEntity.getContent().get(i).getClass_name())) {
                new SqliteHelper().execSQL("insert or replace into compile_data(url,name,section,position,functionID) values(?,?,?,?,?)",
                        applicationEntity.getContent().get(i).getImg(),
                        applicationEntity.getContent().get(i).getTitle(),
                        -1,
                        -1,
                        applicationEntity.getContent().get(i).getFuncid()
                );
            }
        }
    }

    /**
     * 获取应用分类列表
     */
    public void getApplicationClassify() {
        map.clear();
        map.put("xxbh", GetSchoolNumTool.getSchoolNum());
        map.put("userid", UserMessageTool.getUserId());
        map.put("password", UserMessageTool.getPassWord());
        map.put("type", UserMessageTool.getType());
        OkHttpUtil.OkHttpPost(GetAppUrl.Show.getApplicationClassify.getUrl(), map, TagUtil.getApplicationClassifyTag, (Activity) context);
    }

    /**
     * 存储应用分类列表
     */
    public void saveApplicationClassify(String json) {
        new SqliteHelper().rawQuery("delete from new_apply_function");

        ApplicationClassifyEntity entity = GsonImpl.get().toObject(json, ApplicationClassifyEntity.class);
        JSONObject allJsonObject = new JSONObject();
        try {
            JSONArray allTagsList = new JSONArray();

            JSONObject tagsName = null;
            JSONArray jsonArray = null;
            for (int i = 0; i < entity.getContent().size(); i++) {
                tagsName = new JSONObject();
                tagsName.put("tagsName", entity.getContent().get(i).getName());
                tagsName.put("tagsId", entity.getContent().get(i).getClassid());
                jsonArray = new JSONArray();
                for (int j = 0; j < entity.getContent().get(i).getLx().size(); j++) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("tagName", entity.getContent().get(i).getLx().get(j).getTitle());
                    jsonObject.put("latlon", entity.getContent().get(i).getLx().get(j).getImg());
                    jsonObject.put("isSelected", true);
                    jsonObject.put("tagId", entity.getContent().get(i).getLx().get(j).getFuncid());
                    jsonObject.put("lat", entity.getContent().get(i).getLx().get(j).getClass_name());
                    jsonObject.put("tagType", entity.getContent().get(i).getLx().get(j).getType());
                    jsonObject.put("url", entity.getContent().get(i).getLx().get(j).getUrl());
                    jsonObject.put("power", entity.getContent().get(i).getLx().get(j).getPower());
                    JSONArray array = new JSONArray(entity.getContent().get(i).getLx().get(j).getFunction_key());
                    jsonObject.put("function_key", array);
                    jsonObject.put("android_packname", entity.getContent().get(i).getLx().get(j).getAndroid_packname());
                    jsonObject.put("android_address", entity.getContent().get(i).getLx().get(j).getAndroid_address());
                    jsonArray.put(jsonObject);

                    new SqliteHelper().execSQL("update compile_data set url=?,name=?,section=?,position=? where functionID=?"
                            , entity.getContent().get(i).getLx().get(j).getImg(), entity.getContent().get(i).getLx().get(j).getTitle(), i, j, entity.getContent().get(i).getLx().get(j).getFuncid());

                    new SqliteHelper().execSQL("replace into new_apply_function(userid,function_id,function_name,function_cls,function_pkg,function_icon,function_px,function_type,function_key,function_group_id,function_group_name,function_display_homepage)values(?,?,?,?,?,?,?,?,?,?,?,?)",
                            UserMessageTool.getUserId(),
                            entity.getContent().get(i).getLx().get(j).getFuncid(),
                            entity.getContent().get(i).getLx().get(j).getTitle(),
                            entity.getContent().get(i).getLx().get(j).getClass_name(),
                            entity.getContent().get(i).getLx().get(j).getAndroid_packname(),
                            entity.getContent().get(i).getLx().get(j).getImg(),
                            "",
                            entity.getContent().get(i).getLx().get(j).getType(),
                            entity.getContent().get(i).getLx().get(j).getFunction_key(), "", "", "");
                }
                if (jsonArray.length() != 0) {
                    tagsName.put("tagInfoList", jsonArray);
                    allTagsList.put(tagsName);
                }
            }
            allJsonObject.put("allTagsList", allTagsList);

            new SqliteHelper().rawQuery("delete from applicationclassify_json");
            new SqliteHelper().execSQL("insert or replace into applicationclassify_json(json) values(?)", allJsonObject.toString());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        String s = new SqliteHelper().rawQuery("select * from applicationclassify_json").get(0).get("json");
        Log.d(TAG, "saveApplicationClassify: " + s);
    }

    /**
     * 二维码
     */
    public void QRCode() {
        new RxPermissions((Activity) context).request(Manifest.permission.CAMERA).subscribe(observer);
    }

    /**
     * 提示权限
     */
    private Observer<Boolean> observer = new Observer<Boolean>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            Toast.makeText(context, "相机权限打开失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNext(Boolean o) {
            if (o) {
                Intent intent3 = new Intent(context, CaptureActivity.class);
                ((Activity) context).startActivityForResult(intent3, 0);
            } else {
                Toast.makeText(context, "相机权限打开失败", Toast.LENGTH_SHORT).show();
            }
        }
    };

    /**
     * 从网络获取banner
     */
    public void getBanner() {
        map.clear();
        map.put("userid", UserMessageTool.getUserId());
        map.put("password", StringUtil.takeOutSpacing(UserMessageTool.getPassWord()));
        map.put("xxbh", GetSchoolNumTool.getSchoolNum());
        OkHttpUtil.OkHttpPost(GetAppUrl.Show.getBannerList.getUrl(), map, TagUtil.GetBannerTag, (Activity) context);
    }

    /**
     * 显示banner
     */
    public void setBanner(final ConvenientBanner<DAModel.ContentBean> convenientBanner, final List<DAModel.ContentBean> bannerList) {
        convenientBanner.setPages(new CBViewHolderCreator<NetImageLoadHolder>() {
            @Override
            public NetImageLoadHolder createHolder() {
                return new NetImageLoadHolder();
            }
        }, bannerList)
                //设置指示器是否可见
                .setPointViewVisible(true)
                //设置自动切换（同时设置了切换时间间隔）
                .startTurning(5000)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.c1_select, R.drawable.c1_selected})
                //设置指示器的方向（左、中、右）
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                //设置点击监听事件
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        intentBanner(context, bannerList.get(position));
                    }
                })
                //设置手动影响（设置了该项无法手动切换）
                .setManualPageable(true);

        convenientBanner.setOnTouchListener(new MyOnTouchListener(convenientBanner));
    }

    private int lastX, lastY;

    private class MyOnTouchListener implements View.OnTouchListener {
        private ConvenientBanner convenientBanner;

        MyOnTouchListener(ConvenientBanner convenientBanner) {
            this.convenientBanner = convenientBanner;
        }

        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            convenientBanner.getParent().requestDisallowInterceptTouchEvent(true);
            int x = (int) event.getRawX();
            int y = (int) event.getRawY();

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    lastX = x;
                    lastY = y;
                    break;
                case MotionEvent.ACTION_MOVE:
                    int deltaY = y - lastY;
                    int deltaX = x - lastX;
                    if (Math.abs(deltaX) < Math.abs(deltaY)) {
                        convenientBanner.getParent().requestDisallowInterceptTouchEvent(false);
                    } else {
                        convenientBanner.getParent().requestDisallowInterceptTouchEvent(true);
                    }
                default:
                    break;
            }
            return false;
        }
    }

    /**
     * 设置默认banner
     */
    public void setDefaultBanner(ConvenientBanner<DAModel.ContentBean> convenientBanner, List<DAModel.ContentBean> bannerList) {
        for (int i = 0; i < 3; i++) {
            bannerList.add(new DAModel.ContentBean("", "", "", R.drawable.xxhome1, "", "", 0, "", "", "", null));
        }
        setBanner(convenientBanner, bannerList);
    }

    /**
     * 获取刷新随机提示语
     */
    private int showStringIndex = 0;

    public String getRandomString() {
        String showString;
        try {
            JSONArray jsa = new JSONArray(JsonReaderHelper.getJosn(context, "MJRefreshState.json"));
            showStringIndex = RandomTools.getRandomNumber(jsa.length(), showStringIndex);
            showString = jsa.getString(showStringIndex);
        } catch (Exception e) {
            showString = context.getResources().getString(R.string.refreshing);
        }
        return showString;
    }

    /**
     * 控件跳转
     */
    public void intentWidget(final Context context, final HomePageWidgetEntity.ContentBean.LxBean lxBean) {
        new FunctionIntentUtil<>(lxBean, context).intent2();
    }

    /**
     * 应用跳转
     */
    public void intentApplication(final Context context, final ApplicationEntity.ContentBean lxBean) {
        new FunctionIntentUtil<>(lxBean, context).intent2();
    }

    /**
     * banner跳转
     */
    public void intentBanner(final Context context, final DAModel.ContentBean lxBean) {
        new FunctionIntentUtil<>(lxBean, context).intent2();
    }

    /**
     * 消息类型：大图
     */
    public static final String MESSAGE_TYPE_LARGE_PIC = "0";
    /**
     * 消息类型：小图
     */
    public static final String MESSAGE_TYPE_SMALL_PIC = "1";
    /**
     * 消息类型：无图
     */
    public static final String MESSAGE_TYPE_NO_PIC = "2";
    /**
     * 消息类型：widget展示控件
     */
    public static final String MESSAGE_TYPE_Widget = "3";
    private List<Map<String, String>> client_noticeList;

    private long startTime = 0;

    /**
     * 从本地数据库获取推送内容
     */
    public void getPushData() {
        startTime = System.currentTimeMillis();
        List<Map<String, String>> kindMap = new SqliteHelper().rawQuery("select * from client_notice GROUP BY function_id ORDER by n_send_time asc");
        for (int i = 0; i < kindMap.size(); i++) {
            if (client_noticeList != null) {
                client_noticeList.clear();
            }
            client_noticeList = new SqliteHelper().rawQuery("select * from client_notice where function_id=? ORDER by n_send_time desc", kindMap.get(i).get("function_id"));//根据function_id查询相关数据
            for (int j = 0; j < 1; j++) {
                List<Map<String, String>> new_push_functionList = new SqliteHelper().rawQuery("select * from new_push_function where ts_id=?", kindMap.get(i).get("function_id"));
                try {
//                    if (new_push_functionList.get(0).get("ts_group") != null && !new_push_functionList.get(0).get("ts_group").equals("") && !new_push_functionList.get(0).get("ts_group").equals("null")) {//分组消息
                    if (IsNullUtil.isNotNull(new_push_functionList.get(0).get("ts_group"))) {
                        if (CodeManage.APP_Messsage_Group_ID.equals(new_push_functionList.get(0).get("ts_group"))) {//应用消息
                            saveMessageList(MESSAGE_TYPE_SMALL_PIC,
                                    "group",
                                    "应用消息",
                                    client_noticeList.get(j).get("code"),
                                    client_noticeList.get(j).get("read"),
                                    "应用消息",
                                    client_noticeList.get(j).get("n_send_time"),
                                    client_noticeList.get(j).get("n_picpath"),
                                    client_noticeList.get(j).get("n_title"),
                                    client_noticeList.get(j).get("n_message"),
                                    client_noticeList.get(j).get("n_id"),
                                    client_noticeList.get(j).get("function_id"),
                                    new_push_functionList.get(0).get("ts_group"));
                        } else {//订阅消息
                            saveMessageList(MESSAGE_TYPE_NO_PIC,
                                    "group",
                                    "订阅消息",
                                    client_noticeList.get(j).get("code"),
                                    client_noticeList.get(j).get("read"),
                                    "订阅消息",
                                    client_noticeList.get(j).get("n_send_time"),
                                    client_noticeList.get(j).get("n_picpath"),
                                    client_noticeList.get(j).get("n_title"),
                                    client_noticeList.get(j).get("n_message"),
                                    client_noticeList.get(j).get("n_id"),
                                    client_noticeList.get(j).get("function_id"),
                                    new_push_functionList.get(0).get("ts_group"));
                        }
                    } else {//功能消息
                        if (client_noticeList.get(j).get("n_picpath") != null && !client_noticeList.get(j).get("n_picpath").equals("") && !client_noticeList.get(j).get("n_picpath").equals("null")) {//功能有图消息
                            saveMessageList(MESSAGE_TYPE_LARGE_PIC,
                                    "function",
                                    new_push_functionList.get(0).get("ts_name"),
                                    client_noticeList.get(j).get("code"),
                                    client_noticeList.get(j).get("read"),
                                    client_noticeList.get(j).get("n_from"),
                                    client_noticeList.get(j).get("n_send_time"),
                                    client_noticeList.get(j).get("n_picpath"),
                                    client_noticeList.get(j).get("n_title"),
                                    client_noticeList.get(j).get("n_message"),
                                    client_noticeList.get(j).get("n_id"),
                                    client_noticeList.get(j).get("function_id"),
                                    client_noticeList.get(j).get("function_id"));
                        } else {//功能无图消息
                            saveMessageList(MESSAGE_TYPE_NO_PIC,
                                    "function",
                                    new_push_functionList.get(0).get("ts_name"),
                                    client_noticeList.get(j).get("code"),
                                    client_noticeList.get(j).get("read"),
                                    client_noticeList.get(j).get("n_from"),
                                    client_noticeList.get(j).get("n_send_time"),
                                    client_noticeList.get(j).get("n_picpath"),
                                    client_noticeList.get(j).get("n_title"),
                                    client_noticeList.get(j).get("n_message"),
                                    client_noticeList.get(j).get("n_id"),
                                    client_noticeList.get(j).get("function_id"),
                                    client_noticeList.get(j).get("function_id"));
                        }
                    }
                } catch (Exception ignored) {
                    Log.d(TAG, "getPushData: " + ignored);
                    String group = client_noticeList.get(j).get("n_from");
                    if (group == null || group.equals("")) {
                        group = " ";
                    }
//                    if (client_noticeList.get(j).get("n_picpath") != null && !client_noticeList.get(j).get("n_picpath").equals("") && !client_noticeList.get(j).get("n_picpath").equals("null")) {//功能有图消息
                    if (IsNullUtil.isNotNull(client_noticeList.get(j).get("n_picpath"))) {

                        saveMessageList(MESSAGE_TYPE_LARGE_PIC,
                                "function",
                                group,
                                client_noticeList.get(j).get("code"),
                                client_noticeList.get(j).get("read"),
                                client_noticeList.get(j).get("n_from"),
                                client_noticeList.get(j).get("n_send_time"),
                                client_noticeList.get(j).get("n_picpath"),
                                client_noticeList.get(j).get("n_title"),
                                client_noticeList.get(j).get("n_message"),
                                client_noticeList.get(j).get("n_id"),
                                client_noticeList.get(j).get("function_id"),
                                client_noticeList.get(j).get("function_id"));
                    } else {//功能无图消息
                        saveMessageList(MESSAGE_TYPE_NO_PIC,
                                "function",
                                group,
                                client_noticeList.get(j).get("code"),
                                client_noticeList.get(j).get("read"),
                                client_noticeList.get(j).get("n_from"),
                                client_noticeList.get(j).get("n_send_time"),
                                client_noticeList.get(j).get("n_picpath"),
                                client_noticeList.get(j).get("n_title"),
                                client_noticeList.get(j).get("n_message"),
                                client_noticeList.get(j).get("n_id"),
                                client_noticeList.get(j).get("function_id"),
                                client_noticeList.get(j).get("function_id"));
                    }
                }
            }

        }
    }

    /**
     * 储存首页消息
     */
    private void saveMessageList(Object... message) {
        new SqliteHelper().execSQL("insert or replace into homeMessageList(m_type,m_classify,m_group,m_code,m_read,m_from,m_time,m_image,m_title,m_message,m_id,m_function_id,m_group_id) values(?,?,?,?,?,?,?,?,?,?,?,?,?)"
                , message
        );
    }


    private List<Map<String, String>> homeMessageList;

    /**
     * 对首页展示数据获取
     */
    public List<HomePageRecBean> getShowData(List<HomePageRecBean> pageRecBeans) {
        if (homeMessageList == null) {
            homeMessageList = new ArrayList<>();
        } else {
            homeMessageList.clear();
        }
        homeMessageList.addAll(new SqliteHelper().rawQuery("select * from homeMessageList order by m_time desc"));
        for (int i = 0; i < homeMessageList.size(); i++) {
            pageRecBeans.add(pageRecBeans.size(), new HomePageRecBean(
                    homeMessageList.get(i).get("m_type"),
                    homeMessageList.get(i).get("m_classify"),
                    homeMessageList.get(i).get("m_group"),
                    homeMessageList.get(i).get("m_code"),
                    homeMessageList.get(i).get("m_read"),
                    homeMessageList.get(i).get("m_from"),
                    homeMessageList.get(i).get("m_time"),
                    homeMessageList.get(i).get("m_image"),
                    homeMessageList.get(i).get("m_title"),
                    homeMessageList.get(i).get("m_message"),
                    homeMessageList.get(i).get("m_id"),
                    homeMessageList.get(i).get("m_function_id"),
                    homeMessageList.get(i).get("m_group_id")
            ));
        }
        Log.d(TAG, "getShowData: " + (System.currentTimeMillis() - startTime));
        return pageRecBeans;
    }

    public void checkGroup(String groupId) {
        new SqliteHelper().execSQL("update client_notice_messagelist set n_look='true' where n_group_id=?", groupId);
        EventBus.getDefault().post(new MessageEvent(TagUtil.CheckNumTag, ""));
    }

    public void checkFuncation(String function_id) {
        new SqliteHelper().execSQL("update client_notice_messagelist set n_look='true' where n_group_id=?", function_id);
        EventBus.getDefault().post(new MessageEvent(TagUtil.CheckNumTag, ""));
    }

    public void checkID(String id) {
        new SqliteHelper().execSQL("update client_notice_messagelist set n_look='true' where n_id=?", id);
        EventBus.getDefault().post(new MessageEvent(TagUtil.CheckNumTag, ""));
    }

//    /**
//     * 更多页面数据
//     */
//    public void moreActivityData(List<ApplicationEntity.ContentBean> contentBean) {
//        new SqliteHelper().execSQL("delete from new_apply_function where userid=?", Constants.number);
//        for (int i = 0; i <contentBean.size() ; i++) {
//            ApplicationEntity.ContentBean bean=contentBean.get(i);
//            new SqliteHelper().execSQL("replace into new_apply_function(userid, function_id,function_name,function_cls,function_pkg,function_icon,function_px,function_type,function_key,function_group_id,function_group_name,function_display_homepage)" + "values(?,?,?,?,?,?,?,?,?,?,?,?)", new Object[]
//                    {
//                            Constants.number, bean.getFuncid(), item.getString("FUNCTION_NAME"), item.getString("CLASS_NAME"), item.getString("PACKAGE_NAME"), item.getString("FUNCTION_FACE"), item.getString("PX"), item.getString("FUNCTION_TYPE"), item.getString("INTEGRATE_KEY"), item.getString("TYPE_ID"), item.getString("TYPE_NAME"), HomeApplyView.FUNCTION_DISPLAY_HOMEPAGE_NO
//                    });
//        }
//    }

    private static final String TAG = "HomePageFragmentPresent";
}
