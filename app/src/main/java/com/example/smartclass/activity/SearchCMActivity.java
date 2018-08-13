package com.example.smartclass.activity;

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
import com.example.app3.base_recyclear_adapter.QuickAdapter;
import com.example.app3.search.Group;
import com.example.app3.search.LocalGroupSearch;
import com.example.app3.view.MyTitleView;
import com.example.smartclass.base.BaseRecyclerViewActivity;
import com.example.smartclass.eventbus.MessageEvent;
import com.yhkj.cqgyxy.R;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import yh.app.function.liaotianjiemian;


public class SearchCMActivity extends BaseRecyclerViewActivity {


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
    @BindView(R.id.cm_title)
    MyTitleView cmTitle;

    private ArrayList<Group> appGroups = new ArrayList<>();//搜索数据
    private ArrayList<Group> allAppGroups = new ArrayList<>();//搜索数据

    private QuickAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_cm;
    }

    @Override
    protected void setTitle(Context context) {
        cmTitle.setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
                holder.setImageView((Activity) context, R.id.contact_image1, data.getFunction_icon(), R.drawable.np, 0);
                holder.setTextView(R.id.contact_name, data.getFunction_name());
                holder.setTextView(R.id.contact_id, data.getFunction_id()).setVisibility(View.VISIBLE);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            Intent intent = new Intent(context, liaotianjiemian.class);
                            intent.putExtra("friend_id", data.getFunction_id());
                            intent.putExtra("hyName", data.getFunction_name());
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
        List<Map<String, String>> data = (List<Map<String, String>>) getIntent().getExtras().getSerializable("data");
        for (int i = 0; i < data.size(); i++) {
            allAppGroups.add(new Group(data.get(i).get("FRIEND_ID"), data.get(i).get("handimg"), data.get(i).get("name"), data.get(i).get("userid"), data.get(i).get("szm")));
        }
        searchAppEdInput.addTextChangedListener(textWatcher);
        searchAppEdInput.setHint("搜索好友");
        searchAppAppTextHint.setText("输入你要找的好友名称");
        searchAppAppTextNo1.setText("找不到");
        searchAppAppTextNo2.setText("这个好友");
    }

    @Override
    @Subscribe
    public void onEventMainThread(MessageEvent event) {

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
