package com.example.app3.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.app3.base.BaseRecyclerViewActivity;
import com.example.app3.base_recyclear_adapter.QuickAdapter;
import com.example.app3.entity.LayoutEntity;
import com.example.app3.tool.AddView;
import com.example.app3.tool.MyOnClickListener;
import com.example.app3.view.MyTitleView;
import com.yhkj.cqgyxy.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import yh.app.utils.FileUtils;
import yh.app.utils.GsonImpl;
import 云华.智慧校园.工具.RHelper;

/**
 * Created by Administrator on 2017/9/21.
 * <p>
 * 我的数据界面
 */

public class MyDataActivity extends BaseRecyclerViewActivity {

    @BindView(R.id.myreport_title)
    MyTitleView myreportTitle;
    @BindView(R.id.myreport_rec)
    RecyclerView myreportRec;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_report;
    }

    @Override
    protected void setTitle(Context context) {
        myreportTitle.setTitle(context.getResources().getString(R.string.title_activity_mydata), context);
        myreportTitle.setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private Intent intent;
    private QuickAdapter adapter;

    @Override
    protected void loadRecyclerViewData(final Context context) {
        LayoutEntity entity = GsonImpl.get().toObject(FileUtils.readJsonFile(this, "mydata"), LayoutEntity.class);
        adapter = new QuickAdapter<LayoutEntity.AllTagsListBean>(entity.getAllTagsList()) {

            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_archives;
            }

            @Override
            public void convert(VH holder, final LayoutEntity.AllTagsListBean data, final int position) {
                if (data.getType().equals("fill_view")) {
                    holder.setRelativeLayout(R.id.item_archives_lin_fill).addView(AddView.addView(context, data.getLayout(), 15));
                    holder.setLinearLayout(R.id.item_archives_lin_nofill).setVisibility(View.GONE);
                } else {
                    holder.setTextView(R.id.item_archives_text_name, data.getTxt());
                    holder.setImageView((Activity) context, R.id.item_archives_image_icon, data.getPic(), new RHelper().getId(context, data.getPic_default())).setVisibility(View.VISIBLE);
                }
//                holder.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (!data.getType().equals("fill_view")) {
//                            try {
//                                intent = new Intent(data.getCls());
//                                context.startActivity(intent);
//                            } catch (Exception e) {
//
//                            }
//                        }
//                    }
//                });
                holder.itemView.setOnClickListener(new MyOnClickListener(data.getCls(), context));
            }

        };
        myreportRec.setLayoutManager(new LinearLayoutManager(context));
        myreportRec.setAdapter(adapter);
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
}
