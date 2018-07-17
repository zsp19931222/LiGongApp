package com.example.app4.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.app3.activity.AllMessageList;
import com.example.app3.activity.FriendDetailActivity;
import com.example.app3.activity.ListActivity;
import com.example.app3.base_recyclear_adapter.QuickAdapter;
import com.example.app3.tool.HintTool;
import com.example.app3.tool.TimeTool;
import com.example.app3.tool.Tool;
import com.example.app4.base.BaseFragment;
import com.example.app4.presenter.MessageListFragmentPresenter;
import com.example.app4.tool.UserMessageTool;
import com.example.app4.util.NoDataViewUtil;
import com.example.app4.view.FunctionAppItemDecorationHorizontal;
import com.example.jpushdemo.body.BodyAdd;
import com.example.smartclass.eventbus.MessageEvent;
import com.example.smartclass.util.TagUtil;
import yh.app.appstart.lg.R;

import org.androidpn.push.Constants;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import yh.app.function.liaotianjiemian;
import yh.app.tool.SqliteHelper;

/**
 * Created by Administrator on 2018/4/21 0021.
 * <p>
 * 消息列表fragment
 */

public class MessageListFragment extends BaseFragment {
    @BindView(R.id.fragment_messagelist_rec)
    RecyclerView fragmentMessagelistRec;
    @BindView(R.id.no_data_image)
    ImageView noDataImage;
    @BindView(R.id.no_data_text)
    TextView noDataText;
    @BindView(R.id.no_data_lin)
    LinearLayout noDataLin;
    Unbinder unbinder;
    @BindView(R.id.fragment_message_parent_rel)
    RelativeLayout fragmentMessageParentRel;

