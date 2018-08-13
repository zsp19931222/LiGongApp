package com.example.app4.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.app3.tool.HintTool;
import com.example.app4.entity.ADEntity;
import com.example.app4.entity.UserMessageEntity;
import com.example.app4.presenter.LoginUtil;
import com.example.app4.presenter.StartPresenter;
import com.example.app4.util.ActivityUtil;
import com.example.app4.util.DefaultUtil;
import com.example.app4.util.ReLoginUtil;
import com.example.smartclass.eventbus.MessageEvent;
import com.example.smartclass.util.TagUtil;

import com.yhkj.cqgyxy.R;

import org.androidpn.push.Constants;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import yh.app.utils.GsonImpl;
import yh.app.utils.StatusBarUtil;
import yh.app.utils.ToastUtils;

/**
 * Created by Administrator on 2018/5/2 0002.
 * 启动页
 */

public class StartActivity extends Activity {

    private static final String TAG = "StartActivity";

    private String url = "";//广告地址
    private StartPresenter startPresenter;

    @Subscribe
    public void onEventMainThread(MessageEvent event) {
        String result = (String) event.getMessage();
        if (ActivityUtil.isForeground(this))
            switch (event.getTag()) {
                case HintTool.CloseStartPage:
                    finish();
                    break;
                case TagUtil.GetADImageTag:
                    ADEntity adEntity = GsonImpl.get().toObject(result, ADEntity.class);
                    url = adEntity.getContent().get(0).getImg();
                    LoginUtil.getUserMessage(this, new HashMap<String, String>());
                    break;
                case TagUtil.GetNavigationListTag:
                    startPresenter.saveNavigation(result);
                    if (url.equals("") || url == null) {
                        LoginUtil.intentToMain(this);
                    } else {
                        LoginUtil.intentToADActivity(this, url);
                    }
                    break;
                case TagUtil.GetUserMessageTag:
                    try {
                        UserMessageEntity userMessageEntity = GsonImpl.get().toObject(result, UserMessageEntity.class);
                        if (url.equals("") || url == null) {
                            LoginUtil.saveUserMessage(userMessageEntity, this);
                        } else {
                            LoginUtil.saveUserMessage2ADActivity(userMessageEntity, this, url);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case TagUtil.GetMySelfListTag:
                    startPresenter.saveList(result);
                    break;
                case HintTool.REQUESTFAIL:
                    Intent intent = new Intent(this, BindingOtherActivity.class);
                    intent.putExtra("universityName", DefaultUtil.getDefaultSchool());
                    Constants.xxmc = DefaultUtil.getDefaultSchool();
                    startActivity(intent);
                    ToastUtils.Toast(this, "网络异常，请稍后重试。");
                    break;
                case HintTool.ReLogin:
                    new ReLoginUtil(this).intent();
                    break;
            }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);

        startPresenter = new StartPresenter(this);
        startPresenter.intent2Where();
        overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
