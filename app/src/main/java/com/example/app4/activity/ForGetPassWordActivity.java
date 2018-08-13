package com.example.app4.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.app3.tool.HintTool;
import com.example.app3.view.MyTitleView;
import com.example.app4.presenter.ForgetPasswordPresenter;
import com.example.app4.util.ActivityUtil;
import com.example.smartclass.base.BaseRecyclerViewActivity;
import com.example.smartclass.eventbus.MessageEvent;
import com.example.smartclass.util.TagUtil;

import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.yhkj.cqgyxy.R;

/**
 * Created by Administrator on 2018/5/29 0029.
 * 忘记密码
 */

public class ForGetPassWordActivity extends BaseRecyclerViewActivity {
    @BindView(R.id.binding_title)
    MyTitleView bindingTitle;
    @BindView(R.id.binding_text)
    TextView bindingText;
    @BindView(R.id.binding_tips_text)
    TextView bindingTipsText;
    @BindView(R.id.binding_num_text)
    TextView bindingNumText;
    @BindView(R.id.binding_country_code_text)
    TextView bindingCountryCodeText;
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
    @BindView(R.id.binding_judge_text)
    TextView bindingJudgeText;
    @BindView(R.id.binding_next_btn)
    Button bindingNextBtn;
    @BindView(R.id.binding_otherLogin_text)
    TextView bindingOtherLoginText;
    private boolean canClick = false;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_university_binding;
    }

    @Override
    protected void setTitle(Context context) {
        bindingTitle.setTitle("忘记密码", context);
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
        bindingNumText.setVisibility(View.VISIBLE);
        bindingCountryCodeText.setVisibility(View.VISIBLE);
        bindingNumEd.setVisibility(View.VISIBLE);

        bindingText.setText("输入您的手机号");
        bindingNumText.setText("手机号");
        bindingCountryCodeText.setText("+86");
        bindingNumEd.setHint("请输入手机号");
        bindingNextBtn.setText("下一步");

        bindingNumEd.addTextChangedListener(new ForGetPassWordActivity.MyTextWatcher(bindingNumEd));//输入监听
        bindingNumEd.setFilters(new InputFilter[]{new InputFilter.LengthFilter(13)});//最大输入长度
    }

    @Override
    @Subscribe
    public void onEventMainThread(MessageEvent event) {
        dismissLoad();
        String result = (String) event.getMessage();
        if (ActivityUtil.isForeground(this))
            switch (event.getTag()) {
                case TagUtil.forgetPasswordGetCodeTag:
                    bindingJudgeText.setText("手机号验证成功");
                    bindingJudgeText.setTextColor(ContextCompat.getColor(this, R.color.color_blue_3da8f5));
                    Intent intent = new Intent(this, ForgetPasswordVerificationCodeActivity.class);
                    intent.putExtra("phoneNum", bindingNumEd.getText().toString());
                    startActivity(intent);
                    break;
                case HintTool.REQUESTFAIL:
                    bindingJudgeText.setText("手机号码未绑定账号！");
                    bindingJudgeText.setTextColor(ContextCompat.getColor(this, R.color.redColor));
                    break;
            }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.binding_next_btn,R.id.binding_clear_image})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.binding_next_btn:
                if (canClick)
                    showLoad("");
                new ForgetPasswordPresenter(this).forgetPasswordGetCode(bindingNumEd.getText().toString().replace(" ", ""));
                break;
            case R.id.binding_clear_image:
                bindingNumEd.setText("");
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
                    bindingJudgeText.setTextColor(ContextCompat.getColor(ForGetPassWordActivity.this, R.color.color_blue_3da8f5));
                    bindingNextBtn.setBackground(ContextCompat.getDrawable(ForGetPassWordActivity.this, R.drawable.border_shade_3da8f5));
                    canClick = true;
                } else {
                    bindingJudgeText.setText("手机号验证失败，请重新输入~");
                    bindingJudgeText.setTextColor(ContextCompat.getColor(ForGetPassWordActivity.this, R.color.redColor));
                    bindingNextBtn.setBackground(ContextCompat.getDrawable(ForGetPassWordActivity.this, R.drawable.border_shade_e5e9ee));
                }
            } else {
                bindingJudgeText.setVisibility(View.GONE);
                bindingNextBtn.setBackground(ContextCompat.getDrawable(ForGetPassWordActivity.this, R.drawable.border_shade_e5e9ee));
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
}
