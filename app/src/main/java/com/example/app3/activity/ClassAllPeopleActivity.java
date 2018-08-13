package com.example.app3.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.android.volley.VolleyError;
import com.example.app3.base.BaseRecyclerViewActivity;
import com.example.app3.base_recyclear_adapter.HeaderAdapterWrapper;
import com.example.app3.base_recyclear_adapter.QuickAdapter;
import com.example.app3.entity.ClassEntity;
import com.example.app3.search.Group;
import com.example.app3.tool.HintTool;
import com.example.app3.tool.JSONTool;
import com.example.app3.tool.Tool;
import com.example.app3.view.MyTitleView;
import com.yhkj.cqgyxy.R;
import com.yunhuakeji.app.utils.MapTools;

import org.androidpn.push.Constants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import yh.app.tool.Ticket;
import yh.app.utils.GsonImpl;
import yh.app.utils.ToastUtils;
import yh.app.utils.VolleyInterface;
import yh.app.utils.VolleyRequest;
import 云华.智慧校园.工具._链接地址导航;

/**
 * Created by Administrator on 2017/10/19.
 * <p>
 * 班级详情
 */

public class ClassAllPeopleActivity extends BaseRecyclerViewActivity {
    @BindView(R.id.myreport_title)
    MyTitleView myreportTitle;
    @BindView(R.id.myreport_rec)
    RecyclerView myreportRec;
    @BindView(R.id.no_data_lin)
    LinearLayout noDataLin;

    private String title;
    private String type;
    private String function_id;

    private QuickAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_report;
    }

    @Override
    protected void setTitle(Context context) {
        myreportTitle.setTitle(title, context);
        myreportTitle.setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void loadRecyclerViewData(Context context) {
        getData(context, type, function_id);
    }

    @Override
    protected void init(Context context) {
        type = getIntent().getExtras().getString("type");
        function_id = getIntent().getExtras().getString("function_id");
        title = getIntent().getExtras().getString("title");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    private String url;

    /**
     * 获取数据
     *
     * @param function_id 班级ID
     * @param type        班级类型
     */
    private void getData(final Context context, String type, String function_id) {
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
                        {
                                "type", type
                        },
                        {
                                "kcid", function_id
                        }
                });
        VolleyRequest.RequestPost(_链接地址导航.GroupChat.getXSBJLBXQ.getUrl(), param, new VolleyInterface() {
            @Override
            public void onMySuccess(String result) {
                if (JSONTool.SUCCESS.equals(JSONTool.Code(result))) {
                    loadRecyclerViewData(context, result);
                } else {
                    ToastUtils.Toast(context, HintTool.REQUESTFAIL);
                }
            }

            @Override
            public void onMyError(VolleyError error) {
                ToastUtils.Toast(context, HintTool.REQUESTFAIL);
            }
        });
    }

    /**
     * 加载数据
     */
    private List<Group> allAppGroups;
    private List<ClassEntity.ContentBean.UserlistBean> userlist;

    private void loadRecyclerViewData(final Context context, String json) {
        allAppGroups = new ArrayList<>();
        final ClassEntity entity = GsonImpl.get().toObject(json, ClassEntity.class);
        userlist = entity.getContent().getUserlist();
        for (int i = 0; i < userlist.size(); i++) {
            allAppGroups.add(new Group(userlist.get(i).getUserid(), userlist.get(i).getFaceaddress(), userlist.get(i).getName(), Constants.number, ""));
        }
        adapter = new QuickAdapter<ClassEntity.ContentBean.UserlistBean>(userlist) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_contact;
            }

            @Override
            public void convert(VH holder, final ClassEntity.ContentBean.UserlistBean data, int position) {
                holder.setImageView((Activity) context, R.id.contact_image1, data.getFaceaddress(), R.drawable.np, 0);
                holder.setTextView(R.id.contact_name, data.getName());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!Tool.isFastDoubleClick()) {
                            Intent intent = new Intent(context, FriendDetailActivity.class);
                            intent.putExtra("fqr", data.getUserid());
                            context.startActivity(intent);
                        }
                    }
                });
            }

        };
        HeaderAdapterWrapper headerAdapterWrapper = new HeaderAdapterWrapper(adapter);
        View headView = LayoutInflater.from(context).inflate(R.layout.head_search, null, false);
        LinearLayout searchView = (LinearLayout) headView.findViewById(R.id.head_address_lin_search);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SearchFriendNetActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("list", (Serializable) allAppGroups);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
        headView.setLayoutParams(params);
        headerAdapterWrapper.addHeaderView(headView);
        myreportRec.setLayoutManager(new LinearLayoutManager(context));
        myreportRec.setAdapter(headerAdapterWrapper);
    }
}
