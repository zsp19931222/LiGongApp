package com.example.app3.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.app3.adapter.dragrecyclear.common.DividerItemDecoration;
import com.example.app3.base.BaseRecyclerViewActivity;
import com.example.app3.base_recyclear_adapter.QuickAdapter;
import com.example.app3.search.Group;
import com.example.app3.search.LocalGroupSearch;
import com.yhkj.cqgyxy.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import yh.app.function.liaotianjiemian;
import yh.app.tool.SqliteHelper;


public class SearchFriendNetActivity extends BaseRecyclerViewActivity {


    @BindView(R.id.search_app_rel_return)
    RelativeLayout searchAppRelReturn;
    @BindView(R.id.search_app_ed_input)
    EditText searchAppEdInput;
    @BindView(R.id.search_app_rel_cancel)
    RelativeLayout searchAppRelCancel;
    @BindView(R.id.search_app_rec)
    RecyclerView searchAppRec;
    @BindView(R.id.search_app_app_text_hint)
    TextView searchAppAppTextHint;
    @BindView(R.id.search_app_app_text_no)
    TextView searchAppAppTextNo;
    @BindView(R.id.search_app_app_lin_no)
    LinearLayout searchAppAppLinNo;
    @BindView(R.id.search_app_app_text_no1)
    TextView searchAppAppTextNo1;
    @BindView(R.id.search_app_app_text_no2)
    TextView searchAppAppTextNo2;

    private ArrayList<Group> appGroups = new ArrayList<>();//搜索数据
    private ArrayList<Group> allAppGroups = new ArrayList<>();//搜索数据

    private QuickAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_app;
    }

    @Override
    protected void setTitle(Context context) {

    }

    @Override
    protected void loadRecyclerViewData(final Context context) {
        adapter = new QuickAdapter<Group>(appGroups) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_contact;
            }

            @Override
            public void convert(VH holder, final Group data, int position) {
                holder.setImageView((Activity) context, R.id.contact_image1, data.getFunction_icon(),R.drawable.np,0);
                holder.setTextView(R.id.contact_name, data.getFunction_name());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            Intent intent = new Intent(context, liaotianjiemian.class);
                            intent.putExtra("friend_id", data.getFunction_id());
                            context.startActivity(intent);
                        } catch (Exception e) {

                        }
                    }
                });
            }

        };
        searchAppRec.setLayoutManager(new LinearLayoutManager(context));
        searchAppRec.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL_LIST));
        searchAppRec.setAdapter(adapter);

    }

    @Override
    protected void init(Context context) {
        allAppGroups= (ArrayList<Group>) getIntent().getExtras().getSerializable("list");
        searchAppEdInput.addTextChangedListener(textWatcher);
        searchAppEdInput.setHint("搜索好友");
        searchAppAppTextHint.setText("输入你要找的好友名称");
        searchAppAppTextNo1.setText("找不到");
        searchAppAppTextNo2.setText("这个好友");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.search_app_rel_return, R.id.search_app_rel_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.search_app_rel_return:
                finish();
                break;
            case R.id.search_app_rel_cancel:
                finish();
                break;
        }
    }


    /**
     * 搜索框监听
     */
    TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
            if (s != null && s.length() > 0) {
                List<Group> listG = LocalGroupSearch.searchGroup(s, allAppGroups);
                if (listG != null && listG.size() > 0) {
                    searchAppRec.setVisibility(View.VISIBLE);
                    searchAppAppLinNo.setVisibility(View.GONE);
                    searchAppAppTextHint.setVisibility(View.GONE);
                    appGroups.clear();
                    appGroups.addAll(listG);
                    adapter.notifyDataSetChanged();
                } else {
                    searchAppRec.setVisibility(View.GONE);
                    searchAppAppLinNo.setVisibility(View.VISIBLE);
                    searchAppAppTextHint.setVisibility(View.GONE);
                    searchAppAppTextNo.setText("“" + s + "”");
                }
            } else {
                searchAppRec.setVisibility(View.GONE);
                searchAppAppLinNo.setVisibility(View.GONE);
                searchAppAppTextHint.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
        }

        @Override
        public void afterTextChanged(Editable arg0) {
        }
    };
}
