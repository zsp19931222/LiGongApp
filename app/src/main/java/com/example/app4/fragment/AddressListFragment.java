package com.example.app4.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.app3.activity.BrowserActivity;
import com.example.app3.activity.SearchFriendActivity;
import com.example.app3.adapter.ContactAdapter;
import com.example.app3.adapter.dragrecyclear.common.DividerItemDecoration;
import com.example.app3.base_recyclear_adapter.HeaderAdapterWrapper1;
import com.example.app3.base_recyclear_adapter.QuickAdapter;
import com.example.app3.entity.LayoutEntity;
import com.example.app3.tool.ContactComparator;
import com.example.app3.tool.MyOnClickListener;
import com.example.app3.tool.Utils;
import com.example.app3.view.LetterView;
import com.example.app4.base.BaseFragment;
import com.example.smartclass.eventbus.MessageEvent;
import com.yhkj.cqgyxy.R;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import yh.app.tool.SqliteHelper;
import yh.app.utils.FileUtils;
import yh.app.utils.GsonImpl;
import 云华.智慧校园.工具.RHelper;
import 云华.智慧校园.工具._链接地址导航;

/**
 * Created by Administrator on 2018/4/21 0021.
 * <p>
 * 消息列表fragment
 */

public class AddressListFragment extends BaseFragment {

    Unbinder unbinder;
    @BindView(R.id.home_listview_message_list)
    RecyclerView homeListviewMessageList;
    @BindView(R.id.letter_view)
    LetterView letterView;

    private Context context;

    private LinearLayoutManager layoutManager;
    private ContactAdapter contactAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_addresslist;
    }

    @Override
    protected void initView() {
        context = getContext();
        showRecyclerView();
    }

    @Override
    @Subscribe
    public void onEventMainThread(MessageEvent event) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        if (rootView != null) {
            unbinder = ButterKnife.bind(this, rootView);
        }
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void showRecyclerView() {
        List<Map<String, String>> contactNames = new ArrayList<>();
        layoutManager = new LinearLayoutManager(context);
        contactNames.addAll(new SqliteHelper().rawQuery("select * from friend"));

        List<String> mContactList = new ArrayList<>();
        final Map<String, String> map = new HashMap<>();
        for (int i = 0; i < contactNames.size(); i++) {
            if (contactNames.get(i).get("name") != null) {
                String pinyin = Utils.getPingYin(contactNames.get(i).get("name"));
                map.put(pinyin, contactNames.get(i).get("name"));
                mContactList.add(pinyin);
            }
        }
        Collections.sort(mContactList, new ContactComparator());
        List<String> characterList = new ArrayList<>();
        for (int i = 0; i < mContactList.size(); i++) {
            String name = mContactList.get(i);
            String character = (name.charAt(0) + "").toUpperCase(Locale.ENGLISH);
            if (!characterList.contains(character)) {
                if (character.hashCode() >= "A".hashCode() && character.hashCode() <= "Z".hashCode()) { // 是字母
                    characterList.add(character);
                }
            }
        }

        letterView.initView(characterList);

        contactAdapter = new ContactAdapter(context, contactNames);

        HeaderAdapterWrapper1 headerAdapterWrapper1 = new HeaderAdapterWrapper1(contactAdapter, contactNames);
        @SuppressLint("InflateParams") View headView = LayoutInflater.from(context).inflate(R.layout.head_address_list, null, false);
        LinearLayout searchView = headView.findViewById(R.id.head_address_lin_search);
        RecyclerView headRecyclerView = headView.findViewById(R.id.head_address_rec);
        LayoutEntity entity = GsonImpl.get().toObject(FileUtils.readJsonFile(context, "txl"), LayoutEntity.class);
        headRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        headRecyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL_LIST));
        QuickAdapter TXLAdapter = new QuickAdapter<LayoutEntity.AllTagsListBean>(entity.getAllTagsList()) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_contact;
            }

            @Override
            public void convert(VH holder, LayoutEntity.AllTagsListBean data, final int position) {
                holder.setTextView(R.id.contact_name, data.getTxt());

                if (data.getTxt().equals("新的朋友")) {
                    if (new SqliteHelper().rawQuery("select * from hyqqNum where isread=?", "false").size() != 0) {
                        holder.setTextView(R.id.contact_num, new SqliteHelper().rawQuery("select * from hyqqNum where isread=?", "false").size() + "").setVisibility(View.VISIBLE);
                    }
                }
                holder.setImageView((Activity) context, R.id.contact_image1, new RHelper().getDrawable(context, data.getPic_default()));
                if (!data.getCls().equals("com.example.app3.activity.BrowserActivity")) {
                    holder.itemView.setOnClickListener(new MyOnClickListener(data.getCls(), context));
                } else {
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(context, BrowserActivity.class);
                            intent.putExtra("title", "办公电话");
                            intent.putExtra("url", _链接地址导航.UIA.办公电话.getUrl());
                            context.startActivity(intent);
                        }
                    });
                }

            }
        };
        headRecyclerView.setAdapter(TXLAdapter);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
        headView.setLayoutParams(params);
        headerAdapterWrapper1.addHeaderView(headView);

        homeListviewMessageList.setLayoutManager(layoutManager);
        homeListviewMessageList.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL_LIST));
        homeListviewMessageList.setAdapter(headerAdapterWrapper1);

        letterView.setCharacterListener(new LetterView.CharacterClickListener() {
            @Override
            public void clickCharacter(String character) {
                layoutManager.scrollToPositionWithOffset(contactAdapter.getScrollPosition(character), 0);
            }

            @Override
            public void clickArrow() {
                layoutManager.scrollToPositionWithOffset(0, 0);
            }
        });

        //跳转搜索
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SearchFriendActivity.class);
                context.startActivity(intent);
            }
        });
    }
}
