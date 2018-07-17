package com.example.app4.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.app3.tool.HintTool;
import com.example.app3.utils.GlideLoadUtils;
import com.example.smartclass.eventbus.MessageEvent;

import org.greenrobot.eventbus.EventBus;

import yh.app.appstart.lg.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import yh.app.utils.StatusBarUtil;

/**
 * Created by Administrator on 2018/2/27 0027.
 * 广告页
 */

public class ADActivity extends Activity {
    @BindView(R.id.sp_logo_image)
    ImageView spLogoImage;
    @BindView(R.id.sp_advertising_text)
    TextView spAdvertisingText;
    @BindView(R.id.sp_logo_rel)
    RelativeLayout spLogoRel;
    @BindView(R.id.sp_rel)
    LinearLayout spRel;

    private boolean ifFinish = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);
        ButterKnife.bind(this);
        String url = getIntent().getExtras().getString("imageUrl");
        GlideLoadUtils.getInstance().glideLoad(this, url, spLogoImage, 0);
        new CountDownTimer(3000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                intent();
            }
        }.start();
    }
    @OnClick(R.id.sp_advertising_text)
    public void onViewClicked() {
        intent();
    }

    private void intent() {
        if (!ifFinish) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        ifFinish = true;
        EventBus.getDefault().post(new MessageEvent(HintTool.CloseStartPage, ""));
    }
}
