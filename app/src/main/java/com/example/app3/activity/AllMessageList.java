package com.example.app3.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.app3.base.BaseRecyclerViewActivity;
import com.example.app3.base_recyclear_adapter.QuickAdapter;
import com.example.app3.eventbus.MyEventBus;
import com.example.app3.tool.HintTool;
import com.example.app3.tool.TimeTool;
import com.example.app3.tool.Tool;
import yh.app.appstart.lg.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import yh.app.contacts.SendNotificationByTeacher;
import yh.app.tool.SqliteHelper;
import 云华.智慧校园.工具.CodeManage;

/**
 * Created by Administrator on 2017/9/30.
 */

public class AllMessageList extends BaseRecyclerViewActivity {

    @BindView(R.id.am_rel_return)
    RelativeLayout amRelReturn;
    @BindView(R.id.am_text_title)
    TextView amTextTitle;
    @BindView(R.id.am_rel_peopel)
    RelativeLayout amRelPeopel;
    @BindView(R.id.am_rel_edit)
    RelativeLayout amRelEdit;
    @BindView(R.id.am_lin_title)
    RelativeLayout amLinTitle;
    @BindView(R.id.myreport_rec)
    RecyclerView myreportRec;
    @BindView(R.id.no_data_lin)
    LinearLayout noDataLin;
    private String function_id;
    private List<Map<String, String>> client_noticeList, new_push_functionList;

    private String title;
    private QuickAdapter adapter;

