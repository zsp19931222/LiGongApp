package com.example.app4.activity;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;

import com.example.app4.presenter.StartPresenter;
import com.example.smartclass.base.BaseRecyclerViewActivity;
import com.example.smartclass.eventbus.MessageEvent;
import yh.app.appstart.lg.R;

import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import yh.app.tool.GetConfig;
import 云华.智慧校园.工具._链接地址导航;

/**
 * Created by Administrator on 2018/4/2 0002.
 * 启动展示也
 */

public class LaunchActivity extends BaseRecyclerViewActivity {
    @BindView(R.id.btn_update)
    Button btnUpdate;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_launch;
    }

    @Override
    protected void setTitle(Context context) {

    }

    @Override
    protected void loadRecyclerViewData(Context context) {

    }

    @Override
    protected void init(Context context) {
    }

    @Override
    @Subscribe
    public void onEventMainThread(MessageEvent event) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
