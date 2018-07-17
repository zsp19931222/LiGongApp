package com.example.app4.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.app4.activity.BindingOtherActivity;
import com.example.app4.activity.BindingPhoneActivity;
import com.example.app4.api.GetAppUrl;
import com.example.app4.api.OkHttpUtil;
import com.example.app4.entity.HomePageWidgetEntity;
import com.example.app4.tool.GetSchoolNumTool;
import com.example.app4.tool.UserMessageTool;
import com.example.app4.util.DefaultUtil;
import com.example.app4.util.StringUtil;
import com.example.smartclass.util.TagUtil;

import org.androidpn.push.Constants;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import yh.app.appstart.YDY;
import yh.app.tool.SqliteHelper;
import yh.app.utils.GsonImpl;

public class StartPresenter {
    private Context context;
    private Activity activity;
    private Map<String, String> map;

    public StartPresenter(Context context) {
        this.context = context;
        activity = (Activity) context;
        map = new HashMap<>();
        DefaultUtil.setDefaultSchool("重庆理工大学");
    }

    /**
     * 判断跳转第一次打开APP跳转到引导页，第二次跳转第二启动页
     */
    public void intent2Where() {
        if (new SqliteHelper().rawQuery("select * from ydy").size() == 0) {//首次打开APP跳转引导页
            Intent intent = new Intent(context, YDY.class);
            context.startActivity(intent);
            LoginUtil.uploadingPhoneMessage((Activity) context);
        } else {
            String universityName;//学校名称
            if (new SqliteHelper().rawQuery("select * from userinfo4").size() != 0) {//已登录
                getADImage();
                universityName = new SqliteHelper().rawQuery("select * from password4").get(0).get("schoolname");
                Constants.xxmc = universityName;
            } else {//未登录
                Intent intent = new Intent(context, BindingOtherActivity.class);
                universityName = DefaultUtil.getDefaultSchool();
                intent.putExtra("universityName", universityName);
                Constants.xxmc = universityName;
                context.startActivity(intent);
            }
        }

    }

//    /**
//     * 更新
//     */
//    public void updateApp(String url, final View button) {
//
//        VolleyRequest.RequestPost(url, new HashMap<String, String>(), new VolleyInterface() {
//
//            @Override
//            public void onMySuccess(String result) {
//                try {
//                    result = result.replace("\\", "");
//                    result = result.substring(1, result.length() - 1);
//                    final JSONObject jso = new JSONObject(result);
//                    if (jso.has("update") && jso.getBoolean("update")) {
//                        updateURL = jso.getString("url");
//                        button.setVisibility(View.VISIBLE);
//                        button.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                // if(true)
//                                if (NetWork.getNetWorkType(context) == NetWork.NET_TYPE_MOBILE)
//                                    new DiaLogOkAndCancel().buldeDialog(context, "提示", "当前为移动网络，是否继续下载", "继续下载", "取消", new DiaLogOkAndCancel.OnButtonClickLisener() {
//                                        @Override
//                                        public void setButton1ClickLisener(DialogInterface dialog, int which) {
//                                            dialog.dismiss();
//                                            new RxPermissions((Activity) context).request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(observer);
//                                        }
//
//                                        @Override
//                                        public void setButton2ClickLisener(DialogInterface dialog, int which) {
//                                            dialog.dismiss();
//                                            {
//                                                ((Activity) context).finish();
//                                                BreakApp.closeAPP();
//                                            }
//                                        }
//
//                                    });
//                                else if (NetWork.getNetWorkType(context) == NetWork.NET_TYPE_WIFI) {
//                                    new RxPermissions((Activity) context).request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(observer);
//                                }
//                            }
//                        });
//                    } else {
//                        intent2Where();
//                    }
//                } catch (Exception ignored) {
//                }
//            }
//
//            @Override
//            public void onMyError(VolleyError error) {
//                intent2Where();
//            }
//        });
//    }

//    private String updateURL;
//
//    private Observer<Boolean> observer = new Observer<Boolean>() {
//        @Override
//        public void onCompleted() {
//
//        }
//
//        @Override
//        public void onError(Throwable e) {
//            Toast.makeText(context, "下载失败，请稍后重试", Toast.LENGTH_SHORT).show();
//        }
//
//        @Override
//        public void onNext(Boolean o) {
//            if (o) {
//                Intent intent = new Intent(context, UpDateActivity.class);
//                intent.putExtra("url", updateURL);
//                context.startActivity(intent);
//            } else {
//                Toast.makeText(context, "SD卡下载权限被拒绝", Toast.LENGTH_SHORT).show();
//            }
//        }
//    };

