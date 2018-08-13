package com.example.app3.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.app3.base.BaseRecyclerViewActivity;
import com.example.app3.base_recyclear_adapter.HeaderAdapterWrapper;
import com.example.app3.base_recyclear_adapter.QuickAdapter;
import com.example.app3.entity.MyClassEntity;
import com.example.app3.tool.HintTool;
import com.example.app3.tool.JSONTool;
import com.example.app3.view.MyTitleView;
import com.yhkj.cqgyxy.R;
import com.yunhuakeji.app.utils.MapTools;

import org.androidpn.push.Constants;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import yh.app.tool.SqliteHelper;
import yh.app.tool.Ticket;
import yh.app.utils.GetWindowsWH;
import yh.app.utils.GsonImpl;
import yh.app.utils.ToastUtils;
import yh.app.utils.VolleyInterface;
import yh.app.utils.VolleyRequest;
import yh.tool.widget.LoadDiaog;
import 云华.智慧校园.工具._链接地址导航;

/**
 * Created by Administrator on 2017/10/13.
 * <p>
 * 我的班级界面
 */

public class MyClassActivity extends BaseRecyclerViewActivity {

    @BindView(R.id.myreport_title)
    MyTitleView myreportTitle;
    @BindView(R.id.myreport_rec)
    RecyclerView myreportRec;
    @BindView(R.id.no_data_lin)
    LinearLayout noDataLin;
    private RecyclerView headRecyclerView;

    private List<Map<String, String>> list;

    private QuickAdapter headAdapter;
    private QuickAdapter adapter;
    private HeaderAdapterWrapper headerAdapterWrapper;

    private TextView jxbView, xzbView;
    private LoadDiaog loadDiaog;


    private List<MyClassEntity.ContentBean.JxbBean.ListBean> jxbList;
    private List<MyClassEntity.ContentBean.XzbBean.ListBeanX> xzbList;

