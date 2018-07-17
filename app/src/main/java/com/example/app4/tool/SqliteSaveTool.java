package com.example.app4.tool;

import android.util.Log;

import com.example.app3.eventbus.MyEventBus;
import com.example.app3.tool.HintTool;
import com.example.app4.entity.PushEntity;
import com.example.app4.entity.PushOffLineEntity;
import com.example.app4.util.IsNullUtil;
import com.example.jpushdemo.body.BodyPush;
import com.example.smartclass.eventbus.MessageEvent;
import com.example.smartclass.util.TagUtil;

import org.androidpn.push.Constants;
import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Map;

import yh.app.tool.SqliteHelper;
import yh.app.utils.GsonImpl;

/**
 * Created by Administrator on 2018/4/27 0027.
 * <p>
 * 存储推送消息
 */

public class SqliteSaveTool {
    private static final String TAG = "SqliteSaveTool";

    public static void savePushData(String push) {
        PushEntity pushEntity = GsonImpl.get().toObject(push, PushEntity.class);
        PushEntity.ContentBean body = pushEntity.getContent();

        new SqliteHelper().execSQL("insert into client_notice(n_id,userid,read,n_title,n_message,function_id,n_url,n_send_time,code,n_ticket,n_picpath,n_from) values(?,?,?,?,?,?,?,?,?,?,?,?)",
                body.getN_id(), Constants.number, BodyPush.READ_NO, body.getN_title(), body.getN_message(), body.getFunction_id(), body.getN_url(), body.getN_send_time(), body.getCode(), body.getN_ticket(), body.getN_picpath(), body.getN_source());

        //列表消息
        List<Map<String, String>> new_push_functionList = new SqliteHelper().rawQuery("select * from new_push_function where ts_id=?", body.getFunction_id());
        try {
//            if (new_push_functionList.get(0).get("ts_group") != null && !new_push_functionList.get(0).get("ts_group").equals("") && !new_push_functionList.get(0).get("ts_group").equals("null")) {
            if (IsNullUtil.isNotNull(new_push_functionList.get(0).get("ts_group"))) {
                new SqliteHelper().execSQL("insert into client_notice_messagelist(n_id,userid,read,n_title,n_message,function_id,n_url,n_send_time,code,n_ticket,n_picpath,n_look,n_group_id) values(?,?,?,?,?,?,?,?,?,?,?,?,?)",
                        body.getN_id(), Constants.number, BodyPush.READ_NO, body.getN_title(), body.getN_message(), body.getFunction_id(), body.getN_url(), body.getN_send_time(), body.getCode(), body.getN_ticket(), body.getN_picpath(), BodyPush.READ_NO, new_push_functionList.get(0).get("ts_group")
                );
            } else {
                new SqliteHelper().execSQL("insert into client_notice_messagelist(n_id,userid,read,n_title,n_message,function_id,n_url,n_send_time,code,n_ticket,n_picpath,n_look,n_group_id) values(?,?,?,?,?,?,?,?,?,?,?,?,?)",
                        body.getN_id(), Constants.number, BodyPush.READ_NO, body.getN_title(), body.getN_message(), body.getFunction_id(), body.getN_url(), body.getN_send_time(), body.getCode(), body.getN_ticket(), body.getN_picpath(), BodyPush.READ_NO, body.getFunction_id()
                );
            }
        } catch (Exception e) {
            new SqliteHelper().execSQL("insert into client_notice_messagelist(n_id,userid,read,n_title,n_message,function_id,n_url,n_send_time,code,n_ticket,n_picpath,n_look,n_group_id) values(?,?,?,?,?,?,?,?,?,?,?,?,?)",
                    body.getN_id(), Constants.number, BodyPush.READ_NO, body.getN_title(), body.getN_message(), body.getFunction_id(), body.getN_url(), body.getN_send_time(), body.getCode(), body.getN_ticket(), body.getN_picpath(), BodyPush.READ_NO, body.getFunction_id()
            );
        }

        EventBus.getDefault().post(new MessageEvent(HintTool.Receive_Push_Message, ""));
        EventBus.getDefault().post(new MessageEvent(TagUtil.CheckNumTag, ""));
        EventBus.getDefault().post(new MyEventBus(HintTool.Receive_Push_Message));//接受到消息通知更新
    }

