package com.example.app4.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import com.example.app4.entity.UserMessageEntity;
import com.example.app4.presenter.BindingPhoneOtherPresenter;
import com.example.app4.presenter.LoginUtil;
import com.example.app4.presenter.StartPresenter;
import com.example.app4.util.ActivityUtil;
import com.example.app4.util.DefaultUtil;
import com.example.app4.util.StringUtil;
import com.example.smartclass.base.BaseRecyclerViewActivity;
import com.example.smartclass.eventbus.MessageEvent;
import com.example.smartclass.util.TagUtil;

import yh.app.appstart.lg.R;

import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import yh.app.tool.SqliteHelper;
import yh.app.utils.GsonImpl;

/**
 * Created by Administrator on 2018/2/27 0027.
 * <p>
 * 账号密码登录验证码页面
 */

public class VerificationCodeOtherActivity extends BaseRecyclerViewActivity {
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

    private String universityName = "";//学校名称

    private boolean canClick = false;


    private String phoneNum;
    private String login_id;
    /**
     * 获取用户基本信息
     */
    private Map<String, String> getUserMessageMap;


    private static final String TAG = "VerificationCodeActivit";
    private StartPresenter startPresenter;
    private BindingPhoneOtherPresenter phoneOtherPresenter;

    public VerificationCodeOtherActivity() {
    }

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
        startPresenter = new StartPresenter(context);
        phoneOtherPresenter = new BindingPhoneOtherPresenter(context);
        universityName = DefaultUtil.getDefaultSchool();
        phoneNum = getIntent().getExtras().getString("phoneNum");
        login_id = getIntent().getExtras().getString("login_id");
        bindingText.setVisibility(View.VISIBLE);
        bindingTipsText.setVisibility(View.VISIBLE);
        bindingNumText.setVisibility(View.VISIBLE);
        bindingNumEd.setVisibility(View.VISIBLE);
        bindingCountdownText.setVisibility(View.VISIBLE);

        bindingText.setText("绑定手机号码");
        bindingTipsText.setText("我们向" + phoneNum + "发送了一个验证码，\n请在下方正确输入。");
        bindingNumText.setText("验证码");
        bindingNumEd.setHint("请输入验证码");
        bindingNextBtn.setText("下一步");

        countDownTimer.start();
        bindingNumEd.addTextChangedListener(new MyTextWatcher());//输入监听
        bindingNumEd.setFilters(new InputFilter[]{new InputFilter.LengthFilter(7)});//最大输入长度

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
                if (canClick) {
                    phoneOtherPresenter.verifyVerificationCode(phoneNum, VerificationCodeNum, login_id);
                }
                break;
            case R.id.binding_countdown_text:
                if (bindingCountdownText.getText().toString().equals("重新获取")) {
                    countDownTimer.start();
                    phoneOtherPresenter.getVerificationCode(phoneNum, login_id);
                }
                break;
        }
    }

    /**
     * 验证码输入格式
     */
    private String VerificationCodeNum;

    private class MyTextWatcher implements TextWatcher {
        MyTextWatcher() {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            canClick = false;
            if (s.toString().length() == 7) {
                VerificationCodeNum = s.toString().replaceAll(" ", "");
                bindingJudgeText.setTextColor(ContextCompat.getColor(VerificationCodeOtherActivity.this, R.color.color_blue_3da8f5));
                bindingNextBtn.setBackground(ContextCompat.getDrawable(VerificationCodeOtherActivity.this, R.drawable.border_shade_3da8f5));
                canClick = true;
            } else {
                bindingJudgeText.setVisibility(View.GONE);
                bindingNextBtn.setBackground(ContextCompat.getDrawable(VerificationCodeOtherActivity.this, R.drawable.border_shade_e5e9ee));
            }

            if (s.length() == 0) return;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < s.length(); i++) {
                if (i != 1 && i != 3 && i != 5 && s.charAt(i) == ' ') {
                    continue;
                } else {
                    sb.append(s.charAt(i));
                    if ((sb.length() == 2 || sb.length() == 4 || sb.length() == 6) && sb.charAt(sb.length() - 1) != ' ') {
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

    /**
     * 获取验证码倒计时
     */
    private CountDownTimer countDownTimer = new CountDownTimer(60 * 1000, 1000) {
        @SuppressLint("SetTextI18n")
        @Override
        public void onTick(long millisUntilFinished) {
            bindingCountdownText.setText(millisUntilFinished / 1000 + "s后重发");
            bindingCountdownText.setBackground(ContextCompat.getDrawable(VerificationCodeOtherActivity.this, R.drawable.border_radius_d5d5d5));
        }

        @Override
        public void onFinish() {
            bindingCountdownText.setText("重新获取");
            bindingCountdownText.setBackground(ContextCompat.getDrawable(VerificationCodeOtherActivity.this, R.drawable.border_radius_3da8f5_45px));
        }
    };

    @Subscribe
    public void onEventMainThread(MessageEvent event) {
        bindingJudgeText.setText("");
        dismissLoad();
        String result = (String) event.getMessage();
        if (ActivityUtil.isForeground(this))
            switch (event.getTag()) {
                case TagUtil.VerifyVerificationCodeTag:
                    bindingJudgeText.setText("验证码验证成功");
                    bindingJudgeText.setVisibility(View.VISIBLE);
                    bindingJudgeText.setTextColor(ContextCompat.getColor(this, R.color.color_blue_3da8f5));

                    new SqliteHelper().execSQL("update password4 set phone=?", StringUtil.takeOutSpacing(phoneNum));

                    LoginUtil.getUserMessage(this, getUserMessageMap);
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
                    if (result.equals(HintTool.NetWorkError)) {
                        bindingJudgeText.setText(result);
                    } else {
                        bindingJudgeText.setText("验证码错误或失效，请重新获取验证码。");
                    }
                    bindingJudgeText.setVisibility(View.VISIBLE);
                    bindingJudgeText.setTextColor(ContextCompat.getColor(this, R.color.redColor));
                    break;
            }
    }

}
