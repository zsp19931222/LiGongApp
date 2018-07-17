package com.example.smartclass.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.app3.adapter.dragrecyclear.common.DividerItemDecoration;
import com.example.app3.tool.ContactComparator;
import com.example.app3.tool.HintTool;
import com.example.app3.tool.Utils;
import com.example.app3.view.LetterView;
import com.example.app3.view.MyTitleView;
import com.example.smartclass.adapter.ContactAdapter;
import com.example.smartclass.adapter.HeaderAdapterWrapper1;
import com.example.smartclass.base.BaseRecyclerViewActivity;
import com.example.smartclass.entity.GetStudentListEntity;
import com.example.smartclass.eventbus.MessageEvent;
import com.example.smartclass.network.BaseVolleyRequest;
import com.example.smartclass.network.UrlUtil;
import com.example.smartclass.util.TagUtil;
import yh.app.appstart.lg.R;

import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import yh.app.utils.GsonImpl;

/**
 * Created by Administrator on 2018/1/12 0012.
 */

public class ClassMemberActivity extends BaseRecyclerViewActivity {
    @BindView(R.id.cm_title)
    MyTitleView cmTitle;
    @BindView(R.id.cm_rec)
    RecyclerView cmRec;
    @BindView(R.id.cm_letter_view)
    LetterView cmLetterView;
    @BindView(R.id.no_data)
    TextView noData;

    private List<String> mContactList; // 联系人名称List（转换成拼音）
    private List<String> characterList; // 字母List
    private List<Map<String, String>> contactNames;
    private Map<String, String> map;
    private ContactAdapter contactAdapter;
    private HeaderAdapterWrapper1 headerAdapterWrapper1;
    private View headView;
    private LinearLayoutManager layoutManager;

    private String kcbh = "";//课程编号

    @Override
    protected int getLayoutId() {
        return R.layout.activity_class_member;
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
    protected void loadRecyclerViewData(Context context) {

    }

    @Override
    protected void init(final Context context) {
        kcbh = getIntent().getExtras().getString("kcbh");
        characterList = new ArrayList<>();
        mContactList = new ArrayList<>();
        contactNames = new ArrayList<>();
        layoutManager = new LinearLayoutManager(context);
        getStudentList(kcbh);
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


    private Map<String, String> studentListMap;

    /**
     * 获取学生列表数据
     *
     * @param kcbh 课程编号
     */
    private void getStudentList(String kcbh) {
        showLoad(HintTool.Loading);
        if (studentListMap == null) {
            studentListMap = new HashMap<>();
        }
        studentListMap.put("kcbh", kcbh);
        BaseVolleyRequest request = new BaseVolleyRequest();
        request.VolleyRequest(studentListMap, UrlUtil.GetStudentList, TagUtil.GetStudentListTag);
    }

    @SuppressLint("SetTextI18n")
    @Subscribe
    public void onEventMainThread(MessageEvent event) {
        String result = (String) event.getMessage();
        switch (event.getTag()) {
            case TagUtil.GetStudentListTag:
                if (result.equals(HintTool.REQUESTFAIL)) {
                    noData.setVisibility(View.VISIBLE);
                    noData.setText(HintTool.REQUESTFAIL + ",点击刷新");
                    cmRec.setVisibility(View.GONE);
                    dismissLoad();
                } else {
                    GetStudentListEntity studentListEntity = GsonImpl.get().toObject(result, GetStudentListEntity.class);
                    if (studentListEntity.getContent().size() == 0) {
                        noData.setVisibility(View.VISIBLE);
                        cmRec.setVisibility(View.GONE);
                    } else {
                        noData.setVisibility(View.GONE);
                        cmRec.setVisibility(View.VISIBLE);
                        for (int i = 0; i < studentListEntity.getContent().size(); i++) {
                            Map<String, String> map = new HashMap<>();
                            map.put("name", studentListEntity.getContent().get(i).getXm());
                            map.put("FRIEND_ID", studentListEntity.getContent().get(i).getXh());
                            map.put("handimg", studentListEntity.getContent().get(i).getXt());
                            map.put("userid", studentListEntity.getContent().get(i).getXh());
                            map.put("szm", "" + i);
                            contactNames.add(map);
                        }
                        setRecyclerView(this);
                    }
                }
                break;
        }
    }

    private void setRecyclerView(final Context context) {
        map = new HashMap<>();
        for (int i = 0; i < contactNames.size(); i++) {
            if (contactNames.get(i) != null) {
                String pinyin = Utils.getPingYin(contactNames.get(i).get("name"));
                map.put(pinyin, contactNames.get(i).get("name"));
                mContactList.add(pinyin);
            }
        }
        Collections.sort(mContactList, new ContactComparator());
        characterList = new ArrayList<>();
        for (int i = 0; i < mContactList.size(); i++) {
            String name = mContactList.get(i);
            String character = (name.charAt(0) + "").toUpperCase(Locale.ENGLISH);
            if (!characterList.contains(character)) {
                if (character.hashCode() >= "A".hashCode() && character.hashCode() <= "Z".hashCode()) { // 是字母
                    characterList.add(character);
                }
            }
        }
        cmLetterView.initView(characterList);
        contactAdapter = new ContactAdapter(context, contactNames);
        headerAdapterWrapper1 = new HeaderAdapterWrapper1(contactAdapter, contactNames);//添加头
        headView = LayoutInflater.from(context).inflate(R.layout.cm_header, null, false);
        LinearLayout searchView = headView.findViewById(R.id.cm_lin_search);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SearchCMActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("data", (Serializable) contactNames);
                //意图放置bundle变量
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
        headView.setLayoutParams(params);
        headerAdapterWrapper1.addHeaderView(headView);

        cmRec.setLayoutManager(layoutManager);
        cmRec.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL_LIST));
        cmRec.setAdapter(headerAdapterWrapper1);
        cmLetterView.setCharacterListener(new LetterView.CharacterClickListener() {
            @Override
            public void clickCharacter(String character) {
                layoutManager.scrollToPositionWithOffset(contactAdapter.getScrollPosition(character), 0);
            }

            @Override
            public void clickArrow() {
                layoutManager.scrollToPositionWithOffset(0, 0);
            }
        });
        dismissLoad();
    }

    @OnClick(R.id.no_data)
    public void onViewClicked() {
        if (noData.getText().toString().equals(HintTool.REQUESTFAIL + ",点击刷新")) {
            getStudentList(kcbh);
        }
    }
}
