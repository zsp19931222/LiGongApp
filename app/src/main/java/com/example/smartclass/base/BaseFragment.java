package com.example.smartclass.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import yh.tool.widget.LoadDiaog;

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
        }

        return view;
    }

    protected abstract int getLayoutId();

    protected abstract void initView();

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
}