    private int weight;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_report;
    }

    @Override
    protected void setTitle(Context context) {
        myreportTitle.setTitle("我的班级", context);
        myreportTitle.setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void loadRecyclerViewData(Context context) {
        getData(context, list.get(0).get("usertype"));
    }

    @Override
    protected void init(Context context) {
        list = new SqliteHelper().rawQuery("select * from usertype");
        weight = GetWindowsWH.GetW(context);
        weight = weight / 2;
        loadDiaog = new LoadDiaog(context);
        loadDiaog.show();
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
     * @param usertype 用户类型
     */
    private void getData(final Context context, String usertype) {
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
                                "usertype", usertype
                        }
                });
        String url = _链接地址导航.GroupChat.getXSBJLB.getUrl();
        if ("南宁职业技术学院".equals(Constants.xxmc)) {
            url = "http://bigdata.ncvt.net:8082/GroupChat/Contacts/getXSBJLB.action";
        }
        VolleyRequest.RequestPost(url, param, new VolleyInterface() {
            @Override
            public void onMySuccess(String result) {
                if (loadDiaog.isShowing()) {
                    loadDiaog.dismiss();
                }
                if (JSONTool.SUCCESS.equals(JSONTool.Code(result))) {
                    loadRecyclerViewData(context, result);
                } else {
                    noDataLin.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onMyError(VolleyError error) {
                if (loadDiaog.isShowing()) {
                    loadDiaog.dismiss();
                }
                noDataLin.setVisibility(View.VISIBLE);
            }
        });
    }

    /**
     * 加载数据
     */
    private void loadRecyclerViewData(final Context context, String json) {
        final MyClassEntity entity = GsonImpl.get().toObject(json, MyClassEntity.class);
        View headView = LayoutInflater.from(context).inflate(R.layout.myclass_head, null, false);
        headRecyclerView = (RecyclerView) headView.findViewById(R.id.myclass_rec_head);
        jxbView = (TextView) headView.findViewById(R.id.myclass_head_text_jxb);
        xzbView = (TextView) headView.findViewById(R.id.myclass_head_text_xzb);
        try {
            jxbList = entity.getContent().getJxb().getList();
            adapter = new QuickAdapter<MyClassEntity.ContentBean.JxbBean.ListBean>(jxbList) {
                @Override
                public int getLayoutId(int viewType) {
                    return R.layout.item_newfr;
                }

                @Override
                public void convert(VH holder, final MyClassEntity.ContentBean.JxbBean.ListBean data, int position) {
                    holder.setTextView(R.id.item_newfr_text_name, data.getKcmc()).setMaxWidth(weight);
                    holder.setImageView((Activity) context, R.id.item_newfr_image, data.getFaceaddress());
                    holder.forbidSideslip();
                    try {
                        holder.setTextView(R.id.item_newfr_text_introduce, new SqliteHelper().rawQuery("select * from client_notice where function_id=? order by n_send_time desc", data.getId()).get(0).get("n_message"));
                        holder.setTextView(R.id.item_newfr_xxjsq, new SqliteHelper().rawQuery("select * from client_notice where function_id=? and read=?", data.getId(), "false").size() + "").setVisibility(View.VISIBLE);
                    } catch (Exception e) {
                        holder.setTextView(R.id.item_newfr_text_introduce, "无消息");
                    }
                    holder.setTextView(R.id.item_newfr_text_state, data.getCount() + "人").setTextColor(ContextCompat.getColor(context, R.color.color_gray_999999));
                    holder.setTextView(R.id.item_newfr_text_state, data.getCount() + "人").setBackground(null);
                    holder.setRelativeLayout(R.id.item_newfr_rel).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(context, AllMessageList.class);
                            intent.putExtra("function_id", data.getId());
                            intent.putExtra("title", data.getKcmc());
                            intent.putExtra("type", entity.getContent().getJxb().getType());
                            context.startActivity(intent);
                        }
                    });
                }

            };
            headerAdapterWrapper = new HeaderAdapterWrapper(adapter);
            headRecyclerView.setVisibility(View.VISIBLE);
            jxbView.setVisibility(View.VISIBLE);
            xzbView.setVisibility(View.VISIBLE);
            xzbList = entity.getContent().getXzb().getList();
            headAdapter = new QuickAdapter<MyClassEntity.ContentBean.XzbBean.ListBeanX>(xzbList) {
                @Override
                public int getLayoutId(int viewType) {
                    return R.layout.item_newfr;
                }

                @Override
                public void convert(VH holder, final MyClassEntity.ContentBean.XzbBean.ListBeanX data, int position) {
                    holder.setTextView(R.id.item_newfr_text_name, data.getKcmc()).setMaxWidth(weight);
                    holder.setImageView((Activity) context, R.id.item_newfr_image, data.getFaceaddress());
                    holder.forbidSideslip();
                    try {
                        holder.setTextView(R.id.item_newfr_text_introduce, new SqliteHelper().rawQuery("select * from client_notice where n_id=? order by n_send_time desc", data.getId()).get(0).get("n_message"));
                        holder.setTextView(R.id.item_newfr_xxjsq, new SqliteHelper().rawQuery("select * from client_notice where n_id=? and read=?", data.getId(), "false").size() + "").setVisibility(View.VISIBLE);
                    } catch (Exception e) {
                        holder.setTextView(R.id.item_newfr_text_introduce, "无消息");
                    }
                    holder.setTextView(R.id.item_newfr_text_state, data.getCount() + "人").setTextColor(ContextCompat.getColor(context, R.color.color_gray_999999));
                    holder.setTextView(R.id.item_newfr_text_state, data.getCount() + "人").setBackground(null);
                    holder.setRelativeLayout(R.id.item_newfr_rel).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(context, AllMessageList.class);
                            intent.putExtra("function_id", data.getId());
                            intent.putExtra("title", data.getKcmc());
                            intent.putExtra("type", entity.getContent().getXzb().getType());
                            context.startActivity(intent);
                        }
                    });
                }

            };
            headRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            headRecyclerView.setAdapter(headAdapter);
        } catch (Exception e) {
            try {
                xzbList = entity.getContent().getXzb().getList();
                adapter = new QuickAdapter<MyClassEntity.ContentBean.XzbBean.ListBeanX>(xzbList) {
                    @Override
                    public int getLayoutId(int viewType) {
                        return R.layout.item_newfr;
                    }

                    @Override
                    public void convert(VH holder, final MyClassEntity.ContentBean.XzbBean.ListBeanX data, int position) {
                        holder.setTextView(R.id.item_newfr_text_name, data.getKcmc()).setMaxWidth(weight);
                        holder.setImageView((Activity) context, R.id.item_newfr_image, data.getFaceaddress());
                        holder.forbidSideslip();
                        try {
                            holder.setTextView(R.id.item_newfr_text_introduce, new SqliteHelper().rawQuery("select * from client_notice where n_id=? order by n_send_time desc", data.getId()).get(0).get("n_message"));
                            holder.setTextView(R.id.item_newfr_xxjsq, new SqliteHelper().rawQuery("select * from client_notice where n_id=? and read=?", data.getId(), "false").size() + "").setVisibility(View.VISIBLE);
                        } catch (Exception e) {
                            holder.setTextView(R.id.item_newfr_text_introduce, "无消息");
                        }
                        holder.setTextView(R.id.item_newfr_text_state, data.getCount() + "人").setTextColor(ContextCompat.getColor(context, R.color.color_gray_999999));
                        holder.setTextView(R.id.item_newfr_text_state, data.getCount() + "人").setBackground(null);
                        holder.setRelativeLayout(R.id.item_newfr_rel).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(context, AllMessageList.class);
                                intent.putExtra("function_id", data.getId());
                                intent.putExtra("title", data.getKcmc());
                                intent.putExtra("type", entity.getContent().getXzb().getType());
                                context.startActivity(intent);
                            }
                        });
                    }

                };
                xzbView.setVisibility(View.VISIBLE);
                headerAdapterWrapper = new HeaderAdapterWrapper(adapter);
            } catch (Exception e1) {
                jxbList = entity.getContent().getJxb().getList();
                adapter = new QuickAdapter<MyClassEntity.ContentBean.JxbBean.ListBean>(jxbList) {
                    @Override
                    public int getLayoutId(int viewType) {
                        return R.layout.item_newfr;
                    }

                    @Override
                    public void convert(VH holder, final MyClassEntity.ContentBean.JxbBean.ListBean data, int position) {
                        holder.setTextView(R.id.item_newfr_text_name, data.getKcmc()).setMaxWidth(weight);
                        holder.setImageView((Activity) context, R.id.item_newfr_image, data.getFaceaddress());
                        holder.forbidSideslip();
                        try {
                            holder.setTextView(R.id.item_newfr_text_introduce, new SqliteHelper().rawQuery("select * from client_notice where n_id=? order by n_send_time desc", data.getId()).get(0).get("n_message"));
                            holder.setTextView(R.id.item_newfr_xxjsq, new SqliteHelper().rawQuery("select * from client_notice where n_id=? and read=?", data.getId(), "false").size() + "").setVisibility(View.VISIBLE);
                        } catch (Exception e) {
                            holder.setTextView(R.id.item_newfr_text_introduce, "无消息");
                        }
                        holder.setTextView(R.id.item_newfr_text_state, data.getCount() + "人").setTextColor(ContextCompat.getColor(context, R.color.color_gray_999999));
                        holder.setTextView(R.id.item_newfr_text_state, data.getCount() + "人").setBackground(null);
                        holder.setRelativeLayout(R.id.item_newfr_rel).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(context, AllMessageList.class);
                                intent.putExtra("function_id", data.getId());
                                intent.putExtra("title", data.getKcmc());
                                intent.putExtra("type", entity.getContent().getJxb().getType());
                                context.startActivity(intent);
                            }
                        });
                    }

                };
                jxbView.setVisibility(View.VISIBLE);
                headerAdapterWrapper = new HeaderAdapterWrapper(adapter);
            }

        }
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
        headView.setLayoutParams(params);
        headerAdapterWrapper.addHeaderView(headView);

        myreportRec.setLayoutManager(new LinearLayoutManager(context));
        myreportRec.setAdapter(headerAdapterWrapper);

    }

}
