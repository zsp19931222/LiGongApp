package yh.app.utils;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.yunhuakeji.app.utils.IsNull;
import com.yunhuakeji.app.utils.JsonTools;

import android.text.TextUtils;

import org.androidpn.push.Constants;

import yh.app.logTool.Log;

/**
 * Volley 请求监听
 */

public abstract class VolleyInterface {
    // Volley监听类
    public Response.Listener<String> mListener;
    public Response.ErrorListener mErrorListener;

    public VolleyInterface() {
        loadingListener();

        errorListener();
    }

    /**
     * 链接服务器成功
     */
    public abstract void onMySuccess(String result);

    /**
     * 链接服务器失败
     */
    public abstract void onMyError(VolleyError error);

    // 实例化成功监听传人成功返回数据
    public Response.Listener<String> loadingListener() {
        if (null == mListener) {
            this.mListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    String ticket = 云华.智慧校园.工具.JsonTools.getString(JsonTools.createJsonObject(response), "ticket");

                    if (IsNull.isNotNull(ticket)) {
                        Constants.ticket = ticket;
                    } else {

                        try {
                            JSONObject object = new JSONObject(response);
                            Constants.ticket = object.getString("ticket");
                            if (IsNull.isNotNull(ticket)) {
                                Constants.ticket = ticket;
                            }
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    onMySuccess(response);
                    Log.d("data", response + "");
                }
            };
        }
        return mListener;
    }

    // 实例化失败监听传人失败返回值
    public Response.ErrorListener errorListener() {
        if (null == mErrorListener) {
            this.mErrorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    onMyError(error);
                    Log.d("data", error + "");
                }
            };
        }
        return this.mErrorListener;
    }
}
