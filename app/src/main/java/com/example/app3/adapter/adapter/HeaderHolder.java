package com.example.app3.adapter.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import yh.app.appstart.lg.R;


/**
 * Created by lyd10892 on 2016/8/23.
 */

public class HeaderHolder extends RecyclerView.ViewHolder {
    public TextView titleView;
    public HeaderHolder(View itemView) {
        super(itemView);
        initView();
    }

    private void initView() {
        titleView = (TextView) itemView.findViewById(R.id.item_more_text);
    }
}
