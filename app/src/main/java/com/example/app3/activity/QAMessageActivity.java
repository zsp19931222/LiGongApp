package com.example.app3.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.example.app3.base.BaseRecyclerViewActivity;
import com.example.app3.base_recyclear_adapter.QuickAdapter;
import com.example.app3.tool.Tool;
import com.example.app3.view.MyTitleView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import yh.app.appstart.lg.R;
import yh.app.tool.SqliteHelper;
import 云华.智慧校园.工具.CodeManage;

/**
 * Created by Administrator on 2017/10/13.
 */

public class QAMessageActivity extends BaseRecyclerViewActivity {
    @BindView(R.id.myreport_title)
    MyTitleView myreportTitle;
    @BindView(R.id.myreport_rec)
    RecyclerView myreportRec;
    @BindView(R.id.no_data_lin)
    LinearLayout noDataLin;

    private List<Map<String, String>> list;
    private List<Map<String, String>> kindList;
    private Map<String, String> appMap;
    private Map<String, String> subscibeMap;

    private QuickAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_report;
    }

    @Override
    protected void setTitle(Context context) {
        myreportTitle.setTitle("官方消息", context);
        myreportTitle.setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void loadRecyclerViewData(final Context context) {
        list = new ArrayList<>();
        kindList = new SqliteHelper().rawQuery("select distinct ts_id from new_push_function");
        appMap = new HashMap<>();
        subscibeMap = new HashMap<>();
        for (int i = 0; i < kindList.size(); i++) {
            Map<String, String> map = new HashMap<>();
            if (CodeManage.APP_Messsage_Group_ID.equals(new SqliteHelper().rawQuery("select * from new_push_function where ts_id=?", kindList.get(i).get("ts_id")).get(0).get("ts_group"))) {//应用消息
                appMap.put("title", new SqliteHelper().rawQuery("select * from new_push_group where ts_group_id=?", CodeManage.APP_Messsage_Group_ID).get(0).get("ts_group_name"));
                appMap.put("image", new SqliteHelper().rawQuery("select * from new_push_group where ts_group_id=?", CodeManage.APP_Messsage_Group_ID).get(0).get("ts_group_icon"));
                if (new SqliteHelper().rawQuery("select * from client_notice where function_id=?", kindList.get(i).get("ts_id")).size() != 0) {
                    appMap.put("message", new SqliteHelper().rawQuery("select * from client_notice where function_id=?", kindList.get(i).get("ts_id")).get(0).get("n_message"));
                } else {
                    appMap.put("message", "无");
                }
                appMap.put("id", kindList.get(i).get("ts_id"));
                appMap.put("type", "group");
            } else if (CodeManage.Subscibe_Message_Group_ID.equals(new SqliteHelper().rawQuery("select * from new_push_function where ts_id=?", kindList.get(i).get("ts_id")).get(0).get("ts_group"))) {//订阅消息
                subscibeMap.put("title", new SqliteHelper().rawQuery("select * from new_push_group where ts_group_id=?", CodeManage.Subscibe_Message_Group_ID).get(0).get("ts_group_name"));
                subscibeMap.put("image", new SqliteHelper().rawQuery("select * from new_push_group where ts_group_id=?", CodeManage.Subscibe_Message_Group_ID).get(0).get("ts_group_icon"));
                if (new SqliteHelper().rawQuery("select * from client_notice where function_id=?", kindList.get(i).get("ts_id")).size() != 0) {
                    subscibeMap.put("message", new SqliteHelper().rawQuery("select * from client_notice where function_id=?", kindList.get(i).get("ts_id")).get(0).get("n_message"));
                } else {
                    subscibeMap.put("message", "无");
                }
                subscibeMap.put("id", kindList.get(i).get("ts_id"));
                subscibeMap.put("type", "group");
            } else {//功能消息
                map.put("title", new SqliteHelper().rawQuery("select * from new_push_function where ts_id=?", kindList.get(i).get("ts_id")).get(0).get("ts_name"));
                map.put("image", new SqliteHelper().rawQuery("select * from new_push_function where ts_id=?", kindList.get(i).get("ts_id")).get(0).get("ts_icon"));
                if (new SqliteHelper().rawQuery("select * from client_notice where function_id=?", kindList.get(i).get("ts_id")).size() != 0) {
                    map.put("message", new SqliteHelper().rawQuery("select * from client_notice where function_id=?", kindList.get(i).get("ts_id")).get(0).get("n_message"));
                } else {
                    map.put("message", "无");
                }
                map.put("id", kindList.get(i).get("ts_id"));
                map.put("type", "function");
            }
            if (map.size() != 0) {
                list.add(map);
            }
        }
        if (appMap.size() != 0) {
            list.add(appMap);
        }
        if (subscibeMap.size() != 0) {
            list.add(subscibeMap);
        }
        if (list.size() != 0) {

            adapter = new QuickAdapter<Map<String, String>>(list) {
                @Override
                public int getLayoutId(int viewType) {
                    return R.layout.item_newfr;
                }

                @Override
                public void convert(VH holder, final Map<String, String> data, int position) {
                    holder.setImageView((Activity) context, R.id.item_newfr_image, data.get("image"));
                    holder.setTextView(R.id.item_newfr_text_name, data.get("title"));
                    holder.setTextView(R.id.item_newfr_text_introduce, data.get("message"));
                    holder.setTextView(R.id.item_newfr_text_state, "").setVisibility(View.GONE);
                    holder.forbidSideslip();
                    holder.setRelativeLayout(R.id.item_newfr_rel).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!Tool.isFastDoubleClick()) {
                                Intent intent = null;
                                switch (data.get("type")) {
                                    case "group":
                                        intent = new Intent(context, ListActivity.class);
                                        break;
                                    case "function":
                                        intent = new Intent(context, AllMessageList.class);
                                        break;
                                }
                                if (intent != null) {
                                    intent.putExtra("function_id", data.get("id"));
                                    context.startActivity(intent);
                                }
                            }
                        }
                    });
                }

            };
            myreportRec.setLayoutManager(new LinearLayoutManager(context));
            myreportRec.setAdapter(adapter);
        } else {
            noDataLin.setVisibility(View.VISIBLE);
            myreportRec.setVisibility(View.GONE);
        }
    }

    @Override
    protected void init(Context context) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
