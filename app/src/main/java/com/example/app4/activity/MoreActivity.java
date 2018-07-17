package com.example.app4.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.app3.activity.CompileActivity;
import com.example.app3.activity.SearchAppActivity;
import com.example.app3.adapter.adapter.DescHolder;
import com.example.app3.adapter.adapter.EntityAdapter;
import com.example.app3.adapter.adapter.SectionedSpanSizeLookup;
import com.example.app4.presenter.MorePresenter;
import com.example.entity.MoreEntity;
import com.example.smartclass.base.BaseRecyclerViewActivity;
import com.example.smartclass.eventbus.MessageEvent;

import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import yh.app.appstart.lg.R;
import yh.app.tool.SqliteHelper;
import yh.app.utils.GsonImpl;

/**
 * Created by Administrator on 2018/6/7 0007.
 * 更多页面
 */

public class MoreActivity extends BaseRecyclerViewActivity {
    @BindView(R.id.more_relative_return)
    RelativeLayout moreRelativeReturn;
    @BindView(R.id.more_text_search)
    TextView moreTextSearch;
    @BindView(R.id.more_lin_title)
    LinearLayout moreLinTitle;
    @BindView(R.id.more_image_cutLine1)
    ImageView moreImageCutLine1;
    @BindView(R.id.more_lin_appIcon)
    LinearLayout moreLinAppIcon;
    @BindView(R.id.more_text_compile)
    TextView moreTextCompile;
    @BindView(R.id.more_lin_list)
    LinearLayout moreLinList;
    @BindView(R.id.more_image_cutLine2)
    ImageView moreImageCutLine2;
    @BindView(R.id.more_rec)
    RecyclerView moreRec;
    @BindView(R.id.more_rel_parent)
    RelativeLayout moreRelParent;

    private EntityAdapter mAdapter;
    private MorePresenter morePresenter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_more;
    }

    @Override
    protected void setTitle(Context context) {

    }

    private String json;

    @Override
    protected void loadRecyclerViewData(final Context context) {
        try {
            if (mAdapter == null) {
                mAdapter = new EntityAdapter(context);
                json = new SqliteHelper().rawQuery("select * from applicationclassify_json").get(0).get("json");
                final MoreEntity entity = GsonImpl.get().toObject(json, MoreEntity.class);
                mAdapter.setData(entity);
                GridLayoutManager manager = new GridLayoutManager(context, 4);
                //设置header
                manager.setSpanSizeLookup(new SectionedSpanSizeLookup(mAdapter, manager));
                moreRec.setLayoutManager(manager);
                moreRec.setAdapter(mAdapter);
                mAdapter.setOnItemClickLiniser(new EntityAdapter.onItemClickLiniser() {
                    @Override
                    public void onClick(DescHolder holder, final int section, final int position) {
                        holder.layout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (entity.getAllTagsList().get(section).getTagInfoList().get(position).getTagType() == 2) {
                                    showLoad("玩命加载中");
                                }
                                morePresenter.intentApplication(context, entity.getAllTagsList().get(section).getTagInfoList().get(position));
                            }
                        });
                    }

                });
            }
        } catch (Exception ignored) {
            Log.d("zsp", "loadRecyclerViewData: "+ignored);
        }

    }

    @Override
    protected void init(Context context) {
        morePresenter = new MorePresenter(context);
    }

    @Override
    @Subscribe
    public void onEventMainThread(MessageEvent event) {
        switch (event.getTag()) {
            case "获取网页成功":
                dismissLoad();
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
        morePresenter.loadLocationData(moreLinAppIcon);
    }

    @OnClick({R.id.more_relative_return, R.id.more_text_compile, R.id.more_text_search})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.more_relative_return:
                finish();
                break;
            case R.id.more_text_compile:
                intent = new Intent(MoreActivity.this, CompileActivity.class);
                intent.putExtra("locaData", json);
                break;
            case R.id.more_text_search:
                intent = new Intent(this, SearchAppActivity.class);
                break;
        }
        if (intent != null)
            startActivity(intent);

    }
}
