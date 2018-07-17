package com.example.jpushdemo;

import java.util.HashSet;
import java.util.Set;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import org.androidpn.push.Constants;

public class ApnsStart {
    private Context context;

    public ApnsStart(Context context) {
        this.context = context;
    }

    public void start() {
        registerMessageReceiver();
        setAlias();
    }

    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    public MessageReceiver getMessageReceiver() {
        return mMessageReceiver;
    }

    private static final int MSG_SET_ALIAS = 1001;
    private static final int MSG_SET_TAGS = 1002;

    @SuppressLint("HandlerLeak")
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    String s = msg.obj.toString();
                    JPushInterface.setAlias(context, 0, s);
                    Set<String> Set = new HashSet<>();
                    Set.add(s);
                    JPushInterface.setAliasAndTags(context, s, Set, mAliasCallback);
                    break;

                case MSG_SET_TAGS:
                    JPushInterface.deleteAlias(context, 0);
                    break;

                default:
            }
        }
    };
    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {

        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            Log.d("zsp", "jpush状态码：" + code + "  别名:" + alias);
            switch (code) {
                case 0:
                    break;
                case 6002:
                    if (ExampleUtil.isConnected(context)) {
                        mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 2);
                    }
                    break;
                default:
            }
        }

    };

    private void setAlias() {
        if (TextUtils.isEmpty(Constants.jpushID)) {
            return;
        }
        if (!ExampleUtil.isValidTagAndAlias(Constants.jpushID)) {
            return;
        }
        if (!"".equals(Constants.jpushID)) {
            mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, Constants.jpushID));
        }
    }

    public void clearAlias() {
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_TAGS, ""));
        unregisterMessageReceiver();
    }

    private void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        context.registerReceiver(mMessageReceiver, filter);
    }

    public void unregisterMessageReceiver() {
//        context.unregisterReceiver(mMessageReceiver);
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                String messge = intent.getStringExtra(KEY_MESSAGE);
                String extras = intent.getStringExtra(KEY_EXTRAS);
                StringBuilder showMsg = new StringBuilder();
                showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                if (!ExampleUtil.isEmpty(extras)) {
                    showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                }
            }
        }
    }
}