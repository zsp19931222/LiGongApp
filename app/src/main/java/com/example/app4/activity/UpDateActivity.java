package com.example.app4.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartclass.base.BaseRecyclerViewActivity;
import com.example.smartclass.eventbus.MessageEvent;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.yhkj.cqgyxy.R;

import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

public class UpDateActivity extends BaseRecyclerViewActivity {

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
        updateURL = getIntent().getExtras().getString("updateURL");
        hintpopBtnConfirm.setText("更新");
        hintpopTextHint.setText("1.优化了用户体验\n2.修改了已知bug");
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

    @OnClick({R.id.hintpop_btn_confirm, R.id.hintpop_rel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.hintpop_btn_confirm:
                new RxPermissions(this).request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(observer);
                break;
            case R.id.hintpop_rel:
                finish();
                overridePendingTransition(0, android.R.anim.fade_out);
                break;
        }
    }

    private String updateURL;

    private Observer<Boolean> observer = new Observer<Boolean>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            Toast.makeText(UpDateActivity.this, "下载失败，请稍后重试", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNext(Boolean o) {
            if (o) {
                Intent intent = new Intent(UpDateActivity.this, com.example.app3.activity.UpDateActivity.class);
                intent.putExtra("url", updateURL);
                startActivity(intent);
            } else {
                Toast.makeText(UpDateActivity.this, "SD卡下载权限被拒绝", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        setImmersiveStatusBar(true, getResources().getColor(R.color.white));
    }
}
