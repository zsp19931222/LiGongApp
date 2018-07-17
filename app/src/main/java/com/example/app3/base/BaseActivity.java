package com.example.app3.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;

import com.example.app4.base.CompatStatusBarActivity;

import yh.app.activitytool.ActivityPortrait;

/**
 * <p>
 * 已执行requestWindowFeature(Window.FEATURE_NO_TITLE),故不需要手动取消系统标题栏
 * </p>
 * <p>
 * 该类的执行流程:
 * </p>
 * <p>
 * initActivityView() -> initView() -> initData() -> initAction()
 * </p>
 *
 * @author Administrator
 */
public abstract class BaseActivity extends CompatStatusBarActivity {
    public static Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        context = this;
        initActivityView();
        initView();
        initData();
        initAction();
    }

    public Context getContext() {
        return this;
    }

    /**
     * 初始化activity主视图
     */
    protected abstract void initActivityView();

    /**
     * 初始化控件
     */
    protected abstract void initView();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 绑定事件
     */
    protected abstract void initAction();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        context = null;
    }
}
