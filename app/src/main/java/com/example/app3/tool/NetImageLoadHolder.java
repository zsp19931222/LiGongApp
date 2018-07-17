package com.example.app3.tool;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.example.app3.entity.BannerBean;
import com.example.app3.utils.GlideLoadUtils;
import com.scwang.smartrefresh.layout.util.DensityUtil;
import com.yunhuakeji.app.utils.IsNull;

import yh.app.appstart.lg.R;

import yh.app.model.DAModel;

/**
 * Created by Administrator on 2017/10/17.
 */

public class NetImageLoadHolder implements Holder<DAModel.ContentBean> {
    private ImageView image_lv;

    private TextView textView;

    //可以是一个布局也可以是一个Imageview
    @Override
    public RelativeLayout createView(Context context) {
        RelativeLayout relativeLayout = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.banner_layout,null);
        image_lv=relativeLayout.findViewById(R.id.banner_image);
        textView=relativeLayout.findViewById(R.id.banner_text);
        return relativeLayout;

    }

    @Override
    public void UpdateUI(Context context, int position, DAModel.ContentBean data) {
        if (IsNull.isNotNull(data.getTitle())) {
            textView.setText(data.getTitle());
        } else {
            textView.setVisibility(View.GONE);
        }
        GlideLoadUtils.getInstance().glideLoad(context, data.getImg(), image_lv, R.drawable.xxhome1);
    }
}
