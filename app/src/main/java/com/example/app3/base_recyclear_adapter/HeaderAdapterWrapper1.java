package com.example.app3.base_recyclear_adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.app3.adapter.ContactAdapter;
import com.example.app3.entity.Contact;
import com.example.app3.tool.ContactComparator;
import com.example.app3.tool.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * RecyclerView 头尾item
 */
public class HeaderAdapterWrapper1 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    enum ITEM_TYPE {
        HEADER,
        NORMAL,
        ITEM_TYPE_CHARACTER,
        ITEM_TYPE_CONTACT,
    }

    private ContactAdapter mAdapter;
    private View mHeaderView;
    private List<Map<String, String>> mContactNames;//联系人名称字符串和头像
    private List<String> mContactList; // 联系人名称List（转换成拼音）
    private List<Contact> resultList; // 最终结果（包含分组的字母）
    private List<String> characterList; // 字母List

    private void handleContact(boolean first) {
        if (first) {
            mContactList = new ArrayList<>();
            resultList = new ArrayList<>();
            characterList = new ArrayList<>();
        } else {
            mContactList.clear();
            resultList.clear();
            characterList.clear();
        }
        Map<String, String> map = new HashMap<>();

        for (int i = 0; i < mContactNames.size(); i++) {
            if (mContactNames.get(i).get("name") != null) {
                String pinyin = Utils.getPingYin(mContactNames.get(i).get("name"));
                map.put(pinyin, mContactNames.get(i).get("name"));
                mContactList.add(pinyin);
            }
        }
        Collections.sort(mContactList, new ContactComparator());


        for (int i = 0; i < mContactList.size(); i++) {
            String name = mContactList.get(i);
            String character = (name.charAt(0) + "").toUpperCase(Locale.ENGLISH);
            if (!characterList.contains(character)) {
                if (character.hashCode() >= "A".hashCode() && character.hashCode() <= "Z".hashCode()) { // 是字母
                    characterList.add(character);
                    resultList.add(new Contact(character, ContactAdapter.ITEM_TYPE.ITEM_TYPE_CHARACTER.ordinal(), "", ""));
                } else {
                    if (!characterList.contains("#")) {
                        characterList.add("#");
                        resultList.add(new Contact("#", ContactAdapter.ITEM_TYPE.ITEM_TYPE_CHARACTER.ordinal(), "", ""));
                    }
                }
            }
            resultList.add(new Contact(map.get(name), ContactAdapter.ITEM_TYPE.ITEM_TYPE_CONTACT.ordinal(), mContactNames.get(i).get("headimg"), mContactNames.get(i).get("FRIEND_ID")));
        }
        if (!first) {
            mAdapter.handleContact(first);
            notifyDataSetChanged();
        }
    }

    public HeaderAdapterWrapper1(ContactAdapter adapter, List<Map<String, String>> contactNames) {
        mAdapter = adapter;
        this.mContactNames = contactNames;
        handleContact(true);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return ITEM_TYPE.HEADER.ordinal();
        } else {
            try {
                return resultList.get(position - 1).getmType();
            } catch (Exception e) {
                return ITEM_TYPE.NORMAL.ordinal();
            }
        }
    }

    @Override
    public int getItemCount() {
        return mAdapter.getItemCount() + 1;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == 0) {
            return;
        } else {
            mAdapter.onBindViewHolder(holder, position - 1);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE.HEADER.ordinal()) {
            return new RecyclerView.ViewHolder(mHeaderView) {
            };
        } else {
            return mAdapter.onCreateViewHolder(parent, viewType);
        }
    }

    public void addHeaderView(View view) {
        this.mHeaderView = view;
    }


    public void ref() {
        handleContact(false);
    }
}