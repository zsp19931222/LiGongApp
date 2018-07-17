package com.example.app3.adapter.dragrecyclear.common;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.app3.adapter.adapter.DescHolder;
import com.example.app3.adapter.adapter.HeaderHolder;
import com.example.app3.adapter.adapter.SectionedRecyclerViewAdapter;
import com.example.app3.adapter.dragrecyclear.helper.MyItemTouchCallback;
import com.example.app3.utils.GlideLoadUtils;
import com.example.entity.MoreEntity;
import yh.app.appstart.lg.R;

import java.util.Collections;
import java.util.List;

import yh.app.utils.CompileUtils;


/**
 * Created by lyd10892 on 2016/8/23.
 */

public class EntityAdapter extends SectionedRecyclerViewAdapter<HeaderHolder, DescHolder, RecyclerView.ViewHolder> implements MyItemTouchCallback.ItemTouchAdapter {


    public MoreEntity allTagList;
    private Context mContext;
    private LayoutInflater mInflater;
    private List<MoreEntity.AllTagsListBean.TagInfoListBean> tagInfoListBeen;


    public void setOnItemClickLiniser(EntityAdapter.onItemClickLiniser onItemClickLiniser) {
        this.onItemClickLiniser = onItemClickLiniser;
    }

    static onItemClickLiniser onItemClickLiniser;

    public interface onItemClickLiniser {
        void onClick(DescHolder holder, int section, int position);
    }

    private SparseBooleanArray mBooleanMap;

    public EntityAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mBooleanMap = new SparseBooleanArray();
    }

    public void setData(MoreEntity allTagList) {
        this.allTagList = allTagList;
        tagInfoListBeen = allTagList.getAllTagsList().get(0).getTagInfoList();
        notifyDataSetChanged();
    }

    @Override
    protected int getSectionCount() {
        return CompileUtils.isEmpty(allTagList.getAllTagsList()) ? 0 : allTagList.getAllTagsList().size();
    }

    @Override
    protected int getItemCountForSection(int section) {
        int count = allTagList.getAllTagsList().get(section).getTagInfoList().size();
//        if (count >= 8 && !mBooleanMap.get(section)) {
//            count = 8;
//        }

//        while (count % 4 != 0) {
//            count++;
//        }
        return count;
    }

    //是否有footer布局
    @Override
    protected boolean hasFooterInSection(int section) {
        return false;
    }

    @Override
    protected HeaderHolder onCreateSectionHeaderViewHolder(ViewGroup parent, int viewType) {
        return new HeaderHolder(mInflater.inflate(R.layout.item_more_title, parent, false));
    }


    @Override
    protected RecyclerView.ViewHolder onCreateSectionFooterViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    protected DescHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        return new DescHolder(mInflater.inflate(R.layout.item_more_desc, parent, false));
    }


    @Override
    protected void onBindSectionHeaderViewHolder(final HeaderHolder holder, final int section) {
//        holder.openView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                boolean isOpen = mBooleanMap.get(section);
//                String text = isOpen ? "展开" : "关闭";
//                mBooleanMap.put(section, !isOpen);
//                holder.openView.setText(text);
//                notifyDataSetChanged();
//            }
//        });

        holder.titleView.setText(allTagList.getAllTagsList().get(section).getTagsName());
//        holder.openView.setText(mBooleanMap.get(section) ? "关闭" : "展开");

    }


    @Override
    protected void onBindSectionFooterViewHolder(RecyclerView.ViewHolder holder, int section) {

    }

    @Override
    protected void onBindItemViewHolder(DescHolder holder, final int section, final int position) {
        EntityAdapter.onItemClickLiniser.onClick(holder, section, position);
        try {
            if (section==0){
                Log.d("zsp",allTagList.getAllTagsList().get(section).getTagInfoList().get(position).getTagName());
            }
            holder.descView.setText(allTagList.getAllTagsList().get(section).getTagInfoList().get(position).getTagName());
            GlideLoadUtils.getInstance().glideLoad(mContext, allTagList.getAllTagsList().get(section).getTagInfoList().get(position).getLatlon(),holder.descImage,R.drawable.ico_load_little);

        } catch (Exception e) {
            holder.cutImage.setVisibility(View.INVISIBLE);
            holder.descView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onMove(int fromPosition, int toPosition) {
        Log.d("zsp", "fromPosition=" + fromPosition + "    toPosition=" + toPosition + "     长度=" + tagInfoListBeen.size());
        try {
            if (fromPosition < toPosition) {
                for (int i = fromPosition; i < toPosition; i++) {
                    Collections.swap(tagInfoListBeen, i - 1, i);
                }
            } else {
                for (int i = fromPosition; i > toPosition; i--) {
                    Collections.swap(tagInfoListBeen, i - 1, i - 2);//方法被用于交换在指定列表中的指定位置的元素。
                }
            }
            notifyItemMoved(fromPosition, toPosition);
        } catch (Exception e) {
            Log.d("zsp", e + "fromPosition=" + fromPosition + "     toPosition=" + toPosition);
        }

    }

    @Override
    public void onSwiped(int position) {
        try {
            tagInfoListBeen.remove(position);
            notifyItemRemoved(position);
        } catch (Exception e) {
        }
    }


}
