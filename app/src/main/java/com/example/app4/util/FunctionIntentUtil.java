package com.example.app4.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.Toast;

import com.example.app3.search.Group;
import com.example.app3.tool.Tool;
import com.example.app4.api.GetAppUrl;
import com.example.app4.entity.ApplicationEntity;
import com.example.app4.entity.HomePageWidgetEntity;
import com.example.app4.entity.MainWidgetEntity;
import com.example.app4.entity.MySelfEntity;
import com.example.app4.tool.UserMessageTool;
import com.example.entity.MoreEntity;
import com.example.jpushdemo.ExampleApplication;
import com.example.smartclass.activity.BrowserActivity;
import com.example.smartclass.eventbus.MessageEvent;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import okhttp3.Call;
import yh.app.model.DAModel;
import 云华.智慧校园.工具.RSAHelper;

/**
 * Created by Administrator on 2018/6/7 0007.
 * 功能跳转
 */

public class FunctionIntentUtil<T> {
    private T t;
    private Context context;
    private Activity activity;

    public FunctionIntentUtil(T t, Context context) {
        this.t = t;
        this.context = context;
        activity = (Activity) context;
    }

    public void intent2() {
        if (!Tool.isFastDoubleClick()) {
            if (t instanceof HomePageWidgetEntity.ContentBean.LxBean) {
                final HomePageWidgetEntity.ContentBean.LxBean lxBean = ((HomePageWidgetEntity.ContentBean.LxBean) t);
                try {
                    if (lxBean.getPower() == 1) {
                        if (lxBean.getType() == 1) {
                            intentApp(lxBean.getClass_name(), lxBean.getFuncid());//跳转原生功能--智慧课堂
                        } else {
                            intentWeb(lxBean.getFuncid(), lxBean.getFunction_key(), activity);//跳转带身份信息的web--迎新
                        }
                    } else if (lxBean.getPower() == 2) {
                        if (lxBean.getType() == 1) {
                            otherIntentApp(lxBean.getAndroid_packname(), lxBean.getAndroid_address());//跳转第三方APP--微信
                        } else {
                            intentWeb(lxBean.getFuncid(), lxBean.getFunction_key(), activity);//跳转带身份信息的web--迎新
                        }
                    } else {
                        intentWeb2(lxBean.getUrl());//跳转web--百度
                    }
                } catch (Exception ignored) {
                    EventBus.getDefault().post(new MessageEvent("获取网页成功", ""));
                }
            } else if (t instanceof ApplicationEntity.ContentBean) {
                final ApplicationEntity.ContentBean lxBean = ((ApplicationEntity.ContentBean) t);
                try {
                    if (lxBean.getPower() == 1) {
                        if (lxBean.getType() == 1) {
                            intentApp(lxBean.getClass_name(), lxBean.getFuncid());//跳转原生功能--智慧课堂
                        } else {
                            intentWeb(lxBean.getFuncid(), lxBean.getFunction_key(), activity);//跳转带身份信息的web--迎新
                        }
                    } else if (lxBean.getPower() == 2) {
                        if (lxBean.getType() == 1) {
                            otherIntentApp(lxBean.getAndroid_packname(), lxBean.getAndroid_address());//跳转第三方APP--微信
                        } else {
                            intentWeb(lxBean.getFuncid(), lxBean.getFunction_key(), activity);//跳转带身份信息的web--迎新
                        }
                    } else {
                        intentWeb2(lxBean.getUrl());//跳转web--百度
                    }
                } catch (Exception ignored) {
                    EventBus.getDefault().post(new MessageEvent("获取网页成功", ""));
                }
            } else if (t instanceof Group) {//搜索跳转
                final Group lxBean = ((Group) t);
                try {
                    if (lxBean.getPower() == 1) {
                        if (lxBean.getType() == 1) {
                            intentApp(lxBean.getFunction_cls(), lxBean.getFunction_id());//跳转原生功能--智慧课堂
                        } else {
                            intentWeb(lxBean.getFunction_id(), lxBean.getFunction_key(), activity);//跳转带身份信息的web--迎新
                        }
                    } else if (lxBean.getPower() == 2) {
                        if (lxBean.getType() == 1) {
                            otherIntentApp(lxBean.getAndroid_packname(), lxBean.getAndroid_address());//跳转第三方APP--微信
                        } else {
                            intentWeb(lxBean.getFunction_id(), lxBean.getFunction_key(), activity);//跳转带身份信息的web--迎新
                        }
                    } else {
                        intentWeb2(lxBean.getUrl());//跳转web--百度
                    }
                } catch (Exception ignored) {
                    EventBus.getDefault().post(new MessageEvent("获取网页成功", ""));
                }
            } else if (t instanceof MoreEntity.AllTagsListBean.TagInfoListBean) {//更多页面
                final MoreEntity.AllTagsListBean.TagInfoListBean lxBean = ((MoreEntity.AllTagsListBean.TagInfoListBean) t);
                try {
                    if (lxBean.getPower() == 1) {
                        if (lxBean.getTagType() == 1) {
                            intentApp(lxBean.getLat(), lxBean.getTagId());//跳转原生功能--智慧课堂
                        } else {
                            intentWeb(lxBean.getTagId(), lxBean.getFunction_key(), activity);//跳转带身份信息的web--迎新
                        }
                    } else if (lxBean.getPower() == 2) {
                        if (lxBean.getTagType() == 1) {
                            otherIntentApp(lxBean.getAndroid_packname(), lxBean.getAndroid_address());//跳转第三方APP--微信
                        } else {
                            intentWeb(lxBean.getTagId(), lxBean.getFunction_key(), activity);//跳转带身份信息的web--迎新
                        }
                    } else {
                        intentWeb2(lxBean.getUrl());//跳转web--百度
                    }
                } catch (Exception ignored) {
                    EventBus.getDefault().post(new MessageEvent("获取网页成功", ""));
                }
            } else if (t instanceof MySelfEntity.AllTagsListBean) {//个人中心
                final MySelfEntity.AllTagsListBean lxBean = ((MySelfEntity.AllTagsListBean) t);
                try {
                    if (lxBean.getPower() == 1) {
                        if (lxBean.getType() == 1) {
                            intentApp(lxBean.getClass_name(), lxBean.getFuncid());//跳转原生功能--智慧课堂
                        } else {
                            intentWeb(lxBean.getFuncid(), lxBean.getFunction_key(), activity);//跳转带身份信息的web--迎新
                        }
                    } else if (lxBean.getPower() == 2) {
                        if (lxBean.getType() == 1) {
                            otherIntentApp(lxBean.getAndroid_packname(), lxBean.getAndroid_address());//跳转第三方APP--微信
                        } else {
                            intentWeb(lxBean.getFuncid(), lxBean.getFunction_key(), activity);//跳转带身份信息的web--迎新
                        }
                    } else {
                        intentWeb2(lxBean.getUrl());//跳转web--百度
                    }
                } catch (Exception ignored) {
                    EventBus.getDefault().post(new MessageEvent("获取网页成功", ""));
                }
            } else if (t instanceof MainWidgetEntity.ContentBean) {//导航栏
                final MainWidgetEntity.ContentBean lxBean = ((MainWidgetEntity.ContentBean) t);
                try {
                    if (lxBean.getPower() == 1) {
                        if (lxBean.getType() == 1) {
                            intentApp(lxBean.getClass_name(), lxBean.getFuncid());//跳转原生功能--智慧课堂
                        } else {
                            intentWeb(lxBean.getFuncid(), lxBean.getFunction_key(), activity);//跳转带身份信息的web--迎新
                        }
                    } else if (lxBean.getPower() == 2) {
                        if (lxBean.getType() == 1) {
                            otherIntentApp(lxBean.getAndroid_packname(), lxBean.getAndroid_address());//跳转第三方APP--微信
                        } else {
                            intentWeb(lxBean.getFuncid(), lxBean.getFunction_key(), activity);//跳转带身份信息的web--迎新
                        }
                    } else {
                        intentWeb2(lxBean.getUrl());//跳转web--百度
                    }
                } catch (Exception ignored) {
                    EventBus.getDefault().post(new MessageEvent("获取网页成功", ""));
                }
            } else if (t instanceof DAModel.ContentBean) {//banner
                final DAModel.ContentBean lxBean = ((DAModel.ContentBean) t);
                try {
                    if (lxBean.getType() == 1) {
                        intentApp(lxBean.getClass_name(), lxBean.getFuncid());
                    } else if (lxBean.getType() == 2) {
                        intentWeb(lxBean.getFuncid(), lxBean.getFunction_key(), activity);
                    } else {
                        intentWeb2(lxBean.getUrl());
                    }
                } catch (Exception ignored) {
                    EventBus.getDefault().post(new MessageEvent("获取网页成功", ""));
                }
            }
        }
    }