    /**
     * 我的班级穿过来的数据
     */
    private String type;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_allmessage;
    }

    @Override
    protected void setTitle(Context context) {
        amTextTitle.setText(title);
    }

    @Override
    protected void loadRecyclerViewData(final Context context) {
        adapter = new QuickAdapter<Map<String, String>>(client_noticeList) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_all_message_list;
            }

            @Override
            public void convert(VH holder, final Map<String, String> data, final int position) {
                Long stamp = Long.valueOf(TimeTool.date2TimeStamp(data.get("n_send_time"), "yyyy-MM-dd HH:mm:ss"));
                holder.setTextView(R.id.allmessage_text_definiteTime, TimeTool.TimeStamp2date(stamp, "yyyy-MM-dd") + "  " + TimeTool.TimeStamp2date(stamp, "HH:mm:ss"));
                holder.setImageView((Activity) context, R.id.allmessage_image_icon, data.get("n_picpath"));
                holder.setTextView(R.id.allmessage_text_time, TimeTool.TimeStamp2date(stamp, "yyyy-MM-dd HH:mm:ss"));
                if ("true".equals(data.get("read"))) {
                    holder.setImageView((Activity) context, R.id.allmessage_image_circleRed, R.drawable.circle_red).setVisibility(View.INVISIBLE);
                    holder.setTextView(R.id.allmessage_text_from, data.get("n_from")).setTextColor(ContextCompat.getColor(context, R.color.color_gray_999999));
                    holder.setTextView(R.id.allmessage_text_title, data.get("n_title")).setTextColor(ContextCompat.getColor(context, R.color.color_gray_999999));
                    holder.setTextView(R.id.allmessage_text_message, data.get("n_message")).setTextColor(ContextCompat.getColor(context, R.color.color_gray_999999));
                    holder.setTextView(R.id.allmessage_text_time, TimeTool.TimeStamp2date(stamp, "yyyy-MM-dd HH:mm:ss")).setTextColor(ContextCompat.getColor(context, R.color.color_gray_999999));
                    holder.setTextView(R.id.allmessage_text_detail, "查看详情 >").setTextColor(ContextCompat.getColor(context, R.color.color_gray_999999));
                } else {
                    holder.setImageView((Activity) context, R.id.allmessage_image_circleRed, R.drawable.circle_red).setVisibility(View.VISIBLE);
                    holder.setTextView(R.id.allmessage_text_from, data.get("n_from")).setTextColor(ContextCompat.getColor(context, R.color.color_dark_333333));
                    holder.setTextView(R.id.allmessage_text_title, data.get("n_title")).setTextColor(ContextCompat.getColor(context, R.color.color_dark_333333));
                    holder.setTextView(R.id.allmessage_text_message, data.get("n_message")).setTextColor(ContextCompat.getColor(context, R.color.color_somber));
                    holder.setTextView(R.id.allmessage_text_detail, "查看详情 >").setTextColor(ContextCompat.getColor(context, R.color.color_somber));
                }

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new SqliteHelper().rawQuery("update client_notice set read=? where n_id=?", "true", data.get("n_id"));
                        new SqliteHelper().rawQuery("update client_notice_messagelist set read=? where n_id=?", "true", data.get("n_id"));
                        Intent intent = null;
                        if (!Tool.isFastDoubleClick()) {
                            switch (data.get("code")) {
                                case CodeManage.TEXT_PUSH:
                                    intent = new Intent(context, DetailActivity.class);
                                    intent.putExtra("id", data.get("n_id"));
                                    break;
                                case CodeManage.URL_PUSH:
                                    intent = new Intent(context, PushBrowserActivity.class);
                                    intent.putExtra("url", data.get("n_url"));
                                    intent.putExtra("title", "消息");
                                    break;
                            }
                            if (intent != null) {
                                context.startActivity(intent);
                            }
                        }
                    }
                });
            }
        };
        myreportRec.setLayoutManager(new LinearLayoutManager(context));
        myreportRec.setAdapter(adapter);
    }

    @Override
    protected void init(final Context context) {
        function_id = getIntent().getExtras().getString("function_id");
        if (client_noticeList == null) {
            client_noticeList = new ArrayList<>();
            client_noticeList.addAll(new SqliteHelper().rawQuery("select * from client_notice where function_id=? order by n_send_time desc", function_id));//根据function_id查询相关数据
            if (client_noticeList.size() == 0) {
                noDataLin.setVisibility(View.VISIBLE);
            }
        }
        new_push_functionList = new SqliteHelper().rawQuery("select * from new_push_function where ts_id=?", function_id);
        try {
            title = new_push_functionList.get(0).get("ts_name");
        } catch (Exception e) {
            title = getIntent().getExtras().getString("title");
        }

        type = getIntent().getExtras().getString("type");
        if (type != null) {
            if ("1".equals(new SqliteHelper().rawQuery("select * from usertype").get(0).get("usertype"))) {
                amRelPeopel.setVisibility(View.VISIBLE);
            } else {
                amRelPeopel.setVisibility(View.VISIBLE);
                amRelEdit.setVisibility(View.VISIBLE);
            }
        }
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

    @Override
    protected void onResume() {
        super.onResume();
        if (adapter != null) {
            if (client_noticeList != null) {
                client_noticeList.removeAll(client_noticeList);
            }
            //(2017-2018-1)-10402061-2010010318-1
            client_noticeList.addAll(new SqliteHelper().rawQuery("select * from client_notice where function_id=? order by n_send_time desc", function_id));//根据function_id查询相关数据
            handler.sendEmptyMessage(0);
        }
    }

    @Subscribe
    public void onEventMainThread(MyEventBus event) {
        if (event.getMsg().equals(HintTool.Receive_Push_Message)) {//接受到推送更新数据
            if (adapter != null) {
                if (client_noticeList != null) {
                    client_noticeList.removeAll(client_noticeList);
                }
                client_noticeList.addAll(new SqliteHelper().rawQuery("select * from client_notice where function_id=? order by n_send_time desc", function_id));//根据function_id查询相关数据
                handler.sendEmptyMessage(0);
            }
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (client_noticeList.size() == 0) {
                        noDataLin.setVisibility(View.VISIBLE);
                    }
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    @OnClick({R.id.am_rel_return, R.id.am_rel_edit, R.id.am_rel_peopel})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.am_rel_return:
                finish();
                break;
            case R.id.am_rel_edit:
                intent = new Intent(AllMessageList.this, SendNotificationByTeacher.class);
                intent.putExtra("function_id", function_id);
                break;
            case R.id.am_rel_peopel:
                intent = new Intent(AllMessageList.this, ClassAllPeopleActivity.class);
                intent.putExtra("function_id", function_id);
                intent.putExtra("title", title);
                intent.putExtra("type", type);
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }
}
