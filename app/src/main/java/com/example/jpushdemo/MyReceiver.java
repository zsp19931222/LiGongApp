package com.example.jpushdemo;

import org.json.JSONObject;

import com.example.jpushdemo.helper.Receiver;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 * <p>
 * 如果不定义这个 Receiver，则： 1) 默认用户会打开主界面 2) 接收不到自定义消息
 */
@SuppressLint("SimpleDateFormat")
public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Receiver receiver = new Receiver();
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            //接收自定义消息
            try {
                JSONObject data = new JSONObject(bundle.getString("cn.jpush.android.EXTRA"));
                new Receiver().getMessageByID(context, data, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
//			receiver.doCallBack(Constants.number, receiver.getID(bundle), receiver.getExtra(bundle, "code"));
//			receiver.doSave(receiver.getExtra(bundle, "code"), getBodyByBundle(bundle, context));
//			receiver.doDeal(bundle);
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            //接受推送的消息通知
            //点击消息打开
            receiver.open(bundle, context);
        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
        } else {
        }
    }


}