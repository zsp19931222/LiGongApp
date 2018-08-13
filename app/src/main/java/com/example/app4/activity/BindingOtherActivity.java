package com.example.app4.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.app3.tool.HintTool;
import com.example.app3.utils.GetChangePassWord;
import com.example.app3.view.MyTitleView;
import com.example.app4.entity.UserMessageEntity;
import com.example.app4.presenter.BindingOtherPresenter;
import com.example.app4.presenter.LoginUtil;
import com.example.app4.presenter.StartPresenter;
import com.example.app4.util.ActivityUtil;
import com.example.app4.util.DefaultUtil;
import com.example.smartclass.activity.BrowserActivity;
import com.example.smartclass.base.BaseRecyclerViewActivity;
import com.example.smartclass.eventbus.MessageEvent;
import com.example.smartclass.util.TagUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.yhkj.cqgyxy.R;

import yh.app.utils.GsonImpl;

/**
 * Created by Administrator on 2018/5/29 0029.
 * 其他绑定方式
 */

public class BindingOtherActivity extends BaseRecyclerViewActivity {
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
    @BindView(R.id.binding_line_image)
    ImageView bindingLineImage;
    @BindView(R.id.binding_num_text2)
    TextView bindingNumText2;
    @BindView(R.id.binding_num_ed2)
    EditText bindingNumEd2;
    @BindView(R.id.binding_line_image2)
    ImageView bindingLineImage2;
    @BindView(R.id.binding_next_btn)
    Button bindingNextBtn;
    @BindView(R.id.binding_otherLogin_text)
    TextView bindingOtherLoginText;
    @BindView(R.id.binding_show_text)
    TextView bindingShowText;
    @BindView(R.id.binding_forget_btn)
    Button bindingForgetBtn;
    @BindView(R.id.binding_error_btn)
    Button bindingErrorBtn;
    @BindView(R.id.binding_clearNum_image)
    ImageView bindingClearNumImage;
    @BindView(R.id.binding_num_lin)
    LinearLayout bindingNumLin;
    @BindView(R.id.binding_clearNum_image2)
    ImageView bindingClearNumImage2;
    @BindView(R.id.binding_num_lin2)
    LinearLayout bindingNumLin2;
    @BindView(R.id.binding_otherLogin_text1)
    TextView bindingOtherLoginText1;
    @BindView(R.id.binding_otherLogin_text2)
    TextView bindingOtherLoginText2;
    @BindView(R.id.binding_otherLogin_lin)
    LinearLayout bindingOtherLoginLin;

    private boolean canClick = false;

