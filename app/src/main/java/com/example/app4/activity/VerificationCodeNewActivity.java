package com.example.app4.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import com.example.app4.api.GetAppUrl;
import com.example.app4.api.OkHttpUtil;
import com.example.app4.presenter.LoginUtil;
import com.example.app4.tool.Tool;
import com.example.app4.util.ActivityUtil;
import com.example.app4.util.DeviceUtils;
import com.example.app4.util.MapUtil;
import com.example.smartclass.base.BaseRecyclerViewActivity;
import com.example.smartclass.eventbus.MessageEvent;
import com.example.smartclass.util.TagUtil;

import yh.app.appstart.lg.R;

import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import yh.app.tool.SqliteHelper;
import 云华.智慧校园.工具.RSAApi;
import 云华.智慧校园.工具.RSAHelper;

/**
 * Created by Administrator on 2018/2/27 0027.
 * <p>
 * 验证码页面
 */

public class VerificationCodeNewActivity extends BaseRecyclerViewActivity {
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

    private String newPhone;

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
        try {
            universityName = getIntent().getExtras().getString("universityName");
        } catch (Exception e) {
            universityName = "";
        }
        newPhone = getIntent().getExtras().getString("phoneNum");
        bindingText.setVisibility(View.VISIBLE);
        bindingTipsText.setVisibility(View.VISIBLE);
        bindingNumText.setVisibility(View.VISIBLE);
        bindingNumEd.setVisibility(View.VISIBLE);
        bindingCountdownText.setVisibility(View.VISIBLE);

        bindingText.setText("输入4位验证码");
        bindingTipsText.setText("我们向" + newPhone + "发送了一个验证码，\n请在下方正确输入。");
        bindingNumText.setText("验证码");
        bindingNumEd.setHint("请输入验证码");
        bindingNextBtn.setText("下一步");

        countDownTimer.start();
        bindingNumEd.addTextChangedListener(new MyTextWatcher(bindingNumEd));//输入监听
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
                    bindNewPhone();
                }
                break;
            case R.id.binding_countdown_text:
                if (bindingCountdownText.getText().toString().equals("重新获取")) {
                    countDownTimer.start();
                    getVerificationCode();
                }
                break;
        }
    }

    /**
     * 验证码输入格式
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
            if (s.toString().length() == 7) {
                bindingJudgeText.setVisibility(View.VISIBLE);
                bindingJudgeText.setTextColor(ContextCompat.getColor(VerificationCodeNewActivity.this, R.color.color_blue_3da8f5));
                bindingNextBtn.setBackground(ContextCompat.getDrawable(VerificationCodeNewActivity.this, R.drawable.border_shade_3da8f5));
                canClick = true;
            } else {
                bindingJudgeText.setVisibility(View.GONE);
                bindingNextBtn.setBackground(ContextCompat.getDrawable(VerificationCodeNewActivity.this, R.drawable.border_shade_e5e9ee));
            }

            if (s == null || s.length() == 0) return;
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
     * 绑定新手机
     */
    private Map<String, String> bindNewPhoneMap;

    private void bindNewPhone() {
        try {
            String yzm = bindingNumEd.getText().toString().trim().replace(" ", "");
            bindNewPhoneMap = MapUtil.getMap(bindNewPhoneMap);
            bindNewPhoneMap.put("newmob", newPhone.replace(" ", ""));
            bindNewPhoneMap.put("xxbh", new SqliteHelper().rawQuery("select * from password4").get(0).get("schoolId"));
            bindNewPhoneMap.put("yzm", yzm);
            bindNewPhoneMap.put("password", new SqliteHelper().rawQuery("select * from password4").get(0).get("password"));
            OkHttpUtil.OkHttpPost(GetAppUrl.UIA.bindNewPhone.getUrl(), bindNewPhoneMap, TagUtil.BindNewPhoneTag, this);
        } catch (Exception ignored) {

        }


    }

    private Map<String, String> getVerificationCodeMap;

    private void getVerificationCode() {
        showLoad("正在获取验证码，请稍后");
        getVerificationCodeMap = MapUtil.getMap(getVerificationCodeMap);
        try {

            JSONObject mob = new JSONObject();

            mob.put("mob", newPhone.replace(" ", ""));
            mob.put("password", new RSAHelper().decode(new SqliteHelper().rawQuery("select * from password4").get(0).get("password")));
            mob.put("random", Tool.stochasticString());
            mob.put("sn", DeviceUtils.getUniqueId(this));
            mob.put("xxbh", new SqliteHelper().rawQuery("select * from password4").get(0).get("schoolId"));
            mob.put("userid", new SqliteHelper().rawQuery("select * from userinfo4").get(0).get("userid"));


            String b = URLEncoder.encode(RSAApi.getEncryptSecurity(mob.toString()), "utf-8");
            getVerificationCodeMap.put("b", b);

            OkHttpUtil.OkHttpPost(GetAppUrl.UIA.getNewPhoneVerificationCode.getUrl(), getVerificationCodeMap, TagUtil.GetNewPhoneVerificationCodeTag, this);
        } catch (Exception e) {
            dismissLoad();
            e.printStackTrace();
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
            bindingCountdownText.setBackground(ContextCompat.getDrawable(VerificationCodeNewActivity.this, R.drawable.border_radius_d5d5d5));
        }

        @Override
        public void onFinish() {
            bindingCountdownText.setText("重新获取");
            bindingCountdownText.setBackground(ContextCompat.getDrawable(VerificationCodeNewActivity.this, R.drawable.border_radius_3da8f5_45px));
        }
    };

    @Subscribe
    public void onEventMainThread(MessageEvent event) {
        dismissLoad();
        if (ActivityUtil.isForeground(this))
            switch (event.getTag()) {
                case TagUtil.BindNewPhoneTag:
                    new SqliteHelper().execSQL("update password4 set phone=?", newPhone.replace(" ", ""));
                    new SqliteHelper().execSQL("update userinfo4 set lxdh=?", newPhone.replace(" ", ""));
                    Intent intent = new Intent(this, ChangePhoneSuccessActivity.class);
                    startActivity(intent);
                    break;
                case HintTool.REQUESTFAIL:
                    bindingJudgeText.setText((String) event.getMessage());
                    bindingJudgeText.setTextColor(ContextCompat.getColor(VerificationCodeNewActivity.this, R.color.redColor));
                    break;
            }
    }

    private static final String TAG = "VerificationCodeNewActi";
}
