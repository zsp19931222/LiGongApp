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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.app3.tool.HintTool;
import com.example.app3.view.MyTitleView;
import com.example.app4.entity.OtherLoginEntity;
import com.example.app4.entity.UserMessageEntity;
import com.example.app4.presenter.LoginUtil;
import com.example.app4.presenter.NewUserLoginPresenter;
import com.example.app4.presenter.SchoolIdentificationPresenter;
import com.example.app4.presenter.StartPresenter;
import com.example.app4.util.ActivityUtil;
import com.example.app4.util.DefaultUtil;
import com.example.smartclass.base.BaseRecyclerViewActivity;
import com.example.smartclass.eventbus.MessageEvent;
import com.example.smartclass.util.TagUtil;

import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.yhkj.cqgyxy.R;
import yh.app.tool.SqliteHelper;
import yh.app.utils.GsonImpl;

/**
 * Created by Administrator on 2018/5/29 0029.
 * 新用户学校身份认证
 */

public class NewSchoolIdentificationActivity extends BaseRecyclerViewActivity {
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

    private boolean canClick = false;

    private String mob;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_university_other_binding;
    }

    @Override
    protected void setTitle(Context context) {
        bindingTitle.setTitle("学校身份认证", context);
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
        mob = getIntent().getExtras().getString("mob");
        bindingText.setText(DefaultUtil.getDefaultSchool());
        bindingTipsText.setText("个人信息核对，以便于我们对您账户的安全验证。");
        bindingNumText.setText("账号");

        bindingNumText2.setText("证件号");
        bindingShowText.setText("");

        bindingNumEd.setHint("请输入学号/教职工号");
        bindingNumEd2.setHint("请输入您的身份证号码");

        bindingNextBtn.setText("下一步");

        bindingErrorBtn.setText("尾数字母为X，请照常输入大写字母X。");
        bindingErrorBtn.setTextColor(ContextCompat.getColor(context, R.color.color_bbbbbb));

        bindingNumEd.addTextChangedListener(new MyTextWatcher(bindingNumEd));
        bindingNumEd2.addTextChangedListener(new MyTextWatcher(bindingNumEd2));
        bindingNumEd2.setTransformationMethod(PasswordTransformationMethod.getInstance());

        bindingNumEd2.setInputType(InputType.TYPE_CLASS_TEXT);
    }

    @Override
    @Subscribe
    public void onEventMainThread(MessageEvent event) {
        String result = (String) event.getMessage();
        dismissLoad();
        if (ActivityUtil.isForeground(this))
            switch (event.getTag()) {
                case HintTool.REQUESTFAIL:
                    bindingErrorBtn.setTextColor(ContextCompat.getColor(this, R.color.color_ef5a53));
                    bindingErrorBtn.setText("账号或身份证号错误，请重新输入！");
                    break;
                case TagUtil.newUserUserInfoTag:
                    bindingErrorBtn.setText("");
                    Intent intent = new Intent(this, SettingNewPasswordActivity.class);
                    intent.putExtra("mob", mob);
                    intent.putExtra("userid", bindingNumEd.getText().toString());
                    startActivity(intent);
                    break;
            }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
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
                bindingNextBtn.setBackground(ContextCompat.getDrawable(NewSchoolIdentificationActivity.this, R.drawable.border_shade_3da8f5));
                canClick = true;
            } else {
                bindingNextBtn.setBackground(ContextCompat.getDrawable(NewSchoolIdentificationActivity.this, R.drawable.border_shade_e5e9ee));
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

    @OnClick({R.id.binding_show_text, R.id.binding_forget_btn, R.id.binding_next_btn, R.id.binding_otherLogin_text, R.id.binding_clearNum_image, R.id.binding_clearNum_image2})
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
                showLoad("");
                new NewUserLoginPresenter(this).schoolIdentification(bindingNumEd2.getText().toString(), bindingNumEd.getText().toString());
                break;
            case R.id.binding_otherLogin_text:
                break;
            case R.id.binding_clearNum_image:
                bindingNumEd.setText("");
                break;
            case R.id.binding_clearNum_image2:
                bindingNumEd2.setText("");
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }
}
