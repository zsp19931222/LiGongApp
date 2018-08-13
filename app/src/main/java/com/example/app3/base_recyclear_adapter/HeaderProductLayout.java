package com.example.app3.base_recyclear_adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yhkj.cqgyxy.R;

/**
 * Created by wxy on 2017/4/27.
 */

public class HeaderProductLayout extends LinearLayout {
    LinearLayout mImageView;

    public HeaderProductLayout(Context context) {
        super(context);
        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.cd_header_layout, this);
        mImageView = view.findViewById(R.id.cd_people_detail_lin);
    }

    //量取view此时Y轴的距离
    public int getDistanceY() {
        int[] location = new int[2];
        mImageView.getLocationOnScreen(location);
        int y = location[1];
        return y;
    }

}
