package com.example.app3.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.app3.FeedBackActivity;
import com.example.app3.activity.FriendDetailActivity;
import com.example.app3.entity.Contact;
import com.example.app3.tool.ContactComparator;
import com.example.app3.tool.GlideRoundTransform;
import com.example.app3.tool.Tool;
import com.example.app3.tool.Utils;
import com.example.app3.utils.GlideLoadUtils;
import com.yhkj.cqgyxy.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import yh.app.function.liaotianjiemian;
import yh.app.tool.SqliteHelper;
import yh.app.utils.ToastUtils;


/**
 * Created by Administrator on 2017/10/9.
 */

public class ContactAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<Map<String, String>> mContactNames;//联系人名称字符串和头像
    private List<String> mContactList; // 联系人名称List（转换成拼音）
    private List<Contact> resultList; // 最终结果（包含分组的字母）
    private List<String> characterList; // 字母List

    public enum ITEM_TYPE {
        HEADER,
        NORMAL,
        ITEM_TYPE_CHARACTER,
        ITEM_TYPE_CONTACT,
    }

    public ContactAdapter(Context context, List<Map<String, String>> contactNames) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mContactNames = contactNames;

        handleContact(true);
    }

    public void handleContact(boolean first) {
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
        Map<String, String> fqrMap = new HashMap<>();

        for (int i = 0; i < mContactNames.size(); i++) {
            if (mContactNames.get(i).get("name") != null) {
                String pinyin = Utils.getPingYin(mContactNames.get(i).get("name"));
                map.put(pinyin, mContactNames.get(i).get("name"));
                fqrMap.put(pinyin, mContactNames.get(i).get("FRIEND_ID"));
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
                    resultList.add(new Contact(character, ITEM_TYPE.ITEM_TYPE_CHARACTER.ordinal(), "", ""));
                } else {
                    if (!characterList.contains("#")) {
                        characterList.add("#");
                        resultList.add(new Contact("#", ITEM_TYPE.ITEM_TYPE_CHARACTER.ordinal(), "", ""));
                    }
                }
            }
            resultList.add(new Contact(map.get(name), ITEM_TYPE.ITEM_TYPE_CONTACT.ordinal(), "", fqrMap.get(name)));
        }
        if (!first) {
            notifyDataSetChanged();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE.ITEM_TYPE_CHARACTER.ordinal()) {
            return new CharacterHolder(mLayoutInflater.inflate(R.layout.item_character, parent, false));
        } else {
            return new ContactHolder(mLayoutInflater.inflate(R.layout.item_contact, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof CharacterHolder) {
            ((CharacterHolder) holder).mTextView.setText(resultList.get(position).getmName());
        } else if (holder instanceof ContactHolder) {
            ((ContactHolder) holder).mTextView.setText(resultList.get(position).getmName());
            GlideLoadUtils.getInstance().glideLoadBorderRadius(mContext,resultList.get(position).getmUrl(),((ContactHolder) holder).imageView,R.drawable.np,5);
            ((ContactHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!Tool.isFastDoubleClick()) {
                        Intent intent = new Intent(mContext, liaotianjiemian.class);
                        try {
                            intent.putExtra("friend_id", new SqliteHelper().rawQuery("select * from friend where name=?", resultList.get(position).getmName()).get(0).get("FRIEND_ID"));
                            intent.putExtra("hyName",new SqliteHelper().rawQuery("select * from friend where name=?", resultList.get(position).getmName()).get(0).get("name"));

                        }catch (Exception e){
                            intent.putExtra("friend_id", resultList.get(position).getFqr());
                            intent.putExtra("hyName",resultList.get(position).getmName());
                        }
                        mContext.startActivity(intent);
                    }
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        return resultList.get(position).getmType();
    }

    @Override
    public int getItemCount() {
        return resultList == null ? 0 : resultList.size();
    }

    public class CharacterHolder extends RecyclerView.ViewHolder {
        TextView mTextView;

        CharacterHolder(View view) {
            super(view);

            mTextView = (TextView) view.findViewById(R.id.character);
        }
    }

    public class ContactHolder extends RecyclerView.ViewHolder {
        TextView mTextView;
        ImageView imageView;

        ContactHolder(View view) {
            super(view);

            mTextView = (TextView) view.findViewById(R.id.contact_name);
            imageView = (ImageView) view.findViewById(R.id.contact_image1);
        }
    }


    public int getScrollPosition(String character) {
        if (characterList.contains(character)) {
            for (int i = 0; i < resultList.size(); i++) {
                if (resultList.get(i).getmName().equals(character)) {
                    return i;
                }
            }
        }

        return -1; // -1不会滑动
    }
}