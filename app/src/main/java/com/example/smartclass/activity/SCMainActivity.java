package com.example.smartclass.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.app3.utils.GlideLoadUtils;
import com.example.app3.view.MyTitleView;
import com.example.smartclass.base.BaseRecyclerViewActivity;
import com.example.smartclass.eventbus.MessageEvent;
import com.example.smartclass.fragment.MessageFragment;
import com.example.smartclass.fragment.TodayFragment;
import com.example.smartclass.util.AuthenticationUtil;
import com.example.smartclass.util.TagUtil;
import yh.app.appstart.lg.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import yh.app.logTool.Log;

/**
 * Created by Administrator on 2018/1/4 0004.
 * <p>
 * 智慧课堂主页面
 */

public class SCMainActivity extends BaseRecyclerViewActivity {

    @BindView(R.id.sc_title)
    MyTitleView scTitle;
    @BindView(R.id.fram_sc)
    FrameLayout framSc;
    @BindView(R.id.image_sc_class)
    ImageView imageScClass;
    @BindView(R.id.text_sc_class)
    TextView textScClass;
    @BindView(R.id.lin_sc_class)
    LinearLayout linScClass;
    @BindView(R.id.image_sc_message)
    ImageView imageScMessage;
    @BindView(R.id.text_sc_message)
    TextView textScMessage;
    @BindView(R.id.lin_sc_message)
    LinearLayout linScMessage;
    @BindView(R.id.lin_sc)
    LinearLayout linSc;


    private TodayFragment todayFragment;
    private MessageFragment messageFragment;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    public static String function_id = "";
    public static String user_id = "";

    private boolean isFirst = true;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_smart_class;
    }

    @Override
    protected void setTitle(Context context) {
        scTitle.setLeftListener(new View.OnClickListener() {
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
    protected void init(Context context) {
        function_id = getIntent().getExtras().getString("function_id");
        user_id = getIntent().getExtras().getString("user_id");
        fragmentManager = getSupportFragmentManager();
        initToday(this, true);
        initMessage(this, false);

    }

    @Override
    @Subscribe
    public void onEventMainThread(MessageEvent event) {

    }

    @OnClick({R.id.lin_sc_class, R.id.lin_sc_message})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lin_sc_class:
                initToday(this, true);
                initMessage(this, false);
                scTitle.setTitle("智慧课堂", this);
                EventBus.getDefault().post(new MessageEvent(TagUtil.AutoRefreshTag, ""));//通知刷新课程列表
                break;
            case R.id.lin_sc_message:
                initToday(this, false);
                initMessage(this, true);
                if (AuthenticationUtil.getIdentity().equals(AuthenticationUtil.Teacher)) {
                    scTitle.setTitle("授课信息", this);
                } else {
                    scTitle.setTitle("我的课程", this);
                }
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isFirst) {
            EventBus.getDefault().post(new MessageEvent(TagUtil.AutoRefreshTag, ""));//通知刷新课程列表
        }
        isFirst = false;
    }

    private void initToday(Context context, boolean b) {
        if (b) {
            textScClass.setTextColor(ContextCompat.getColor(context, R.color.color_blue_3da8f5));
            GlideLoadUtils.getInstance().glideLoadLocal(context, R.drawable.today_class_true, imageScClass);
            initTodayFragment();
        } else {
            textScClass.setTextColor(ContextCompat.getColor(this, R.color.color_gray_666666));
            GlideLoadUtils.getInstance().glideLoadLocal(this, R.drawable.today_class_false, imageScClass);
            initMessageFragment();
        }
    }

    private void initMessage(Context context, boolean b) {
        if (b) {
            if (AuthenticationUtil.getIdentity().equals(AuthenticationUtil.Teacher)) {
                textScMessage.setText("授课信息");
                textScMessage.setTextColor(ContextCompat.getColor(context, R.color.color_blue_3da8f5));
                GlideLoadUtils.getInstance().glideLoadLocal(context, R.drawable.class_message_true, imageScMessage);
            } else {
                textScMessage.setText("我的课程");
                textScMessage.setTextColor(ContextCompat.getColor(context, R.color.color_blue_3da8f5));
                GlideLoadUtils.getInstance().glideLoadLocal(context, R.drawable.my_course_true, imageScMessage);
            }
        } else {
            if (AuthenticationUtil.getIdentity().equals(AuthenticationUtil.Teacher)) {
                textScMessage.setText("授课信息");
                textScMessage.setTextColor(ContextCompat.getColor(context, R.color.color_gray_666666));
                GlideLoadUtils.getInstance().glideLoadLocal(context, R.drawable.class_message_false, imageScMessage);
            } else {
                textScMessage.setText("我的课程");
                textScMessage.setTextColor(ContextCompat.getColor(context, R.color.color_gray_666666));
                GlideLoadUtils.getInstance().glideLoadLocal(context, R.drawable.my_course_false, imageScMessage);
            }
        }
    }

    /**
     * 隐藏-显示今日课程
     */
    private void initTodayFragment() {
        try {
            fragmentTransaction = fragmentManager.beginTransaction();
            hideFragment(fragmentTransaction);
            if (todayFragment == null) {
                todayFragment = new TodayFragment();
                fragmentTransaction.add(R.id.fram_sc, todayFragment);
            } else {
                fragmentTransaction.show(todayFragment);
            }
            fragmentTransaction.commit();
        } catch (Exception e) {
            Log.d("Exception", e.toString());
        }
    }

    /**
     * 隐藏-显示授课信息
     */
    private void initMessageFragment() {
        try {
            fragmentTransaction = fragmentManager.beginTransaction();
            hideFragment(fragmentTransaction);
            if (messageFragment == null) {
                messageFragment = new MessageFragment();
                fragmentTransaction.add(R.id.fram_sc, messageFragment);
            } else {
                fragmentTransaction.show(messageFragment);
            }
            fragmentTransaction.commit();
        } catch (Exception e) {
            Log.d("Exception", e.toString());
        }
    }

    /**
     * 去除（隐藏）所有的Fragment
     */
    private void hideFragment(FragmentTransaction transaction) {
        if (todayFragment != null) {
            //隐藏方法
            transaction.hide(todayFragment);
        }
        if (messageFragment != null) {
            transaction.hide(messageFragment);
        }
    }
}
