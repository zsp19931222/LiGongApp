package com.example.smartclass.base;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.app3.tool.HintTool;
import com.example.app4.base.CompatStatusBarActivity;
import com.example.app4.util.AndroidBug5497Workaround;
import com.example.app4.util.ReLoginUtil;
import com.example.smartclass.eventbus.MessageEvent;
import com.example.smartclass.util.TagUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;
import com.yhkj.cqgyxy.R;
import yh.tool.widget.LoadDiaog;

/**
 * Created by Administrator on 2018/1/4 0004.
 */

public abstract class BaseRecyclerViewActivity extends CompatStatusBarActivity {
    public Activity activity;
    public LoadDiaog loadDiaog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        loadDiaog = new LoadDiaog(activity);
        setStatusBar();

        setContentView(getLayoutId());
        ButterKnife.bind(this);
        init(this);
        setTitle(this);
        loadRecyclerViewData(this);
        EventBus.getDefault().register(this);
        AndroidBug5497Workaround.assistActivity(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        OkHttpUtils.getInstance().cancelTag(activity);
    }

    protected abstract int getLayoutId();


    protected abstract void setTitle(Context context);

    protected abstract void loadRecyclerViewData(Context context);

    protected abstract void init(Context context);

    @Subscribe
    protected abstract void onEventMainThread(MessageEvent event);

    public void showLoad(String hint) {
        loadDiaog.setTitle(hint);

        loadDiaog.show();

    }

    public void dismissLoad() {
        if (loadDiaog.isShowing())
            loadDiaog.dismiss();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.fontScale != 1)//非默认值
            getResources();
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        if (res.getConfiguration().fontScale != 1) {//非默认值
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();//设置默认
            res.updateConfiguration(newConfig, res.getDisplayMetrics());
        }
        return res;
    }

    /**
     * 设置状态栏颜色
     */
    protected void setStatusBar() {
        setImmersiveStatusBar(true, getResources().getColor(R.color.white));
//        StatusBarUtil.setColor(this,
//                getResources().getColor(R.color.white), 1);
    }


    @Subscribe
    public void reLogin(MessageEvent event) {
        switch (event.getTag()) {
            case HintTool.ReLogin:
                new ReLoginUtil(this).intent();
                break;
            case HintTool.CancelRequest:
                OkHttpUtils.getInstance().cancelTag(activity);
                break;
            case TagUtil.ChangePhoneBindingSuccess:
                activity.finish();
                break;
        }
    }
}
