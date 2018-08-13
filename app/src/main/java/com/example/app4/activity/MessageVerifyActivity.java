package com.example.app4.activity;

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

import com.example.app3.tool.HintTool;
import com.example.app3.view.MyTitleView;
import com.example.app4.api.GetAppUrl;
import com.example.app4.api.OkHttpUtil;
import com.example.app4.entity.UserMessageEntity;
import com.example.app4.entity.VerifyIdentityMessageErrorEntity;
import com.example.app4.util.ActivityUtil;
import com.example.app4.util.CheckPhoneNumUtil;
import com.example.app4.util.MapUtil;
import com.example.smartclass.base.BaseRecyclerViewActivity;
import com.example.smartclass.eventbus.MessageEvent;
import com.example.smartclass.util.TagUtil;

import com.yhkj.cqgyxy.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import yh.app.tool.SqliteHelper;
import yh.app.utils.GsonImpl;
import yh.app.utils.ToastUtils;

/**
 * Created by Administrator on 2018/2/28 0028.
 * 个人信息
 */

public class MessageVerifyActivity extends BaseRecyclerViewActivity {

    @BindView(R.id.mv_title)
    MyTitleView mvTitle;
    @BindView(R.id.mv_old_ed)
    EditText mvOldEd;
    @BindView(R.id.mv_old_image)
    ImageView mvOldImage;
    @BindView(R.id.mv_work_num_ed)
    EditText mvWorkNumEd;
    @BindView(R.id.mv_work_num_image)
    ImageView mvWorkNumImage;
    @BindView(R.id.mv_id_num_ed)
    EditText mvIdNumEd;
    @BindView(R.id.mv_id_num_image)
    ImageView mvIdNumImage;
    @BindView(R.id.mv_next_btn)
    Button mvNextBtn;

    private boolean b1 = false, b2 = false, b3 = false;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_message_verify;
    }

    @Override
    protected void setTitle(Context context) {
        mvTitle.setLeftListener(new View.OnClickListener() {
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
    protected void init(final Context context) {
        mvNextBtn.setText("下一步");
        mvOldEd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                b1 = false;
                String s1 = s.toString();
                if (s1.length() == 11) {
                    mvOldImage.setVisibility(View.VISIBLE);
                    mvOldImage.setBackground(ContextCompat.getDrawable(context, R.drawable.right));
                    b1 = true;
                } else {
                    mvOldImage.setVisibility(View.GONE);
                }
                setBtnBackground(context);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mvWorkNumEd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                b2 = false;
                String s1 = s.toString();
                if (s1.length() > 0) {
                    mvWorkNumImage.setVisibility(View.VISIBLE);
                    b2 = true;
                    mvWorkNumImage.setBackground(ContextCompat.getDrawable(context, R.drawable.right));
                } else {
                    mvWorkNumImage.setVisibility(View.GONE);
                }
                setBtnBackground(context);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mvIdNumEd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                b3 = false;
                String s1 = s.toString();
                if (s1.length() == 6) {
                    b3 = true;
                    mvIdNumImage.setVisibility(View.VISIBLE);
                    mvIdNumImage.setBackground(ContextCompat.getDrawable(context, R.drawable.right));
                } else {
                    mvIdNumImage.setVisibility(View.GONE);
                }
                //限制输入空格
                if (s.toString().contains(" ")) {
                    String[] str = s.toString().split(" ");
                    String str1 = "";
                    for (int i = 0; i < str.length; i++) {
                        str1 += str[i];
                    }
                    mvIdNumEd.setText(str1);
                    mvIdNumEd.setSelection(start);
                }
                setBtnBackground(context);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void setBtnBackground(Context context) {
        if (b1 && b2 && b3) {
            mvNextBtn.setBackground(ContextCompat.getDrawable(context, R.drawable.border_shade_3da8f5));
        } else {
            mvNextBtn.setBackground(ContextCompat.getDrawable(context, R.drawable.border_shade_e5e9ee));
        }
    }

    @OnClick(R.id.mv_next_btn)
    public void onViewClicked() {
        if (b1 && b2 && b3) {
            verifyOldPhoneNoUsed();
        }
    }

    /**
     * 验证身份信息
     */
    private Map<String, String> verifyOldPhoneNoUsedMap;

    private void verifyOldPhoneNoUsed() {
        showLoad("");
        try {
            verifyOldPhoneNoUsedMap = MapUtil.getMap(verifyOldPhoneNoUsedMap);
            verifyOldPhoneNoUsedMap.put("mob", mvOldEd.getText().toString().trim());
            verifyOldPhoneNoUsedMap.put("stuid", mvWorkNumEd.getText().toString().trim());
            verifyOldPhoneNoUsedMap.put("sfzh", mvIdNumEd.getText().toString().trim());
            verifyOldPhoneNoUsedMap.put("xxbh", new SqliteHelper().rawQuery("select * from password4").get(0).get("schoolId"));
            verifyOldPhoneNoUsedMap.put("userid", new SqliteHelper().rawQuery("select * from userinfo4").get(0).get("userid"));
            OkHttpUtil.OkHttpPost(GetAppUrl.UIA.verifyOldPhoneNoUsed.getUrl(), verifyOldPhoneNoUsedMap, TagUtil.VerifyOldPhoneNoUsedTag, this);
        } catch (Exception ignored) {

        }
    }

    @Subscribe
    public void onEventMainThread(MessageEvent event) {
        dismissLoad();
        String result = (String) event.getMessage();
        if (ActivityUtil.isForeground(this))
            switch (event.getTag()) {
                case TagUtil.VerifyOldPhoneNoUsedTag:
                    Intent intent = new Intent(this, BindingNewPhoneActivity.class);
                    startActivity(intent);
                    break;
                case HintTool.REQUESTFAIL:
                    try {
                        VerifyIdentityMessageErrorEntity errorEntity = GsonImpl.get().toObject(result, VerifyIdentityMessageErrorEntity.class);
                        if (errorEntity.getContent().getSfzherror().equals("0")) {
                            result = "身份证号错误，请重新输入。";
                            mvIdNumImage.setBackground(ContextCompat.getDrawable(this, R.drawable.error));
                        }
                        if (errorEntity.getContent().getTelerror().equals("0")) {
                            result = "手机号错误，请重新输入。";
                            mvOldImage.setBackground(ContextCompat.getDrawable(this, R.drawable.error));
                        }
                        if (errorEntity.getContent().getXherror().equals("0")) {
                            result = "教职工号错误，请重新输入。";
                            mvWorkNumImage.setBackground(ContextCompat.getDrawable(this, R.drawable.error));
                        }
                    } catch (Exception ignored) {

                    }
                    ToastUtils.Toast(this, result);
                    break;
            }
    }
}
