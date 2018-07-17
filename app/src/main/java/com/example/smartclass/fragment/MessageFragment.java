package com.example.smartclass.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.app3.adapter.dragrecyclear.common.DividerItemDecoration;
import com.example.app3.base_recyclear_adapter.QuickAdapter;
import com.example.app3.tool.HintTool;
import com.example.app3.tool.Tool;
import com.example.smartclass.activity.BrowserActivity;
import com.example.smartclass.base.BaseFragment;
import com.example.smartclass.entity.MyClassEntity;
import com.example.smartclass.eventbus.MessageEvent;
import com.example.smartclass.network.BaseVolleyRequest;
import com.example.smartclass.network.UrlUtil;
import com.example.smartclass.util.AuthenticationUtil;
import com.example.smartclass.util.TagUtil;
import yh.app.appstart.lg.R;

import org.androidpn.push.Constants;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import yh.app.utils.GsonImpl;

/**
 * Created by Administrator on 2018/1/4 0004.
 * <p>
 * 授课信息
 */

public class MessageFragment extends BaseFragment {
    @BindView(R.id.rec_sc_message)
    RecyclerView recScMessage;
    Unbinder unbinder;
    @BindView(R.id.text_sc_no_data)
    TextView textScNoData;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sc_message;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        getData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private Map<String, String> messageMap;

    private void getData() {
        showLoad(HintTool.Loading);
        if (messageMap == null) {
            messageMap = new HashMap<>();
        }
        BaseVolleyRequest request = new BaseVolleyRequest();
        request.VolleyRequest(messageMap, UrlUtil.MyClass, TagUtil.MyClassTag);
    }

    @SuppressLint("SetTextI18n")
    @Subscribe
    public void onEventMainThread(MessageEvent event) {
        String result = (String) event.getMessage();
        switch (event.getTag()) {
            case TagUtil.MyClassTag:
                if (result.equals(HintTool.REQUESTFAIL)) {
                    textScNoData.setText(HintTool.NoData1);
                    recScMessage.setVisibility(View.GONE);
                    textScNoData.setVisibility(View.VISIBLE);
                } else {
                    MyClassEntity myClassEntity = GsonImpl.get().toObject(result, MyClassEntity.class);
                    List<MyClassEntity.ContentBean> content = myClassEntity.getContent();
                    if (content.size() == 0) {
                        textScNoData.setText(HintTool.NoData1);
                        recScMessage.setVisibility(View.GONE);
                        textScNoData.setVisibility(View.VISIBLE);
                    } else {
                        recScMessage.setVisibility(View.VISIBLE);
                        textScNoData.setVisibility(View.GONE);
                        QuickAdapter adapter = new QuickAdapter<MyClassEntity.ContentBean>(content) {
                            @Override
                            public int getLayoutId(int viewType) {
                                return R.layout.sc_message_item;
                            }

                            @Override
                            public void convert(VH holder, final MyClassEntity.ContentBean data, final int position) {
                                holder.setTextView(R.id.class_sc_message_item, data.getBjmc());
                                holder.setTextView(R.id.course_sc_message_item, data.getKcmc());
                                holder.setRelativeLayout(R.id.parent_sc_message_item).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (!Tool.isFastDoubleClick()) {
                                            Intent intent = new Intent(getActivity(), BrowserActivity.class);
                                            intent.putExtra("title", "授课详情");
                                            if (AuthenticationUtil.getIdentity().equals(AuthenticationUtil.Teacher)) {
                                                intent.putExtra("url", UrlUtil.TeachDetailTeacher + data.getKcbh()+ "&userid=" + Constants.number);
                                            } else {
                                                intent.putExtra("url", UrlUtil.TeachDetailStudent + data.getKcbh()+ "&userid=" + Constants.number);
                                            }
                                            startActivity(intent);
                                        }
                                    }
                                });
                            }

                        };
                        recScMessage.setLayoutManager(new LinearLayoutManager(getActivity()));
                        recScMessage.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
                        recScMessage.setAdapter(adapter);
                    }
                }
                break;
        }
        dismissLoad();
    }

    @OnClick(R.id.text_sc_no_data)
    public void onViewClicked() {
        if (textScNoData.getText().toString().equals(HintTool.NoData1)){
            getData();
        }
    }
}
