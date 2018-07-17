package com.example.app4.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.app3.tool.HintTool;
import com.example.app3.view.MyTitleView;
import com.example.app4.api.GetAppUrl;
import com.example.app4.api.OkHttpUtil;
import com.example.app4.tool.UserMessageTool;
import com.example.app4.util.StringUtil;
import com.example.smartclass.base.BaseRecyclerViewActivity;
import com.example.smartclass.eventbus.MessageEvent;
import com.example.smartclass.util.TagUtil;

import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import yh.app.appstart.lg.R;
import yh.app.tool.MD5;

/**
 * Created by Administrator on 2018/2/27 0027.
 * <p>
 * 验证码密码
 */

public class VerificationPasswordActivity extends BaseRecyclerViewActivity {
    @BindView(R.id.binding_title)
    MyTitleView bindingTitle;
    @BindView(R.id.binding_text)
    TextView bindingText;
    @BindView(R.id.binding_tips_text)
    TextView bindingTipsText;
    @BindView(R.id.binding_num_text)
    TextView bindingNumText;
    @BindView(R.id.binding_num_ed)
    EditText bindingNumEd;
    @BindView(R.id.binding_clear_image)
    ImageView bindingClearImage;
    @BindView(R.id.binding_countdown_text)
    TextView bindingCountdownText;
    @BindView(R.id.binding_num_lin)
    LinearLayout bindingNumLin;
    @BindView(R.id.binding_line_image)
    ImageView bindingLineImage;
    @BindView(R.id.binding_next_btn)
    Button bindingNextBtn;
    @BindView(R.id.binding_country_code_text)
    TextView bindingCountryCodeText;
    @BindView(R.id.binding_judge_text)
    TextView bindingJudgeText;

    public VerificationPasswordActivity() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_university_binding;
    }

    @Override
    protected void setTitle(Context context) {
        bindingTitle.setTitle("修改手机号", context);
        bindingTitle.setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void loadRecyclerViewData(Context context) {

    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void init(Context context) {
        bindingText.setVisibility(View.VISIBLE);
        bindingTipsText.setVisibility(View.VISIBLE);
        bindingNumText.setVisibility(View.VISIBLE);
        bindingNumEd.setVisibility(View.VISIBLE);

        bindingText.setText("请输入登录密码");
        bindingTipsText.setText("");
        bindingNumText.setText("密码");
        bindingNumEd.setHint("请输入密码");
        bindingNextBtn.setText("下一步");

        bindingNextBtn.setBackground(ContextCompat.getDrawable(this, R.drawable.border_shade_3da8f5));
        bindingNumEd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick({R.id.binding_clear_image, R.id.binding_next_btn, R.id.binding_countdown_text})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.binding_clear_image:
                bindingNumEd.setText("");
                break;
            case R.id.binding_next_btn:
                VerificationPasssword();
                break;
            case R.id.binding_countdown_text:
                break;
        }
    }


    @Subscribe
    public void onEventMainThread(MessageEvent event) {
        bindingJudgeText.setText("");
        dismissLoad();
        switch (event.getTag()) {
            case TagUtil.VerificationPassswordTag:
                Intent intent = new Intent(this, BindingNewPhoneActivity.class);
                startActivity(intent);
                break;
            case HintTool.REQUESTFAIL:
                bindingJudgeText.setText("密码错误，请核对后重新输入。");
                bindingJudgeText.setVisibility(View.VISIBLE);
                bindingJudgeText.setTextColor(ContextCompat.getColor(this, R.color.redColor));
                break;
        }
    }

    private void VerificationPasssword() {
        Map<String, String> map = new HashMap<>();
        map.put("userid", UserMessageTool.getUserId());
        map.put("password", MD5.MD5(StringUtil.takeOutSpacing(bindingNumEd.getText().toString())));
        OkHttpUtil.OkHttpPost(GetAppUrl.UIA.VerificationPasssword.getUrl(), map, TagUtil.VerificationPassswordTag, this);
    }

}