    public static void saveOffLineData(String push) {
        Log.d(TAG, "saveOffLineData: " + push);
        PushOffLineEntity pushEntity = GsonImpl.get().toObject(push, PushOffLineEntity.class);
        for (int i = 0; i < pushEntity.getContent().getPush().getContent().size(); i++) {
            PushOffLineEntity.ContentBeanX.PushBean.ContentBean body = pushEntity.getContent().getPush().getContent().get(i);
            new SqliteHelper().execSQL("insert into client_notice(n_id,userid,read,n_title,n_message,function_id,n_url,n_send_time,code,n_ticket,n_picpath,n_from) values(?,?,?,?,?,?,?,?,?,?,?,?)",
                    body.getN_id(), Constants.number, BodyPush.READ_NO, body.getN_title(), body.getN_message(), body.getFunction_id(), body.getN_url(), body.getN_send_time(), body.getCode(), body.getN_ticket(), body.getN_picpath(), body.getN_source());

            //列表消息
            List<Map<String, String>> new_push_functionList = new SqliteHelper().rawQuery("select * from new_push_function where ts_id=?", body.getFunction_id());
            try {
//                if (new_push_functionList.get(0).get("ts_group") != null && !new_push_functionList.get(0).get("ts_group").equals("") && !new_push_functionList.get(0).get("ts_group").equals("null")) {
                   if (IsNullUtil.isNotNull(new_push_functionList.get(0).get("ts_group"))){
                    new SqliteHelper().execSQL("insert into client_notice_messagelist(n_id,userid,read,n_title,n_message,function_id,n_url,n_send_time,code,n_ticket,n_picpath,n_look,n_group_id) values(?,?,?,?,?,?,?,?,?,?,?,?,?)",
                            body.getN_id(), Constants.number, BodyPush.READ_NO, body.getN_title(), body.getN_message(), body.getFunction_id(), body.getN_url(), body.getN_send_time(), body.getCode(), body.getN_ticket(), body.getN_picpath(), BodyPush.READ_NO, new_push_functionList.get(0).get("ts_group")
                    );
                } else {
                    new SqliteHelper().execSQL("insert into client_notice_messagelist(n_id,userid,read,n_title,n_message,function_id,n_url,n_send_time,code,n_ticket,n_picpath,n_look,n_group_id) values(?,?,?,?,?,?,?,?,?,?,?,?,?)",
                            body.getN_id(), Constants.number, BodyPush.READ_NO, body.getN_title(), body.getN_message(), body.getFunction_id(), body.getN_url(), body.getN_send_time(), body.getCode(), body.getN_ticket(), body.getN_picpath(), BodyPush.READ_NO, body.getFunction_id()
                    );
                }
            } catch (Exception e) {
                new SqliteHelper().execSQL("insert into client_notice_messagelist(n_id,userid,read,n_title,n_message,function_id,n_url,n_send_time,code,n_ticket,n_picpath,n_look,n_group_id) values(?,?,?,?,?,?,?,?,?,?,?,?,?)",
                        body.getN_id(), Constants.number, BodyPush.READ_NO, body.getN_title(), body.getN_message(), body.getFunction_id(), body.getN_url(), body.getN_send_time(), body.getCode(), body.getN_ticket(), body.getN_picpath(), BodyPush.READ_NO, body.getFunction_id()
                );
            }

            EventBus.getDefault().post(new MessageEvent(HintTool.Receive_Push_Message, ""));
            EventBus.getDefault().post(new MessageEvent(TagUtil.CheckNumTag, ""));
            EventBus.getDefault().post(new MyEventBus(HintTool.Receive_Push_Message));//接受到消息通知更新

        }
    }
}
