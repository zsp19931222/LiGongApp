package com.example.app4.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.app3.tool.HintTool;
import com.example.app4.activity.LogoutActivity;
import com.example.app4.util.ReLoginUtil;
import com.example.smartclass.eventbus.MessageEvent;
import com.example.smartclass.util.TagUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import yh.tool.widget.LoadDiaog;
import 云华.智慧校园.功能.LoginOut;

/**
 * Created by Leahy on 2017/6/18.
 */

public abstract class BaseFragment extends Fragment {

    private View view;
    public Activity activity;
    Unbinder unbinder;
    private LoadDiaog loadDiaog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        loadDiaog = new LoadDiaog(activity);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (null != parent) {
                parent.removeView(view);
            }
        } else {
            view = inflater.inflate(getLayoutId(), null);
            unbinder = ButterKnife.bind(this, view);
            initView();
            EventBus.getDefault().register(this);
        }

        return view;
    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void onEventMainThread(MessageEvent event);

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void startAcivity(Class classNamse) {
        startActivity(new Intent(activity, classNamse));
    }

    public void showLoad(String hint) {
        loadDiaog.setTitle(hint);
        loadDiaog.show();
    }

    public void dismissLoad() {
        if (loadDiaog.isShowing())
            loadDiaog.dismiss();
    }

    @Subscribe
    public void reLogin(MessageEvent event) {
        switch (event.getTag()) {
            case HintTool.ReLogin:
                new ReLoginUtil(getActivity()).intent();
                break;
        }
    }
}