    //原生应用
    private void intentApp(String class_name, String funcid) {
        String user_id = UserMessageTool.getUserId();
        Intent intent = new Intent(class_name);
        intent.setPackage(ExampleApplication.getAppPackage());
        intent.putExtra("function_id", funcid); // 功能id
        intent.putExtra("title", ""); // 功能名称
        intent.putExtra("user_id", user_id);
        context.startActivity(intent);
    }

    //web应用
    private void intentWeb(final String function_id, List<String> function_keyList, final Activity activity) {
        String user_id = UserMessageTool.getUserId();
        Map<String, String> map = new HashMap<>();
        map.put("userid", user_id);
        map.put("function_id", function_id);
        StringBuilder function_key = new StringBuilder();
        for (int i = 0; i < function_keyList.size(); i++) {
            function_key.append(new RSAHelper().decode(function_keyList.get(i)));
        }
        String ticket = MD5Util.getTicket(function_key.toString());
        map.put("Ticket".toLowerCase(Locale.CHINA), ticket);
        OkHttpUtils.post()
                .url(GetAppUrl.Show.getURL.getUrl())
                .params(map)
                .tag(activity)
                .build()
                .connTimeOut(8 * 1000)
                .readTimeOut(8 * 1000)
                .writeTimeOut(8 * 1000)
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (!call.isCanceled()) {
                            EventBus.getDefault().post(new MessageEvent("获取网页成功", ""));
                            Toast.makeText(context, "系统异常", Toast.LENGTH_SHORT).show();
                        } else {
                            android.util.Log.d(TAG, "取消请求");
                        }
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        EventBus.getDefault().post(new MessageEvent("获取网页成功", ""));
                        try {
                            JSONObject object = new JSONObject(response);
                            JSONArray array = object.getJSONArray("content");
                            String url = new RSAHelper().decode(array);
                            Intent intent = new Intent(context, BrowserActivity.class);
                            intent.setPackage(ExampleApplication.getAppPackage());
                            intent.putExtra("url", url);
                            intent.putExtra("function_id", function_id);
                            intent.putExtra("title", "");
                            context.startActivity(intent);
                        } catch (JSONException e1) {
                            Toast.makeText(context, "系统异常", Toast.LENGTH_SHORT).show();
                        }
                    }

                });
    }

    //web地址
    private void intentWeb2(String url) {
        if (IsNullUtil.isNotNull(url)) {
            Intent intent = new Intent(context, BrowserActivity.class);
            intent.putExtra("url", url);
            intent.putExtra("title", "");
            context.startActivity(intent);
        }
    }

    //跳转第三方
    private void otherIntentApp(String packageName, String url) {
        Intent intent;
        try {
            intent = new Intent();
            PackageManager packageManager = context.getPackageManager();
            intent = packageManager.getLaunchIntentForPackage(packageName);
            intent.setAction("android.intent.action.VIEW");
        } catch (Exception e) {
            Uri uri = Uri.parse(url);
            intent = new Intent(Intent.ACTION_VIEW, uri);
        }
        context.startActivity(intent);
    }

    private static final String TAG = "FunctionIntentUtil";
}
