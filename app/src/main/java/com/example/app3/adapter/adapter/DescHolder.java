package com.example.app3.adapter.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yhkj.cqgyxy.R;


/**
 * Created by lyd10892 on 2016/8/23.
 */

public class DescHolder extends RecyclerView.ViewHolder {
    public TextView descView;
    public ImageView descImage;
    public ImageView cutImage;
    public LinearLayout layout;

    public TextView descView1;
    public ImageView descImage1;
    public ImageView cutImage1;
    public RelativeLayout addRel;
    public LinearLayout layout1;

    public DescHolder(View itemView) {
        super(itemView);
        initView();
    }

    private void initView() {
        descView1 = (TextView) itemView.findViewById(R.id.compile_item_desc_text);
        descImage1 = (ImageView) itemView.findViewById(R.id.compile_item_desc_image);
        cutImage1 = (ImageView) itemView.findViewById(R.id.compile_item_desc_role);
        addRel = (RelativeLayout) itemView.findViewById(R.id.compile_item_desc_role_rel);
        layout1 = (LinearLayout) itemView.findViewById(R.id.compile_item_desc_lin);

        descView = (TextView) itemView.findViewById(R.id.more_item_desc_text);
        descImage = (ImageView) itemView.findViewById(R.id.more_item_desc_image);
        cutImage = (ImageView) itemView.findViewById(R.id.more_item_desc_image_cutLine);
        layout = (LinearLayout) itemView.findViewById(R.id.more_item_desc_lin);

    }
}
