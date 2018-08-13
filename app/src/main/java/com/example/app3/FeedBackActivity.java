package com.example.app3;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.app3.base.BaseActivity;
import com.example.app3.tool.HintTool;
import com.example.app3.tool.JSONTool;
import com.example.app3.view.MyTitleView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import org.androidpn.push.Constants;

import java.util.HashMap;
import java.util.Map;

import com.yhkj.cqgyxy.R;

import yh.app.logTool.Log;
import yh.app.tool.MD5;
import yh.app.utils.ToastUtils;
import yh.app.utils.VolleyInterface;
import yh.app.utils.VolleyRequest;
import yh.tool.widget.LoadDiaog;
import 云华.智慧校园.工具.CodeManage;
import 云华.智慧校园.工具.IsNull;
import 云华.智慧校园.工具.MapTools;
import 云华.智慧校园.工具._链接地址导航;

public class FeedBackActivity extends BaseActivity {
    private EditText content, mail;
    private TextView workCount;
    private Button submit;
    private MyTitleView myTitleView;
    private int max = 500;

    private LoadDiaog loadDiaog;

    @Override
    protected void initView() {
        // TODO Auto-generated method stub
//        new CenterTopBarHelper(this).setTitle(getResources().getString(R.string.str_feedback_title));
        content = (EditText) ((LinearLayout) findViewById(R.id.myself_feedback_linear_content)).getChildAt(0);
        workCount = (TextView) ((LinearLayout) findViewById(R.id.myself_feedback_linear_content)).getChildAt(1);
        mail = (EditText) findViewById(R.id.myself_feedback_et_mail_content);
        myTitleView = (MyTitleView) findViewById(R.id.myself_feedback_title);
        submit = (Button) findViewById(R.id.myself_feedback_btn_submit);

        loadDiaog = new LoadDiaog(getContext());
    }

    @Override
    protected void initActivityView() {

        // TODO Auto-generated method stub
        setContentView(R.layout.activity_myself_feedback);
        setImmersiveStatusBar(true,getResources().getColor(R.color.white));

    }

    @Override
    protected void initData() {
        // TODO Auto-generated method stub
        workCount.setText(0 + "/" + max);
        myTitleView.setLeftListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initAction() {
        submit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (content.getText() == null || !IsNull.isNotNull(content.getText().toString())) {
                    Toast.makeText(getContext(), getContext().getResources().getString(R.string.str_feedback_content_not_null), Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    loadDiaog.setTitle("正在提交,请稍后");
                    loadDiaog.show();
                    submitFeedBack(getContext(), content.getText().toString(), mail.getText().toString());
                }
            }
        });

        content.addTextChangedListener(new TextWatcher() {
            private CharSequence temp;// 监听前的文本
            private int editStart;// 光标开始位置
            private int editEnd;// 光标结束位置

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                temp = s;
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                editStart = content.getSelectionStart();
                editEnd = content.getSelectionEnd();
                if (temp.length() > max) {
                    s.delete(editStart - 1, editEnd);
                    int tempSelection = editStart;
                    content.setText(s);
                    content.setSelection(tempSelection);
                    workCount.setText(max + "/" + max);
                } else {
                    workCount.setText((s == null ? 0 : s.toString().length()) + "/" + max);
                }
            }
        });
    }

    /**
     * 提交反馈
     *
     * @param suggestions 意见内容
     * @param lxfs        联系方式
     */
    private void submitFeedBack(final Context context, String suggestions, String lxfs) {
        Map<String, String> params = MapTools.buildMap(new String[][]
                {
                        {
                                "userid", Constants.number
                        },
                        {
                                "ticket", MD5.MD5(_链接地址导航.addString + Constants.number + Constants.code)
                        },
                        {
                                "suggestions", suggestions
                        }
                        ,
                        {
                                "lxfs", lxfs
                        }
                });
        VolleyRequest.RequestPost(_链接地址导航.DC.提交反馈意见v3.getUrl(), params, new VolleyInterface() {
            @Override
            public void onMySuccess(String result) {
                if (CodeManage.NET_SUCCESS.equals(JSONTool.Code(result))) {
                    ToastUtils.Toast(context, context.getResources().getString(R.string.str_feedback_succeed));
                    finish();
                } else {
                    ToastUtils.Toast(context, HintTool.FEEDBACKFAIL);
                }
                if (loadDiaog.isShowing()) {
                    loadDiaog.dismiss();
                }
            }

            @Override
            public void onMyError(VolleyError error) {
                if (loadDiaog.isShowing()) {
                    loadDiaog.dismiss();
                }
                ToastUtils.Toast(context, HintTool.FEEDBACKFAIL);
            }
        });
    }

}
