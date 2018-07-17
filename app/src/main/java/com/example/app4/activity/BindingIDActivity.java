package com.example.app4.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
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
import com.example.app4.entity.HomePageWidgetEntity;
import com.example.app4.entity.UserMessageEntity;
import com.example.app4.presenter.LoginUtil;
import com.example.app4.presenter.StartPresenter;
import com.example.app4.util.ActivityUtil;
import com.example.smartclass.base.BaseRecyclerViewActivity;
import com.example.smartclass.eventbus.MessageEvent;
import com.example.smartclass.util.TagUtil;

import yh.app.appstart.lg.R;

import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import yh.app.utils.GsonImpl;
import yh.app.utils.ToastUtils;

/**
 * Created by Administrator on 2018/2/27 0027.
 * <p>
 * 绑定证件号
 */

public class BindingIDActivity extends BaseRecyclerViewActivity {
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
        universityName = getIntent().getExtras().getString("universityName");
        bindingText.setVisibility(View.VISIBLE);
        bindingNumText.setVisibility(View.VISIBLE);
        bindingNumEd.setVisibility(View.VISIBLE);
        bindingTipsText.setVisibility(View.VISIBLE);
        bindingJudgeText.setVisibility(View.VISIBLE);

        bindingText.setText("绑定个人信息");
        bindingNumText.setText("证件号");
        bindingNumEd.setHint("请输入身份证后六位");
        bindingNextBtn.setText("登录");
        bindingTipsText.setText("个人信息核对，以便于我们对您账户的安全验证。");
        bindingJudgeText.setText("尾数为字母X，请照常输入大写字母X。");
        bindingJudgeText.setTextColor(ContextCompat.getColor(context, R.color.color_bbbbbb));

        bindingNumEd.addTextChangedListener(new MyTextWatcher(bindingNumEd));//输入监听
        bindingNumEd.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});//最大输入长度
        bindingNumEd.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);

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
                    showLoad("登录中");
                    LoginUtil.verifyID(this, verifyIDCardMap, getIntent().getExtras().getString("phoneNum"), bindingNumEd.getText().toString().trim(), universityName);
                }
                break;
        }
    }

    /**
     * 验证身份证
     */
    private Map<String, String> verifyIDCardMap;


    /**
     * 获取用户基本信息
     */
    private Map<String, String> getUserMessageMap;


    /**
     * 学工号输入限制
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
            if (s.toString().length() == 6) {
                bindingClearImage.setVisibility(View.VISIBLE);
                bindingNextBtn.setBackground(ContextCompat.getDrawable(BindingIDActivity.this, R.drawable.border_shade_3da8f5));
                canClick = true;
            } else {
                bindingClearImage.setVisibility(View.GONE);
                bindingNextBtn.setBackground(ContextCompat.getDrawable(BindingIDActivity.this, R.drawable.border_shade_e5e9ee));
            }
            //限制输入空格
            if (s.toString().contains(" ")) {
                String[] str = s.toString().split(" ");
                StringBuilder str1 = new StringBuilder();
                for (String aStr : str) {
                    str1.append(aStr);
                }
                bindingNumEd.setText(str1.toString());
                bindingNumEd.setSelection(start);
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
                case TagUtil.VerifyIDCardTag:
                    showLoad("获取用户信息中");
                    LoginUtil.getUserMessage(this, getUserMessageMap);
                    break;
                case TagUtil.GetUserMessageTag:
                    try {
                        UserMessageEntity userMessageEntity = GsonImpl.get().toObject(result, UserMessageEntity.class);
                        LoginUtil.saveUserMessage(userMessageEntity, this);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case TagUtil.GetMySelfListTag:
                    new StartPresenter(this).saveList(result);
                    break;
                case TagUtil.GetNavigationListTag:
                    new StartPresenter(this).saveNavigation(result);
                    LoginUtil.intentToMain(this);
                    break;
                case HintTool.REQUESTFAIL:
                    ToastUtils.Toast(this, result);
                    break;
            }
    }
}
