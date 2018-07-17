package com.example.app3.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.app3.base.BaseRecyclerViewActivity;
import com.example.app3.base_recyclear_adapter.QuickAdapter;
import com.example.app3.entity.SearchEntity;
import com.example.app3.tool.HintTool;
import com.example.app3.tool.JSONTool;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import yh.app.appstart.lg.R;
import com.zxing.decoding.Intents;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import yh.app.utils.GsonImpl;
import yh.app.utils.ToastUtils;
import yh.app.utils.VolleyInterface;
import yh.app.utils.VolleyRequest;
import yh.tool.widget.LoadDiaog;
import 云华.智慧校园.工具.MapTools;
import 云华.智慧校园.工具._链接地址导航;

/**
 * Created by Administrator on 2017/10/12.
 */

public class SearchActivity extends BaseRecyclerViewActivity {
    @BindView(R.id.search_rel_return)
    RelativeLayout searchRelReturn;
    @BindView(R.id.search_ed_input)
    EditText searchEdInput;
    @BindView(R.id.search_rel_cancel)
    RelativeLayout searchRelCancel;
    @BindView(R.id.search_rec)
    RecyclerView searchRec;
    @BindView(R.id.search_refreshLayout)
    SmartRefreshLayout searchRefreshLayout;
    @BindView(R.id.search_text_no)
    TextView searchTextNo;
    @BindView(R.id.search_lin_no)
    LinearLayout searchLinNo;

    private QuickAdapter adapter;
    private int pagesize = 20;
    private int pagenow = 1;
    private String cxtj = "";

    private LoadDiaog loadDiaog;
    private boolean isFirst = true;
    private List<SearchEntity.ContentBean.MessageBean> message;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void setTitle(Context context) {

    }

    @Override
    protected void loadRecyclerViewData(Context context) {

    }

    @Override
    protected void init(final Context context) {
        loadDiaog = new LoadDiaog(context);
        searchRefreshLayout.setEnableLoadmore(true);
        searchRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {//加载更多
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                if (cxtj.equals(searchEdInput.getText().toString())) {
                    pagenow++;
                } else {
                    cxtj = searchEdInput.getText().toString();
                    pagenow = 1;
                }
                searchNet(context, pagesize, pagenow, cxtj);
            }
        });
        searchRefreshLayout.setEnableRefresh(true);
        searchRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                pagenow = 1;
                cxtj = searchEdInput.getText().toString();
                searchNet(context, pagesize, pagenow, cxtj);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.search_rel_return, R.id.search_rel_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.search_rel_return:
                finish();
                break;
            case R.id.search_rel_cancel:
                cxtj = searchEdInput.getText().toString().trim();
                searchNet(SearchActivity.this, pagesize, pagenow, cxtj);
                break;
        }
    }

    /**
     * 搜索好友
     *
     * @param pagesize 条数
     * @param pagenow  页数
     * @param cxtj     搜索内容
     * @param context
     */
    private void searchNet(final Context context, int pagesize, int pagenow, String cxtj) {
        if (isFirst) {
            loadDiaog.show();
            message = new ArrayList<>();
        }
        if (pagenow == 1) {
            message.clear();
        }
        Map<String, String> params = MapTools.buildMap(new String[][]
                {
                        {
                                "pagesize", pagesize + ""
                        },
                        {
                                "pagenow", pagenow + ""
                        }
                        ,
                        {
                                "cxtj", cxtj
                        }
                });
        VolleyRequest.RequestPost(_链接地址导航.DC.搜索好友.getUrl(), params, new VolleyInterface() {
            @Override
            public void onMySuccess(String result) {
                if (JSONTool.Code(result).equals(JSONTool.SUCCESS)) {
                    setRecyclerViewData(context, result);

                } else {
                    ToastUtils.Toast(context, HintTool.REQUESTFAIL);
                }
                if (loadDiaog.isShowing()) {
                    loadDiaog.dismiss();
                }
            }

            @Override
            public void onMyError(VolleyError error) {
                ToastUtils.Toast(context, HintTool.REQUESTFAIL);
                if (loadDiaog.isShowing()) {
                    loadDiaog.dismiss();
                }
            }
        });
    }

    private void setRecyclerViewData(final Context context, String json) {
        SearchEntity entity = GsonImpl.get().toObject(json, SearchEntity.class);
        message.addAll(entity.getContent().getMessage());
        if (message.size() != 0) {
            searchRec.setVisibility(View.VISIBLE);
            searchLinNo.setVisibility(View.GONE);
            if (isFirst) {
                adapter = new QuickAdapter<SearchEntity.ContentBean.MessageBean>(message) {
                    @Override
                    public int getLayoutId(int viewType) {
                        return R.layout.item_archives;
                    }

                    @Override
                    public void convert(VH holder, final SearchEntity.ContentBean.MessageBean data, int position) {
                        holder.setImageView((Activity) context, R.id.item_archives_image_icon, data.getFACEADDRESS()).setVisibility(View.VISIBLE);
                        holder.setImageView((Activity) context, R.id.item_archives_iamge_head, data.getFACEADDRESS()).setVisibility(View.GONE);
                        holder.setImageView((Activity) context, R.id.item_archives_iamge_icon, data.getFACEADDRESS()).setVisibility(View.GONE);
                        holder.setTextView(R.id.item_archives_text_name, data.getXM());
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(context, FriendDetailActivity.class);
                                intent.putExtra("fqr", data.getUSERID());
                                context.startActivity(intent);
                            }
                        });
                    }

                };
                searchRec.setLayoutManager(new LinearLayoutManager(context));
                searchRec.setAdapter(adapter);
                isFirst = false;
            } else {
                adapter.notifyDataSetChanged();
            }
        } else {
            searchRec.setVisibility(View.GONE);
            searchLinNo.setVisibility(View.VISIBLE);
            searchTextNo.setText("“"+cxtj+"”");
        }

        searchRefreshLayout.finishLoadmore();
        searchRefreshLayout.finishRefresh();
    }

}
