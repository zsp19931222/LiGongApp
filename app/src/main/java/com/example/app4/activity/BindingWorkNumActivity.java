package com.example.app4.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.app3.tool.HintTool;
import com.example.app3.view.MyTitleView;
import com.example.app4.presenter.LoginUtil;
import com.example.app4.util.ActivityUtil;
import com.example.smartclass.base.BaseRecyclerViewActivity;
import com.example.smartclass.eventbus.MessageEvent;
import com.example.smartclass.util.TagUtil;
import yh.app.appstart.lg.R;

import org.greenrobot.eventbus.Subscribe;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/2/27 0027.
 * <p>
 * 绑定学工号
 */

public class BindingWorkNumActivity extends BaseRecyclerViewActivity {
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

        bindingText.setText("绑定个人信息");
        bindingNumText.setText("学工号");
        bindingNumEd.setHint("请输入您的学/工号");
        bindingNextBtn.setText("下一步");

        bindingNumEd.addTextChangedListener(new MyTextWatcher(bindingNumEd));//输入监听

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
                if (canClick)
                    LoginUtil.verifyWorkNum(this,verifyIDMap,getIntent().getExtras().getString("phoneNum"),bindingNumEd.getText().toString().trim(),universityName);
                break;
        }
    }

    /**
     * 验证学/工号
     */
    private Map<String, String> verifyIDMap;

//    private void verifyID(String mob) {
//        try {
//            if (verifyIDMap == null) {
//                verifyIDMap = new HashMap<>();
//            }
//            verifyIDMap.put("mob", mob);
//            verifyIDMap.put("stuid", bindingNumEd.getText().toString().trim());
//            List<Map<String,String>> maps= new ArrayList<>();
//            maps= GetSchoolListUtil.getSchoolData("select * from schools where xxmc='"+universityName+"'",maps);
//            verifyIDMap.put("xxbh", maps.get(0).get("xxbh"));
//            OkHttpUtil.OkHttpPost(GetAppUrl.UIA.verifyID.getUrl(), verifyIDMap, TagUtil.VerifyIDTag);
//        }catch (Exception ignored){
//
//        }
//    }

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
            bindingJudgeText.setVisibility(View.GONE);
            if (s.toString().length() > 0) {
                canClick = true;
                bindingClearImage.setVisibility(View.VISIBLE);
                bindingNextBtn.setBackground(ContextCompat.getDrawable(BindingWorkNumActivity.this, R.drawable.border_shade_3da8f5));
            } else {
                canClick = false;
                bindingClearImage.setVisibility(View.GONE);
                bindingNextBtn.setBackground(ContextCompat.getDrawable(BindingWorkNumActivity.this, R.drawable.border_shade_e5e9ee));
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    }

    @Subscribe
    public void onEventMainThread(MessageEvent event) {
        String result = (String) event.getMessage();
        if (ActivityUtil.isForeground(this))
            switch (event.getTag()) {
            case TagUtil.VerifyIDTag:
                Intent intent = new Intent(this, BindingIDActivity.class);
                intent.putExtra("universityName", universityName);
                intent.putExtra("phoneNum", getIntent().getExtras().getString("phoneNum"));
                startActivity(intent);
                break;
            case HintTool.REQUESTFAIL:
                bindingJudgeText.setVisibility(View.VISIBLE);
                bindingJudgeText.setText(result);
                bindingJudgeText.setTextColor(ContextCompat.getColor(this, R.color.redColor));
                break;
        }
    }
}
