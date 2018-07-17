package com.example.app3.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.app3.base.BaseRecyclerViewActivity;
import com.example.app3.tool.HintTool;
import com.example.app3.tool.JSONTool;
import com.example.app3.tool.TimeTool;
import com.example.jpushdemo.body.BodyAdd;
import com.yunhuakeji.app.utils.MapTools;
import yh.app.appstart.lg.R;

import org.androidpn.push.Constants;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import yh.app.logTool.Log;
import yh.app.tool.SqliteHelper;
import yh.app.tool.Ticket;
import yh.app.utils.ToastUtils;
import yh.app.utils.VolleyInterface;
import yh.app.utils.VolleyRequest;
import yh.tool.widget.LoadDiaog;
import 云华.智慧校园.工具._链接地址导航;

/**
 * Created by Administrator on 2017/10/12.
 * <p>
 * 身份验证界面
 */

public class VerifyActivity extends BaseRecyclerViewActivity {
    @BindView(R.id.quanzi_qrtjhy_sfyz_back)
    RelativeLayout quanziQrtjhySfyzBack;
    @BindView(R.id.quanzi_qrtjhy_sfyz_fs)
    TextView quanziQrtjhySfyzFs;
    @BindView(R.id.quanzi_qrtjhy_sfyz_xm)
    TextView quanziQrtjhySfyzXm;
    @BindView(R.id.quanzi_qrtjhy_sfyz_xh)
    TextView quanziQrtjhySfyzXh;
    @BindView(R.id.quanzi_qrtjhy_sfyz_message)
    EditText quanziQrtjhySfyzMessage;

    private String code;//传过来的好友学号
    private String name;//传过来的好友名字

    private String fqrname;//发起人姓名

    private LoadDiaog loadDiaog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_verify;
    }

    @Override
    protected void setTitle(Context context) {

    }

    @Override
    protected void loadRecyclerViewData(Context context) {

    }

    @Override
    protected void init(Context context) {
        loadDiaog = new LoadDiaog(context);
        name = getIntent().getExtras().getString("name");
        code = getIntent().getExtras().getString("code");
        quanziQrtjhySfyzXm.setText(name);
        quanziQrtjhySfyzXh.setText("学号：" + code);
        fqrname = new SqliteHelper().rawQuery("select * from user").get(0).get("name");//我的名字
        quanziQrtjhySfyzMessage.setText("你好我是" + fqrname);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.quanzi_qrtjhy_sfyz_back, R.id.quanzi_qrtjhy_sfyz_fs})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.quanzi_qrtjhy_sfyz_back:
                finish();
                break;
            case R.id.quanzi_qrtjhy_sfyz_fs:
                sendRequest(VerifyActivity.this, code, quanziQrtjhySfyzMessage.getText().toString().trim());
                break;
        }
    }

    /**
     * 发送验证请求
     */

    private void sendRequest(final Context context, String hybh, final String fjxx) {
        loadDiaog.show();
        Log.d("zsp",_链接地址导航.DC.添加好友.getUrl());
        Map<String, String> param = MapTools.buildMap(new String[][]
                {
                        {
                                "hybh", hybh
                        },
                        {
                                "fjxx", fjxx
                        },
                        {
                                "userid", Constants.number
                        },
                        {
                                "function_id", "20150120"
                        },
                        {
                                "ticket", Ticket.getFunctionTicket("20150120")
                        },

                });
        VolleyRequest.RequestPost(_链接地址导航.DC.添加好友.getUrl(), param, new VolleyInterface() {
            @Override
            public void onMySuccess(String result) {
                if (loadDiaog.isShowing()) {
                    loadDiaog.dismiss();
                }
                if (JSONTool.SUCCESS.equals(JSONTool.Code(result))) {
                    ToastUtils.Toast(context, HintTool.Add_Success);
                    new SqliteHelper().execSQL("insert into addFriend(id,userid,type,fqr,fqrname,jsr,jsrname,fjnr,fssj,faceaddress,deal,isread,m_deal) values(?,?,?,?,?,?,?,?,?,?,?,?,?)", new Object[]
                            {
                                    code, Constants.number, "", code, name, Constants.number, "", fjxx, TimeTool.TimeStamp2date(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss"), "", BodyAdd.DEAL_DISAGREE, BodyAdd.DEAL_NOREAD,"false"
                            });
                    new SqliteHelper().execSQL("insert into addFriendList(id,userid,type,fqr,fqrname,jsr,jsrname,fjnr,fssj,faceaddress,deal,isread) values(?,?,?,?,?,?,?,?,?,?,?,?)", new Object[]
                            {
                                    code, Constants.number, "", code, name, Constants.number, "", fjxx, TimeTool.TimeStamp2date(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss"), "", BodyAdd.DEAL_DISAGREE, BodyAdd.DEAL_NOREAD
                            });
                    new SqliteHelper().execSQL("insert or replace into hyqqNum(fqr,isread) values(?,?)", new Object[]
                            {
                                    code,"false"
                            });
                    finish();
                } else {
                    ToastUtils.Toast(context, HintTool.Add_Fail);
                }

            }

            @Override
            public void onMyError(VolleyError error) {
                if (loadDiaog.isShowing()) {
                    loadDiaog.dismiss();
                }
                ToastUtils.Toast(context, HintTool.Add_Fail);
            }
        });
    }
}
