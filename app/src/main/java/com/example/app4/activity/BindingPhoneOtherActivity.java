package com.example.app4.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.app3.tool.HintTool;
import com.example.app3.view.MyTitleView;
import com.example.app4.entity.GetPassWordEntity;
import com.example.app4.entity.StateEntity;
import com.example.app4.presenter.BindingPhoneOtherPresenter;
import com.example.app4.presenter.LoginUtil;
import com.example.app4.util.ActivityUtil;
import com.example.app4.util.DefaultUtil;
import com.example.app4.util.GetSchoolListUtil;
import com.example.app4.util.IsNull;
import com.example.smartclass.base.BaseRecyclerViewActivity;
import com.example.smartclass.eventbus.MessageEvent;
import com.example.smartclass.util.TagUtil;

import com.yhkj.cqgyxy.R;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import yh.app.tool.SqliteHelper;
import yh.app.utils.GsonImpl;
import 云华.智慧校园.工具.RSAHelper;

/**
 * Created by Administrator on 2018/2/27 0027.
 * <p>
 * 账号密码绑定手机
 */

public class BindingPhoneOtherActivity extends BaseRecyclerViewActivity {
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
    @BindView(R.id.binding_otherLogin_text)
    TextView bindingOtherLoginText;

    private String universityName = "";//学校名称
    private String login_id = "";

    private boolean canClick = false;
    private boolean isChangeBinding = false;//是否更换绑定


    @Override
    protected int getLayoutId() {
        return R.layout.activity_university_binding;
    }

    @Override
    protected void setTitle(Context context) {
        bindingTitle.setTitle(universityName, context);
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
        universityName = DefaultUtil.getDefaultSchool();
        try {
            login_id = getIntent().getExtras().getString("login_id");
        } catch (Exception e) {
            login_id = "";
        }
        bindingText.setVisibility(View.VISIBLE);
        bindingNumText.setVisibility(View.VISIBLE);
        bindingCountryCodeText.setVisibility(View.VISIBLE);
        bindingNumEd.setVisibility(View.VISIBLE);

        bindingText.setText("绑定手机号");
        bindingNumText.setText("手机号");
        bindingCountryCodeText.setText("+86");
        bindingNumEd.setHint("请输入手机号");
        bindingNextBtn.setText("下一步");

        bindingNumEd.addTextChangedListener(new MyTextWatcher(bindingNumEd));//输入监听
        bindingNumEd.setFilters(new InputFilter[]{new InputFilter.LengthFilter(13)});//最大输入长度

    }

    @Subscribe
    public void onEventMainThread(MessageEvent event) {
        dismissLoad();
        String result = (String) event.getMessage();
        Intent intent = null;
        if (ActivityUtil.isForeground(this))
            switch (event.getTag()) {
                case TagUtil.GetVerificationCodeTag:
                    bindingJudgeText.setText("手机号验证成功");
                    bindingJudgeText.setTextColor(ContextCompat.getColor(BindingPhoneOtherActivity.this, R.color.color_blue_3da8f5));
                    intent = new Intent(this, VerificationCodeOtherActivity.class);
                    intent.putExtra("login_id", login_id);
                    intent.putExtra("phoneNum", bindingNumEd.getText().toString());
                    break;
                case HintTool.REQUESTFAIL:
                    bindingJudgeText.setText(result);
                    bindingJudgeText.setTextColor(ContextCompat.getColor(BindingPhoneOtherActivity.this, R.color.redColor));
                    break;
            }
        if (intent != null) {
            startActivity(intent);
        }
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

    @OnClick({R.id.binding_clear_image, R.id.binding_next_btn, R.id.binding_otherLogin_text})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.binding_clear_image:
                bindingNumEd.setText("");
                break;
            case R.id.binding_next_btn:
                if (canClick) {
                    showLoad("");
                    String phoneNum = bindingNumEd.getText().toString().trim().replace(" ", "");
                    new BindingPhoneOtherPresenter(this).getVerificationCode(phoneNum, login_id);
                }
                break;
            case R.id.binding_otherLogin_text:
                Intent intent = new Intent(this, BindingOtherActivity.class);
                startActivity(intent);
                break;
        }
    }

    /**
     * 手机输入格式344
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
            if (s.toString().length() > 0) {
                bindingClearImage.setVisibility(View.VISIBLE);
            } else {
                bindingClearImage.setVisibility(View.GONE);
            }
            if (s.toString().length() == 13) {
                bindingJudgeText.setVisibility(View.VISIBLE);
                if (s.toString().startsWith("1")) {
                    bindingJudgeText.setText("手机号验证成功");
                    bindingJudgeText.setTextColor(ContextCompat.getColor(BindingPhoneOtherActivity.this, R.color.color_blue_3da8f5));
                    bindingNextBtn.setBackground(ContextCompat.getDrawable(BindingPhoneOtherActivity.this, R.drawable.border_shade_3da8f5));
                    canClick = true;
                } else {
                    bindingJudgeText.setText("手机号验证失败，请重新输入~");
                    bindingJudgeText.setTextColor(ContextCompat.getColor(BindingPhoneOtherActivity.this, R.color.redColor));
                    bindingNextBtn.setBackground(ContextCompat.getDrawable(BindingPhoneOtherActivity.this, R.drawable.border_shade_e5e9ee));
                }
            } else {
                bindingJudgeText.setVisibility(View.GONE);
                bindingNextBtn.setBackground(ContextCompat.getDrawable(BindingPhoneOtherActivity.this, R.drawable.border_shade_e5e9ee));
            }

            if (s.length() == 0) return;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < s.length(); i++) {
                if (i != 3 && i != 8 && s.charAt(i) == ' ') {
                    continue;
                } else {
                    sb.append(s.charAt(i));
                    if ((sb.length() == 4 || sb.length() == 9) && sb.charAt(sb.length() - 1) != ' ') {
                        sb.insert(sb.length() - 1, ' ');
                    }
                }
            }
            if (!sb.toString().equals(s.toString())) {
                int index = start + 1;
                if (sb.charAt(start) == ' ') {
                    if (before == 0) {
                        index++;
                    } else {
                        index--;
                    }
                } else {
                    if (before == 1) {
                        index--;
                    }
                }
                bindingNumEd.setText(sb.toString());
                bindingNumEd.setSelection(index);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    }


    private static final String TAG = "BindingPhoneActivity";

}
