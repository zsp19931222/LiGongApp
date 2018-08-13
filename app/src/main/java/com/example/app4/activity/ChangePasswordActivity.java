package com.example.app4.activity;

import android.annotation.SuppressLint;
import android.content.Context;
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
import com.example.app4.api.GetAppUrl;
import com.example.app4.api.OkHttpUtil;
import com.example.app4.tool.UserMessageTool;
import com.example.app4.util.ActivityUtil;
import com.example.app4.util.AndroidBug5497Workaround;
import com.example.app4.util.MapUtil;
import com.example.app4.util.RegexUtil;
import com.example.smartclass.base.BaseRecyclerViewActivity;
import com.example.smartclass.eventbus.MessageEvent;
import com.example.smartclass.util.TagUtil;

import org.greenrobot.eventbus.Subscribe;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.yhkj.cqgyxy.R;
import yh.app.tool.MD5;
import yh.app.utils.ToastUtils;
import 云华.智慧校园.功能.LoginOut;

/**
 * Created by Administrator on 2018/5/29 0029.
 * 修改密码
 */

public class ChangePasswordActivity extends BaseRecyclerViewActivity {

    @BindView(R.id.binding_title)
    MyTitleView bindingTitle;
    @BindView(R.id.binding_text)
    TextView bindingText;
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
    @BindView(R.id.binding_error_text)
    TextView bindingErrorText;
    @BindView(R.id.binding_num_text2)
    TextView bindingNumText2;
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
    @BindView(R.id.binding_show_text3)
    TextView bindingShowText3;
    @BindView(R.id.binding_num_ed3)
    EditText bindingNumEd3;
    @BindView(R.id.binding_clearNum_image3)
    ImageView bindingClearNumImage3;
    @BindView(R.id.binding_num_lin3)
    LinearLayout bindingNumLin3;
    @BindView(R.id.binding_line_image3)
    ImageView bindingLineImage3;
    @BindView(R.id.binding_error_btn3)
    Button bindingErrorBtn3;
    @BindView(R.id.binding_next_btn)
    Button bindingNextBtn;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_change_password;
    }

    @Override
    protected void setTitle(Context context) {
        bindingTitle.setTitle("修改密码", context);
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
        bindingNextBtn.setBackground(ContextCompat.getDrawable(ChangePasswordActivity.this, R.drawable.border_shade_e5e9ee));

        bindingNumEd.addTextChangedListener(new MyTextWatcher(bindingNumEd));
        bindingNumEd2.addTextChangedListener(new MyTextWatcher(bindingNumEd2));
        bindingNumEd3.addTextChangedListener(new MyTextWatcher(bindingNumEd3));
    }

    @Override
    @Subscribe
    public void onEventMainThread(MessageEvent event) {
        dismissLoad();
        if (ActivityUtil.isForeground(this))
            switch (event.getTag()) {
                case HintTool.REQUESTFAIL:
                    bindingErrorText.setText("原密码错误，请核对后重新输入");
                    break;
                case TagUtil.ChangePasswordTag:
                    ToastUtils.Toast(this, "修改密码成功,请重新登录");
                    new LoginOut().doLoginOut(this);
                    break;
            }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        AndroidBug5497Workaround.assistActivity(this);
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
            if (bindingNumEd3.getText().toString().length() > 0) {
                bindingClearNumImage3.setVisibility(View.VISIBLE);
            } else {
                bindingClearNumImage3.setVisibility(View.GONE);
            }
            bindingNextBtn.setBackground(ContextCompat.getDrawable(ChangePasswordActivity.this, R.drawable.border_shade_e5e9ee));
            if (bindingNumEd.getText().toString().length() > 0 && bindingNumEd2.getText().toString().length() > 0 && bindingNumEd3.getText().toString().length() > 0) {
                bindingNextBtn.setBackground(ContextCompat.getDrawable(ChangePasswordActivity.this, R.drawable.border_shade_3da8f5));
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

    @SuppressLint("SetTextI18n")
    @OnClick({R.id.binding_next_btn, R.id.binding_clearNum_image, R.id.binding_clearNum_image2, R.id.binding_clearNum_image3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.binding_next_btn:
                boolean canClick0;
                bindingErrorBtn3.setText("");
                bindingErrorText.setText("");
                if (bindingNumEd.getText().toString().length() == 0) {
                    bindingErrorText.setText("请输入原始密码");
                    canClick0 = false;
                } else {
                    canClick0 = true;
                }
                boolean canClick;
                if (bindingNumEd2.getText().toString().length() < 8) {
                    bindingErrorBtn3.setText("密码至少8个字符，而且同时包含字母和数字！");
                    canClick = false;
                } else {
                    canClick = true;
                }
                boolean canClick1;
                if (!bindingNumEd2.getText().toString().equals(bindingNumEd3.getText().toString())) {
                    bindingErrorBtn3.setText("两次输入的密码不一致！");
                    canClick1 = false;
                } else {
                    canClick1 = true;
                }
                if (canClick && canClick1 && canClick0) {
                    if (RegexUtil.regex(bindingNumEd3.getText().toString())) {
                        changePassWord(bindingNumEd.getText().toString(), bindingNumEd3.getText().toString());
                    } else {
                        bindingErrorBtn3.setText("密码至少8个字符，而且同时包含字母和数字！");
                    }
                }
                break;
            case R.id.binding_clearNum_image:
                bindingNumEd.setText("");
                break;
            case R.id.binding_clearNum_image2:
                bindingNumEd2.setText("");
                break;
            case R.id.binding_clearNum_image3:
                bindingNumEd3.setText("");
                break;
        }
    }

    /**
     * 修改密码
     */
    private Map<String, String> changePasswordMap;

    private void changePassWord(String oldps, String newps) {
        showLoad("");
        changePasswordMap = MapUtil.getMap(changePasswordMap);
        changePasswordMap.put("ticket", MD5.MD5("yunhua" + UserMessageTool.getUserId() + MD5.MD5(oldps)));
        changePasswordMap.put("userid", UserMessageTool.getUserId());
        changePasswordMap.put("np", MD5.MD5(newps));
        changePasswordMap.put("mob", UserMessageTool.getPhone());
        OkHttpUtil.OkHttpPost(GetAppUrl.UIA.changePassword.getUrl(), changePasswordMap, TagUtil.ChangePasswordTag, this);
    }

//    private void text(int tag) {
//        try {
//            changePasswordMap = MapUtil.getMap(changePasswordMap);
//            changePasswordMap.put("tag",tag+"");
//            OkHttpUtil.OkHttpPost("http://login.i.cqut.edu.cn/uia/login/usermes?mob=18523921972&password=%5B%22kviCLojU0631vfnu9jHQeukrwulX8jUBqoKJFRmffatnaBWPM16eZyRmvzXa0LFehjirEzzsydUZ%5CnVDdidK13nYO04%2BIq4%5C%2FCcFtc6C%5C%2F7KFz5xreU%2BSUbF3z7HCV9K5IEHhTI%2BmS6iRoIIZSS1FDzGYBFH%5CnePdzRuoyVB2nAoHhSqY%3D%5Cn%22%5D&userid=20141013&xxbh=6C08D809CD580E2348276FB83A195C56&tag="+tag, changePasswordMap, TagUtil.ChangePasswordTag, this);
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
}
