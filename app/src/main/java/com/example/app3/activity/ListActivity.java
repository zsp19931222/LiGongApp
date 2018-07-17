package com.example.app3.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.app3.base.BaseRecyclerViewActivity;
import com.example.app3.base_recyclear_adapter.QuickAdapter;
import com.example.app3.eventbus.MyEventBus;
import com.example.app3.tool.HintTool;
import com.example.app3.tool.TimeTool;
import com.example.app3.tool.Tool;
import com.example.app3.view.MyTitleView;
import yh.app.appstart.lg.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import yh.app.logTool.Log;
import yh.app.tool.SqliteHelper;
import yh.app.utils.ToastUtils;

/**
 * Created by Administrator on 2017/9/30.
 * <p>
 * 分组列表
 */

public class ListActivity extends BaseRecyclerViewActivity {
    @BindView(R.id.myreport_title)
    MyTitleView myreportTitle;
    @BindView(R.id.myreport_rec)
    RecyclerView myreportRec;

    private String function_id;
    private String title;

    private List<Map<String, String>> new_push_functionList, new_push_groupList, client_noticeList;

    QuickAdapter adapter;

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
    protected void loadRecyclerViewData(final Context context) {
        adapter = new QuickAdapter<Map<String, String>>(new_push_functionList) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.view_home_message_chat_item;
            }

            @Override
            public void convert(VH holder, final Map<String, String> data, final int position) {
                if (client_noticeList != null) {
                    client_noticeList.clear();
                }
                holder.forbidSideslip();
                client_noticeList = new SqliteHelper().rawQuery("select * from client_notice where function_id=? order by n_id desc", data.get("ts_id"));
                holder.setTextView(R.id.xxjsq, new SqliteHelper().rawQuery("select * from client_notice where function_id=? and read=? ", data.get("ts_id"), "false").size() + "");
                holder.setTextView(R.id.item_message_text_name, data.get("ts_name"));
                holder.setTextView(R.id.item_message_text_from, "").setVisibility(View.INVISIBLE);

                holder.setTextView(R.id.item_message_text_messageTop, "取消订阅").setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!Tool.isFastDoubleClick()) {
                            ToastUtils.Toast(context, "取消订阅 " + position);
                        }
                    }
                });
                holder.setTextView(R.id.item_message_text_del, "删除").setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!Tool.isFastDoubleClick()) {
                            ToastUtils.Toast(context, "删除 " + position);
                        }
                    }
                });
                holder.setLinearLayout(R.id.item_message_lin).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!Tool.isFastDoubleClick()) {
                            Intent intent = new Intent(context, AllMessageList.class);
                            intent.putExtra("function_id", data.get("ts_id"));
                            context.startActivity(intent);
                        }
                    }
                });
                if (client_noticeList.size() != 0) {
                    holder.setTextView(R.id.item_message_text_explain, client_noticeList.get(0).get("n_message"));
                    holder.setTextView(R.id.item_message_text_time, TimeTool.getDateSx(Long.valueOf(TimeTool.date2TimeStamp(client_noticeList.get(0).get("n_send_time"), "yyyy-MM-dd HH:mm:ss"))) + "  " + TimeTool.TimeStamp2date(Long.valueOf(TimeTool.date2TimeStamp(client_noticeList.get(0).get("n_send_time"), "yyyy-MM-dd HH:mm:ss")), "HH:mm"));
                    holder.setImageView((Activity) context, R.id.item_message_image_user, data.get("ts_icon"));
                } else {
                    holder.setTextView(R.id.item_message_text_explain, "当前没有" + data.get("ts_name") + "消息");
                    holder.setTextView(R.id.item_message_text_time, "");
                    holder.setImageView((Activity) context, R.id.item_message_image_user, data.get("ts_icon"));
                }
            }
        };
        myreportRec.setLayoutManager(new LinearLayoutManager(context));
        myreportRec.setAdapter(adapter);
    }

    @Override
    protected void init(Context context) {
        function_id = getIntent().getExtras().getString("function_id");
        new_push_functionList = new SqliteHelper().rawQuery("select * from new_push_function where ts_id=?", function_id);//得到分组数据
        new_push_groupList = new SqliteHelper().rawQuery("select * from new_push_group where ts_group_id=?", new_push_functionList.get(0).get("ts_group"));
        new_push_functionList = new SqliteHelper().rawQuery("select * from new_push_function where ts_group=?", new_push_functionList.get(0).get("ts_group"));
        title = new_push_groupList.get(0).get("ts_group_name");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//反注册EventBus
    }

    @Subscribe
    public void onEventMainThread(MyEventBus event) {
        if (event.getMsg().equals(HintTool.Receive_Push_Message)) {//接受到推送更新数据
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }
}
