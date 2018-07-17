package com.example.app4.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.app3.activity.BrowserActivity;
import com.example.app3.tool.Tool;
import com.example.app4.entity.MySelfEntity;
import com.example.app4.util.FunctionIntentUtil;
import com.example.jpushdemo.ExampleApplication;

import org.androidpn.push.Constants;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import yh.app.tool.MD5;
import yh.app.utils.VolleyInterface;
import yh.app.utils.VolleyRequest;
import 云华.智慧校园.工具.RSAHelper;
import 云华.智慧校园.工具._链接地址导航;

/**
 * Created by Administrator on 2018/4/20 0020.
 *
 * 我的界面方法
 */

public class MySelfPresenter {
    private Context context;
    private Activity activity;

    public MySelfPresenter(Context context) {
        this.context = context;
        activity= (Activity) context;
    }
    /**
     * 控件跳转
     */
    public void intentWidget(final Context context, View view, final MySelfEntity.AllTagsListBean lxBean) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FunctionIntentUtil<>(lxBean, context).intent2();
            }
        });
    }
}
