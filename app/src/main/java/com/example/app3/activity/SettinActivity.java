package com.example.app3.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.app3.base.BaseRecyclerViewActivity;
import com.example.app3.base_recyclear_adapter.QuickAdapter;
import com.example.app3.entity.LayoutEntity;
import com.example.app3.eventbus.MyEventBus;
import com.example.app3.popupwindow.HintPopup;
import com.example.app3.tool.AddView;
import com.example.app3.tool.GlideCacheUtil;
import com.example.app3.tool.HintTool;
import com.example.app3.tool.Tool;
import com.example.app3.utils.GetChangePassWord;
import com.example.app3.view.MyTitleView;
import com.example.app4.util.DefaultUtil;
import com.yhkj.cqgyxy.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import yh.app.utils.FileUtils;
import yh.app.utils.GsonImpl;
import 云华.智慧校园.功能.LoginOut;

/**
 * Created by Administrator on 2017/9/11.
 * <p>
 * 设置页面
 */

public class SettinActivity extends BaseRecyclerViewActivity {

    @BindView(R.id.setting_title)
    MyTitleView settingTitle;
    @BindView(R.id.setting_rec)
    RecyclerView settingRec;
    @BindView(R.id.setting_btn_logout)
    Button settingBtnLogout;
    @BindView(R.id.setting_lin_parent)
    LinearLayout settingLinParent;
    private QuickAdapter adapter;

    private String titleStr;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    /**
     * 设置title
     */
    @Override
    protected void setTitle(Context context) {
        titleStr = context.getResources().getString(R.string.title_activity_setting);
        settingTitle.setTitle(titleStr, context);
        settingTitle.setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 加载数据
     *
     * @param context
     */

    private Intent intent;
    private List<LayoutEntity.AllTagsListBean> allTagsListBeans = new ArrayList<>();

    @Override
    protected void loadRecyclerViewData(final Context context) {
        LayoutEntity entity = GsonImpl.get().toObject(FileUtils.readJsonFile(this, "setting"), LayoutEntity.class);
        allTagsListBeans.addAll(entity.getAllTagsList());
        adapter = new QuickAdapter<LayoutEntity.AllTagsListBean>(allTagsListBeans) {

            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_archives;
            }

            @Override
            public void convert(VH holder, final LayoutEntity.AllTagsListBean data, final int position) {
                if (data.getType().equals("fill_view")) {
                    holder.setRelativeLayout(R.id.item_archives_lin_fill).addView(AddView.addView(context, data.getLayout(), 15));
                    holder.setLinearLayout(R.id.item_archives_lin_nofill).setVisibility(View.GONE);
                    holder.setRelativeLayout(R.id.item_archives_lin_fill).setVisibility(View.VISIBLE);
                } else {
                    holder.setLinearLayout(R.id.item_archives_lin_nofill).setVisibility(View.VISIBLE);
                    holder.setRelativeLayout(R.id.item_archives_lin_fill).setVisibility(View.GONE);
                    holder.setTextView(R.id.item_archives_text_name, data.getTxt());
                    if (data.getTxt().equals("空间清理")) {
                        holder.setTextView(R.id.item_archives_text_num, new GlideCacheUtil().getCacheSize(context));
                    } else {
                        holder.setTextView(R.id.item_archives_text_num, "");
                    }
                }
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!Tool.isFastDoubleClick()) {
                            try {
                                if (data.getTxt().equals("空间清理")) {
                                    HintPopup hintPopup = new HintPopup(context, settingLinParent, HintTool.CLEARCACHE);
                                    hintPopup.showPopupWindow(settingLinParent);
                                } else if (data.getTxt().equals("修改密码")) {
                                    if (DefaultUtil.isIsIntegrate()) {
                                        intent = new Intent(context, com.example.smartclass.activity.BrowserActivity.class);
                                        intent.putExtra("url", GetChangePassWord.getChangePasswordUrl());
                                        intent.putExtra("title", "");
                                    } else {
                                        intent = new Intent(data.getCls());
                                    }
                                } else {
                                    intent = new Intent(data.getCls());
                                }
                                startActivity(intent);
                            } catch (Exception ignored) {

                            }
                        }
                    }
                });

            }

        };
        settingRec.setLayoutManager(new LinearLayoutManager(context));
        settingRec.setAdapter(adapter);
    }

    @Override
    protected void init(Context context) {
        EventBus.getDefault().register(this);
    }


    @OnClick(R.id.setting_btn_logout)
    public void onViewClicked() {
        HintPopup hintPopup = new HintPopup(this, settingLinParent, HintTool.LOGINOUT);
        hintPopup.showPopupWindow(settingLinParent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//反注册EventBus
    }

    @Subscribe
    public void onEventMainThread(MyEventBus event) {
        if (event.getMsg().equals(HintTool.CLEARCACHE)) {//清空缓存成功
            allTagsListBeans.clear();
            LayoutEntity entity = GsonImpl.get().toObject(FileUtils.readJsonFile(this, "setting"), LayoutEntity.class);
            allTagsListBeans.addAll(entity.getAllTagsList());
            adapter.notifyDataSetChanged();
        } else if (event.getMsg().equals(HintTool.LOGINOUT)) {
            new LoginOut().doLoginOut(this);
        }
    }
}
