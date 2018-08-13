package com.example.app4.api;

import android.app.Activity;
import android.widget.Toast;

import com.example.app3.tool.HintTool;
import com.example.app3.tool.JSONTool;
import com.example.app4.tool.UserMessageTool;
import com.example.jpushdemo.ExampleApplication;
import com.example.smartclass.activity.SCMainActivity;
import com.example.smartclass.eventbus.MessageEvent;
import com.example.smartclass.util.TagUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.androidpn.push.Constants;
import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import yh.app.logTool.Log;
import yh.app.tool.SqliteHelper;
import yh.app.tool.Ticket;
import yh.app.utils.ToastUtils;

import static java.lang.String.valueOf;

public class OkHttpUtil {

    /**
     * Post请求
     */
    public static void OkHttpPost(final String url, final Map<String, String> params, final String tag, final Activity activity) {
        try {
            try {
                params.put("xxbh", UserMessageTool.getXXBH());
            } catch (Exception e) {
                params.put("xxbh", "");
            }
            try {
                params.put("userid", new SqliteHelper().rawQuery("select * from userinfo4").get(0).get("userid"));
            } catch (Exception e) {
                params.put("userid", Constants.number);
            }
            if (com.example.app3.utils.LogUtils.APP_DBG) {
                StringBuilder s = new StringBuilder();
                for (String key : params.keySet()) {
                    s.append(key).append("=").append(params.get(key)).append("&");
                }
                Log.d(Log.DEFAULT_TAG, "地址：" + url + "?" + s);
            }
            OkHttpUtils.post()
                    .url(url)
                    .params(params)
                    .tag(activity)
                    .build()
                    .connTimeOut(8 * 1000)
                    .readTimeOut(8 * 1000)
                    .writeTimeOut(8 * 1000)
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            if (!call.isCanceled()) {
                                EventBus.getDefault().post(new MessageEvent(HintTool.REQUESTFAIL, HintTool.NetWorkError));
                                Log.e(Log.DEFAULT_TAG, "标识符：" + tag + "\n返回结果：" + e.toString());
                            } else {
                                Log.e(Log.DEFAULT_TAG, "标识符：" + tag + "---" + call.isCanceled());
                            }
                            Log.e("timeout", "地址：" + url + "\n错误：" + e.toString());
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            Log.e(Log.DEFAULT_TAG, "标识符：" + tag + "\n返回结果：\n" + response);
                            if (JSONTool.Code(response).equals(JSONTool.SUCCESS)) {
                                EventBus.getDefault().post(new MessageEvent(tag, response));
                            } else if (JSONTool.Code(response).equals(JSONTool.ReLogin)) {
                                EventBus.getDefault().post(new MessageEvent(HintTool.ReLogin, HintTool.ReLogin));
                                EventBus.getDefault().post(new MessageEvent(HintTool.REQUESTFAIL, "身份验证失败"));
                            } else {
                                if (tag.equals(TagUtil.VerifyOldPhoneNoUsedTag)) {
                                    EventBus.getDefault().post(new MessageEvent(HintTool.REQUESTFAIL, response));
                                } else {
                                    EventBus.getDefault().post(new MessageEvent(HintTool.REQUESTFAIL, JSONTool.Tips(response)));
//                                    EventBus.getDefault().post(new MessageEvent(HintTool.REQUESTFAIL, HintTool.REQUESTFAIL));
                                }
                            }
                        }

                    });
        } catch (Exception ignored) {

        }
    }


    /**
     * Post请求
     */
    public static void OkHttpPostOld(String url, final Map<String, String> params, final String tag) {
        try {
            params.put("function_id", SCMainActivity.function_id);
            params.put("ticket", Ticket.getFunctionTicket(SCMainActivity.function_id));
            try {
                params.put("xxbh", new SqliteHelper().rawQuery("select * from password4").get(0).get("schoolId"));
            } catch (Exception e) {
                params.put("xxbh", "");
            }
            try {
                params.put("userid", new SqliteHelper().rawQuery("select * from userinfo4").get(0).get("userid"));
            } catch (Exception e) {
                params.put("userid", Constants.number);
            }

            if (com.example.app3.utils.LogUtils.APP_DBG) {
                StringBuilder s = new StringBuilder();
                for (String key : params.keySet()) {
                    s.append(key).append("=").append(params.get(key)).append("&");
                }
                Log.d(Log.DEFAULT_TAG, "地址：" + url + "?" + s);
            }
            OkHttpUtils.post().url(url).params(params).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    EventBus.getDefault().post(new MessageEvent(HintTool.REQUESTFAIL, HintTool.NetWorkError));
                }

                @Override
                public void onResponse(String response, int id) {
                    if (JSONTool.Tips(response).equals("成功")) {
                        EventBus.getDefault().post(new MessageEvent(tag, response));
                    } else if (JSONTool.Tips(response).equals("身份验证失败")) {
                        EventBus.getDefault().post(new MessageEvent(HintTool.ReLogin, HintTool.ReLogin));
                    } else {
                        EventBus.getDefault().post(new MessageEvent(HintTool.REQUESTFAIL, HintTool.REQUESTFAIL));
                    }
                }
            });
        } catch (Exception ignored) {

        }
    }

    /**
     * Get请求
     */
    public static void OkHttpGet(String url, final String tag) {
        Log.d(Log.DEFAULT_TAG, "地址：" + url);
        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                EventBus.getDefault().post(new MessageEvent(tag, HintTool.NetWorkError));
            }

            @Override
            public void onResponse(String response, int id) {
                if (JSONTool.Code(response).equals(JSONTool.SUCCESS)) {
                    EventBus.getDefault().post(new MessageEvent(tag, response));
                } else {
                    EventBus.getDefault().post(new MessageEvent(tag, HintTool.REQUESTFAIL));
                }
            }
        });
    }

    /**
     * 上传文件
     */
    public static void OkHttpPostFile(String url, final File params, final String tag) {
        Log.d(Log.DEFAULT_TAG, "地址：" + url);
        OkHttpUtils.postFile().url(url).file(params).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                EventBus.getDefault().post(new MessageEvent(tag, HintTool.NetWorkError));
            }

            @Override
            public void onResponse(String response, int id) {
                if (JSONTool.Code(response).equals(JSONTool.SUCCESS)) {
                    EventBus.getDefault().post(new MessageEvent(tag, response));
                } else {
                    EventBus.getDefault().post(new MessageEvent(tag, "上传失败"));
                }
            }
        });
    }

    protected void post_file(final String url, final Map<String, Object> map, File file, String tag) {
        OkHttpClient client = new OkHttpClient();
        // form 表单形式上传
        MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (file != null) {
            // MediaType.parse() 里面是上传的文件类型。
            RequestBody body = RequestBody.create(MediaType.parse("image/*"), file);
            String filename = file.getName();
            // 参数分别为， 请求key ，文件名称 ， RequestBody
            requestBody.addFormDataPart("userface", file.getName(), body);
        }
        if (map != null) {
            // map 里面是请求中所需要的 key 和 value
            for (Map.Entry entry : map.entrySet()) {
                requestBody.addFormDataPart(valueOf(entry.getKey()), valueOf(entry.getValue()));
            }
        }
        Request request = new Request.Builder().url(url).post(requestBody.build()).tag(tag).build();
        // readTimeout("请求超时时间" , 时间单位);
        client.newBuilder().readTimeout(5000, TimeUnit.MILLISECONDS).build().newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });

    }
}
