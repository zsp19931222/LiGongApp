package com.example.app3.childview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.app3.activity.AllMessageList;
import com.example.app3.activity.BrowserActivity;
import com.example.app3.activity.FriendDetailActivity;
import com.example.app3.activity.ListActivity;
import com.example.app3.activity.SearchFriendActivity;
import com.example.app3.adapter.ContactAdapter;
import com.example.app3.adapter.dragrecyclear.common.DividerItemDecoration;
import com.example.app3.base_recyclear_adapter.HeaderAdapterWrapper1;
import com.example.app3.base_recyclear_adapter.QuickAdapter;
import com.example.app3.entity.LayoutEntity;
import com.example.app3.eventbus.MyEventBus;
import com.example.app3.tool.ContactComparator;
import com.example.app3.tool.HintTool;
import com.example.app3.tool.MyOnClickListener;
import com.example.app3.tool.TimeTool;
import com.example.app3.tool.Tool;
import com.example.app3.tool.Utils;
import com.example.app3.view.LetterView;
import com.example.jpushdemo.body.BodyAdd;
import com.yhkj.cqgyxy.R;

import org.androidpn.push.Constants;
import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import yh.app.function.liaotianjiemian;
import yh.app.logTool.Log;
import yh.app.tool.SqliteHelper;
import yh.app.utils.FileUtils;
import yh.app.utils.GsonImpl;
import 云华.智慧校园.工具.CodeManage;
import 云华.智慧校园.工具.RHelper;
import 云华.智慧校园.工具._链接地址导航;

public class HomeMessageView implements View.OnClickListener {

    private Button top_left, top_right;
    private View messageView;
    private Context context;
    private ListView txl_list;
    private RecyclerView message_list;
    private LinearLayout message_main_layout;
    private View view_messge_chat, view_messge_txl;
    private int currentItem = 0;
    private LinearLayout no_data_lin;

    public boolean isFirst = true;

    private List<Map<String, String>> messageList, mapList, contactNames, frMessageList, frList, chatList;

    private List<Map<String, String>> client_noticeList, new_push_functionList, new_push_groupList, kindList;

    private QuickAdapter adapter;

    public HomeMessageView(View messageView, Context context) {
        this.messageView = messageView;
        this.context = context;
    }

    public void initView() {
        top_left = (Button) messageView.findViewById(R.id.home_message_title_left);
        top_right = (Button) messageView.findViewById(R.id.home_message_title_right);
        message_main_layout = (LinearLayout) messageView.findViewById(R.id.home_message_linear_message_main_layout);
        if (view_messge_chat == null) {
            view_messge_chat = LayoutInflater.from(context).inflate(R.layout.view_home_message_item1, null, false);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            view_messge_chat.setLayoutParams(params);
            message_list = (RecyclerView) view_messge_chat.findViewById(R.id.home_listview_message_list);
            no_data_lin = (LinearLayout) view_messge_chat.findViewById(R.id.no_data_lin);
        }
        getListMessage(false);
        initChatView();
    }

    public void initData() {
        message_main_layout.addView(view_messge_chat);
    }

    public void initAction() {
        top_left.setOnClickListener(this);
        top_right.setOnClickListener(this);
//        message_list.setOnItemClickListener(new OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if (position == 0) {
//                    Toast.makeText(context, "点击的是头", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(context, "位置:" + (position - 1) + "   " + list.get(position - 1).get("ttt"), Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
    }

    private synchronized void initChatView() {
        initRecyclerView();
    }


    /**
     * 通讯录
     */
    private List<String> mContactList; // 联系人名称List（转换成拼音）
    private List<String> characterList; // 字母List

    private RecyclerView contactList;
    private LinearLayoutManager layoutManager;
    private LetterView letterView;
    private ContactAdapter contactAdapter;
    private HeaderAdapterWrapper1 headerAdapterWrapper1;
    private final int FreshTXL = 0;
    private View headView;
    private QuickAdapter TXLAdapter;

    private RecyclerView recyclerView;

