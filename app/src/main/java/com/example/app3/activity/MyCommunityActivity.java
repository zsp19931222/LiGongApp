package com.example.app3.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.android.volley.VolleyError;
import com.example.app3.adapter.ContactAdapter;
import com.example.app3.adapter.dragrecyclear.common.DividerItemDecoration;
import com.example.app3.base.BaseRecyclerViewActivity;
import com.example.app3.entity.MyCommunityEntity;
import com.example.app3.tool.HintTool;
import com.example.app3.view.MyTitleView;
import com.yhkj.cqgyxy.R;
import com.yunhuakeji.app.utils.MapTools;

import org.androidpn.push.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import yh.app.tool.Ticket;
import yh.app.utils.GsonImpl;
import yh.app.utils.ToastUtils;
import yh.app.utils.VolleyInterface;
import yh.app.utils.VolleyRequest;
import yh.tool.widget.LoadDiaog;
import 云华.智慧校园.工具._链接地址导航;

/**
 * Created by Administrator on 2017/10/18.
 * <p>
 * 我的社团
 */

public class MyCommunityActivity extends BaseRecyclerViewActivity {
    @BindView(R.id.myreport_title)
    MyTitleView myreportTitle;
    @BindView(R.id.myreport_rec)
    RecyclerView myreportRec;
    @BindView(R.id.no_data_lin)
    LinearLayout noDataLin;

    private LinearLayoutManager layoutManager;
    private ContactAdapter contactAdapter;
    private List<Map<String, String>> contactNames;

    private List<MyCommunityEntity.MessageBean> messageList;

    private LoadDiaog loadDiaog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_report;
    }

    @Override
    protected void setTitle(Context context) {
        myreportTitle.setTitle("我的社团", context);
        myreportTitle.setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void loadRecyclerViewData(final Context context) {
        Map<String, String> param = MapTools.buildMap(new String[][]
                {
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
        VolleyRequest.RequestPost(_链接地址导航.DC.圈子默认列表.getUrl(), param, new VolleyInterface() {
            @Override
            public void onMySuccess(String result) {
                String backlogJsonStrTmp = result.replace("\\", "");
                result = backlogJsonStrTmp.substring(1, backlogJsonStrTmp.length() - 1);
                MyCommunityEntity entity = GsonImpl.get().toObject(result, MyCommunityEntity.class);
                if (entity.isBooleanX()) {
                    messageList = entity.getMessage();
                    if (messageList.size()==0){
                        noDataLin.setVisibility(View.VISIBLE);
                    }
                    for (int i = 0; i < messageList.size(); i++) {
                        Map<String, String> map = new HashMap<>();
                        map.put("name", messageList.get(i).getYHBZ());
                        map.put("headimg", messageList.get(i).getFACEADDRESS() + "");
                        map.put("headimg", messageList.get(i).getFACEADDRESS() + "");
                        map.put("FRIEND_ID", messageList.get(i).getHYBH());
                        contactNames.add(map);
                    }
                    contactAdapter = new ContactAdapter(context, contactNames);
                    myreportRec.setLayoutManager(layoutManager);
                    myreportRec.setAdapter(contactAdapter);

                } else {
                    ToastUtils.Toast(context, HintTool.REQUESTFAIL);
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
                noDataLin.setVisibility(View.VISIBLE);
                ToastUtils.Toast(context, HintTool.REQUESTFAIL);
            }
        });
    }

    @Override
    protected void init(Context context) {
        loadDiaog = new LoadDiaog(context);
        loadDiaog.show();
        contactNames = new ArrayList<>();
        layoutManager = new LinearLayoutManager(context);
        myreportRec.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL_LIST));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
