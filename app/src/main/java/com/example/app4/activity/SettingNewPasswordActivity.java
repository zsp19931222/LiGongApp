package com.example.app4.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.app3.tool.HintTool;
import com.example.app3.view.MyTitleView;
import com.example.app4.entity.SettingPasswordEntity;
import com.example.app4.entity.UserMessageEntity;
import com.example.app4.presenter.LoginUtil;
import com.example.app4.presenter.NewUserLoginPresenter;
import com.example.app4.presenter.StartPresenter;
import com.example.app4.tool.GetSchoolNumTool;
import com.example.app4.util.ActivityUtil;
import com.example.app4.util.DefaultUtil;
import com.example.app4.util.RegexUtil;
import com.example.app4.util.StringUtil;
import com.example.smartclass.base.BaseRecyclerViewActivity;
import com.example.smartclass.eventbus.MessageEvent;
import com.example.smartclass.util.TagUtil;

import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.yhkj.cqgyxy.R;
import yh.app.utils.GsonImpl;

/**
 * Created by Administrator on 2018/7/13 0013.
 * 设置新密码
 */

public class SettingNewPasswordActivity extends BaseRecyclerViewActivity {
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
    @BindView(R.id.binding_clearNum_image)
    ImageView bindingClearNumImage;
    @BindView(R.id.binding_num_lin)
    LinearLayout bindingNumLin;
    @BindView(R.id.binding_line_image)
    ImageView bindingLineImage;
    @BindView(R.id.binding_num_text2)
    TextView bindingNumText2;
    @BindView(R.id.binding_show_text)
    TextView bindingShowText;
    @BindView(R.id.binding_num_ed2)
    EditText bindingNumEd2;
    @BindView(R.id.binding_clearNum_image2)
    ImageView bindingClearNumImage2;
    @BindView(R.id.binding_num_lin2)
    LinearLayout bindingNumLin2;
    @BindView(R.id.binding_line_image2)
    ImageView bindingLineImage2;
    @BindView(R.id.binding_error_btn)
    Button bindingErrorBtn;
    @BindView(R.id.binding_forget_btn)
    Button bindingForgetBtn;
    @BindView(R.id.binding_next_btn)
    Button bindingNextBtn;
    @BindView(R.id.binding_otherLogin_text)
    TextView bindingOtherLoginText;
    @BindView(R.id.binding_otherLogin_text1)
    TextView bindingOtherLoginText1;
    @BindView(R.id.binding_otherLogin_text2)
    TextView bindingOtherLoginText2;
    @BindView(R.id.binding_otherLogin_lin)
    LinearLayout bindingOtherLoginLin;
    @BindView(R.id.binding_error_text)
    TextView bindingErrorText;

    private String phoneNum;
    private String userid;
    private StartPresenter startPresenter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_university_other_binding;
    }

    @Override
    protected void setTitle(Context context) {
        bindingTitle.setTitle("设置新密码", context);
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

    @Override
    protected void init(Context context) {
        startPresenter = new StartPresenter(context);

        phoneNum = getIntent().getExtras().getString("mob");
        userid = getIntent().getExtras().getString("userid");
        bindingText.setText("设置新密码");
        bindingTipsText.setText("");
        bindingNumText.setText("设置新密码");

        bindingNumText2.setText("确认新密码");
        bindingShowText.setText("");

        bindingNumEd.setHint("请设置新密码");
        bindingNumEd2.setHint("请确认新密码");

        bindingNextBtn.setText("确定");

        bindingErrorText.setText("密码必须至少8个字符，而且同时包含字母和数字！");

        bindingNextBtn.setBackground(ContextCompat.getDrawable(this, R.drawable.border_shade_3da8f5));

        bindingNumEd.addTextChangedListener(new MyTextWatcher(bindingNumEd));
        bindingNumEd2.addTextChangedListener(new MyTextWatcher(bindingNumEd2));

        bindingNumEd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        bindingNumEd2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
    }

    @Override
    @Subscribe
    public void onEventMainThread(MessageEvent event) {
        String result = (String) event.getMessage();
        if (ActivityUtil.isForeground(this))
            switch (event.getTag()) {
            case TagUtil.newUserSetPasswordTag:
                    try {
                        SettingPasswordEntity entity = GsonImpl.get().toObject(result, SettingPasswordEntity.class);
                        LoginUtil.saveSchoolName_Phone_PassWord(DefaultUtil.getDefaultSchool(), StringUtil.takeOutSpacing(phoneNum), entity.getContent().getPassword(), GetSchoolNumTool.getSchoolNum());
                        LoginUtil.getUserMessage(this, new HashMap<String, String>());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                break;
            case TagUtil.GetMySelfListTag:
                    startPresenter.saveList(result);
                break;
            case TagUtil.GetNavigationListTag:
                    startPresenter.saveNavigation(result);
                    LoginUtil.intentToMain(this);
                    dismissLoad();
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
                    dismissLoad();
                    bindingErrorBtn.setText(result);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.binding_clearNum_image, R.id.binding_clearNum_image2, R.id.binding_next_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.binding_clearNum_image:
                bindingNumEd.setText("");
                break;
            case R.id.binding_clearNum_image2:
                bindingNumEd2.setText("");
                break;
            case R.id.binding_next_btn:
                bindingErrorBtn.setText("");
                boolean canClick;
                if (bindingNumEd.getText().toString().length() < 8) {
                    bindingErrorBtn.setText("密码至少8个字符，请重新输入~");
                    canClick = false;
                } else {
                    canClick = true;
                }
                boolean canClick1;
                if (!bindingNumEd2.getText().toString().equals(bindingNumEd.getText().toString())) {
                    bindingErrorBtn.setText("两次输入的密码不一致！");
                    canClick1 = false;
                } else {
                    canClick1 = true;
                }
                if (canClick && canClick1) {
                    if (RegexUtil.regex(bindingNumEd.getText().toString())) {
                        showLoad("");
                        new NewUserLoginPresenter(this).newUserSetPassword(bindingNumEd2.getText().toString(), userid, phoneNum);
                    } else {
                        bindingErrorBtn.setText("密码至少8个字符，而且同时包含字母和数字！");
                    }
                }
                break;
        }
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


}
