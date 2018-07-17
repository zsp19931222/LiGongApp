package com.example.app4.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.app3.adapter.dragrecyclear.common.DividerItemDecoration;
import com.example.app3.base_recyclear_adapter.QuickAdapter;
import com.example.app3.search.Group;
import com.example.app3.search.LocalGroupSearch;
import com.example.app3.tool.HintTool;
import com.example.app4.util.GetSchoolListUtil;
import com.example.smartclass.base.BaseRecyclerViewActivity;
import com.example.smartclass.eventbus.MessageEvent;
import com.example.smartclass.util.TagUtil;
import yh.app.appstart.lg.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SearchSchoolActivity extends BaseRecyclerViewActivity {


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

    private static final String TAG = "SearchSchoolActivity";

    private ArrayList<Group> appGroups = new ArrayList<>();//搜索数据
    private ArrayList<Group> allAppGroups = new ArrayList<>();//搜索数据

    private QuickAdapter adapter;
    List<Map<String, String>> data = new ArrayList<>();


    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_app;
    }

    @Override
    protected void setTitle(Context context) {

    }

    @Override
    protected void loadRecyclerViewData(final Context context) {
        adapter = new QuickAdapter<Map<String, String>>(data) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_contact;
            }

            @Override
            public void convert(VH holder, final Map<String, String> data, int position) {
                holder.setImageView((Activity) context, R.id.contact_image1, data.get("handimg"));
                holder.setTextView(R.id.contact_name, data.get("name"));
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
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
        try {
            showLoad(HintTool.Loading);
//            data = GetSchoolListUtil.getSchoolData("select xxmcpy as szm, xxmc as name, xxbh as FRIEND_ID, szd as userid, xxtb as handimg from schools ", data);
//            for (int i = 0; i < data.size(); i++) {
//                allAppGroups.add(new Group(data.get(i).get("FRIEND_ID"), data.get(i).get("handimg"), data.get(i).get("name"), data.get(i).get("userid"), data.get(i).get("szm")));
//            }
            searchAppEdInput.addTextChangedListener(textWatcher);
            searchAppEdInput.setHint("搜索学校");
            dismissLoad();
        } catch (Exception e) {
            Log.d(TAG, "init: " + e);
        }

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

        @SuppressLint("SetTextI18n")
        @Override
        public void onTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
            if (!isNumber(s.toString()))
                if (s.length() > 0) {
//                    List<Group> listG = LocalGroupSearch.searchGroup(s, allAppGroups);
                    data.clear();
                    data = GetSchoolListUtil.getSchoolData("select xxmcpy as szm, xxmc as name, xxbh as FRIEND_ID, szd as userid, xxtb as handimg from schools where xxmcpy like '%" + s + "%'"+"or xxmc like '%" + s + "%'"+"or xxmcpyjc like '%" + s + "%'", data);
                    if (data != null && data.size() > 0) {
                        searchAppRec.setVisibility(View.VISIBLE);
                        searchAppAppLinNo.setVisibility(View.GONE);
                        searchAppAppTextHint.setVisibility(View.GONE);
//                        appGroups.clear();
//                        appGroups.addAll(listG);
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

    /**
     * 判断字符串是否是数字
     */
    public boolean isNumber(String value) {
        return isInteger(value) || isDouble(value);
    }

    /**
     * 判断字符串是否是整数
     */
    public boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 判断字符串是否是浮点数
     */
    public boolean isDouble(String value) {
        try {
            Double.parseDouble(value);
            if (value.contains("."))
                return true;
            return false;
        } catch (NumberFormatException e) {
            return false;
        }
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
