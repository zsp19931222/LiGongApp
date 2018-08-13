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
import com.example.app4.api.GetAppUrl;
import com.example.app4.api.OkHttpUtil;
import com.example.app4.util.ActivityUtil;
import com.example.app4.util.CheckPhoneNumUtil;
import com.example.app4.util.DeviceUtils;
import com.example.smartclass.base.BaseRecyclerViewActivity;
import com.example.smartclass.eventbus.MessageEvent;
import com.example.smartclass.util.TagUtil;

import com.yhkj.cqgyxy.R;

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
import yh.app.utils.ToastUtils;
import 云华.智慧校园.工具.RSAApi;
import 云华.智慧校园.工具.RSAHelper;

/**
 * Created by Administrator on 2018/2/27 0027.
 * <p>
 * 绑定新手机
 */

public class BindingNewPhoneActivity extends BaseRecyclerViewActivity {
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
        try {
            universityName = getIntent().getExtras().getString("universityName");
        } catch (Exception e) {
            universityName = "";
        }
        bindingText.setVisibility(View.VISIBLE);
        bindingNumText.setVisibility(View.VISIBLE);
        bindingCountryCodeText.setVisibility(View.VISIBLE);
        bindingNumEd.setVisibility(View.VISIBLE);

        bindingText.setText("绑定新手机号");
        bindingNumText.setText("手机号");
        bindingCountryCodeText.setText("+86");
        bindingNumEd.setHint("请输入手机号");
        bindingNextBtn.setText("下一步");

        bindingNumEd.addTextChangedListener(new MyTextWatcher(bindingNumEd));//输入监听
        bindingNumEd.setFilters(new InputFilter[]{new InputFilter.LengthFilter(13)});//最大输入长度

        printEnglish();
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

    @OnClick({R.id.binding_clear_image, R.id.binding_next_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.binding_clear_image:
                bindingNumEd.setText("");
                break;
            case R.id.binding_next_btn:
                if (canClick) {
                    getVerificationCode();
                }
                break;
        }
    }

    /**
     * 获取验证码
     */
    private Map<String, String> getVerificationCodeMap;

    private void getVerificationCode() {
        showLoad("正在获取验证码，请稍后");
        if (getVerificationCodeMap == null) {
            getVerificationCodeMap = new HashMap<>();
        }
        try {

            JSONObject mob = new JSONObject();
            String newPhone = bindingNumEd.getText().toString().trim().replace(" ", "");

            mob.put("mob", newPhone);
            mob.put("password", new RSAHelper().decode(new SqliteHelper().rawQuery("select * from password4").get(0).get("password")));
            mob.put("random", stochasticString());
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
     * 手机输入格式344
     */
    private class MyTextWatcher implements TextWatcher {
        MyTextWatcher(EditText etPhone) {
            this.etPhone = etPhone;
        }

        private EditText etPhone;
        private boolean isAdd;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            if (after == 1) {//增加
                isAdd = true;
            } else {
                isAdd = false;
            }
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
                    bindingJudgeText.setTextColor(ContextCompat.getColor(BindingNewPhoneActivity.this, R.color.color_blue_3da8f5));
                    bindingNextBtn.setBackground(ContextCompat.getDrawable(BindingNewPhoneActivity.this, R.drawable.border_shade_3da8f5));
                    canClick = true;
                } else {
                    bindingJudgeText.setText("手机号验证失败，请重新输入~");
                    bindingJudgeText.setTextColor(ContextCompat.getColor(BindingNewPhoneActivity.this, R.color.redColor));
                    bindingNextBtn.setBackground(ContextCompat.getDrawable(BindingNewPhoneActivity.this, R.drawable.border_shade_e5e9ee));
                }
            } else {
                bindingJudgeText.setVisibility(View.GONE);
                bindingNextBtn.setBackground(ContextCompat.getDrawable(BindingNewPhoneActivity.this, R.drawable.border_shade_e5e9ee));
            }

            if (s == null || s.length() == 0) return;
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

    @Subscribe
    public void onEventMainThread(MessageEvent event) {
        dismissLoad();
        String result = (String) event.getMessage();
        if (ActivityUtil.isForeground(this))
            switch (event.getTag()) {
                case TagUtil.GetNewPhoneVerificationCodeTag:
                    Intent intent = new Intent(this, VerificationCodeNewActivity.class);
                    intent.putExtra("universityName", universityName);
                    intent.putExtra("phoneNum", bindingNumEd.getText().toString());
                    startActivity(intent);
                    break;
                case HintTool.REQUESTFAIL:
                    ToastUtils.Toast(this, result);
                    break;
            }
    }

    /**
     * 获取十二位随机英文字母字符串
     */
    List<String> charList = null;

    private String stochasticString() {
        String result = "";
        Random random = new Random();

        for (int i = 0; i < 12; i++) {
            int position = random.nextInt(charList.size());
            result += charList.get(position);
        }
        return result;
    }

    /**
     * 获取英文字母
     */
    public void printEnglish() {
        if (charList == null) {
            charList = new ArrayList<>();
        }
        int firstEnglish, lastEnglish;
        char firstE = 'A', lastE = 'Z';      //获取首字母与末字母的值

        firstEnglish = (int) firstE;
        lastEnglish = (int) lastE;

        for (int i = firstEnglish; i <= lastEnglish; ++i) {
            char uppercase, lowercase;

            uppercase = (char) i;
            lowercase = (char) (i + 32);

            charList.add(uppercase + "");
            charList.add(lowercase + "");
        }

    }
}
