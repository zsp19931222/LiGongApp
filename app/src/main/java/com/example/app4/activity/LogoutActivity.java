package com.example.app4.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.TextView;

import com.example.app3.tool.HintTool;
import com.example.smartclass.base.BaseRecyclerViewActivity;
import com.example.smartclass.eventbus.MessageEvent;
import com.example.smartclass.util.TagUtil;
import com.yhkj.cqgyxy.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import 云华.智慧校园.功能.LoginOut;

/**
 * Created by Administrator on 2018/1/19 0019.
 */

public class LogoutActivity extends BaseRecyclerViewActivity {

    @BindView(R.id.hintpop_text_hint)
    TextView hintpopTextHint;
    @BindView(R.id.hintpop_btn_confirm)
    Button hintpopBtnConfirm;

    @Override
    protected int getLayoutId() {
        return R.layout.popup_logout;
    }

    @Override
    protected void setTitle(Context context) {

    }

    @Override
    protected void loadRecyclerViewData(Context context) {

    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void init(Context context) {
        hintpopTextHint.setText(HintTool.ReLogin);
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
        setImmersiveStatusBar(true, getResources().getColor(R.color.color_66000000));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }

    @OnClick(R.id.hintpop_btn_confirm)
    public void onViewClicked() {
        new LoginOut().doLoginOut(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        setImmersiveStatusBar(true, getResources().getColor(R.color.white));
    }
}
