package com.example.app4.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.app3.view.MyTitleView;
import com.example.smartclass.base.BaseRecyclerViewActivity;
import com.example.smartclass.eventbus.MessageEvent;

import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.yhkj.cqgyxy.R;

public class QRResultActivity extends BaseRecyclerViewActivity {
    @BindView(R.id.qr_result_title)
    MyTitleView qrResultTitle;
    @BindView(R.id.qr_result_text)
    TextView qrResultText;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_qr_result;
    }

    @Override
    protected void setTitle(Context context) {
        qrResultTitle.setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void loadRecyclerViewData(Context context) {

    }

    @Override
    protected void init(Context context) {
        String result = getIntent().getExtras().getString("result");
        qrResultText.setText(result);
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
