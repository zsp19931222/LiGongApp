package com.example.app4.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.app3.base_recyclear_adapter.QuickAdapter;
import com.example.app3.entity.LayoutEntity;
import com.example.app3.view.MyTitleView;
import com.example.smartclass.base.BaseRecyclerViewActivity;
import com.example.smartclass.eventbus.MessageEvent;
import com.example.smartclass.util.TagUtil;
import com.yhkj.cqgyxy.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import yh.app.utils.FileUtils;
import yh.app.utils.GsonImpl;
import 云华.智慧校园.工具.RHelper;

/**
 * Created by Administrator on 2018/2/28 0028.
 * 修改手机号选择页面
 */

public class ChangePhoneNumActivity extends BaseRecyclerViewActivity {
    @BindView(R.id.change_phone_title)
    MyTitleView changePhoneTitle;
    @BindView(R.id.change_phone_rec)
    RecyclerView changePhoneRec;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_change_phone;
    }

    @Override
    protected void setTitle(Context context) {
        changePhoneTitle.setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void loadRecyclerViewData(final Context context) {
        LayoutEntity entity = GsonImpl.get().toObject(FileUtils.readJsonFile(context, "change_phone"), LayoutEntity.class);
        QuickAdapter quickAdapter = new QuickAdapter<LayoutEntity.AllTagsListBean>(entity.getAllTagsList()) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_course_detail;
            }

            @Override
            public void convert(VH holder, final LayoutEntity.AllTagsListBean data, int position) {
                holder.setTextView(R.id.item_cd_text, data.getTxt());
                holder.setImageView((Activity) context, R.id.item_cd_image, new RHelper().getDrawable(context, data.getPic_default())).setVisibility(View.GONE);
                holder.setRelativeLayout(R.id.item_cd_rel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(data.getCls());
                        startActivity(intent);
                    }
                });
            }

        };
        changePhoneRec.setLayoutManager(new LinearLayoutManager(context));
        changePhoneRec.setAdapter(quickAdapter);
    }

    @Override
    protected void init(Context context) {

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

    }

    @Subscribe
    public void onEventMainThread(MessageEvent event) {
        switch (event.getTag()) {
            case TagUtil.ChangePhoneBindingSuccess:
                finish();
                break;
        }
    }
}
