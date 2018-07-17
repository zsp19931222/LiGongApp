package com.example.app3.activity;

import android.app.Notification;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.app3.base.BaseRecyclerViewActivity;
import com.example.app3.popupwindow.HintPopup;
import com.example.app3.tool.HintTool;
import com.example.app3.view.MyTitleView;

import yh.app.appstart.lg.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;
import yh.app.logTool.Log;
import yh.app.tool.SqliteHelper;
import yh.app.utils.SharedPreferencesUtils;

/**
 * Created by Administrator on 2017/9/21.
 */

public class MessageActivity extends BaseRecyclerViewActivity {


    @BindView(R.id.message_title)
    MyTitleView messageTitle;
    @BindView(R.id.message_text_open)
    TextView messageTextOpen;
    @BindView(R.id.message_rel_open)
    RelativeLayout messageRelOpen;
    @BindView(R.id.message_text_tips)
    TextView messageTextTips;
    @BindView(R.id.message_box_ring)
    CheckBox messageBoxRing;
    @BindView(R.id.message_rel_ring)
    RelativeLayout messageRelRing;
    @BindView(R.id.message_box_clear)
    CheckBox messageBoxClear;
    @BindView(R.id.message_rel_clear)
    RelativeLayout messageRelClear;
    @BindView(R.id.message_btn_logout)
    Button messageBtnLogout;
    @BindView(R.id.message_rel_parent)
    RelativeLayout messageRelParent;

    private boolean voice_bool;
    private boolean vibrate_bool;
    private SharedPreferences.Editor editor;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_message_setting;
    }

    /**
     * 设置title
     */
    @Override
    protected void setTitle(Context context) {
        messageTitle.setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void loadRecyclerViewData(Context context) {

    }

    @Override
    protected void init(final Context context) {
        SharedPreferences sharedPreferences = getSharedPreferences("voice_vibrate", Context.MODE_APPEND);

        voice_bool = sharedPreferences.getBoolean("voice_bool", true);
        vibrate_bool = sharedPreferences.getBoolean("vibrate_bool", true);

        messageBoxRing.setChecked(voice_bool);
        messageBoxClear.setChecked(vibrate_bool);

        editor = sharedPreferences.edit();



        messageBoxRing.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                voice_bool = isChecked;
                setSoundAndVibrate(voice_bool, vibrate_bool,context);
            }
        });
        messageBoxClear.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                vibrate_bool = isChecked;
                setSoundAndVibrate(voice_bool, vibrate_bool,context);
            }
        });
    }

    @OnClick(R.id.message_btn_logout)
    public void onViewClicked() {
        HintPopup hintPopup = new HintPopup(this, messageRelParent, HintTool.LOGINOUT);
        hintPopup.showPopupWindow(messageRelParent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    private void setSoundAndVibrate(boolean isOpenSound, boolean isOpenVibrate,Context context) {
        BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(context);
        if (isOpenVibrate && !isOpenSound) {//只有振动
            builder.notificationDefaults = Notification.DEFAULT_VIBRATE;
        } else if (isOpenSound && !isOpenVibrate) {//只有声音
            builder.notificationDefaults = Notification.DEFAULT_SOUND;
        } else if (isOpenSound && isOpenVibrate) {//两个都有
            builder.notificationDefaults = Notification.DEFAULT_ALL;
        } else {//只有呼吸灯
            builder.notificationDefaults = Notification.DEFAULT_LIGHTS;
        }
        JPushInterface.setDefaultPushNotificationBuilder(builder);
        editor.putBoolean("voice_bool", isOpenSound);
        editor.putBoolean("vibrate_bool", isOpenVibrate);
        editor.commit();
    }
}