    private List<Map<String, String>> messageList;
    private QuickAdapter<Map<String, String>> adapter;
    private Context context;
    private MessageListFragmentPresenter preserter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_messagelist;
    }

    @Override
    protected void initView() {
        context = getContext();
        preserter = new MessageListFragmentPresenter(context);
        init();
    }

    @Override
    @Subscribe
    public void onEventMainThread(MessageEvent event) {
        switch (event.getTag()) {
            case HintTool.Del_Message:
                handler.sendEmptyMessageDelayed(0, 50);
                break;
            case HintTool.Receive_Push_Message:
                init();
                break;
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    messageList.clear();
                    messageList.addAll(new SqliteHelper().rawQuery("select * from messageList where m_del=? order by m_top desc,m_time desc", "false"));
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        if (rootView != null) {
            unbinder = ButterKnife.bind(this, rootView);
        }
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private boolean isFirst = true;

    @Override
    public void onResume() {
        super.onResume();
        if (!isFirst) {
            init();
        }
        isFirst = false;
    }

    private void init() {
        preserter.getPushData();
        showRecyclerView();
    }

    private void showRecyclerView() {
        if (messageList == null) {
            messageList = new ArrayList<>();
            messageList.addAll(new SqliteHelper().rawQuery("select * from messageList where m_del=? order by m_top desc,m_time desc", "false"));
            adapter = new QuickAdapter<Map<String, String>>(messageList) {
                @Override
                public int getLayoutId(int viewType) {
                    return R.layout.view_home_message_chat_item;
                }

                @Override
                public void convert(VH holder, final Map<String, String> data, final int position) {
                    holder.setTextView(R.id.xxjsq, data.get("m_unread"));
                    holder.setTextView(R.id.item_message_text_name, data.get("m_group"));

                    if ("fr".equals(data.get("m_classify"))) {
                        if (BodyAdd.DEAL_NO.equals(data.get("m_message"))) {
                            holder.setTextView(R.id.item_message_text_explain, "好友请求");
                        } else {
                            String s;
                            try {
                                s = new SqliteHelper().rawQuery("select * from lt where userid=? and friend_id=? order by fssj desc", UserMessageTool.getUserId(), data.get("m_function_id")).get(0).get("message");
                            } catch (Exception e) {
                                s = "验证通过";
                            }
                            holder.setTextView(R.id.item_message_text_explain, s);
                        }
                        holder.setImageView((Activity) context, R.id.item_message_image_user, data.get("m_image"), R.drawable.np, 0);
                    } else {
                        holder.setTextView(R.id.item_message_text_explain, data.get("m_message"));
                        holder.setImageView((Activity) context, R.id.item_message_image_user, data.get("m_image"), R.drawable.push_defaults, 0);
                    }
                    holder.setTextView(R.id.item_message_text_time, TimeTool.getDateSx(Long.valueOf(TimeTool.date2TimeStamp(data.get("m_time"), "yyyy-MM-dd HH:mm:ss"))) + "  " + TimeTool.TimeStamp2date(Long.valueOf(TimeTool.date2TimeStamp(data.get("m_time"), "yyyy-MM-dd HH:mm:ss")), "HH:mm"));
                    if ("".equals(data.get("m_from"))) {
                        holder.setTextView(R.id.item_message_text_from, data.get("m_from")).setVisibility(View.INVISIBLE);
                    } else {
                        holder.setTextView(R.id.item_message_text_from, data.get("m_from")).setVisibility(View.VISIBLE);
                    }

                    if (data.get("m_top").equals("true")) {
                        holder.setTextView(R.id.item_message_text_messageTop, "取消置顶").setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (!Tool.isFastDoubleClick()) {
                                    for (int i = 0; i < messageList.size(); i++) {
                                        if (i == position) {
                                            new SqliteHelper().rawQuery("update messageList set m_top=? where m_group=? ", "false", messageList.get(i).get("m_group"));
                                        }
                                    }
                                    EventBus.getDefault().post(new MessageEvent(HintTool.Del_Message, ""));
                                }
                            }
                        });
                    } else {
                        holder.setTextView(R.id.item_message_text_messageTop, "消息置顶").setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (!Tool.isFastDoubleClick()) {
                                    for (int i = 0; i < messageList.size(); i++) {
                                        if (i == position) {
                                            new SqliteHelper().rawQuery("update messageList set m_top=? where m_group=? ", "true", messageList.get(i).get("m_group"));
                                        }
                                    }
                                    EventBus.getDefault().post(new MessageEvent(HintTool.Del_Message, ""));
                                }
                            }
                        });
                    }
                    holder.setTextView(R.id.item_message_text_del, "删除").setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!Tool.isFastDoubleClick()) {
                                new SqliteHelper().execSQL("update addFriend set m_deal='true' where fqr=?", data.get("m_function_id"));
                                new SqliteHelper().rawQuery("delete from messageList where m_group=? and m_group_id=?", messageList.get(position).get("m_group"), messageList.get(position).get("m_group_id"));
                                new SqliteHelper().rawQuery("delete from lt where friend_id=?", data.get("m_function_id"));
                                new SqliteHelper().rawQuery("delete from client_notice_messagelist where n_group_id=?", data.get("m_group_id"));
                                EventBus.getDefault().post(new MessageEvent(TagUtil.CheckNumTag, ""));
                                EventBus.getDefault().post(new MessageEvent(HintTool.Del_Message, ""));
                            }
                        }
                    });
                    holder.setLinearLayout(R.id.item_message_lin).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!Tool.isFastDoubleClick()) {
                                new SqliteHelper().execSQL("update client_notice_messagelist set n_look='true' where n_group_id=?", data.get("m_group_id"));

                                EventBus.getDefault().post(new MessageEvent(TagUtil.CheckNumTag, ""));
                                Intent intent = null;
                                switch (data.get("m_classify")) {
                                    case "group":
                                        intent = new Intent(context, ListActivity.class);
                                        intent.putExtra("function_id", data.get("m_function_id"));
                                        break;
                                    case "function":
                                        intent = new Intent(context, AllMessageList.class);
                                        intent.putExtra("function_id", data.get("m_function_id"));
                                        break;
                                    case "fr":
                                        new SqliteHelper().execSQL("update addFriend set isread=? where fqr=?", "true", data.get("m_function_id"));
                                        if (BodyAdd.DEAL_NO.equals(data.get("m_message")) || BodyAdd.DEAL_DISAGREE.equals(data.get("m_message"))) {
                                            intent = new Intent(context, FriendDetailActivity.class);
                                            intent.putExtra("fqr", data.get("m_function_id"));
                                        } else {
                                            intent = new Intent(context, liaotianjiemian.class);
                                            intent.putExtra("friend_id", data.get("m_function_id"));
                                            intent.putExtra("hyName", data.get("m_group"));
                                        }
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
        } else {
            messageList.clear();
            messageList.addAll(new SqliteHelper().rawQuery("select * from messageList where m_del=? order by m_top desc,m_time desc", "false"));
            adapter.notifyDataSetChanged();
        }
        fragmentMessagelistRec.setLayoutManager(new LinearLayoutManager(context));
        fragmentMessagelistRec.setAdapter(adapter);
        fragmentMessagelistRec.addItemDecoration(new FunctionAppItemDecorationHorizontal(context));
        new NoDataViewUtil<>(messageList, fragmentMessageParentRel, context).showNoDataView(R.drawable.no_data, "暂无数据，请到别处看看");
    }

}