    private synchronized void initTXL() {
        if (view_messge_txl == null) {
            contactNames = new ArrayList<>();
            view_messge_txl = LayoutInflater.from(context).inflate(R.layout.view_home_message_item11, null, false);
            contactList =  view_messge_txl.findViewById(R.id.home_listview_message_list);
            letterView =  view_messge_txl.findViewById(R.id.letter_view);
            layoutManager = new LinearLayoutManager(context);
            contactNames.addAll(new SqliteHelper().rawQuery("select * from friend"));

            mContactList = new ArrayList<>();
            final Map<String, String> map = new HashMap<>();
            for (int i = 0; i < contactNames.size(); i++) {
                if (contactNames.get(i).get("name") != null) {
                    String pinyin = Utils.getPingYin(contactNames.get(i).get("name"));
                    map.put(pinyin, contactNames.get(i).get("name"));
                    mContactList.add(pinyin);
                }
            }
            Collections.sort(mContactList, new ContactComparator());
            characterList = new ArrayList<>();
            for (int i = 0; i < mContactList.size(); i++) {
                String name = mContactList.get(i);
                String character = (name.charAt(0) + "").toUpperCase(Locale.ENGLISH);
                if (!characterList.contains(character)) {
                    if (character.hashCode() >= "A".hashCode() && character.hashCode() <= "Z".hashCode()) { // 是字母
                        characterList.add(character);
                    }
                }
            }

            letterView.initView(characterList);

            contactAdapter = new ContactAdapter(context, contactNames);

            headerAdapterWrapper1 = new HeaderAdapterWrapper1(contactAdapter, contactNames);//添加头
            headView = LayoutInflater.from(context).inflate(R.layout.head_address_list, null, false);
            LinearLayout searchView = headView.findViewById(R.id.head_address_lin_search);
            recyclerView =  headView.findViewById(R.id.head_address_rec);
            LayoutEntity entity = GsonImpl.get().toObject(FileUtils.readJsonFile(context, "txl"), LayoutEntity.class);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL_LIST));
            TXLAdapter = new QuickAdapter<LayoutEntity.AllTagsListBean>(entity.getAllTagsList()) {
                @Override
                public int getLayoutId(int viewType) {
                    return R.layout.item_contact;
                }

                @Override
                public void convert(VH holder, LayoutEntity.AllTagsListBean data, final int position) {
                    holder.setTextView(R.id.contact_name, data.getTxt());

                    if (data.getTxt().equals("新的朋友")) {
                        List<Map<String, String>> maps = new SqliteHelper().rawQuery("select * from hyqqNum");
                        if (new SqliteHelper().rawQuery("select * from hyqqNum where isread=?", "false").size() != 0) {
                            holder.setTextView(R.id.contact_num, new SqliteHelper().rawQuery("select * from hyqqNum where isread=?", "false").size() + "").setVisibility(View.VISIBLE);
                        }
                    }
                    holder.setImageView((Activity) context, R.id.contact_image1, new RHelper().getDrawable(context, data.getPic_default()));
                    if (!data.getCls().equals("com.example.app3.activity.BrowserActivity")) {
                        holder.itemView.setOnClickListener(new MyOnClickListener(data.getCls(), context));
                    } else {
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(context, BrowserActivity.class);
                                intent.putExtra("title", "办公电话");
                                intent.putExtra("url", _链接地址导航.UIA.办公电话.getUrl());
                                context.startActivity(intent);
                            }
                        });
                    }

                }
            };
            recyclerView.setAdapter(TXLAdapter);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
            headView.setLayoutParams(params);
            headerAdapterWrapper1.addHeaderView(headView);

            contactList.setLayoutManager(layoutManager);
            contactList.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL_LIST));
            contactList.setAdapter(headerAdapterWrapper1);

            letterView.setCharacterListener(new LetterView.CharacterClickListener() {
                @Override
                public void clickCharacter(String character) {
                    layoutManager.scrollToPositionWithOffset(contactAdapter.getScrollPosition(character), 0);
                }

                @Override
                public void clickArrow() {
                    layoutManager.scrollToPositionWithOffset(0, 0);
                }
            });

            /**
             * 跳转搜索
             * */
            searchView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, SearchFriendActivity.class);
                    context.startActivity(intent);
                }
            });
        }
    }

    private void initRecyclerView() {
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
//                    if (BodyAdd.DEAL_NO.equals(data.get("m_message")) || BodyAdd.DEAL_DISAGREE.equals(data.get("m_message"))) {
                    if (BodyAdd.DEAL_NO.equals(data.get("m_message"))) {
                        holder.setTextView(R.id.item_message_text_explain, "好友请求");
                    } else {
                        String s;
                        try {
                            s = new SqliteHelper().rawQuery("select * from lt where userid=? and friend_id=? order by fssj desc", Constants.number, data.get("m_function_id")).get(0).get("message");
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
                                messageList.clear();
                                messageList.addAll(new SqliteHelper().rawQuery("select * from messageList where m_del=? order by m_top desc", "false"));
                                adapter.notifyDataSetChanged();
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
                                messageList.clear();
                                messageList.addAll(new SqliteHelper().rawQuery("select * from messageList where m_del=? order by m_top desc", "false"));
                                adapter.notifyDataSetChanged();
                            }
                        }
                    });
                }
                holder.setTextView(R.id.item_message_text_del, "删除").setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!Tool.isFastDoubleClick()) {
                            new SqliteHelper().execSQL("update addFriend set m_deal='true' where fqr=?", data.get("m_function_id"));
                            new SqliteHelper().rawQuery("delete from messageList where m_group=?", messageList.get(position).get("m_group"));
                            new SqliteHelper().rawQuery("delete from lt where friend_id=?", data.get("m_function_id"));
                            new SqliteHelper().rawQuery("delete from client_notice where function_id=?", data.get("m_function_id"));
                            EventBus.getDefault().post(new MyEventBus(HintTool.Del_Message));
                            messageList.clear();
                            messageList.addAll(new SqliteHelper().rawQuery("select * from messageList where m_del=? order by m_top desc", "false"));
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
                holder.setLinearLayout(R.id.item_message_lin).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!Tool.isFastDoubleClick()) {
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
        message_list.setLayoutManager(new LinearLayoutManager(context));
        message_list.setAdapter(adapter);
        isFirst = false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_message_title_left:
                ((Activity) context).findViewById(R.id.home_message_title_left).setBackgroundResource(R.drawable.d_biankuang_soild_touming_buttom_2dp_333333);
                ((Activity) context).findViewById(R.id.home_message_title_right).setBackgroundColor(context.getResources().getColor(R.color.white));
                initChatView();
                break;
            case R.id.home_message_title_right:
                ((Activity) context).findViewById(R.id.home_message_title_right).setBackgroundResource(R.drawable.d_biankuang_soild_touming_buttom_2dp_333333);
                ((Activity) context).findViewById(R.id.home_message_title_left).setBackgroundColor(context.getResources().getColor(R.color.white));
                initTXL();
                break;
            default:
                break;
        }

        switchItem(v.getId());
    }

    private void switchItem(int id) {
        synchronized (message_main_layout) {
            if (currentItem == id) {
                return;
            }
            currentItem = id;
            message_main_layout.removeAllViews();
            switch (id) {
                case R.id.home_message_title_left:
                    message_main_layout.addView(view_messge_chat);
                    break;
                case R.id.home_message_title_right:
                    message_main_layout.addView(view_messge_txl);
                    break;
                default:
                    break;
            }
        }

    }


    public static class ViewHolder {
        yh.tool.widget.DragPointView dragPointView;
    }

    /**
     * 获取消息列表
     */
    private int subscibeListSize = 0;//订阅消息未读条数
    private String lastSubscibeFunction_id = "";//记录上一次订阅消息的function_id
    private int appListSize = 0;//应用消息未读条数
    private String lastAppFunction_id = "";//记录上一次应用消息的function_id

    /**
     * 获取订阅或者功能消息
     */

    public void getListMessage(boolean fromPush) {
        if (kindList != null) {
            kindList.clear();
        }
        kindList = new SqliteHelper().rawQuery("select distinct function_id from client_notice");//去重分类

        if (messageList == null) {
            messageList = new ArrayList<>();
        } else {
            messageList.clear();
        }
        clientNoticeToMessageList(fromPush);
        getFrMessage(fromPush);
    }

    /**
     * 获取好友消息
     */
    private void getFrMessage(boolean fromPush) {
        if (frMessageList == null) {
            frMessageList = new ArrayList<>();
        } else {
            frMessageList.clear();
        }
        frMessageList.addAll(new SqliteHelper().rawQuery("select * from addFriend where userid=? and m_deal=? order by fssj desc", Constants.number, "false"));
        friendToMessageList(fromPush, frMessageList);
        messageList.addAll(new SqliteHelper().rawQuery("select * from messageList where m_del=? order by m_top desc,m_time desc", "false"));
        Log.d("zsp", messageList.size() + "");
        if (messageList.size() != 0) {
            no_data_lin.setVisibility(View.GONE);
            message_list.setVisibility(View.VISIBLE);
        } else {
            no_data_lin.setVisibility(View.VISIBLE);
            message_list.setVisibility(View.GONE);
        }
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }


    /**
     * 刷新消息
     */
    public void freshMessageList(boolean fromPush) {
        subscibeListSize = 0;
        lastSubscibeFunction_id = "";
        appListSize = 0;
        lastAppFunction_id = "";
        getListMessage(fromPush);
    }

    /**
     * 刷新通讯录
     */

    public void freshTXL() {
        handler.sendEmptyMessage(FreshTXL);
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case FreshTXL:
                    if (contactNames != null) {
                        contactNames.clear();
                        mContactList.clear();
                        characterList.clear();
                        contactNames.addAll(new SqliteHelper().rawQuery("select * from friend"));
                        headerAdapterWrapper1.ref();
//                        contactAdapter.handleContact(false);
//                        contactAdapter.notifyDataSetChanged();
//                        contactAdapter = new ContactAdapter(context, contactNames);
                        recyclerView.setAdapter(TXLAdapter);
//                        headerAdapterWrapper1 = new HeaderAdapterWrapper1(contactAdapter, contactNames);//添加头
//                        headerAdapterWrapper1.addHeaderView(headView);
//                        contactList.setAdapter(headerAdapterWrapper1);
//
                        Map<String, String> map = new HashMap<>();
                        for (int i = 0; i < contactNames.size(); i++) {
                            if (contactNames.get(i).get("name") != null) {
                                String pinyin = Utils.getPingYin(contactNames.get(i).get("name"));
                                map.put(pinyin, contactNames.get(i).get("name"));
                                mContactList.add(pinyin);
                            }
                        }
                        Collections.sort(mContactList, new ContactComparator());
                        for (int i = 0; i < mContactList.size(); i++) {
                            String name = mContactList.get(i);
                            String character = (name.charAt(0) + "").toUpperCase(Locale.ENGLISH);
                            if (!characterList.contains(character)) {
                                if (character.hashCode() >= "A".hashCode() && character.hashCode() <= "Z".hashCode()) { // 是字母
                                    characterList.add(character);
                                }
                            }
                        }
                        letterView.initView(characterList);
                    }
                    break;
            }
        }
    };


    /**
     * 将推送的好友消息放入消息列表
     */
    private void friendToMessageList(boolean fromPush, List<Map<String, String>> frMessageList) {
        for (int i = 0; i < frMessageList.size(); i++) {
            if (!fromPush) {//不是来自推送
                if (BodyAdd.DEAL_NO.equals(frMessageList.get(i).get("deal"))) {
                    try {
                        new SqliteHelper().execSQL("insert or replace into messageList(m_id,m_classify,m_group,m_code,m_read,m_from,m_time,m_image,m_title,m_message,m_function_id,m_unread,m_top,m_del) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                frMessageList.get(i).get("id"),
                                "fr",
                                frMessageList.get(i).get("fqrname"),
                                frMessageList.get(i).get("code"),
                                frMessageList.get(i).get("isread"),
                                "",
                                frMessageList.get(i).get("fssj"),
                                frMessageList.get(i).get("faceaddress"),
                                frMessageList.get(i).get("fqrname"),
                                frMessageList.get(i).get("deal"),
                                frMessageList.get(i).get("fqr"),
                                new SqliteHelper().rawQuery("select * from addFriend where fqr=? and isread=? ", frMessageList.get(i).get("fqr"), "false").size() + "",
                                new SqliteHelper().rawQuery("select * from messageList where m_group=? ", frMessageList.get(i).get("fqrname")).get(0).get("m_top"),
                                new SqliteHelper().rawQuery("select * from messageList where m_group=? ", frMessageList.get(i).get("fqrname")).get(0).get("m_del")
                        );
                    } catch (Exception e) {
                        new SqliteHelper().execSQL("insert or replace into messageList(m_id,m_classify,m_group,m_code,m_read,m_from,m_time,m_image,m_title,m_message,m_function_id,m_unread,m_top,m_del) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                frMessageList.get(i).get("id"),
                                "fr",
                                frMessageList.get(i).get("fqrname"),
                                frMessageList.get(i).get("type"),
                                frMessageList.get(i).get("isread"),
                                "",
                                frMessageList.get(i).get("fssj"),
                                frMessageList.get(i).get("faceaddress"),
                                frMessageList.get(i).get("fqrname"),
                                frMessageList.get(i).get("deal"),
                                frMessageList.get(i).get("fqr"),
                                new SqliteHelper().rawQuery("select * from addFriend where fqr=? and isread=? ", frMessageList.get(i).get("fqr"), "false").size() + "",
                                "false",
                                "false"
                        );
                    }
                } else if (BodyAdd.DEAL_AGREE.equals(frMessageList.get(i).get("deal"))) {
                    try {
                        new SqliteHelper().execSQL("insert or replace into messageList(m_id,m_classify,m_group,m_code,m_read,m_from,m_time,m_image,m_title,m_message,m_function_id,m_unread,m_top,m_del) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                frMessageList.get(i).get("id"),
                                "fr",
                                frMessageList.get(i).get("fqrname"),
                                frMessageList.get(i).get("code"),
                                frMessageList.get(i).get("isread"),
                                "",
                                frMessageList.get(i).get("fssj"),
                                frMessageList.get(i).get("faceaddress"),
                                frMessageList.get(i).get("fqrname"),
                                frMessageList.get(i).get("deal"),
                                frMessageList.get(i).get("fqr"),
                                new SqliteHelper().rawQuery("select * from lt where userid=? and friend_id=? and isread=?", Constants.number, frMessageList.get(i).get("fqr"), "false").size() + "",
                                new SqliteHelper().rawQuery("select * from messageList where m_group=? ", frMessageList.get(i).get("fqrname")).get(0).get("m_top"),
                                new SqliteHelper().rawQuery("select * from messageList where m_group=? ", frMessageList.get(i).get("fqrname")).get(0).get("m_del")
                        );
                    } catch (Exception e) {
                        new SqliteHelper().execSQL("insert or replace into messageList(m_id,m_classify,m_group,m_code,m_read,m_from,m_time,m_image,m_title,m_message,m_function_id,m_unread,m_top,m_del) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                frMessageList.get(i).get("id"),
                                "fr",
                                frMessageList.get(i).get("fqrname"),
                                frMessageList.get(i).get("type"),
                                frMessageList.get(i).get("isread"),
                                "",
                                frMessageList.get(i).get("fssj"),
                                frMessageList.get(i).get("faceaddress"),
                                frMessageList.get(i).get("fqrname"),
                                frMessageList.get(i).get("deal"),
                                frMessageList.get(i).get("fqr"),
                                new SqliteHelper().rawQuery("select * from lt where userid=? and friend_id=?", Constants.number, frMessageList.get(i).get("fqr"), "false").size() + "",
                                "false",
                                "false"
                        );
                    }
                }
            } else {//来自推送
                if (BodyAdd.DEAL_NO.equals(frMessageList.get(i).get("deal"))) {
                    try {
                        new SqliteHelper().execSQL("insert or replace into messageList(m_id,m_classify,m_group,m_code,m_read,m_from,m_time,m_image,m_title,m_message,m_function_id,m_unread,m_top,m_del) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                frMessageList.get(i).get("id"),
                                "fr",
                                frMessageList.get(i).get("fqrname"),
                                frMessageList.get(i).get("code"),
                                frMessageList.get(i).get("isread"),
                                "",
                                frMessageList.get(i).get("fssj"),
                                frMessageList.get(i).get("faceaddress"),
                                frMessageList.get(i).get("fqrname"),
                                frMessageList.get(i).get("deal"),
                                frMessageList.get(i).get("fqr"),
                                new SqliteHelper().rawQuery("select * from addFriend where fqr=? and isread=? ", frMessageList.get(i).get("fqr"), "false").size() + "",
                                new SqliteHelper().rawQuery("select * from messageList where m_group=? ", frMessageList.get(i).get("fqrname")).get(0).get("m_top"),
                                "false"
                        );
                    } catch (Exception e) {
                        new SqliteHelper().execSQL("insert or replace into messageList(m_id,m_classify,m_group,m_code,m_read,m_from,m_time,m_image,m_title,m_message,m_function_id,m_unread,m_top,m_del) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                frMessageList.get(i).get("id"),
                                "fr",
                                frMessageList.get(i).get("fqrname"),
                                frMessageList.get(i).get("type"),
                                frMessageList.get(i).get("isread"),
                                "",
                                frMessageList.get(i).get("fssj"),
                                frMessageList.get(i).get("faceaddress"),
                                frMessageList.get(i).get("fqrname"),
                                frMessageList.get(i).get("deal"),
                                frMessageList.get(i).get("fqr"),
                                new SqliteHelper().rawQuery("select * from addFriend where fqr=? and isread=? ", frMessageList.get(i).get("fqr"), "false").size() + "",
                                "false",
                                "false"
                        );
                    }
                } else if (BodyAdd.DEAL_AGREE.equals(frMessageList.get(i).get("deal"))) {
                    try {
                        new SqliteHelper().execSQL("insert or replace into messageList(m_id,m_classify,m_group,m_code,m_read,m_from,m_time,m_image,m_title,m_message,m_function_id,m_unread,m_top,m_del) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                frMessageList.get(i).get("id"),
                                "fr",
                                frMessageList.get(i).get("fqrname"),
                                frMessageList.get(i).get("code"),
                                frMessageList.get(i).get("isread"),
                                "",
                                frMessageList.get(i).get("fssj"),
                                frMessageList.get(i).get("faceaddress"),
                                frMessageList.get(i).get("fqrname"),
                                frMessageList.get(i).get("deal"),
                                frMessageList.get(i).get("fqr"),
                                new SqliteHelper().rawQuery("select * from lt where userid=? and friend_id=? and isread=?", Constants.number, frMessageList.get(i).get("fqr"), "false").size() + "",
                                new SqliteHelper().rawQuery("select * from messageList where m_group=? ", frMessageList.get(i).get("fqrname")).get(0).get("m_top"),
                                "false"
                        );
                    } catch (Exception e) {
                        new SqliteHelper().execSQL("insert or replace into messageList(m_id,m_classify,m_group,m_code,m_read,m_from,m_time,m_image,m_title,m_message,m_function_id,m_unread,m_top,m_del) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                frMessageList.get(i).get("id"),
                                "fr",
                                frMessageList.get(i).get("fqrname"),
                                frMessageList.get(i).get("type"),
                                frMessageList.get(i).get("isread"),
                                "",
                                frMessageList.get(i).get("fssj"),
                                frMessageList.get(i).get("faceaddress"),
                                frMessageList.get(i).get("fqrname"),
                                frMessageList.get(i).get("deal"),
                                frMessageList.get(i).get("fqr"),
                                new SqliteHelper().rawQuery("select * from lt where userid=? and friend_id=? and isread=?", Constants.number, frMessageList.get(i).get("fqr"), "false").size() + "",
                                "false",
                                "false"
                        );
                    }
                }
            }

        }
    }

    /**
     * 将推送的其他消息放入消息列表
     */
    private void clientNoticeToMessageList(boolean fromPush) {
        if (!fromPush) {
            for (int i = 0; i < kindList.size(); i++) {
                if (client_noticeList != null) {
                    client_noticeList.clear();
                }
                client_noticeList = new SqliteHelper().rawQuery("select * from client_notice where function_id=? ", kindList.get(i).get("function_id"));//根据function_id查询相关数据
                for (int j = 0; j < client_noticeList.size(); j++) {
                    new_push_functionList = new SqliteHelper().rawQuery("select * from new_push_function where ts_id=?", kindList.get(i).get("function_id"));//得到分组数据

                    if (new_push_functionList.size() == 0) {

                    } else
                        //应用消息
                        if (CodeManage.APP_Messsage_Group_ID.equals(new_push_functionList.get(0).get("ts_group"))) {
                            if (!lastAppFunction_id.equals(kindList.get(i).get("function_id"))) {
                                lastAppFunction_id = kindList.get(i).get("function_id");
                                appListSize += new SqliteHelper().rawQuery("select * from client_notice where function_id=? and read=? ", kindList.get(i).get("function_id"), "false").size();
                            }
                            try {
                                new SqliteHelper().execSQL("insert or replace into messageList(m_id,m_classify,m_group,m_code,m_read,m_from,m_time,m_image,m_title,m_message,m_function_id,m_unread,m_top,m_del) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                        client_noticeList.get(j).get("n_id"),
                                        "group",
                                        "应用消息",
                                        client_noticeList.get(j).get("code"),
                                        client_noticeList.get(j).get("read"),
                                        "",
                                        client_noticeList.get(j).get("n_send_time"),
                                        client_noticeList.get(j).get("n_picpath"),
                                        client_noticeList.get(j).get("n_title"),
                                        client_noticeList.get(j).get("n_message"),
                                        client_noticeList.get(j).get("function_id"),
                                        appListSize + "",
                                        new SqliteHelper().rawQuery("select * from messageList where m_group=? ", "应用消息").get(0).get("m_top"),
                                        new SqliteHelper().rawQuery("select * from messageList where m_group=? ", "应用消息").get(0).get("m_del")
                                );
                            } catch (Exception e) {
                                new SqliteHelper().execSQL("insert or replace into messageList(m_id,m_classify,m_group,m_code,m_read,m_from,m_time,m_image,m_title,m_message,m_function_id,m_unread,m_top,m_del) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                        client_noticeList.get(j).get("n_id"),
                                        "group",
                                        "应用消息",
                                        client_noticeList.get(j).get("code"),
                                        client_noticeList.get(j).get("read"),
                                        "",
                                        client_noticeList.get(j).get("n_send_time"),
                                        client_noticeList.get(j).get("n_picpath"),
                                        client_noticeList.get(j).get("n_title"),
                                        client_noticeList.get(j).get("n_message"),
                                        client_noticeList.get(j).get("function_id"),
                                        appListSize + "",
                                        "false",
                                        "fasle"
                                );
                            }

                            //订阅消息
                        } else if (CodeManage.Subscibe_Message_Group_ID.equals(new_push_functionList.get(0).get("ts_group"))) {
                            if (!lastSubscibeFunction_id.equals(kindList.get(i).get("function_id"))) {
                                lastSubscibeFunction_id = kindList.get(i).get("function_id");
                                subscibeListSize += new SqliteHelper().rawQuery("select * from client_notice where function_id=? and read=? ", kindList.get(i).get("function_id"), "false").size();
                            }
                            try {
                                new SqliteHelper().execSQL("insert or replace into messageList(m_id,m_classify,m_group,m_code,m_read,m_from,m_time,m_image,m_title,m_message,m_function_id,m_unread,m_top,m_del) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                        client_noticeList.get(j).get("n_id"),
                                        "group",
                                        "订阅消息",
                                        client_noticeList.get(j).get("code"),
                                        client_noticeList.get(j).get("read"),
                                        "",
                                        client_noticeList.get(j).get("n_send_time"),
                                        client_noticeList.get(j).get("n_picpath"),
                                        client_noticeList.get(j).get("n_title"),
                                        client_noticeList.get(j).get("n_message"),
                                        client_noticeList.get(j).get("function_id"),
                                        subscibeListSize + "",
                                        new SqliteHelper().rawQuery("select * from messageList where m_group=? ", "订阅消息").get(0).get("m_top"),
                                        new SqliteHelper().rawQuery("select * from messageList where m_group=? ", "订阅消息").get(0).get("m_del")
                                );
                            } catch (Exception e) {
                                new SqliteHelper().execSQL("insert or replace into messageList(m_id,m_classify,m_group,m_code,m_read,m_from,m_time,m_image,m_title,m_message,m_function_id,m_unread,m_top,m_del) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                        client_noticeList.get(j).get("n_id"),
                                        "group",
                                        "订阅消息",
                                        client_noticeList.get(j).get("code"),
                                        client_noticeList.get(j).get("read"),
                                        "",
                                        client_noticeList.get(j).get("n_send_time"),
                                        client_noticeList.get(j).get("n_picpath"),
                                        client_noticeList.get(j).get("n_title"),
                                        client_noticeList.get(j).get("n_message"),
                                        client_noticeList.get(j).get("function_id"),
                                        subscibeListSize + "",
                                        "false",
                                        "false"
                                );
                            }
                            //功能消息
                        } else {
                            try {
                                new SqliteHelper().execSQL("insert or replace into messageList(m_id,m_classify,m_group,m_code,m_read,m_from,m_time,m_image,m_title,m_message,m_function_id,m_unread,m_top,m_del) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                        client_noticeList.get(j).get("n_id"),
                                        "function",
                                        new_push_functionList.get(0).get("ts_name"),
                                        client_noticeList.get(j).get("code"),
                                        client_noticeList.get(j).get("read"),
                                        "",
                                        client_noticeList.get(j).get("n_send_time"),
                                        client_noticeList.get(j).get("n_picpath"),
                                        client_noticeList.get(j).get("n_title"),
                                        client_noticeList.get(j).get("n_message"),
                                        client_noticeList.get(j).get("function_id"),
                                        new SqliteHelper().rawQuery("select * from client_notice where function_id=? and read=? ", kindList.get(i).get("function_id"), "false").size() + "",
                                        new SqliteHelper().rawQuery("select * from messageList where m_group=? ", new_push_functionList.get(0).get("ts_name")).get(0).get("m_top"),
                                        new SqliteHelper().rawQuery("select * from messageList where m_group=? ", new_push_functionList.get(0).get("ts_name")).get(0).get("m_del")
                                );
                            } catch (Exception e) {
                                new SqliteHelper().execSQL("insert or replace into messageList(m_id,m_classify,m_group,m_code,m_read,m_from,m_time,m_image,m_title,m_message,m_function_id,m_unread,m_top,m_del) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                        client_noticeList.get(j).get("n_id"),
                                        "function",
                                        new_push_functionList.get(0).get("ts_name"),
                                        client_noticeList.get(j).get("code"),
                                        client_noticeList.get(j).get("read"),
                                        "",
                                        client_noticeList.get(j).get("n_send_time"),
                                        client_noticeList.get(j).get("n_picpath"),
                                        client_noticeList.get(j).get("n_title"),
                                        client_noticeList.get(j).get("n_message"),
                                        client_noticeList.get(j).get("function_id"),
                                        new SqliteHelper().rawQuery("select * from client_notice where function_id=? and read=? ", kindList.get(i).get("function_id"), "false").size() + "",
                                        "false",
                                        "false"
                                );
                            }

                        }
                }
            }
            //来自于推送消息
        } else {
            for (int i = 0; i < kindList.size(); i++) {
                if (client_noticeList != null) {
                    client_noticeList.clear();
                }
                client_noticeList = new SqliteHelper().rawQuery("select * from client_notice where function_id=? ", kindList.get(i).get("function_id"));//根据function_id查询相关数据
                for (int j = 0; j < client_noticeList.size(); j++) {
                    new_push_functionList = new SqliteHelper().rawQuery("select * from new_push_function where ts_id=?", kindList.get(i).get("function_id"));//得到分组数据
                    //应用消息
                    if (new_push_functionList.size() == 0) {

                    } else if (CodeManage.APP_Messsage_Group_ID.equals(new_push_functionList.get(0).get("ts_group"))) {
                        if (!lastAppFunction_id.equals(kindList.get(i).get("function_id"))) {
                            lastAppFunction_id = kindList.get(i).get("function_id");
                            appListSize += new SqliteHelper().rawQuery("select * from client_notice where function_id=? and read=? ", kindList.get(i).get("function_id"), "false").size();
                        }
                        try {
                            new SqliteHelper().execSQL("insert or replace into messageList(m_id,m_classify,m_group,m_code,m_read,m_from,m_time,m_image,m_title,m_message,m_function_id,m_unread,m_top,m_del) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                    client_noticeList.get(j).get("n_id"),
                                    "group",
                                    "应用消息",
                                    client_noticeList.get(j).get("code"),
                                    client_noticeList.get(j).get("read"),
                                    "",
                                    client_noticeList.get(j).get("n_send_time"),
                                    client_noticeList.get(j).get("n_picpath"),
                                    client_noticeList.get(j).get("n_title"),
                                    client_noticeList.get(j).get("n_message"),
                                    client_noticeList.get(j).get("function_id"),
                                    appListSize + "",
                                    new SqliteHelper().rawQuery("select * from messageList where m_group=? ", "应用消息").get(0).get("m_top"),
                                    "false"
                            );
                        } catch (Exception e) {
                            new SqliteHelper().execSQL("insert or replace into messageList(m_id,m_classify,m_group,m_code,m_read,m_from,m_time,m_image,m_title,m_message,m_function_id,m_unread,m_top,m_del) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                    client_noticeList.get(j).get("n_id"),
                                    "group",
                                    "应用消息",
                                    client_noticeList.get(j).get("code"),
                                    client_noticeList.get(j).get("read"),
                                    "",
                                    client_noticeList.get(j).get("n_send_time"),
                                    client_noticeList.get(j).get("n_picpath"),
                                    client_noticeList.get(j).get("n_title"),
                                    client_noticeList.get(j).get("n_message"),
                                    client_noticeList.get(j).get("function_id"),
                                    appListSize + "",
                                    "false",
                                    "fasle"
                            );
                        }

                        //订阅消息
                    } else if (CodeManage.Subscibe_Message_Group_ID.equals(new_push_functionList.get(0).get("ts_group"))) {
                        if (!lastSubscibeFunction_id.equals(kindList.get(i).get("function_id"))) {
                            lastSubscibeFunction_id = kindList.get(i).get("function_id");
                            subscibeListSize += new SqliteHelper().rawQuery("select * from client_notice where function_id=? and read=? ", kindList.get(i).get("function_id"), "false").size();
                        }
                        try {
                            new SqliteHelper().execSQL("insert or replace into messageList(m_id,m_classify,m_group,m_code,m_read,m_from,m_time,m_image,m_title,m_message,m_function_id,m_unread,m_top,m_del) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                    client_noticeList.get(j).get("n_id"),
                                    "group",
                                    "订阅消息",
                                    client_noticeList.get(j).get("code"),
                                    client_noticeList.get(j).get("read"),
                                    "",
                                    client_noticeList.get(j).get("n_send_time"),
                                    client_noticeList.get(j).get("n_picpath"),
                                    client_noticeList.get(j).get("n_title"),
                                    client_noticeList.get(j).get("n_message"),
                                    client_noticeList.get(j).get("function_id"),
                                    subscibeListSize + "",
                                    new SqliteHelper().rawQuery("select * from messageList where m_group=? ", "订阅消息").get(0).get("m_top"),
                                    "false"
                            );
                        } catch (Exception e) {
                            new SqliteHelper().execSQL("insert or replace into messageList(m_id,m_classify,m_group,m_code,m_read,m_from,m_time,m_image,m_title,m_message,m_function_id,m_unread,m_top,m_del) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                    client_noticeList.get(j).get("n_id"),
                                    "group",
                                    "订阅消息",
                                    client_noticeList.get(j).get("code"),
                                    client_noticeList.get(j).get("read"),
                                    "",
                                    client_noticeList.get(j).get("n_send_time"),
                                    client_noticeList.get(j).get("n_picpath"),
                                    client_noticeList.get(j).get("n_title"),
                                    client_noticeList.get(j).get("n_message"),
                                    client_noticeList.get(j).get("function_id"),
                                    subscibeListSize + "",
                                    "false",
                                    "false"
                            );
                        }

                        //功能消息
                    } else {
                        try {
                            new SqliteHelper().execSQL("insert or replace into messageList(m_id,m_classify,m_group,m_code,m_read,m_from,m_time,m_image,m_title,m_message,m_function_id,m_unread,m_top,m_del) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                    client_noticeList.get(j).get("n_id"),
                                    "function",
                                    new_push_functionList.get(0).get("ts_name"),
                                    client_noticeList.get(j).get("code"),
                                    client_noticeList.get(j).get("read"),
                                    "",
                                    client_noticeList.get(j).get("n_send_time"),
                                    client_noticeList.get(j).get("n_picpath"),
                                    client_noticeList.get(j).get("n_title"),
                                    client_noticeList.get(j).get("n_message"),
                                    client_noticeList.get(j).get("function_id"),
                                    new SqliteHelper().rawQuery("select * from client_notice where function_id=? and read=? ", kindList.get(i).get("function_id"), "false").size() + "",
                                    new SqliteHelper().rawQuery("select * from messageList where m_group=? ", new_push_functionList.get(0).get("ts_name")).get(0).get("m_top"),
                                    "false"
                            );
                        } catch (Exception e) {
                            new SqliteHelper().execSQL("insert or replace into messageList(m_id,m_classify,m_group,m_code,m_read,m_from,m_time,m_image,m_title,m_message,m_function_id,m_unread,m_top,m_del) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                    client_noticeList.get(j).get("n_id"),
                                    "function",
                                    new_push_functionList.get(0).get("ts_name"),
                                    client_noticeList.get(j).get("code"),
                                    client_noticeList.get(j).get("read"),
                                    "",
                                    client_noticeList.get(j).get("n_send_time"),
                                    client_noticeList.get(j).get("n_picpath"),
                                    client_noticeList.get(j).get("n_title"),
                                    client_noticeList.get(j).get("n_message"),
                                    client_noticeList.get(j).get("function_id"),
                                    new SqliteHelper().rawQuery("select * from client_notice where function_id=? and read=? ", kindList.get(i).get("function_id"), "false").size() + "",
                                    "false",
                                    "false"
                            );
                        }

                    }
                }
            }
        }
    }
}