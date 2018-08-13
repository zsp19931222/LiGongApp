package com.example.app4.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.app3.view.MyTitleView;
import com.example.app4.presenter.LoginUtil;
import com.example.smartclass.base.BaseRecyclerViewActivity;
import com.example.smartclass.eventbus.MessageEvent;
import com.example.smartclass.util.TagUtil;
import com.yhkj.cqgyxy.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import yh.app.utils.ToastUtils;

/**
 * Created by Administrator on 2018/2/28 0028.
 */

public class ChangePhoneSuccessActivity extends BaseRecyclerViewActivity {
    @BindView(R.id.change_phone_success_title)
    MyTitleView changePhoneSuccessTitle;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_change_phone_success;
    }

    @Override
    protected void setTitle(final Context context) {
        changePhoneSuccessTitle.setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new MessageEvent(TagUtil.ChangePhoneBindingSuccess, ""));
                finish();
            }
        });
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

    @OnClick(R.id.change_phone_success_next_btn)
    public void onViewClicked() {
//        Intent intent = new Intent(this, MainActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
        EventBus.getDefault().post(new MessageEvent(TagUtil.ChangePhoneBindingSuccess, ""));
        finish();
    }
}
