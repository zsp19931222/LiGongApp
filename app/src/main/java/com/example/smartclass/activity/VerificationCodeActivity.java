package com.example.smartclass.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Html;
import android.view.KeyEvent;
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

/**
 * Created by Administrator on 2018/1/19 0019.
 */

public class VerificationCodeActivity extends BaseRecyclerViewActivity {
    @BindView(R.id.pop_code_hint_text)
    TextView popCodeHintText;
    @BindView(R.id.pop_code_code_text)
    TextView popCodeCodeText;
    @BindView(R.id.pop_code_time_text)
    TextView popCodeTimeText;

    private int time;//倒计时时间
    private String verificationCode;//验证码

    private boolean isSend = false;

    @Override
    protected int getLayoutId() {
        return R.layout.popup_verification_code;
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
        time = getIntent().getExtras().getInt("time");
        verificationCode = getIntent().getExtras().getString("code");
        popCodeCodeText.setText(verificationCode + "");
        new CountDownTimer(time * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (!isSend) {
                    EventBus.getDefault().post(new MessageEvent(TagUtil.RefreshStudentStateTag, HintTool.StartSignIn));
                    isSend = true;
                }
                String s = "结束<font color='#55b2f5'>（" + millisUntilFinished / 1000 + "s）</font>";
                popCodeTimeText.setText(Html.fromHtml(s));
            }

            @Override
            public void onFinish() {
                EventBus.getDefault().post(new MessageEvent(TagUtil.RefreshStudentStateTag, HintTool.EndSignIn));
                finish();
            }
        }.start();
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }
}