    private BindingOtherPresenter otherPresenter;
    private StartPresenter startPresenter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_university_other_binding;
    }

    @Override
    protected void setTitle(Context context) {
        bindingTitle.setTitle("账号密码登录", context);
        bindingTitle.setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        bindingTitle.setLeftImage(0);
    }

    @Override
    protected void loadRecyclerViewData(Context context) {

    }

    @Override
    protected void init(Context context) {

        startPresenter = new StartPresenter(context);
        otherPresenter = new BindingOtherPresenter(context);
        bindingOtherLoginLin.setVisibility(View.VISIBLE);

        bindingText.setText(DefaultUtil.getDefaultSchool());
        bindingTipsText.setText("请使用统一身份认证账号登录！");
        bindingNumText.setText("账号");
        bindingNumText2.setText("密码");
        bindingShowText.setText("显示");
        bindingNumEd.setHint("请输入学号/教职工号");
        bindingNextBtn.setText("登录");
        bindingOtherLoginText1.setText("手机号登录");
        bindingNumEd2.setHint("请输入密码");

        if (!DefaultUtil.isIsIntegrate()) {
            bindingForgetBtn.setText("忘记密码？");
            bindingOtherLoginText2.setText("新用户登录");
        } else {
            bindingForgetBtn.setText("");
            bindingOtherLoginText2.setText("忘记密码？");
        }
        bindingForgetBtn.setTextColor(ContextCompat.getColor(context, R.color.color_bbbbbb));

        bindingNumEd.addTextChangedListener(new MyTextWatcher(bindingNumEd));
        bindingNumEd2.addTextChangedListener(new MyTextWatcher(bindingNumEd2));
        bindingNumEd2.setTransformationMethod(PasswordTransformationMethod.getInstance());

        bindingNumEd.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        bindingNumEd2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    @Subscribe
    public void onEventMainThread(MessageEvent event) {
        String result = (String) event.getMessage();
        dismissLoad();
        if (ActivityUtil.isForeground(this))
            switch (event.getTag()) {
                case TagUtil.otherRegisterTag:
                    otherPresenter.intent2Where(result);
                    break;
                case TagUtil.GetMySelfListTag:
                    startPresenter.saveList(result);
                    break;
                case TagUtil.GetNavigationListTag:
                    startPresenter.saveNavigation(result);
                    LoginUtil.intentToMain(this);
                    break;
                case TagUtil.GetUserMessageTag:
                    try {
                        UserMessageEntity userMessageEntity = GsonImpl.get().toObject(result, UserMessageEntity.class);
                        LoginUtil.saveUserMessage(userMessageEntity, this);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case HintTool.REQUESTFAIL:
                    bindingErrorBtn.setText(result);
                    break;
            }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        EventBus.getDefault().post(new MessageEvent(HintTool.CloseStartPage, ""));
    }

    /**
     * 监听账号输入
     */
    private class MyTextWatcher implements TextWatcher {
        MyTextWatcher(EditText etPhone) {
            this.etPhone = etPhone;
        }

        private EditText etPhone;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            canClick = false;
            bindingErrorBtn.setText("");

            if (bindingNumEd.getText().toString().length() > 0) {
                bindingClearNumImage.setVisibility(View.VISIBLE);
            } else {
                bindingClearNumImage.setVisibility(View.GONE);
            }
            if (bindingNumEd2.getText().toString().length() > 0) {
                bindingClearNumImage2.setVisibility(View.VISIBLE);
            } else {
                bindingClearNumImage2.setVisibility(View.GONE);
            }
            if (bindingNumEd2.getText().toString().length() > 0 && bindingNumEd.getText().toString().length() > 0) {
                bindingNextBtn.setBackground(ContextCompat.getDrawable(BindingOtherActivity.this, R.drawable.border_shade_3da8f5));
                canClick = true;
            } else {
                bindingNextBtn.setBackground(ContextCompat.getDrawable(BindingOtherActivity.this, R.drawable.border_shade_e5e9ee));
            }
            //限制输入空格
            if (s.toString().contains(" ")) {
                String[] str = s.toString().split(" ");
                StringBuilder str1 = new StringBuilder();
                for (String aStr : str) {
                    str1.append(aStr);
                }
                etPhone.setText(str1.toString());
                etPhone.setSelection(start);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    }

    @OnClick({R.id.binding_show_text, R.id.binding_forget_btn, R.id.binding_next_btn, R.id.binding_otherLogin_text, R.id.binding_clearNum_image, R.id.binding_clearNum_image2, R.id.binding_otherLogin_text1, R.id.binding_otherLogin_text2})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.binding_show_text:
                if ("显示".equals(bindingShowText.getText().toString().trim())) {
                    bindingShowText.setText("隐藏");
                    bindingNumEd2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    bindingShowText.setText("显示");
                    bindingNumEd2.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                bindingNumEd2.setSelection(bindingNumEd2.getText().toString().length());
                break;
            case R.id.binding_forget_btn:
                intent = new Intent(this, ForGetPassWordActivity.class);
                break;
            case R.id.binding_next_btn:
                if (canClick) {
                    showLoad("");
                    otherPresenter.login(bindingNumEd.getText().toString(), bindingNumEd2.getText().toString());
                }
                break;
            case R.id.binding_otherLogin_text:
                break;
            case R.id.binding_clearNum_image:
                bindingNumEd.setText("");
                break;
            case R.id.binding_clearNum_image2:
                bindingNumEd2.setText("");
                break;
            case R.id.binding_otherLogin_text1:
                intent = new Intent(this, BindingPhoneActivity.class);
                intent.putExtra("universityName", DefaultUtil.getDefaultSchool());
                break;
            case R.id.binding_otherLogin_text2:
                if (DefaultUtil.isIsIntegrate()) {
                    intent = new Intent(this, BrowserActivity.class);
                    intent.putExtra("url",  GetChangePassWord.getForgetPasswordUrl());
                    intent.putExtra("title", "");
                } else {
                    intent = new Intent(this, NewUserLoginActivity.class);
                }
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }
}