    /**
     * 获取广告图片
     */
    private void getADImage() {
        map.clear();
        map.put("xxbh", StringUtil.takeOutSpacing(GetSchoolNumTool.getSchoolNum()));
        map.put("mob_type", "2");
        OkHttpUtil.OkHttpPost(GetAppUrl.Show.getADImage.getUrl(), map, TagUtil.GetADImageTag, (Activity) context);
    }

    /**
     * 获取我的列表
     */
    void getMyselfList() {
        map.clear();
        map.put("password", UserMessageTool.getPassWord());
        map.put("userid", Constants.number);
        map.put("xxbh", GetSchoolNumTool.getSchoolNum());
        map.put("type", new SqliteHelper().rawQuery("select * from usertype").get(0).get("usertype"));
        OkHttpUtil.OkHttpPost(GetAppUrl.Show.getMySelfList.getUrl(), map, TagUtil.GetMySelfListTag,(Activity) context);
    }

    /**
     * 获取我的导航栏
     */
    private void getNavigationList() {
        map.clear();
        map.put("password", UserMessageTool.getPassWord());
        map.put("userid", Constants.number);
        map.put("xxbh", GetSchoolNumTool.getSchoolNum());
        map.put("type", new SqliteHelper().rawQuery("select * from usertype").get(0).get("usertype"));
        OkHttpUtil.OkHttpPost(GetAppUrl.Show.getNavigationList.getUrl(), map, TagUtil.GetNavigationListTag,(Activity) context);
    }

    public void saveNavigation(String json) {
        new SqliteHelper().rawQuery("delete from navigation_list_json");
        new SqliteHelper().execSQL("insert or replace into navigation_list_json(json) values(?)", json);
    }

    /**
     * 储存我的列表
     */
    public void saveList(String json) {
        try {
            HomePageWidgetEntity widgetEntity = GsonImpl.get().toObject(json, HomePageWidgetEntity.class);
            JSONObject object = new JSONObject();
            JSONArray allTagsList = new JSONArray();
            for (int i = 0; i < widgetEntity.getContent().size(); i++) {
                JSONObject object1 = new JSONObject();
                object1.put("layout", "fill_view");
                object1.put("view", "item_fill_h15dp_wm_bacf7f7f7");
                allTagsList.put(object1);
                for (int j = 0; j < widgetEntity.getContent().get(i).getLx().size(); j++) {
                    HomePageWidgetEntity.ContentBean.LxBean beans = widgetEntity.getContent().get(i).getLx().get(j);
                    JSONObject object2 = new JSONObject();
                    object2.put("layout", "no_fill_view");
                    object2.put("title", beans.getTitle());
                    object2.put("sort", beans.getSort());
                    JSONArray array = new JSONArray(beans.getFunction_key());
                    object2.put("function_key", array);
                    object2.put("img", beans.getImg());
                    object2.put("funcid", beans.getFuncid());
                    object2.put("type", beans.getType());
                    object2.put("class_name", beans.getClass_name());
                    object2.put("url", beans.getUrl());
                    object2.put("power", beans.getPower());
                    object2.put("android_packname", beans.getAndroid_packname());
                    object2.put("android_address", beans.getAndroid_address());
                    allTagsList.put(object2);
                }
            }
            object.put("allTagsList", allTagsList);
            Log.d(TAG, "onEventMainThread: " + object.toString());
            new SqliteHelper().rawQuery("delete from myself_list_json");
            new SqliteHelper().execSQL("insert or replace into myself_list_json(json) values(?)", object.toString());
        } catch (Exception ignored) {

        }
        getNavigationList();
    }


    private static final String TAG = "StartPresenter";
}
