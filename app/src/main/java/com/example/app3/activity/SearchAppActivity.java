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
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.app3.adapter.dragrecyclear.common.DividerItemDecoration;
import com.example.app3.base_recyclear_adapter.QuickAdapter;
import com.example.app3.search.Group;
import com.example.app3.search.LocalGroupSearch;
import com.example.app3.tool.Tool;
import com.example.app4.util.FunctionIntentUtil;
import com.example.entity.MoreEntity;
import com.example.jpushdemo.ExampleApplication;
import com.example.smartclass.eventbus.MessageEvent;

import org.androidpn.push.Constants;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.yhkj.cqgyxy.R;
import yh.app.tool.MD5;
import yh.app.tool.SqliteHelper;
import yh.app.utils.GsonImpl;
import yh.app.utils.VolleyInterface;
import yh.app.utils.VolleyRequest;
import 云华.智慧校园.工具.RSAHelper;
import 云华.智慧校园.工具._链接地址导航;


public class SearchAppActivity extends com.example.smartclass.base.BaseRecyclerViewActivity {


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
                holder.setImageView((Activity) context, R.id.contact_image1, data.getFunction_icon());
                holder.setTextView(R.id.contact_name, data.getFunction_name());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new FunctionIntentUtil<>(data, context).intent2();
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
        String json = new SqliteHelper().rawQuery("select * from applicationclassify_json").get(0).get("json");
        final MoreEntity entity = GsonImpl.get().toObject(json, MoreEntity.class);
        for (int i = 0; i < entity.getAllTagsList().size(); i++) {
            for (int j = 0; j < entity.getAllTagsList().get(i).getTagInfoList().size(); j++) {
                allAppGroups.add(new Group(
                        entity.getAllTagsList().get(i).getTagInfoList().get(j).getTagId(),
                        entity.getAllTagsList().get(i).getTagInfoList().get(j).getLatlon(),
                        entity.getAllTagsList().get(i).getTagInfoList().get(j).getTagName(),
                        "",
                        entity.getAllTagsList().get(i).getTagInfoList().get(j).getLat(),
                        entity.getAllTagsList().get(i).getTagInfoList().get(j).getPower(),
                        entity.getAllTagsList().get(i).getTagInfoList().get(j).getAndroid_packname(),
                        entity.getAllTagsList().get(i).getTagInfoList().get(j).getAndroid_address(),
                        entity.getAllTagsList().get(i).getTagInfoList().get(j).getTagType(),
                        entity.getAllTagsList().get(i).getTagInfoList().get(j).getFunction_key(),
                        entity.getAllTagsList().get(i).getTagInfoList().get(j).getUrl()
                ));
            }
        }
        searchAppEdInput.addTextChangedListener(textWatcher);
    }

    @Override
    @Subscribe
    public void onEventMainThread(MessageEvent event) {
        switch (event.getTag()){
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
            if (!isNumber(s.toString()))
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
}
