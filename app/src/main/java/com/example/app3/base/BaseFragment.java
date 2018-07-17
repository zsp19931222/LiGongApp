package com.example.app3.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
	// TODO Auto-generated method stub
	return inflater.inflate(initContentView(), container, false);
    }

    /**
     * 初始化activity主视图
     */
    public abstract int initContentView();

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
}
