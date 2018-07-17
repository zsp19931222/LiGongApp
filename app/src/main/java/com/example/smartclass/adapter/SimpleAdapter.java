package com.example.smartclass.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.smartclass.entity.StudentListEntity;
import com.example.smartclass.util.BeanState;
import yh.app.appstart.lg.R;

import java.util.List;

/**
 * 点名详情adapter
 */
public class SimpleAdapter extends BaseQuickAdapter<StudentListEntity.ContentBean, BaseViewHolder> {


    private Context context;

    public SimpleAdapter(@Nullable List<StudentListEntity.ContentBean> data, Context context) {
        super(R.layout.item_cd, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, StudentListEntity.ContentBean item) {

        switch (item.getState()) {
            case BeanState.DM_State.WD:
                holder.setImageDrawable(R.id.item_cd_bg_image, ContextCompat.getDrawable(context, R.drawable.border_radius_db));
                holder.setText(R.id.item_cd_condition_text, "未\n到");
                holder.setText(R.id.item_cd_name_text, item.getName());
                holder.setText(R.id.item_cd_id_text, item.getXh());
                holder.setTextColor(R.id.item_cd_name_text, ContextCompat.getColor(context, R.color.tab_font));
                holder.setTextColor(R.id.item_cd_id_text, ContextCompat.getColor(context, R.color.tab_font));
                break;
            case BeanState.DM_State.DQ:
                holder.setImageDrawable(R.id.item_cd_bg_image, ContextCompat.getDrawable(context, R.drawable.border_radius_55b2f5));
                holder.setText(R.id.item_cd_condition_text, "到\n勤");
                holder.setText(R.id.item_cd_name_text, item.getName());
                holder.setText(R.id.item_cd_id_text, item.getXh());
                holder.setTextColor(R.id.item_cd_name_text, ContextCompat.getColor(context, R.color.color_somber));
                holder.setTextColor(R.id.item_cd_id_text, ContextCompat.getColor(context, R.color.color_gray_999999));
                break;
            case BeanState.DM_State.QJ:
                holder.setImageDrawable(R.id.item_cd_bg_image, ContextCompat.getDrawable(context, R.drawable.border_radius_4ee5c3));
                holder.setText(R.id.item_cd_condition_text, "请\n假");
                holder.setText(R.id.item_cd_name_text, item.getName());
                holder.setText(R.id.item_cd_id_text, item.getXh());
                holder.setTextColor(R.id.item_cd_name_text, ContextCompat.getColor(context, R.color.color_somber));
                holder.setTextColor(R.id.item_cd_id_text, ContextCompat.getColor(context, R.color.color_gray_999999));
                break;
            case BeanState.DM_State.CD:
                holder.setImageDrawable(R.id.item_cd_bg_image, ContextCompat.getDrawable(context, R.drawable.border_radius_ffd763));
                holder.setText(R.id.item_cd_condition_text, "迟\n到");
                holder.setText(R.id.item_cd_name_text, item.getName());
                holder.setText(R.id.item_cd_id_text, item.getXh());
                holder.setTextColor(R.id.item_cd_name_text, ContextCompat.getColor(context, R.color.color_somber));
                holder.setTextColor(R.id.item_cd_id_text, ContextCompat.getColor(context, R.color.color_gray_999999));
                break;
            case BeanState.DM_State.ZT:
                holder.setImageDrawable(R.id.item_cd_bg_image, ContextCompat.getDrawable(context, R.drawable.border_radius_fe9860));
                holder.setText(R.id.item_cd_condition_text, "早\n退");
                holder.setText(R.id.item_cd_name_text, item.getName());
                holder.setText(R.id.item_cd_id_text, item.getXh());
                holder.setTextColor(R.id.item_cd_name_text, ContextCompat.getColor(context, R.color.color_somber));
                holder.setTextColor(R.id.item_cd_id_text, ContextCompat.getColor(context, R.color.color_gray_999999));
                break;
            case BeanState.DM_State.KK:
                holder.setImageDrawable(R.id.item_cd_bg_image, ContextCompat.getDrawable(context, R.drawable.border_radius_ff8e8e));
                holder.setText(R.id.item_cd_condition_text, "旷\n课");
                holder.setText(R.id.item_cd_name_text, item.getName());
                holder.setText(R.id.item_cd_id_text, item.getXh());
                holder.setTextColor(R.id.item_cd_name_text, ContextCompat.getColor(context, R.color.color_somber));
                holder.setTextColor(R.id.item_cd_id_text, ContextCompat.getColor(context, R.color.color_gray_999999));
                break;
        }
    }
}
