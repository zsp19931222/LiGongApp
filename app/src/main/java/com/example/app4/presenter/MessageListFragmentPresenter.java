package com.example.app4.presenter;

import android.content.Context;
import android.util.Log;

import com.example.app4.util.IsNull;
import com.example.app4.util.IsNullUtil;

import java.util.List;
import java.util.Map;

import yh.app.tool.SqliteHelper;
import 云华.智慧校园.工具.CodeManage;


public class MessageListFragmentPresenter {
    private Context context;
    private static final String TAG = "MessageListFragmentPres";

    public MessageListFragmentPresenter(Context context) {
        this.context = context;
    }

    private List<Map<String, String>> client_noticeList;

    /**
     * 获取推送消息
     */

    public void getPushData() {
        List<Map<String, String>> kindMap = new SqliteHelper().rawQuery("select * from client_notice_messagelist GROUP BY function_id ORDER by n_send_time asc");
        for (int i = 0; i < kindMap.size(); i++) {
            if (client_noticeList != null) {
                client_noticeList.clear();
            }
            client_noticeList = new SqliteHelper().rawQuery("select * from client_notice_messagelist where function_id=? ORDER by n_send_time desc", kindMap.get(i).get("function_id"));//根据function_id查询相关数据
            for (int j = 0; j < 1; j++) {
                List<Map<String, String>> new_push_functionList = new SqliteHelper().rawQuery("select * from new_push_function where ts_id=?", kindMap.get(i).get("function_id"));
                try {
//                    if (new_push_functionList.get(0).get("ts_group") != null && !new_push_functionList.get(0).get("ts_group").equals("") && !new_push_functionList.get(0).get("ts_group").equals("null")) {//分组消息
                    if (IsNullUtil.isNotNull(new_push_functionList.get(0).get("ts_group"))) {
                        if (CodeManage.APP_Messsage_Group_ID.equals(new_push_functionList.get(0).get("ts_group"))) {//应用消息
                            try {
                                saveMessageList(client_noticeList.get(j).get("n_id"),
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
                                        new SqliteHelper().rawQuery("select * from client_notice_messagelist where n_group_id=? and n_look=? ", new_push_functionList.get(0).get("ts_group"), "false").size() + "",
                                        new SqliteHelper().rawQuery("select * from messageList where m_group=? ", "应用消息").get(0).get("m_top"),
                                        new SqliteHelper().rawQuery("select * from messageList where m_group=? ", "应用消息").get(0).get("m_del"),
                                        new_push_functionList.get(0).get("ts_group")
                                );
                            } catch (Exception e) {
                                saveMessageList(client_noticeList.get(j).get("n_id"),
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
                                        new SqliteHelper().rawQuery("select * from client_notice_messagelist where n_group_id=? and n_look=? ", new_push_functionList.get(0).get("ts_group"), "false").size() + "",
                                        "false",
                                        "fasle",
                                        new_push_functionList.get(0).get("ts_group")
                                );
                            }
                        } else {//订阅消息
                            try {
                                saveMessageList(client_noticeList.get(j).get("n_id"),
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
                                        new SqliteHelper().rawQuery("select * from client_notice_messagelist where n_group_id=? and n_look=? ", new_push_functionList.get(0).get("ts_group"), "false").size() + "",
                                        new SqliteHelper().rawQuery("select * from messageList where m_group=? ", "订阅消息").get(0).get("m_top"),
                                        new SqliteHelper().rawQuery("select * from messageList where m_group=? ", "订阅消息").get(0).get("m_del"),
                                        new_push_functionList.get(0).get("ts_group")
                                );
                            } catch (Exception e) {
                                saveMessageList(client_noticeList.get(j).get("n_id"),
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
                                        new SqliteHelper().rawQuery("select * from client_notice_messagelist where n_group_id=? and n_look=? ", new_push_functionList.get(0).get("ts_group"), "false").size() + "",
                                        "false",
                                        "false", new_push_functionList.get(0).get("ts_group"));
                            }
                        }
                    } else {//功能消息
                        try {
                            saveMessageList(client_noticeList.get(j).get("n_id"),
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
                                    new SqliteHelper().rawQuery("select * from client_notice_messagelist where function_id=? and n_look=? ", kindMap.get(i).get("function_id"), "false").size() + "",
                                    new SqliteHelper().rawQuery("select * from messageList where m_group=? ", new_push_functionList.get(0).get("ts_name")).get(0).get("m_top"),
                                    new SqliteHelper().rawQuery("select * from messageList where m_group=? ", new_push_functionList.get(0).get("ts_name")).get(0).get("m_del"),
                                    client_noticeList.get(j).get("function_id")
                            );
                        } catch (Exception e) {
                            saveMessageList(client_noticeList.get(j).get("n_id"),
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
                                    new SqliteHelper().rawQuery("select * from client_notice_messagelist where function_id=? and n_look=? ", kindMap.get(i).get("function_id"), "false").size() + "",
                                    "false",
                                    "false", client_noticeList.get(j).get("function_id"));
                        }
                    }
                } catch (Exception ignored) {
                    String group = client_noticeList.get(j).get("n_from");
                    if (group == null || group.equals("")) {
                        group = " ";
                    }
                    try {
                        saveMessageList(client_noticeList.get(j).get("n_id"),
                                "function",
                                group,
                                client_noticeList.get(j).get("code"),
                                client_noticeList.get(j).get("read"),
                                "",
                                client_noticeList.get(j).get("n_send_time"),
                                client_noticeList.get(j).get("n_picpath"),
                                client_noticeList.get(j).get("n_title"),
                                client_noticeList.get(j).get("n_message"),
                                client_noticeList.get(j).get("function_id"),
                                new SqliteHelper().rawQuery("select * from client_notice_messagelist where function_id=? and n_look=? ", kindMap.get(i).get("function_id"), "false").size() + "",
                                new SqliteHelper().rawQuery("select * from messageList where m_group_id=? ", client_noticeList.get(j).get("n_group_id")).get(0).get("m_top"),
                                new SqliteHelper().rawQuery("select * from messageList where m_group_id=? ", client_noticeList.get(j).get("n_group_id")).get(0).get("m_del"),
                                client_noticeList.get(j).get("function_id"));
                    } catch (Exception e) {
                        saveMessageList(client_noticeList.get(j).get("n_id"),
                                "function",
                                group,
                                client_noticeList.get(j).get("code"),
                                client_noticeList.get(j).get("read"),
                                "",
                                client_noticeList.get(j).get("n_send_time"),
                                client_noticeList.get(j).get("n_picpath"),
                                client_noticeList.get(j).get("n_title"),
                                client_noticeList.get(j).get("n_message"),
                                client_noticeList.get(j).get("function_id"),
                                new SqliteHelper().rawQuery("select * from client_notice_messagelist where function_id=? and n_look=? ", kindMap.get(i).get("function_id"), "false").size() + "",
                                "false",
                                "false",
                                client_noticeList.get(j).get("function_id"));
                    }
                }
            }
        }
    }

    private void saveMessageList(Object... message) {
        new SqliteHelper().execSQL("insert or replace into messageList(m_id,m_classify,m_group,m_code,m_read,m_from,m_time,m_image,m_title,m_message,m_function_id,m_unread,m_top,m_del,m_group_id) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                message
        );
    }
}
