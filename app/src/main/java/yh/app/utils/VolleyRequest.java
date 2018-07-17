package yh.app.utils;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.example.jpushdemo.ExampleApplication;

import java.util.Map;

import yh.app.logTool.Log;

/**
 * Volloey 封装
 */

public abstract class VolleyRequest {

    /**
     * VOLLEY GET 请求
     *
     * @param url 请求地址
     * @param vif 请求监听
     */
    public static void RequestGet(String url, VolleyInterface vif) {
        if (com.example.app3.utils.LogUtils.APP_DBG) {

            Log.d(Log.DEFAULT_TAG, "地址：" + url);
        }
        MyStringRequest stringRequest = new MyStringRequest(Request.Method.GET, url, vif.loadingListener(), vif.errorListener());
        stringRequest.setShouldCache(false);
// 设置超时时间 1,设置当前超时时间，2最大重试次数，3超时时间乘积因子
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(2000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        ExampleApplication.getInstance().getRequestQueue().add(stringRequest);

    }

    /**
     * VOLLEY POST 请求
     *
     * @param url    请求地址
     * @param params 键值对
     * @param vif    请求监听
     */

    public static void RequestPost(String url, final Map<String, String> params, VolleyInterface vif) {
        if (com.example.app3.utils.LogUtils.APP_DBG) {
            String s = "";
            for (String key : params.keySet()) {
                s += key + "=" + params.get(key) + "&";
            }
            Log.d(Log.DEFAULT_TAG, "地址：" + url + "?" + s);
        }
        MyStringRequest stringRequest = new MyStringRequest(Request.Method.POST, url, vif.loadingListener(), vif.errorListener()) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };
        stringRequest.setShouldCache(false);
        // 设置超时时间 1,设置当前超时时间，2最大重试次数，3超时时间乘积因子
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(2000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        ExampleApplication.getInstance().getRequestQueue().add(stringRequest);
    }


}
